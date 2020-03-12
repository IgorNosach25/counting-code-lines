package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

class CodeLinesCounter {

  int getCodeLinesCount(Path pathToFile) throws IOException {
    // can be improved if necessary by reading only first letters to find out either line comment or
    // not
    List<String> allLines = getAllLines(pathToFile);
    int codeLinesCount = 0;
    boolean isInsideComment = false;

    for (String line : allLines) {
      if (line.startsWith("/*")) {
        isInsideComment = true;
      } else if (line.startsWith("*/")) {
        isInsideComment = false;
      } else if (!isInsideComment && !line.startsWith("//")) {
        codeLinesCount++;
      }
    }
    return codeLinesCount;
  }

  private List<String> getAllLines(Path pathToFile) throws IOException {
    return Files.lines(pathToFile)
        .filter(s -> !s.isEmpty())
        .map(String::trim)
        .collect(Collectors.toList());
  }
}
