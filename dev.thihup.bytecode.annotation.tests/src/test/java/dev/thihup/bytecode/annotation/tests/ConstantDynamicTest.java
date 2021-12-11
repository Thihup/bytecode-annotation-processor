package dev.thihup.bytecode.annotation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.thihup.bytecode.annotation.examples.CondyBSMExample;
import dev.thihup.bytecode.annotation.examples.CondyConstantBootstrapsExample;
import dev.thihup.bytecode.annotation.examples.IndyAddInts;
import dev.thihup.bytecode.annotation.examples.IndySimple;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConstantDynamicTest {

    @Test
    void test() {
        assertEquals(int.class, new CondyConstantBootstrapsExample().myMethod());
    }

    @Test
    void test2() {
        List<Integer> fibo = new CondyBSMExample().myMethod(5, 7);
        assertEquals(10, fibo.size());
    }

}
