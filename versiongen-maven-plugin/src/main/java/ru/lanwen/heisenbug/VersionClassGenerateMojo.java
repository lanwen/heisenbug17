package ru.lanwen.heisenbug;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * @author lanwen (Merkushev Kirill)
 */
@Mojo(name = "generate-version-class", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@Execute(goal = "generate-version-class")
public class VersionClassGenerateMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/misc")
    private String outputDir;

    @Parameter(required = true, readonly = true, defaultValue = "${project}")
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Path directory = Paths.get(outputDir);
            generationMagicWritedTo(directory);
            project.addCompileSourceRoot(outputDir);

        } catch (Exception e) {
            throw new MojoExecutionException("Exception while generating", e);
        }
    }

    private void generationMagicWritedTo(Path directory) throws IOException {
        JavaFile.builder(
                "ru.lanwen.heisenbug.generated",
                TypeSpec.classBuilder("ProjectVersion").addModifiers(PUBLIC, FINAL)
                        .addField(
                                FieldSpec.builder(
                                        ClassName.get(String.class),
                                        "PROJECT_VERSION",
                                        PUBLIC,
                                        STATIC,
                                        FINAL
                                )
                                        .initializer("$S", project.getVersion())
                                        .build()
                        )
                        .build()
        ).build().writeTo(directory);
    }
}
