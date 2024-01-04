package pl.edu.pw.ee.utils;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResourceUtils {

    @SneakyThrows
    public static String readResourceFile(String resourceName) {
        var uri = getResourceURL(resourceName).toURI();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(new File(uri)))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public static Icon getIcon(String resourceName, int size) {
        var icon = new ImageIcon(ResourceUtils.getResourceURL(resourceName));
        return new ImageIcon(icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    public static URL getResourceURL(String resourceName) {
        return Objects.requireNonNull(ResourceUtils.class.getClassLoader().getResource(resourceName),
                String.format("Resource with name %s not found", resourceName));
    }

}
