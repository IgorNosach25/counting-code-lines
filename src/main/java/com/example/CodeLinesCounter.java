package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

class CodeLinesCounter {

  private static final String MULTI_LINE_COMMENT_START = "/*";
  private static final String MULTI_LINE_COMMENT_END = "*/";
  private static final String SINGLE_LINE_COMMENT_START = "//";
  private static final String CHECK_IF_CODE_EXISTS_AFTER_COMMENT_REGEX = ".*\\*/\\s*[a-zA-Z]+.*";
  private boolean isInsideComment = false;

  // http://codekata.com/kata/kata13-counting-code-lines/ -- all requirements are described here
  int getCodeLinesCount(Path pathToFile) throws IOException {
    List<String> allLines = getAllLines(pathToFile);
    int codeLinesCount = 0;

    for (String line : allLines) {
      if (isInsideComment || line.startsWith(MULTI_LINE_COMMENT_START)) {
        if (lineContainsCode(line)) {
          codeLinesCount++;
        }
      } else if (!line.startsWith(SINGLE_LINE_COMMENT_START)) {
        codeLinesCount++;
      }
      checkIfLineEndIsComment(line);
    }
    return codeLinesCount;
  }

  private boolean lineContainsCode(String line) {
    return line.matches(CHECK_IF_CODE_EXISTS_AFTER_COMMENT_REGEX);
  }

  private void checkIfLineEndIsComment(String line) {
    boolean isInsideString = false;
    for (int i = 0; i < line.length() - 1; i++) {
      if (line.charAt(i) == '"') {
        isInsideString = !isInsideString;
      }
      if (!isInsideString) {
        String substring = line.substring(i, i + 2);
        if (substring.equals(MULTI_LINE_COMMENT_START)) isInsideComment = true;
        else if (substring.equals(MULTI_LINE_COMMENT_END)) isInsideComment = false;
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
