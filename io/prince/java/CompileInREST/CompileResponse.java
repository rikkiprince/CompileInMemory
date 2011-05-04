package io.prince.java.CompileInREST;

import io.prince.java.BeerPump.*;
import io.prince.java.CompileInMemory.*;

import java.io.*;
import java.util.Arrays;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;


public class CompileResponse extends HTTPResponse
{
	public CompileResponse(HTTPRequest req) throws HTTPException
	{
		super(req);
		
		//set up variables needed for in memory compilation
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		JavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);;
		MemoryFileManager fileManager = new MemoryFileManager(standardFileManager);
		
		// get source code from this request's body, and determine the classname
		String sourceCode = req.getBodyString();
		String className = "";
		byte[] classFile = new byte[0];
		try
		{
			className = MemoryCompiler.detectClassName(sourceCode);
			
			// compile the source code
			JavaFileObject source = new JavaSourceFromString(className, sourceCode);
			Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(source);
			CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
			boolean success = task.call();
			
			// TODO: Look up processors to see if I can find out error here.
			if(!success)
				throw new io.prince.java.BeerPump.HTTPException(500, "Compilation failed.");
			
			// get the compiled class file from the file manager
			JavaFileObject compiledClass = fileManager.getJavaFileForInput(StandardLocation.CLASS_OUTPUT, className, Kind.CLASS);
			
			// transfer the bytes in the file into a byte array.
			classFile = convertInputStreamToByteArray(compiledClass.openInputStream());
		}
		catch (ClassNameNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	// TODO: handle Exception properly
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		// send the compiled class back!
		this.setBody(classFile);
	}
	
	public static byte[] convertInputStreamToByteArray(InputStream is) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int read = 0;
		byte[] buf = new byte[32768];
		while((read = is.read(buf, 0, buf.length)) != -1)	baos.write(buf, 0, read);
		baos.flush();
		
		return baos.toByteArray();
	}
}
