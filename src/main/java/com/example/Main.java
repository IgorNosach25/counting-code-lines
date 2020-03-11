package com.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        if (args.length == 0) {
//            System.out.println("Please enter path!");
//            return;
//        }
        CodeLinesCounter codeLinesCounter = new CodeLinesCounter();
        CodeLinesPrinter codeLinesPrinter = new CodeLinesPrinter(codeLinesCounter, "/Users/igono/IdeaProjects/counting-code-lines/src");
        try {
            codeLinesPrinter.printLines();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
