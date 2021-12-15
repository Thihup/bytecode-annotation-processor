package dev.thihup.bytecode.annotation.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bytecode annotation
 *
 * It creates a new class with a method body specified in this annotation.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Bytecode {

    /**
     * The bytecode of the method utilizing the JASM syntax
     */
    String value();

    /**
     * The class name of the class that contains the method to be invoked.
     * It uses the fully qualified name of the class (using dots).
     */
    String className();

    /**
     * The signature of the method to be invoked.
     * Default to "()V"
     */
    String methodSignature() default "()V";

    /**
     * The name of the method to be invoked. Default to "invoke"
     */
    String methodName() default "invoke";

    /**
     * The class file version
     * Default to "61:0" (Java 17)
     */
    String classVersion() default "61:0";

    /**
     * If specified, it will use the of the jasmCode instead of creating a Jasm code
     * from the other parameters.
     */
    String jasmCode() default "";
}
