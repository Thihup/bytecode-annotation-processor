Bytecode annotation processor
----------------------

This project allows the usage of inline bytecode in the Java programming language.
It uses the [JASM syntax](https://wiki.openjdk.java.net/display/CodeTools/Appendix+A) for the instructions.

One use case would be to use the InvokeDynamic instruction and/or the CONSTANT_Dynamic. Both
features are not available in Java programming language.

It is heavily inspired by the [Groovy Bytecode AST](https://github.com/melix/groovy-bytecode-ast/) project.

However, this project uses only supported API.

Example
------
```java
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
```
See the [tests](dev.thihup.bytecode.annotation.tests) folder for more examples.

Limitations
-----------

- As we can only generate external classes, it is not possible to create an inner class to store the inline bytecode (the generated class is valid, but you won't be able to reference it in a Java source file);
- For the same reason as above, it is not possible to access private field of the annotated class.