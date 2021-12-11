package dev.thihup.bytecode.annotation.examples;

import dev.thihup.bytecode.annotation.core.Bytecode;
import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

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
