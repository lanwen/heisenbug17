package ru.lanwen.heisenbug.codewrite;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.AbstractTemplateLoader;
import com.github.jknack.handlebars.io.StringTemplateSource;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import org.apache.commons.io.IOUtils;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class HandlebarsCodeWriter extends CodeWriter {

    public HandlebarsCodeWriter(Map<String, String> consts) {
        super(consts);
    }

    @Override
    public void writeTo(Filer filer) {
        try {
            String pkg = "ru.lanwen.heisenbug.consts";
            String className = "HandlebarsTestMethodConsts";

            JavaFileObject jfo = filer.createSourceFile(pkg + "." + className);

            Template template = handlebars().compile("consts");

            try (Writer writer = jfo.openWriter()) {
                template.apply(
                        Context.newContext(new Object())
                                .data("package", pkg)
                                .data("class", className)
                                .data("consts", consts.entrySet()),
                        writer
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Handlebars handlebars() {
        TemplateLoader loader = new AbstractTemplateLoader() {
            @Override
            public TemplateSource sourceAt(String uri) throws IOException {
                String location = resolve(normalize(uri));
                try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(location)) {
                    return new StringTemplateSource(uri, IOUtils.toString(is, StandardCharsets.UTF_8));
                }
            }
        };
        loader.setPrefix("hbs/");
        return new Handlebars(loader);
    }
}
