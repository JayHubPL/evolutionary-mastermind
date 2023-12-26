package pl.edu.pw.ee.gui.utils;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import pl.edu.pw.ee.utils.FileReader;

import java.util.List;

public class MarkdownToHTMLConverter {

    private static final String cssResourceName = "css/style.css";
    private static final Parser parser;
    private static final HtmlRenderer renderer;
    private static final String styling;

    static {
        var options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, List.of(TablesExtension.create()));
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
        styling = String.format("<style>%s</style>", FileReader.readResourceFile(cssResourceName));
    }

    public static String convert(String markdown) {
        return styling + renderer.render(parser.parse(markdown));
    }

}
