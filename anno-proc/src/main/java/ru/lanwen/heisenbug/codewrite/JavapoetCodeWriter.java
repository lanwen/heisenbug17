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
    public JavapoetCodeWriter(Map<String, String> consts) {
        super(consts);
    }

    @Override
    public void writeTo(Filer filer) {
        String pkg = "ru.lanwen.heisenbug.consts";
        String className = "JavapoetTestMethodConsts";

        TypeSpec.Builder constsClass = TypeSpec.classBuilder(className)
                .addModifiers(PUBLIC, FINAL);

        consts.forEach(
                (key, value) -> constsClass.addField(
                        FieldSpec.builder(ClassName.get(String.class), key, PUBLIC, STATIC, FINAL)
                                .initializer("$S", value)
                                .build()
                )
        );

        try {
            JavaFile.builder(pkg, constsClass.build()).build().writeTo(filer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
