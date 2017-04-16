package ru.lanwen.heisenbug;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import static com.google.testing.compile.Compiler.javac;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * @author lanwen (Merkushev Kirill)
 */
class MethodConstantsWriterTest {

    @Test
    void shouldCompile() {
        Compilation compilation = javac()
                .withProcessors(new MethodConstantsWriter())
                .compile(JavaFileObjects.forResource("ExampleTest.java"));

        assertThat("errors", compilation.errors(), hasSize(0));
        assertThat("files", compilation.generatedSourceFiles(), hasSize(2));
    }
}
