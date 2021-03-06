package se.demo.applianceservice.repository.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileReader {

    public String readFileContent(String filename) {

        try {
            Path path = Path.of("src/main/resources/sql", filename);
            return Files.readString(path, StandardCharsets.US_ASCII);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
