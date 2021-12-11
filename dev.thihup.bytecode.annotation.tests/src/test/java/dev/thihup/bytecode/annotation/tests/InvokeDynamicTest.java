package dev.thihup.bytecode.annotation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.thihup.bytecode.annotation.examples.IndyAddInts;
import dev.thihup.bytecode.annotation.examples.IndySimple;
import org.junit.jupiter.api.Test;

class InvokeDynamicTest {

    @Test
    void test() {
        assertEquals("Hello World", new IndySimple().myMethod());
    }

    @Test
    void test2() {
        assertEquals(new IndyAddInts().myMethod(5, 7), 12);
    }

}
