package com.saylorsolutions.util.classpath_utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ClasspathLoader {
	/**
	 * Creates an {@code InputStream} for the file specified by the {@code filename} parameter.
	 * In the case of the base directory specified below: <br/>
	 * <br/>
	 * {@code BASE_DIR=$PROJECT_LOCATION/src/main/resources}<br/>
	 * <br/>
	 * The {@code filename} parameter without a path prefix would reference files in
	 * {@code $BASE_DIR}. Adding a path prefix like
	 * {@code "folder/"} would follow the specified path
	 * {@code $BASE_DIR/folder}.
	 * 
	 * @param filename The name and path of the file to be referenced, relative to
	 *                 the base directory.
	 * 
	 * @return An {@code InputStream} for the file referenced by the given file
	 *         name, or null if the file cannot be opened or read.
	 */
	public static InputStream fileInputStream(String filename) {
		return ClasspathLoader.class.getClassLoader().getResourceAsStream(filename);
	}

	/**
	 * Creates a {@code BufferedReader} for the file specified by the {@code filename} parameter.
	 * In the case of the base directory specified below: <br/>
	 * <br/>
	 * {@code BASE_DIR=$PROJECT_LOCATION/src/main/resources}<br/>
	 * <br/>
	 * The {@code filename} parameter without a path prefix would reference files in
	 * {@code $BASE_DIR}. Adding a path prefix like
	 * {@code "folder/"} would follow the specified path
	 * {@code $BASE_DIR/folder}.
	 * 
	 * @param filename The name and path of the file to be referenced, relative to
	 *                 the base directory.
	 * 
	 * @return A {@code BufferedReader} for the file referenced by the given file
	 *         name, or null if the file cannot be opened or read.
	 */
	public static BufferedReader fileBufferedReader(String filename) {
		return wrapReaderInBufferedReader(wrapInputStreamInReader(fileInputStream(filename)));
	}

	/**
	 * Uses {@code fileStreamBufferedReader} to read in all lines from the specified
	 * file and returns them as a single {@code String}.
	 * 
	 * @param filename The {@code filename} parameter to be passed to
	 *                 {@code fileStreamBufferedReader}.
	 * @return A single {@code String} containing the file's contents, or an empty
	 *         {@code String} if an error occurred or the file is empty.
	 * @see ClasspathLoader#fileBufferedReader(String)
	 */
	public static String fileContents(String filename) {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = fileBufferedReader(filename);
		if(reader != null) {
			reader.lines().forEach(line -> builder.append(line));
			try { reader.close(); } catch (IOException iox) { /* Ignore */ }
		}
		return builder.toString();
	}
	
	public static ZipFile zip(String zipFileName) {
		File fileReference = fileReference(zipFileName);
		try {
			return (fileReference != null ? new ZipFile(fileReference) : null);
		} catch (ZipException zx) {
			System.err.println("Exception occurred creating ZipFile reference");
			zx.printStackTrace();
		} catch (IOException iox) {
			System.err.println("Exception occurred while opening the ZipFile");
			iox.printStackTrace();
		}

		return null;
	}

	public static InputStream zipFileInputStream(String zipFileName, String fileWithinArchive) {
		try {
			File fileReference = fileReference(zipFileName);
			if (fileReference != null) {
				ZipFile zipFile = zip(zipFileName);
				return (zipFile != null ? zipFile.getInputStream(zipFile.getEntry(fileWithinArchive)) : null);
			} else {
				System.err.println("Failed to get a File reference to the file '" + zipFileName + "'");
			}
		} catch (IOException iox) {
			System.err.println("Exception occurred while reading the ZipFile");
			iox.printStackTrace();
		}

		return null;
	}
	
	public static BufferedReader zipFileBufferedReader(String zipFileName, String fileWithinArchive) {
		InputStream is = zipFileInputStream(zipFileName, fileWithinArchive);
		return (is != null ? wrapInBufferedReader(is) : null);
	}

	public static File fileReference(String filename) {
		try {
			return new File(ClasspathLoader.class.getClassLoader().getResource(filename).toURI());
		} catch (URISyntaxException usx) {
			System.err.println("Failed to get URI for file: '" + filename + "'");
			usx.printStackTrace();
		}
		return null;
	}

	private static InputStreamReader wrapInputStreamInReader(InputStream resourceAsStream) {
		return resourceAsStream != null ? new InputStreamReader(resourceAsStream) : null;
	}

	private static BufferedReader wrapReaderInBufferedReader(InputStreamReader inputStreamReader) {
		return inputStreamReader != null ? new BufferedReader(inputStreamReader) : null;
	}
	
	private static BufferedReader wrapInBufferedReader(InputStream resourceAsStream) {
		return (resourceAsStream != null ? new BufferedReader(new InputStreamReader(resourceAsStream)) : null);
	}
}
