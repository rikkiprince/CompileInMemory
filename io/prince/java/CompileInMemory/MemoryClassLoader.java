package io.prince.java.CompileInMemory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.tools.*;
import javax.tools.JavaFileObject.*;


// Inspired by https://privateer.dev.java.net/source/browse/privateer/trunk/privateer/generator/src/test/java/test/net/java/privateer/tools/JavaFileManagerClassLoader.java?rev=53&view=markup
// Provides a class loader interface that knows about our way of managing files (see MemoryFileManager)
// Trys to get file from our FileManager, and if it's not there, passes control onto super.
public class MemoryClassLoader extends ClassLoader
{
	MemoryFileManager fileManager;
	
	public MemoryClassLoader(MemoryFileManager manager)
	{
		super();
		
		this.fileManager = manager;
	}
	
	public MemoryClassLoader(MemoryFileManager manager, ClassLoader parent)
	{
		super(parent);
		
		this.fileManager = manager;
	}
	
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		JavaFileObject file;
		
		try
		{
			file = fileManager.getJavaFileForInput(StandardLocation.CLASS_OUTPUT, name, Kind.CLASS);

			
			if(file != null)
			{
				System.out.flush();
				byte[] bytes = loadBytes(file.openInputStream());
				
				return defineClass(name, bytes, 0, bytes.length);
			}
			else
				return super.findClass(name);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ClassNotFoundException(name, e);
		}
		catch(ClassFormatError cfe)
		{
			System.out.println(cfe);
			throw new ClassNotFoundException(name, cfe);
		}
	}

	private byte[] loadBytes(InputStream in) throws IOException
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		final int bufSize = 4096;
		byte[] temp = new byte[bufSize];
		int read = 0;
		
		do
		{
			read = in.read(temp);
			bytes.write(temp,0,read);
		} while(read == 0);
		
		return bytes.toByteArray();
	}
}
