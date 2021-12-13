package dev.thihup.bytecode.annotation.examples;

import dev.thihup.bytecode.annotation.core.Bytecode;

public class HelloWorld {

    @Bytecode(
        value = """
        getstatic java/lang/System.out:"Ljava/io/PrintStream;";
        ldc "Hello, world!";
        invokevirtual java/io/PrintStream.println:"(Ljava/lang/String;)V";
        return;
        """,
        className="dev.thihup.bytecode.annotation.examples.HelloWorldImpl"
    )
    void myMethod() {
        HelloWorldImpl.invoke();
    }

}
