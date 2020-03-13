package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

class CodeLinesCounter {

  //http://codekata.com/kata/kata13-counting-code-lines/ -- all requirements are described here
  private boolean isInsideComment = false;

  int getCodeLinesCount(Path pathToFile) throws IOException {
    List<String> allLines = getAllLines(pathToFile);
    int codeLinesCount = 0;

    for (String line : allLines) {
      if (isInsideComment || line.startsWith("/*")) {
        if (lineContainsCode(line)) {
          codeLinesCount++;
        }
      } else if (!line.startsWith("//")) {
        codeLinesCount++;
      }
      isLineInComment(line);
    }
    return codeLinesCount;
  }

  private boolean lineContainsCode(String line) {
    return line.matches(".*\\*/\\s*[a-zA-Z]+.*");
  }

  private void isLineInComment(String line) {
    boolean isInsideString = false;
    for (int i = 0; i < line.length() - 1; i++) {
      if (line.charAt(i) == '"') {
        isInsideString = !isInsideString;
      }
      if (!isInsideString) {
        String substring = line.substring(i, i + 2);
        if (substring.equals("/*")) isInsideComment = true;
        else if (substring.equals("*/")) isInsideComment = false;
      }
    }
  }

  private List<String> getAllLines(Path pathToFile) throws IOException {
    return Files.lines(pathToFile)
            .filter(s -> !s.isEmpty())
            .map(String::trim)
            .collect(Collectors.toList());
  }
}
