package io.prince.java.CompileInMemory;
import java.lang.reflect.*;
import java.util.Arrays;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;


public class LoopCompiler
{	
	public void compile(String code, String className, String methodName)
	{
		System.out.println("Compiling...");

		// http://www.java2s.com/Code/Java/JDK-6/CompilingfromMemory.htm
		JavaFileObject source = new JavaSourceFromString(className, code);
		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(source);


		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		JavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
		MemoryFileManager fileManager = new MemoryFileManager(standardFileManager);
		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
		MemoryClassLoader classLoader = new MemoryClassLoader(fileManager);
		
		boolean success = task.call();
	    
		if (success)
		{
			System.out.println("Compiled successfully!");
			try
			{
				//Class.forName(className).getDeclaredMethod(methodName).invoke(null);
				classLoader.loadClass(className).getDeclaredMethod(methodName).invoke(null);
			}
			catch (ClassNotFoundException e) {
				System.err.println("Class not found: " + e);
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				System.err.println("No such method: " + e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.err.println("Illegal access: " + e);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				System.err.println("Invocation target: " + e);
				e.printStackTrace();
			}
		}
	}
}
