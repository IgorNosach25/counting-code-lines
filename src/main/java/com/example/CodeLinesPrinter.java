package com.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class CodeLinesPrinter {

    private CodeLinesCounter codeLinesCounter;
    private String root;

    void printLines() throws IOException {
        CodeLineDto codeLines = findAllCodeLines(root);
        codeLines.paths.forEach(System.out::println);
    }

    private CodeLineDto findAllCodeLines(String path) throws IOException {
        if (path.isEmpty()) return new CodeLineDto(0, Collections.singletonList(""));

        File currentFile = new File(path);
        if (!currentFile.exists()) throw new RuntimeException("File with path [ " + path + " ] not found!");

        File[] files = currentFile.listFiles();
        List<String> paths = new ArrayList<>();

        if (files == null) {
            Path localPath = Paths.get(currentFile.getPath());
            int codeLinesCount = codeLinesCounter.getCodeLinesCount(localPath);
            paths.add(currentFile.getName() + " : " + codeLinesCount);
            return new CodeLineDto(codeLinesCount, paths);
        }

        int codeLinesInDirectory = 0;

        for (File file : files) {
            if (file.isDirectory()) {
                CodeLineDto codeLines = findAllCodeLines(file.getPath());
                codeLinesInDirectory += codeLines.codeLineCount;

                List<String> loc = codeLines
                        .paths
                        .stream()
                        .map(line -> " " + line)
                        .collect(Collectors.toList());
                loc.add(0, file.getName() + " : " + codeLines.codeLineCount);
                paths.addAll(loc);
            } else {
                Path localPath = Paths.get(file.getPath());
                int codeLinesCount = codeLinesCounter.getCodeLinesCount(localPath);
                codeLinesInDirectory += codeLinesCount;
                paths.add(file.getName() + " : " + codeLinesCount);
            }
        }
        return new CodeLineDto(codeLinesInDirectory, paths);
    }

    //it is necessary for one iteration to count all code lines and build a path's tree with spaces
    @AllArgsConstructor
    @Getter
    private static class CodeLineDto {

        private int codeLineCount;
        private List<String> paths;
    }
}
