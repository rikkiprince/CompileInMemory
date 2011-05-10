package io.prince.java.CompileInMemory;

public class CompileTest
{
	public static void main(String[] args) throws CompilerNotAccessibleException
	{
		String className = "Dynamic";
		String methodName = "run";
		String sourceCode = "";
		sourceCode += "public class "+className;
		sourceCode += "{";
		sourceCode += "public static void "+methodName+"()";
		sourceCode += "{";
		sourceCode += "System.out.println(\"Hello, compiler world!\");";
		sourceCode += "}";
		sourceCode += "}";
		
		String className2 = "Second";
		String methodName2 = "stuff";
		String sourceCode2 = "";
		sourceCode2 += "public class "+className2;
		sourceCode2 += "{";
		sourceCode2 += "public static void "+methodName2+"()";
		sourceCode2 += "{";
		sourceCode2 += "System.out.println(\"Second method!\");";
		sourceCode2 += "}";
		sourceCode2 += "}";
		
		//sourceCode += "";
		
		/*LoopCompiler compiler = new LoopCompiler();
		
		compiler.compile(sourceCode, className, methodName);*/
		
		SomeSortOfCompiler compiler = new MemoryCompiler();
		try
		{
			compiler.compile(sourceCode);
			compiler.compile(sourceCode2);
			compiler.run(className, methodName);
			compiler.run(className2, methodName2);
//			System.out.println(compiler.detectClassName(sourceCode));
		}
		catch (ClassNameNotFoundException e)
		{e.printStackTrace();}
		
		sourceCode = "";
		sourceCode += "public class "+className;
		sourceCode += "{";
		sourceCode += "public static void "+methodName+"()";
		sourceCode += "{";
		sourceCode += "System.out.println(\"Oh shit\");";
		sourceCode += "}";
		sourceCode += "}";
		
		System.out.println();
		System.out.println();
		
		try
		{
			compiler.compile(sourceCode);
			compiler.run(className, methodName);
			compiler.run(className2, methodName2);
//			System.out.println(compiler.detectClassName(sourceCode));
		}
		catch (ClassNameNotFoundException e)
		{e.printStackTrace();}
		
	}
}
