package dev.thihup.bytecode.annotation.examples;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HelloWorldTest {

    @Test
    void myMethod() {
        new HelloWorld().myMethod();
    }
}