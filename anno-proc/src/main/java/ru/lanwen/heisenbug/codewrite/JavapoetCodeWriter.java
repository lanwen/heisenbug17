package ru.lanwen.heisenbug.codewrite;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.util.Map;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class JavapoetCodeWriter extends CodeWriter {
    /**
     * @param consts список полей, прилетает с верхнего уровня
     */
    public JavapoetCodeWriter(Map<String, String> consts) {
        super(consts);
    }

    @Override
    protected void writeExceptionally(Filer filer) throws IOException {
       /*
        * Координаты генерируемого класса - пакет и имя
        */
        String pkg = "ru.lanwen.heisenbug.consts";
        String className = "JavapoetTestMethodConsts";

        // Наш класс
        TypeSpec.Builder constsClass = TypeSpec.classBuilder(className)
                .addModifiers(PUBLIC, FINAL);


        consts.forEach( // для каждого найденного метода (будущей константы)

                (key, value) -> constsClass.addField( // добавляем поле

                        FieldSpec.builder(ClassName.get(String.class), key, PUBLIC, STATIC, FINAL)
                                .initializer("$S", value)
                                .build()
                )
        );


        // Записываем исходник на диск
        JavaFile.builder(pkg, constsClass.build()).build().writeTo(filer);
    }
}
