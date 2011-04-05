package io.prince.java.CompileInMemory;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;


public class MemoryCompiler
{
	JavaCompiler compiler;
	JavaFileManager standardFileManager;
	MemoryFileManager fileManager;
	MemoryClassLoader classLoader;
	
	
	public boolean compile(String sourceCode) throws ClassNameNotFoundException
	{
		String className = detectClassName(sourceCode);
		
		return compile(sourceCode, className);
	}
	
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
	
	public void compileAndRun(String sourceCode, String className, String methodName)
	{
	    boolean success = compile(sourceCode, className);
	    
		if (success)
		{
			run(className, methodName);
		}
	}
	
	public void run(String className, String methodName)
	{
		try
		{
			//Class.forName(className).getDeclaredMethod(methodName).invoke(null);
			// Have to create a new class loader, otherwise it will cache the previous class.
			// TODO: change this so that it keeps old compiled stuff but updates just the recent compilation.
			//MemoryClassLoader classLoader = new MemoryClassLoader(fileManager);
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
	
	public static String detectClassName(String sourceCode) throws ClassNameNotFoundException
	{
//		System.out.println(sourceCode);
		String qualifiers = "(?:(?:public )?|(?:abstract )?|(?:static )?)*";	//(?:private )?|
		String regex = qualifiers+"class (\\w+)";	//qualifiers+"class (\\w+)"
		Matcher m = Pattern.compile(regex).matcher(sourceCode);
//		System.out.println(m.find());
//		for(int i=0; i<=m.groupCount(); i++)
//			System.out.println(i+":"+m.group(i));
		
		if(!m.find() || m.groupCount() < 1) throw new ClassNameNotFoundException("Could not find regular expression ("+regex+") within "+sourceCode);
		
		String className = m.group(1);
		
		return className;
	}
	
	public ClassLoader getClassLoader()
	{
		return this.classLoader;
	}
}
