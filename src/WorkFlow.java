import dto.Entity;
import dto.header.AbstractHeader;
import dto.payload.AbstractPayload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;

public class WorkFlow<H extends AbstractHeader, P extends AbstractPayload> {

    private static WorkFlow workFlow = null;
    private final Map<Class<H>, List<Class<P>>> forest = new Forester().plantForest();

    private WorkFlow() {
    }

    public static WorkFlow getInstance() {
        if (Objects.isNull(workFlow)) workFlow = new WorkFlow();
        return workFlow;
    }

    public List<Entity<H, P>> createEntities() {
        final List<Entity<H, P>> entities = new ArrayList<>();
        for (String word : fillWordList()) {
            for (Class<H> clazz : forest.keySet()) {
                H header = createHeader(clazz, word);
                if (Objects.isNull(header)) continue;
                P payload = createPayload(header, word);
                if (Objects.nonNull(payload)) entities.add(new Entity<>(header, payload));
            }
        }
        System.out.println(entities);
        return entities;
    }

    private H createHeader(Class<H> clazz, String word) {
        H header = null;
        Method ofStringMethod = null;
        try {
            ofStringMethod = clazz.getMethod("ofString", String.class);
            header = (H) ofStringMethod.invoke(ofStringMethod, word);
        } catch (Exception e) {
            return null;
        }
        return header;
    }

    private P createPayload(H header, String word) {
        P payload = null;
        for (Class<P> klass : forest.get(header.getClass())) {
            try {
                Method ofStringMethod = klass.getMethod("ofString", String.class);
                payload = (P) ofStringMethod.invoke(ofStringMethod, word.substring(header.getHeaderLength()));
            } catch (Exception e) {
                return null;
            }
        }
        return payload;
    }

    private List<String> fillWordList() {
        List<String> stringList = new ArrayList<>();
        String directory = System.getenv().get("PWD") + File.separator + "src" + File.separator + "files";
        List<Path> filePaths = new ArrayList<>();
        try (Stream<Path> paths = walk(Paths.get(directory))) {
            paths.filter(Files::isRegularFile).forEach(filePaths::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Path path : filePaths) {
            InputStream inputStream = null;
            final byte[] bytes;
            try {
                inputStream = new FileInputStream(path.toFile());
                bytes = inputStream.readAllBytes();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (Objects.nonNull(inputStream)) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        System.exit(1);
                    }
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                final char symbol = (char) b;
                if (b == 10) continue;
                if (symbol != ';') {
                    stringBuilder.append(symbol);
                } else {
                    stringList.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
            }
        }
        return stringList;
    }
}
