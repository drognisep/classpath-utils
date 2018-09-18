package com.saylorsolutions.util.classpath_utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

class ClasspathLoaderTest {

	@Test
	void testFileStream() throws IOException {
		InputStream file1 = ClasspathLoader.fileInputStream("Test File 1");
		InputStream file2 = ClasspathLoader.fileInputStream("Test File 2");
		InputStream file3 = ClasspathLoader.fileInputStream("folder/Test File 3");
		InputStream fileX = ClasspathLoader.fileInputStream("This file doesn't exist");

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
		BufferedReader file1 = ClasspathLoader.fileBufferedReader("Test File 1");
		BufferedReader file2 = ClasspathLoader.fileBufferedReader("Test File 2");
		BufferedReader file3 = ClasspathLoader.fileBufferedReader("folder/Test File 3");
		BufferedReader fileX = ClasspathLoader.fileBufferedReader("This file doesn't exist");

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

	@Test
	void testGetFileReferenceToZip() throws Exception {
		File f = ClasspathLoader.fileReference("archive.zip");
		assertTrue(f.exists());
		assertFalse(f.isDirectory());
	}

	@Test
	void testGetZipFileData() throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(ClasspathLoader.zipFileInputStream("archive.zip", "folder/Test File 3")));
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		assertEquals("Test File 3", line);
	}
}
