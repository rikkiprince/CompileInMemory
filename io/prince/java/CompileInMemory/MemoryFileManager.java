package io.prince.java.CompileInMemory;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import javax.tools.*;
import javax.tools.JavaFileObject.Kind;

// Inspired by https://privateer.dev.java.net/source/browse/privateer/trunk/privateer/generator/src/test/java/test/net/java/privateer/tools/MemoryOutputJavaFileManager.java?rev=53&view=markup
// Stores a HashMap of FileObjects.  This only stores CLASS output files.
// If .java or other files are required, control is passed onto super.
public class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager>
{
	Map<String,JavaFileObject> memoryFileSystem;

	protected MemoryFileManager(JavaFileManager manager)
	{
		super(manager);
		
		memoryFileSystem = new HashMap<String,JavaFileObject>();
	}
	
	public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String fileName, JavaFileObject.Kind kind, FileObject sibling) throws IOException
	{
		//System.out.println("getJavaFileForOutput("+location+","+fileName+","+kind+","+sibling+")");
		if(kind == Kind.CLASS && location == StandardLocation.CLASS_OUTPUT)
		{
			try
			{
				MemoryClassFile file = new MemoryClassFile(new URI(fileName), kind);
				
				@SuppressWarnings("unused") // just in case we want to allow the user to access old files in future
				JavaFileObject old = memoryFileSystem.put(fileName, file);
				
				return file;
			}
			catch (URISyntaxException e)
			{
				throw new IOException(e);
			}
		}
		else
			return super.getJavaFileForOutput(location, fileName, kind, sibling);
	}

	public JavaFileObject getJavaFileForInput(JavaFileManager.Location location, String fileName, JavaFileObject.Kind kind) throws IOException
	{
		if(kind == Kind.CLASS && location == StandardLocation.CLASS_OUTPUT)
		{
			return memoryFileSystem.get(fileName);
		}
		else
			return super.getJavaFileForInput(location, fileName, kind);
	}
}
