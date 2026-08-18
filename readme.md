# Java Compilation and Execution

`javac` and `java` do two different jobs:

* `javac` = **Java Compiler** → converts `.java` source code into `.class` bytecode.
* `java` = **Java Runtime** → runs the compiled `.class` bytecode.

So, run `javac fileName.java` first, then `java fileName`.

### Example

```text
echoClient.java
      ↓ javac
echoClient.class
      ↓ java
Program runs
```

### Commands

```bash
javac echoClient.java   # compile
java echoClient         # run
```