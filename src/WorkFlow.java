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

public class WorkFlow {

    private static WorkFlow workFlow = null;

    private WorkFlow() {
    }

    public static WorkFlow getInstance() {
        if (Objects.isNull(workFlow)) workFlow = new WorkFlow();
        return workFlow;
    }

    public void createTree(){
        String directory = System.getenv().get("PWD") + File.separator + "src" + File.separator + "dto";
        Set<Path> files = new HashSet<>();
        try(Stream<Path> paths = walk(Paths.get(directory))) {
            paths.filter(Files::isRegularFile).forEach(files::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(files);
        Map<? extends AbstractHeader, ? extends AbstractDto> headersAndBodies = new HashMap<>();
        Set<Class<? extends AbstractHeader>> headers = new HashSet<>();
        for (Path path : files) {
            try {
               String fileName = path.toString().substring(path.toString()
                       .lastIndexOf("dto"), path.toString().lastIndexOf("."))
                       .replace(File.separator, ".");
               Class<?> klass = Class.forName(fileName);
                System.out.println(klass + " is header: " + isHeader(klass));
               if (isHeader(klass) && !Modifier.isAbstract(klass.getModifiers())) {
                   headers.add((Class<? extends AbstractHeader>) klass);
               }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(headers);

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
