import annotation.Header;
import dto.AbstractDto;
import dto.header.AbstractHeader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;

public class WorkFlow <T extends AbstractDto> {

    private static WorkFlow workFlow = null;

    private WorkFlow() {
    }

    public static WorkFlow getInstance() {
        if (Objects.isNull(workFlow)) workFlow = new WorkFlow();
        return workFlow;
    }

    public void createTree() {
        String directory = System.getenv().get("PWD") + File.separator + "src" + File.separator + "dto";
        List<Path> files = new ArrayList<>();
        try (Stream<Path> paths = walk(Paths.get(directory))) {
            paths.filter(Files::isRegularFile).forEach(files::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(files);
        Map<Class<? extends AbstractHeader>, List<T>> headersAndBodies = new HashMap<>();
        //Set<Class<? extends AbstractHeader>> headers = new HashSet<>();

        ListIterator<Path> listIterator = files.listIterator();
        while (listIterator.hasNext()) {
            Path path = listIterator.next();
            Class<?> klass;
            try {
                String fileName = path.toString().substring(path.toString()
                                .lastIndexOf("dto"), path.toString().lastIndexOf("."))
                        .replace(File.separator, ".");
                klass = Class.forName(fileName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(klass + " is header: " + isHeader(klass));
            if (isHeader(klass) && !Modifier.isAbstract(klass.getModifiers())) {
                headersAndBodies.put((Class<? extends AbstractHeader>) klass, new ArrayList<>());
                listIterator.remove();
            }
            if (Modifier.isAbstract(klass.getModifiers())) listIterator.remove();
        }
        System.out.println(headersAndBodies);
        while (listIterator.hasPrevious()) {
            Path path = listIterator.previous();
            Class<?> klass;
            try {
                String fileName = path.toString().substring(path.toString()
                                .lastIndexOf("dto"), path.toString().lastIndexOf("."))
                        .replace(File.separator, ".");
                klass = Class.forName(fileName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(klass + " is header: " + isHeader(klass));
            if (klass.isAnnotationPresent(Header.class)) {
                Header annotation = klass.getAnnotation(Header.class);
                Class<? extends AbstractHeader> header = annotation.header();
                T instance;
                try {
                    instance = (T) klass.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                headersAndBodies.get(header).add(instance);
                listIterator.remove();
            }
        }
        System.out.println(headersAndBodies);
    }

    private boolean isHeader(Class klass) {
        Class currentClass = klass;
        while (true) {
            if (currentClass.equals(AbstractHeader.class)) return true;
            if (currentClass.getSuperclass().isInstance(Objects.class)) break;
            currentClass = currentClass.getSuperclass();
        }
        return false;
    }

}
