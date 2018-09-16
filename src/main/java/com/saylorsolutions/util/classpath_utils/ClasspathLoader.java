package com.saylorsolutions.util.classpath_utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	public static InputStream fileStream(String filename) {
		return ClasspathLoader.class.getClassLoader().getResourceAsStream(filename);
	}

	/**
	 * Creates an {@code InputStreamReader} for the file specified by the {@code filename} parameter.
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
	 * @return An {@code InputStreamReader} for the file referenced by the given file
	 *         name, or null if the file cannot be opened or read.
	 */
	public static InputStreamReader fileStreamReader(String filename) {
		return wrapInputStreamInReader(fileStream(filename));
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
	public static BufferedReader fileStreamBufferedReader(String filename) {
		return wrapReaderInBufferedReader(fileStreamReader(filename));
	}

	/**
	 * Uses {@code fileStreamBufferedReader} to read in all lines from the specified
	 * file and returns them as a single {@code String}.
	 * 
	 * @param filename The {@code filename} parameter to be passed to
	 *                 {@code fileStreamBufferedReader}.
	 * @return A single {@code String} containing the file's contents, or an empty
	 *         {@code String} if an error occurred or the file is empty.
	 * @see ClasspathLoader#fileStreamBufferedReader(String)
	 */
	public static String fileContents(String filename) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = fileStreamBufferedReader(filename);
		if(br != null) {
			br.lines().forEach(line -> sb.append(line));
			try { br.close(); } catch (IOException iox) { /* Ignore */ }
		}
		return sb.toString();
	}
//	public static ZipInputStream zipFileStream(String file, String path) {
//		InputStream is = ClasspathLoader.class.getClassLoader().getResourceAsStream(file);
//	}

	private static InputStreamReader wrapInputStreamInReader(InputStream resourceAsStream) {
		return resourceAsStream != null ? new InputStreamReader(resourceAsStream) : null;
	}

	private static BufferedReader wrapReaderInBufferedReader(InputStreamReader inputStreamReader) {
		return inputStreamReader != null ? new BufferedReader(inputStreamReader) : null;
	}
}
