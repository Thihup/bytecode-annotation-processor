package dev.thihup.bytecode.annotation.examples;

import dev.thihup.bytecode.annotation.core.Bytecode;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class CondyBSMExample {

    @Bytecode(value =
           """
           ldc Dynamic REF_invokeStatic:dev/thihup/bytecode/annotation/examples/CondyBSMExample.fibonacci
                :"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/Class;)Ljava/util/List;"
                :foo:"Ljava/util/List;";
           areturn;
           """,
        methodSignature = "()Ljava/util/List;",
        className="dev.thihup.bytecode.annotation.examples.Example2"
    )
    public List<Integer> myMethod(int a, int b) {
        return Example2.invoke();
    }

    static List<Integer> fibonacci(MethodHandles.Lookup lookup, String name, Class<?> methodType) throws Throwable {
        return List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34);
    }

}
