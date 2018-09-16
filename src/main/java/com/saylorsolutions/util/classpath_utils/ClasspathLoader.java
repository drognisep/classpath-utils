package com.saylorsolutions.util.classpath_utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClasspathLoader {
	public static InputStream fileStream(String filename) {
		return ClasspathLoader.class.getClassLoader().getResourceAsStream(filename);
	}
	public static InputStreamReader fileStreamReader(String filename) {
		return new InputStreamReader(ClasspathLoader.class.getClassLoader().getResourceAsStream(filename));
	}
	public static BufferedReader fileStreamBufferedReader(String filename) {
		return new BufferedReader(new InputStreamReader(ClasspathLoader.class.getClassLoader().getResourceAsStream(filename)));
	}
//	public static ZipInputStream zipFileStream(String file, String path) {
//		InputStream is = ClasspathLoader.class.getClassLoader().getResourceAsStream(file);
//	}
}
