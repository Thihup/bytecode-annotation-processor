import javax.annotation.processing.Processor;

module dev.thihup.bytecode.annotation.processor {
    requires dev.thihup.bytecode.annotation.core;
    requires java.compiler;
    requires asmtools.core;
    provides Processor with dev.thihup.bytecode.annotation.processor.BytecodeProcessor;
}