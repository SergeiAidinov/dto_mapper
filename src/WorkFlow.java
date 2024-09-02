import dto.payload.AbstracPayload;
import dto.header.AbstractHeader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;

public class WorkFlow<H extends AbstractHeader, P extends AbstracPayload> {

    private static WorkFlow workFlow = null;
    private final Map<Class<H>, List<Class<P>>> forest = new Forester().plantForest();
    private final List<String> data = new ArrayList<>();

    private WorkFlow() {
    }

    public static WorkFlow getInstance() {
        if (Objects.isNull(workFlow)) workFlow = new WorkFlow();
        return workFlow;
    }

    public void work() {
        fillWordList();
        System.out.println(data);

    }

    private void fillWordList() {
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
            final List<String> wordList = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                final char symbol = (char) b;
                if (b == 10) continue;
                if (symbol != ';') {
                    stringBuilder.append(symbol);
                } else {
                    data.add(stringBuilder.toString().toLowerCase());
                    stringBuilder = new StringBuilder();
                }
            }
        }
    }
}
