package io.prince.java.CompileInMemory;
import java.io.*;
import java.util.*;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;


public class MemoryCompiler extends SomeSortOfCompiler
{
	private JavaCompiler compiler;
	
	public MemoryCompiler() throws CompilerNotAccessibleException
	{
		this(null);
	}

	public MemoryCompiler(Writer out) throws CompilerNotAccessibleException
	{
		super(out);
		this.compiler = ToolProvider.getSystemJavaCompiler();
		if(this.compiler == null)
		{
			throw new CompilerNotAccessibleException("Tried to get it, cannot.");
		}
		
		this.standardFileManager = compiler.getStandardFileManager(null, null, null);
		this.fileManager = new MemoryFileManager(standardFileManager);
		this.classLoader = new MemoryClassLoader(fileManager);
	}
	
	public boolean compile(String sourceCode, String className)
	{
		// ANON: (1) This should return a Collection<Diagnostic> or at least of a simpler class that stores the info.
		/* Although possibly assuming failure is incorrect?  Maybe return a "ResultSet" or something?  It could contain compilation
		 * errors, a reference to the classloader and a method to run this new class?
		 */
		System.out.println("Compiling...");

		// http://www.java2s.com/Code/Java/JDK-6/CompilingfromMemory.htm
		JavaFileObject source = new JavaSourceFromString(className, sourceCode);
		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(source);

		CompilationTask task = compiler.getTask(this.out, fileManager, new MyDiagnosticListener(), null, null, compilationUnits);
		
		boolean success = task.call();
		if(success) System.out.println("Compiled successfully!");
		
		classLoader = new MemoryClassLoader(fileManager);
		
		return success;
	}
}
