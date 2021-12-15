package dev.thihup.bytecode.annotation.examples;

import dev.thihup.bytecode.annotation.core.Bytecode;

public class CondyConstantBootstrapsExample {

    @Bytecode(value =
           """
           ldc Dynamic REF_invokeStatic:java/lang/invoke/ConstantBootstraps.primitiveClass
                :"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Class;"
                :I:"Ljava/lang/Class;";
           areturn;
           """,
        methodSignature = "()Ljava/lang/Class;",
        className="dev.thihup.bytecode.annotation.examples.Example1"
    )
    public Class<?> myMethod() {
        return Example1.invoke();
    }
}
