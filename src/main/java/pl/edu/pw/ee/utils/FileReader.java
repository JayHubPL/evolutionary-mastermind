package pl.edu.pw.ee.utils;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileReader {

    @SneakyThrows
    public static String readResourceFile(String resourceName) {
        var uri = getResourceURL(resourceName).toURI();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(new File(uri)))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public static URL getResourceURL(String resourceName) {
        return Objects.requireNonNull(FileReader.class.getClassLoader().getResource(resourceName),
                String.format("Resource with name %s not found", resourceName));
    }

}
