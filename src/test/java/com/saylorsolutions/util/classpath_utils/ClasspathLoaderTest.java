package com.saylorsolutions.util.classpath_utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

class ClasspathLoaderTest {

	@Test
	void testFileStream() throws IOException {
		InputStream file1 = ClasspathLoader.fileStream("Test File 1");
		InputStream file2 = ClasspathLoader.fileStream("Test File 2");
		InputStream file3 = ClasspathLoader.fileStream("folder/Test File 3");
		InputStream fileX = ClasspathLoader.fileStream("This file doesn't exist");

		assertNotNull(file1);
		assertNotNull(file2);
		assertNotNull(file3);
		assertNull(fileX);

		file1.close();
		file2.close();
		file3.close();
	}

	@Test
	void testFileStreamReader() throws IOException {
		InputStreamReader file1 = ClasspathLoader.fileStreamReader("Test File 1");
		InputStreamReader file2 = ClasspathLoader.fileStreamReader("Test File 2");
		InputStreamReader file3 = ClasspathLoader.fileStreamReader("folder/Test File 3");
		InputStreamReader fileX = ClasspathLoader.fileStreamReader("This file doesn't exist");

		assertNotNull(file1);
		assertNotNull(file2);
		assertNotNull(file3);
		assertNull(fileX);

		file1.close();
		file2.close();
		file3.close();
	}

	@Test
	void testFileStreamBufferedReader() throws IOException {
		BufferedReader file1 = ClasspathLoader.fileStreamBufferedReader("Test File 1");
		BufferedReader file2 = ClasspathLoader.fileStreamBufferedReader("Test File 2");
		BufferedReader file3 = ClasspathLoader.fileStreamBufferedReader("folder/Test File 3");
		BufferedReader fileX = ClasspathLoader.fileStreamBufferedReader("This file doesn't exist");

		assertNotNull(file1);
		assertNotNull(file2);
		assertNotNull(file3);
		assertNull(fileX);

		file1.close();
		file2.close();
		file3.close();
	}


	@Test
	void testFileContents() throws IOException {
		String file1 = ClasspathLoader.fileContents("Test File 1");
		String file2 = ClasspathLoader.fileContents("Test File 2");
		String file3 = ClasspathLoader.fileContents("folder/Test File 3");
		String fileX = ClasspathLoader.fileContents("This file doesn't exist");

		assertNotNull(file1);
		assertEquals("Test File 1", file1);
		assertNotNull(file2);
		assertEquals("Test File 2", file2);
		assertNotNull(file3);
		assertEquals("Test File 3", file3);
		assertNotNull(fileX);
		assertEquals("", fileX);
	}
}
