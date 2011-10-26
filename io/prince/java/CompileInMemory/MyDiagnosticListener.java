package io.prince.java.CompileInMemory;

import javax.tools.*;

public class MyDiagnosticListener implements DiagnosticListener<JavaFileObject>
{

	@Override
	public void report(Diagnostic<? extends JavaFileObject> diagnostic)
	{
		System.err.println();
		System.err.println(diagnostic);
		System.err.println("===========================");
		
		System.err.println("Code: "+diagnostic.getCode());
		System.err.println("Column Number: "+diagnostic.getColumnNumber());
		System.err.println("End Position: "+diagnostic.getEndPosition());
		System.err.println("Line Number: "+diagnostic.getLineNumber());
		System.err.println("Position: "+diagnostic.getPosition());
		System.err.println("Start Position: "+diagnostic.getStartPosition());
		System.err.println("Kind: "+diagnostic.getKind());
		System.err.println("Source: "+diagnostic.getSource());
		
	}

	

}
