package se.demo.applianceservice.repository.util;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileReader {

    FileReader() {

    }
    public String readFileContent(String filename) {

        try {
            Path path = Path.of("src/main/resources/sql", filename);
            return Files.readString(path, StandardCharsets.US_ASCII);
        } catch (IOException e) {
            /* Should be handled by RestExceptionHandler */
            throw new RuntimeException(e);
        }
    }
}
