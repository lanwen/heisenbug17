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
        /*
         * Второй раз нам наши сорцы не интересны
         */
        if (roundEnv.processingOver()) {
            return false;
        }

        if (annotations.isEmpty()) {
            return false;
        }


        /*
         * Пробегаемся по интересным нам аннотациям (она одна на самом деле)
         */

        Map<String, String> consts = annotations
                .stream()
                .flatMap( // обмен аннотаций на методы
                        annotation -> roundEnv.getElementsAnnotatedWith(annotation).stream()
                )
                .collect(toMap(
                        // имя константы
                        element -> "TEST_METHOD_" + element.getSimpleName(),

                        // ее значение
                        element -> element.getEnclosingElement().asType().toString() + "#" + element.getSimpleName()
                ));


        /*-------------
         *
         * ИМПЕРАТИВНО
         *
         *------------*/
        new JavapoetCodeWriter(consts).writeTo(processingEnv.getFiler());


        /*-------------
         *
         * ДЕКЛАРАТИВНО (Через шаблонизатор)
         *
         *------------*/
        new HandlebarsCodeWriter(consts).writeTo(processingEnv.getFiler());

        return false;
    }
}
