import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CodeLinesCounter codeLinesCounter = new CodeLinesCounter();
        CodeLinesPrinter codeLinesPrinter = new CodeLinesPrinter(codeLinesCounter, "C:\\Users\\Admin\\Desktop\\counting-code-lines\\src");
        codeLinesPrinter.printLines();
    }
}
