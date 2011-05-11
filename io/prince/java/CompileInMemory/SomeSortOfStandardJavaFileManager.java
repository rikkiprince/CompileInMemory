package io.prince.java.CompileInMemory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.JavaFileObject.Kind;

public class SomeSortOfStandardJavaFileManager implements
		StandardJavaFileManager
{

	@Override
	public Iterable<? extends JavaFileObject> getJavaFileObjects(File... arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<? extends JavaFileObject> getJavaFileObjects(String... arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<? extends JavaFileObject> getJavaFileObjectsFromFiles(
			Iterable<? extends File> arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<? extends JavaFileObject> getJavaFileObjectsFromStrings(
			Iterable<String> arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<? extends File> getLocation(Location arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSameFile(FileObject arg0, FileObject arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLocation(Location arg0, Iterable<? extends File> arg1)
			throws IOException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws IOException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() throws IOException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ClassLoader getClassLoader(Location location)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileObject getFileForInput(Location location, String packageName,
			String relativeName) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileObject getFileForOutput(Location location, String packageName,
			String relativeName, FileObject sibling) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JavaFileObject getJavaFileForInput(Location location,
			String className, Kind kind) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location,
			String className, Kind kind, FileObject sibling) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean handleOption(String current, Iterator<String> remaining)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasLocation(Location location)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String inferBinaryName(Location location, JavaFileObject file)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName,
			Set<Kind> kinds, boolean recurse) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isSupportedOption(String arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
