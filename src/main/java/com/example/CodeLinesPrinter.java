package com.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    File currentFile = new File(path);
    if (!currentFile.exists())
      throw new RuntimeException("File with path [ " + path + " ] not found!");

    File[] files = currentFile.listFiles();
    List<String> paths = new ArrayList<>();

    if (files == null) {
      return countCodeLinesForSingleFile(currentFile);
    }

    int codeLinesInDirectory = 0;
    for (File file : files) {
      if (file.isDirectory()) {
        CodeLineDto codeLines = findAllCodeLines(file.getPath());
        codeLinesInDirectory += codeLines.codeLineCount;
        List<String> foundedPaths =
            codeLines.paths.stream().map(line -> " " + line).collect(Collectors.toList());
        paths.addAll(foundedPaths);
      } else {
        Path localPath = Paths.get(file.getPath());
        int codeLinesCount = codeLinesCounter.getCodeLinesCount(localPath);
        codeLinesInDirectory += codeLinesCount;
        paths.add(" " + file.getName() + " : " + codeLinesCount);
      }
    }
    paths.add(0, currentFile.getName() + " : " + codeLinesInDirectory);
    return new CodeLineDto(codeLinesInDirectory, paths);
  }

  private CodeLineDto countCodeLinesForSingleFile(File currentFile) throws IOException {
    Path localPath = Paths.get(currentFile.getPath());
    int codeLinesCount = codeLinesCounter.getCodeLinesCount(localPath);
    return new CodeLineDto(codeLinesCount, List.of(currentFile.getName() + " : " + codeLinesCount));
  }

  // it is necessary for one iteration to count all code lines and build a path's tree with spaces
  @AllArgsConstructor
  @Getter
  private static class CodeLineDto {

    private int codeLineCount;
    private List<String> paths;
  }
}
