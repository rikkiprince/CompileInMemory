package io.prince.java.CompileInMemory;
import java.util.Arrays;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;


public class MemoryCompiler extends SomeSortOfCompiler
{
	private JavaCompiler compiler;

	public MemoryCompiler() throws CompilerNotAccessibleException
	{
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
		System.out.println("Compiling...");

		// http://www.java2s.com/Code/Java/JDK-6/CompilingfromMemory.htm
		JavaFileObject source = new JavaSourceFromString(className, sourceCode);
		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(source);

		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
		
		boolean success = task.call();
		if(success) System.out.println("Compiled successfully!");
		
		classLoader = new MemoryClassLoader(fileManager);
		
		return success;
	}
}
