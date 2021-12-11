package dev.thihup.bytecode.annotation.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Bytecode {
    String value();

    String className();

    String methodSignature() default "()V";

    String methodName() default "invoke";

    String classVersion() default "61:0";

    /**
     * If specified, it will use the of the jasmCode instead of creating a Jasm code
     * from the other parameters.
     */
    String jasmCode() default "";
}
