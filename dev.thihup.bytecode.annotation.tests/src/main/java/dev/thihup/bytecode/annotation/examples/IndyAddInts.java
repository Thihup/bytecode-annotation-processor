package dev.thihup.bytecode.annotation.examples;

import dev.thihup.bytecode.annotation.core.Bytecode;
import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class IndyAddInts {

    @Bytecode(value =
        """
           iload_0;
           iload_1;
           invokedynamic REF_invokeStatic:
            dev/thihup/bytecode/annotation/examples/IndyAddInts.superAddBSM:
            "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;":
            "foo":"(II)I";
            ireturn;
        """,
        methodSignature = "(II)I",
        className="dev.thihup.bytecode.annotation.examples.AddInts"
    )
    public int myMethod(int a, int b) {
        return AddInts.invoke(a, b);
    }

    static CallSite superAddBSM(MethodHandles.Lookup lookup, String methodName, MethodType methodType) throws Throwable {
        MethodHandle addExact = lookup.findStatic(Math.class, "addExact",
            MethodType.methodType(int.class, int.class, int.class));

        return new ConstantCallSite(addExact.asType(methodType));
    }

}
