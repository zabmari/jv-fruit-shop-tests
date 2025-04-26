package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderServiceImplTest {

    private final FileReaderServiceImpl fileReaderService =
            new FileReaderServiceImpl();

    @TempDir
    private Path tempDir;

    @Test
    void readFile_singleLine_shouldReadCorrectly() throws IOException {
        Path input = tempDir.resolve("input.csv");
        String data = "test data";
        Files.writeString(input, data, StandardCharsets.UTF_8);
        List<String> file = fileReaderService.readFromFile(input.toString());
        assertEquals(List.of(data), file,
                "The file should contain the written line.");
    }

    @Test
    void readFile_multipleLines_shouldReadCorrectly() throws IOException {
        Path input = tempDir.resolve("input.csv");
        List<String> data = List.of("First line", "Second line", "Third line");
        Files.write(input, data, StandardCharsets.UTF_8);
        List<String> file = fileReaderService.readFromFile(input.toString());
        assertEquals(data, file,
                "The file should contain all written lines.");
    }

    @Test
    void readFile_fileDoesNotExist_shouldThrowException() {
        String nonExistFile = tempDir.resolve("input.csv").toString();
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(nonExistFile),
                "Reading a non-existent file should throw a RuntimeException.");

    }

    @Test
    void readFile_emptyFile_shouldReturnEmptyList() throws IOException {
        Path input = tempDir.resolve("empty.csv");
        Files.createFile(input);

        List<String> file = fileReaderService.readFromFile(input.toString());

        assertTrue(file.isEmpty(), "Reading an empty file should return an empty list.");
    }

}
