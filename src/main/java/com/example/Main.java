package com.example;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    if (args.length == 0 || args[0].isEmpty()) {
      System.out.println("Please enter path!");
      return;
    }
    CodeLinesCounter codeLinesCounter = new CodeLinesCounter();
    CodeLinesPrinter codeLinesPrinter = new CodeLinesPrinter(codeLinesCounter, args[0]);
    try {
      codeLinesPrinter.printLines();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
