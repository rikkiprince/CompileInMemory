package io.prince.java.CompileInMemory;

public class ClassNameNotFoundException extends Exception
{
	private static final long serialVersionUID = -5772887420787540511L;

	public ClassNameNotFoundException(String msg)
	{
		super(msg);
		// not an ideal error message, as it is specific to this code
		System.err.println("When searching for the name of the class within the provided code, we could not guess the class name.  You must call .compile(String sourceCode, String className);");
	}
}
