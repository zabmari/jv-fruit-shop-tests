package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterServiceImplTest {

    private final FileWriterServiceImpl fileWriterService =
            new FileWriterServiceImpl();

    @TempDir
    private Path tempDir;

    @Test
    void writeToFile_singleLine_shouldWriteCorrectly() throws IOException {
        Path outputFile = tempDir.resolve("output.csv");
        String data = "test data";
        fileWriterService.writeToFile(data, outputFile.toString());
        String file = Files.readString(outputFile, StandardCharsets.UTF_8);
        assertEquals(data, file, "The file should contain the written line.");
    }

    @Test
    void writeToFile_multipleLines_shouldWriteCorrectly() throws IOException {
        Path outputFile = tempDir.resolve("output_multi.csv");
        String data = "First line\nSecond line\nThird line";
        fileWriterService.writeToFile(data, outputFile.toString());
        String file = Files.readString(outputFile, StandardCharsets.UTF_8);
        assertEquals(data, file, "The file should contain all written lines.");
    }

    @Test
    void writeToFile_fileDoesNotExist_shouldCreateFile() throws IOException {
        Path outputFile = tempDir.resolve("output_nonexist.csv");
        String data = "test data";
        assertFalse(Files.exists(outputFile),
                "The file should not exist before writing.");
        fileWriterService.writeToFile(data, outputFile.toString());
        assertTrue(Files.exists(outputFile),
                "The file should have been created.");
        assertEquals(data, Files.readString(outputFile, StandardCharsets.UTF_8));
    }

    @Test
    void writeToFile_emptyContent_shouldWriteEmptyFile() throws IOException {
        Path outputFile = tempDir.resolve("empty.csv");
        String data = "";
        fileWriterService.writeToFile(data, outputFile.toString());
        String file = Files.readString(outputFile, StandardCharsets.UTF_8);
        assertEquals(data, file, "The file should be empty.");
    }

}
