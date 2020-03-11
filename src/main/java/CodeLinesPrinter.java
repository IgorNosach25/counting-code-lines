import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class CodeLinesPrinter {

    private CodeLinesCounter codeLinesCounter;
    private String root;

    public CodeLinesPrinter(CodeLinesCounter codeLinesCounter, String path) {
        this.codeLinesCounter = codeLinesCounter;
        this.root = path;
    }

    void printLines() throws IOException {
        Pair<Integer, List<String>> allCodeLines = findAllCodeLines(root);
        allCodeLines.getValue().forEach(System.out::println);
    }

    private Pair<Integer, List<String>> findAllCodeLines(String path) throws IOException {
        if (path.isEmpty()) return new Pair<>(0, Collections.singletonList(""));
        File currentFile = new File(path);
        File[] files = currentFile.listFiles();
        int codeLinesInDirectory = 0;
        List<String> result = new ArrayList<>();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isDirectory()) {

                Pair<Integer, List<String>> lines = findAllCodeLines(file.getPath());
                codeLinesInDirectory += lines.getKey();

                List<String> loc = lines
                        .getValue()
                        .stream()
                        .map(line -> " " + line)
                        .collect(Collectors.toList());

                loc.add(0, file.getName() + " : " + lines.getKey());
                result.addAll(loc);
            } else {
                int codeLinesCount = codeLinesCounter.getCodeLinesCount(Paths.get(file.getPath()));
                codeLinesInDirectory += codeLinesCount;
                result.add(file.getName() + " : " + codeLinesCount);
            }
        }
        return new Pair<>(codeLinesInDirectory, result);
    }
}
