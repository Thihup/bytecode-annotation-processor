Bytecode annotation processor
----------------------

This project allows the usage of inline bytecode in the Java programming language.
It uses the [JAsm syntax](https://wiki.openjdk.java.net/display/CodeTools/Appendix+A) for the instructions.

One use case would be to use the InvokeDynamic instruction and/or the CONSTANT_Dynamic. Both
features are not available in Java programming language.

It is heavily inspired by the [Groovy Bytecode AST](https://github.com/melix/groovy-bytecode-ast/) project.

However, this project uses only supported API.

