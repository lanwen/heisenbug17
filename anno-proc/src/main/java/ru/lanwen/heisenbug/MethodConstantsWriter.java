package ru.lanwen.heisenbug;

import ru.lanwen.heisenbug.codewrite.HandlebarsCodeWriter;
import ru.lanwen.heisenbug.codewrite.JavapoetCodeWriter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

/**
 * @author lanwen (Merkushev Kirill)
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("org.junit.jupiter.api.Test")
public class MethodConstantsWriter extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }

        if (annotations.isEmpty()) {
            return false;
        }

        Map<String, String> consts = annotations
                .stream()
                .flatMap(
                        annotation -> roundEnv.getElementsAnnotatedWith(annotation).stream()
                )
                .collect(toMap(
                        element -> "TEST_METHOD_" + element.getSimpleName(),
                        element -> element.getEnclosingElement().asType().toString() + "#" + element.getSimpleName()
                ));

        new JavapoetCodeWriter(consts).writeTo(processingEnv.getFiler());
        new HandlebarsCodeWriter(consts).writeTo(processingEnv.getFiler());

        return false;
    }
}
