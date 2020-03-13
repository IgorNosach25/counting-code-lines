package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

class CodeLinesCounter {

  private boolean isInsideCode = false;
  int getCodeLinesCount(Path pathToFile) throws IOException {

    //Can be improved if necessary by reading only first letters to find out either line is comment or not
    List<String> allLines = getAllLines(pathToFile);
    int codeLinesCount = 0;

    for (String line : allLines) {
      if (isInsideCode || line.startsWith("/*")) {
        if (lineInsideCommentContainsCode(line)) {
          codeLinesCount++;
        }
      } else if (!line.startsWith("//")) {
        codeLinesCount++;
      }
      isLineInComment(line);
    }
    return codeLinesCount;
  }

  private boolean lineInsideCommentContainsCode(String line) {
    return line.matches(".*\\*/\\s*[a-zA-Z]+.*");
  }

  private void isLineInComment(String line) {
    for (int i = 0; i < line.length() - 1; i++) {
      String substring = line.substring(i, i + 2);
      if (substring.equals("/*")) isInsideCode = true;
      else if (substring.equals("*/")) isInsideCode = false;
    }
  }

  private List<String> getAllLines(Path pathToFile) throws IOException {
    return Files.lines(pathToFile)
            .filter(s -> !s.isEmpty())
            .map(String::trim)
            .collect(Collectors.toList());
  }
}
