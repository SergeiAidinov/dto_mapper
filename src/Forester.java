import annotation.Header;
import dto.header.AbstractHeader;
import dto.payload.AbstractPayload;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;

public class Forester <H extends AbstractHeader, P extends AbstractPayload> {

    public SortedMap<Class<H>, List<Class<P>>> plantForest() {
        String directory = System.getenv().get("PWD") + File.separator + "src" + File.separator + "dto";
        List<Path> filePaths = new ArrayList<>();
        try (Stream<Path> paths = walk(Paths.get(directory))) {
            paths.filter(Files::isRegularFile).forEach(filePaths::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(filePaths);
        SortedMap<Class<H>, List<Class<P>>> headersAndBodies = new TreeMap<>(new HeaderComparator());
        ListIterator<Path> listIterator = filePaths.listIterator();
        while (listIterator.hasNext()) {
            Path path = listIterator.next();
            Class<H> klass;
            try {
                String fileName = path.toString().substring(path.toString()
                                .lastIndexOf("dto"), path.toString().lastIndexOf("."))
                        .replace(File.separator, ".");
                klass = (Class<H>) Class.forName(fileName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(klass + " is header: " + isHeader(klass));
            if (isHeader(klass) && !Modifier.isAbstract(klass.getModifiers())) {
                headersAndBodies.put(klass, new ArrayList<>());
                listIterator.remove();
            }
            if (Modifier.isAbstract(klass.getModifiers())) listIterator.remove();
        }
        System.out.println(headersAndBodies);
        while (listIterator.hasPrevious()) {
            Path path = listIterator.previous();
            Class<P> klass;
            try {
                String fileName = path.toString().substring(path.toString()
                                .lastIndexOf("dto"), path.toString().lastIndexOf("."))
                        .replace(File.separator, ".");
                klass = (Class<P>) Class.forName(fileName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(klass + " is header: " + isHeader(klass));
            if (klass.isAnnotationPresent(Header.class)) {
                Header annotation = klass.getAnnotation(Header.class);
                Class<? extends AbstractHeader> header = annotation.header();
                if (Objects.nonNull(headersAndBodies.get(header))) headersAndBodies.get(header).add(klass);
                listIterator.remove();
            }
        }
        System.out.println(headersAndBodies);
        return headersAndBodies;
    }

    private boolean isHeader(Class<?> klass) {
        Class currentClass = klass;
        while (true) {
            if (currentClass.equals(AbstractHeader.class)) return true;
            if (currentClass.getSuperclass().isInstance(Objects.class)) break;
            currentClass = currentClass.getSuperclass();
        }
        return false;
    }
}
