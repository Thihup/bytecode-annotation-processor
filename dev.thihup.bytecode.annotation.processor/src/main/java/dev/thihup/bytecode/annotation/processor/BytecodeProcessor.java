package dev.thihup.bytecode.annotation.processor;

import static java.util.stream.Collectors.toMap;

import dev.thihup.bytecode.annotation.core.Bytecode;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import org.openjdk.asmtools.jasm.Main;

@SupportedAnnotationTypes("dev.thihup.bytecode.annotation.core.Bytecode")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public final class BytecodeProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith =
            roundEnv.getElementsAnnotatedWith(Bytecode.class);
        try {
            Path outputDirectory = Files.createTempDirectory("bytecodeProcessor");
            for (Element element : elementsAnnotatedWith) {
                processMethod(outputDirectory, element);
            }

            Files.walkFileTree(outputDirectory, new DeleteAllFilesVisitor());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private void processMethod(Path outputDirectory, Element element) throws IOException {
        Bytecode annotation = element.getAnnotation(Bytecode.class);

        String className = annotation.className();
        String classNameWithSlashes = className.replace('.', '/');
        String jasmCode = generateJasmCode(annotation, classNameWithSlashes);

        Path jasmFile = outputDirectory.resolve(className + ".jasm");
        Files.writeString(jasmFile, jasmCode);

        compile(outputDirectory, jasmFile);

        Filer filer = processingEnv.getFiler();
        JavaFileObject bytecode = filer.createClassFile(className, element);
        try (var outputStream = bytecode.openOutputStream()) {
            Path classFile = outputDirectory.resolve(classNameWithSlashes.concat(".class"));
            Files.copy(classFile, outputStream);
        }
    }

    private String generateJasmCode(Bytecode annotation, String classNameWithSlashes) {
        return !annotation.jasmCode().isEmpty() ? annotation.jasmCode() : """
            class %s
             version %s  {
                static Method "%s":"%s" stack 100 locals 100 {
                    %s
                }
            }
            """.formatted(classNameWithSlashes,
            annotation.classVersion(),
            annotation.methodName(), annotation.methodSignature(), annotation.value());
    }

    private void compile(Path outputDirectory, Path jasmFile) {
        Main compiler = new Main(new PrintWriter(System.out), "jasm") {
            {
                this.err = new PrintWriter(System.err);
            }
        };
        boolean compile = compiler.compile(
            new String[]{"-d", outputDirectory.toString(), jasmFile.toString()});
        if (!compile) {
            throw new RuntimeException("Compilation failed");
        }
    }

    private static class DeleteAllFilesVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc)
            throws IOException {
            Files.deleteIfExists(dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException {
            Files.deleteIfExists(file);
            return FileVisitResult.CONTINUE;
        }
    }
}
