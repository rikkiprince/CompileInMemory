package io.prince.java.CompileInMemory;
import java.io.*;
import java.net.URI;

import javax.tools.*;

// Inspired by https://privateer.dev.java.net/source/browse/privateer/trunk/privateer/generator/src/test/java/test/net/java/privateer/tools/MemoryOutputJavaFileObject.java?rev=53&view=markup
// Simplified so that it just has an output stream of bytes to store files in memory, then provides the necessary
// interface so that it can be accessed.
public class MemoryClassFile extends SimpleJavaFileObject
{
	ByteArrayOutputStream file;

	public MemoryClassFile(URI uri, Kind kind)
	{
		super(uri, kind);
	}
	
	// To WRITE TO this file
	public OutputStream openOutputStream()
	{
		// TODO: check if file == null?
		file = new ByteArrayOutputStream();
		return file;
	}
	
	// To READ FROM this file
	public InputStream openInputStream()
	{
		if(file != null)
			return new ByteArrayInputStream(file.toByteArray());	// return what is currently held
		else
			return new ByteArrayInputStream(new byte[0]);	// empty byte array
	}
}
