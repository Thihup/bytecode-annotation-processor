package dev.thihup.bytecode.annotation.examples;

import dev.thihup.bytecode.annotation.core.Bytecode;
import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class IndySimple {

    @Bytecode(value =
        """
           invokedynamic REF_invokeStatic:
            dev/thihup/bytecode/annotation/examples/IndySimple.myBSMWithoutArgs:
            "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;":
            "foo":"()Ljava/lang/String;";
            areturn;
        """,
        methodSignature = "()Ljava/lang/String;",
        className="dev.thihup.bytecode.annotation.examples.IndySimpleWithoutArgs"
    )
    public String myMethod() {
        return IndySimpleWithoutArgs.invoke();
    }

    static CallSite myBSMWithoutArgs(MethodHandles.Lookup lookup, String methodName, MethodType methodType) throws Throwable {
        return new ConstantCallSite(MethodHandles.constant(String.class, "Hello World"));
    }

}
