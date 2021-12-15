package dev.thihup.bytecode.annotation.tests;

import dev.thihup.bytecode.annotation.examples.HelloWorld;
import org.junit.jupiter.api.Test;

class HelloWorldTest {

    @Test
    void myMethod() {
        new HelloWorld().myMethod();
    }
}