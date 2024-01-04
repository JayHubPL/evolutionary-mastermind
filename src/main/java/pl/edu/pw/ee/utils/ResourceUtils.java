package pl.edu.pw.ee.utils;

import com.opencsv.CSVWriter;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.List;
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

    @SneakyThrows
    public static void saveResultsToCsv(File selectedFile, List<String[]> data) {
        var fileNameWithExtension = selectedFile.getName();
        if (!fileNameWithExtension.endsWith(".csv")) {
            fileNameWithExtension += ".csv";
            selectedFile = new File(selectedFile.getParent(), fileNameWithExtension);
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(selectedFile), ',', '\0', CSVWriter.DEFAULT_ESCAPE_CHARACTER, System.lineSeparator())) {
            writer.writeAll(data);
        }
    }
}
