package io.prince.java.CompileInMemory;
import java.io.*;
import java.lang.reflect.*;
import java.util.regex.*;
import javax.tools.*;


public abstract class SomeSortOfCompiler
{
	protected JavaFileManager standardFileManager;
	protected MemoryFileManager fileManager;
	protected MemoryClassLoader classLoader;
	
	protected Writer out;
	
	public SomeSortOfCompiler()
	{
		this(null);
	}
	
	public SomeSortOfCompiler(Writer out)
	{
		this.standardFileManager = new SomeSortOfStandardJavaFileManager();	// can this be null? It cannot!
		this.fileManager = new MemoryFileManager(standardFileManager);	// what happens here if given null? NullPointerException!
		this.classLoader = new MemoryClassLoader(fileManager);
		
		this.out = out;
	}
	
	// the method that actually does the compilation (varies by type of compiler)
	public abstract boolean compile(String sourceCode, String className);

	// helper method to detect class name first, then compile
	public boolean compile(String sourceCode) throws ClassNameNotFoundException
	{
		String className = detectClassName(sourceCode);
		
		return compile(sourceCode, className);
	}
	
	// helper method to compile, then run
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
