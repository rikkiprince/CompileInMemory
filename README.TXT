Compile In Memory

Rikki Prince - rikki@prince.io

Code influenced by examples on the web, which now seem to have disappeared.  URLs for original source are in the code comments.

This code was altered and simplified from the example code referenced, to make it into this usable library.


File Summary (in order of usefulness!)

MemoryCompiler.java
- You should create one of these.
- It keeps references to a compiler, file managers and class loader.
- Use .compile() and .run() or .compileAndRun(), to do your thing!
- detectClassName() uses a regular expression to find the name of the first class in the source.

CompileTest.java
- a tester class containing only a main method which constructs a couple of pieces of source code, compiles and runs them.

ClassNameNotFoundException.java
- Used to indicate failure of the MemoryCompile.detectClassName() method.

CompilerNotAccessibleException.java
- Used to indicate that when attempting to retrieve the JDK's compiler, 'null' was returned.

JavaSourceFromString.java
- a SimpleJavaFileObject that simply stores a string containing the source code

MemoryFileManager.java
- essentially a Map of filename strings to JavaFileObjects. In this case, only MemoryClassFile: all other file types passed to super.

MemoryClassFile.java
- wrapper around a ByteArrayOutputStream, so compiler can write byte code into it, and class loader can read byte code from it.

MemoryClassLoader.java
- looks for the requested class in the MemoryFileManager, then loads the bytes from the MemoryClassFile. If it doesn't exist, passes request to super.

LoopCompiler.java
- erm, not sure! TODO: work out what this does.
