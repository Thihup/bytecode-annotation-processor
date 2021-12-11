package dev.thihup.bytecode.annotation.processor;

import dev.thihup.bytecode.annotation.core.Bytecode;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import org.openjdk.asmtools.jasm.Main;

@SupportedAnnotationTypes({
    "*"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class BytecodeProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(
            Bytecode.class);
        elementsAnnotatedWith.forEach(this::processMethod);

        return true;
    }

    private void processMethod(Element element) {
        try {
            Filer filer = processingEnv.getFiler();

            List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
            var entries =
                annotationMirrors.get(0).getElementValues();

            String className = "";
            String methodBody = "";
            String returnTypeDescriptor = "V";
            String methodName = "invoke";
            String methodSignature = "()V";
            String classVersion = "61:0";
            String jasmCode = "";

            for (var entry : entries.entrySet()) {
                ExecutableElement key = entry.getKey();
                String value = entry.getValue().getValue().toString();
                switch (key.toString()) {
                    case "className()" -> className = value;
                    case "value()" -> methodBody = value;
                    case "methodName()" -> methodName = value;
                    case "methodSignature()" -> methodSignature = value;
                    case "classVersion()" -> classVersion = value;
                    case "jasmCode()" -> jasmCode = value;
                }
            }
            String formatted = jasmCode.isEmpty() ? """
                class %s
                 version %s  {
                    static Method "%s":"%s" stack 100 locals 100 {
                        %s
                    }
                }
                """.formatted(className.replace('.', '/'),
                classVersion,
                methodName, methodSignature, methodBody) : jasmCode;

            JavaFileObject bytecode = filer.createClassFile(className, element);

            Path outputDirectory = Files.createTempDirectory("bytecodeProcessor");
            Path jasmFile = outputDirectory.resolve(className + ".jasm");
            Files.writeString(jasmFile, formatted);

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

            try (var outputStream = bytecode.openOutputStream()) {
                Files.copy(outputDirectory.resolve(className.replace('.', '/').concat(".class")),
                    outputStream);
            }

            System.out.println("Method");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
