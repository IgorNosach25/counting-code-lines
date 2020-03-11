package com.example;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class CodeLinesCounterTest {
    private CodeLinesCounter codeLinesCounter;

    @Before
    public void setUp() {
        codeLinesCounter = new CodeLinesCounter();
    }

    @Test(expected = IOException.class)
    public void shouldThrowIOExceptionIfPathIsIncorrect() throws IOException {
        Path path = Paths.get("src/test/java/resources/NotExists.java");
        codeLinesCounter.getCodeLinesCount(path);
    }

    @Test
    public void shouldNotCountSingleRowComment() throws IOException {
        Path path = Paths.get("src/test/java/resources/SingleLineComment.java");
        int codeLinesCount = codeLinesCounter.getCodeLinesCount(path);
        assertEquals(4, codeLinesCount);
    }

    @Test
    public void shouldNotCountMultiRowComment() throws IOException {
        Path path = Paths.get("src/test/java/resources/MultiRowComment.java");
        int codeLinesCount = codeLinesCounter.getCodeLinesCount(path);
        assertEquals(3, codeLinesCount);
    }

    @Test
    public void shouldCountCodeWithoutComments() throws IOException {
        Path path = Paths.get("src/test/java/resources/WithoutComment.java");
        int codeLinesCount = codeLinesCounter.getCodeLinesCount(path);
        assertEquals(5, codeLinesCount);
    }
}
