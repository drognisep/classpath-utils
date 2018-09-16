package com.saylorsolutions.util.classpath_utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Properties;

/**
 * Provides read-only view of a classpath properties file, as well as
 * convenience methods for common parsing operations on retrieval.
 *
 * @author doug
 */
public class ClasspathProperties {
	public static String[] TRUTHY = { "true", "t", "yes", "y", "1" };
	public static String[] FALSY = { "false", "f", "no", "n", "0" };

	private Properties props;
	private boolean showStacktraces = false;

	/**
	 * Initialize an object with a given named resource.
	 * @param resourceName The filename on the classpath to use as a source for properties information.
	 */
	public ClasspathProperties(String resourceName) {
		InputStream is = ClasspathProperties.class.getClassLoader().getResourceAsStream((resourceName == null ? "" : resourceName));
		this.props = new Properties();
		try {
			if (is != null) {
				this.props.load(is);
			} else {
				throw new FileNotFoundException("Failed to open stream for classpath resource '" + resourceName + "'");
			}
		} catch (IOException iox) {
			System.err.println(this.getClass().getSimpleName() + ": " + iox.getMessage());
			if (showStacktraces) {
				iox.printStackTrace();
			}
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException iox) { /* Ignore me */ }
			}
		}
	}

	/**
	 * Gets a property from the file by key.
	 * @param key The key used to refer to the property to be retrieved.
	 * @return The property retrieved, or {@code ""} if the key could not be found. Never returns null.
	 */
	public String getProperty(String key) {
		String property = props.getProperty(key);
		if (property != null) {
			return property;
		}
		return "";
	}

	/**
	 * Get a property as a boolean value.
	 * @param key The key used to refer to the property to be retrieved.
	 * @return The property retrieved, or {@code false} if the property could not be translated to a boolean.
	 * @see ClasspathProperties#TRUTHY
	 * @see ClasspathProperties#FALSY
	 */
	public boolean getBooleanProperty(String key) {
		return getBooleanProperty(key, false);
	}


	/**
	 * Get a property as a boolean value.
	 * @param key The key used to refer to the property to be retrieved.
	 * @param defaultValue The value to be returned in case the key is not found or the property could not be translated.
	 * @return The property retrieved, or {@code defaultValue} if the property could not be translated to a boolean.
	 * @see ClasspathProperties#TRUTHY
	 * @see ClasspathProperties#FALSY
	 */
	public boolean getBooleanProperty(String key, boolean defaultValue) {
		String property = getProperty(key);
		for(String truth : TRUTHY) {
			if(property.equalsIgnoreCase(truth)) return true;
		}
		for(String fals3 : FALSY) {
			if(property.equalsIgnoreCase(fals3)) return false;
		}
		return defaultValue;
	}

	/**
	 * Get a property as a {@code long} value.
	 * @param key The key used to refer to the property to be retrieved.
	 * @return The property retrieved, or {@code 0L} if the property could not be translated to a {@code long}.
	 */
	public long getIntegerProperty(String key) {
		return getIntegerProperty(key, 0L);
	}

	/**
	 * Get a property as a {@code long} value.
	 * @param key The key used to refer to the property to be retrieved.
	 * @param defaultValue The value to be returned in case the key is not found or the property could not be translated.
	 * @return The property retrieved, or {@code defaultValue} if the property could not be translated to a {@code long}.
	 */
	public long getIntegerProperty(String key, long defaultValue) {
		String property = getProperty(key);
		try {
			return Integer.parseInt(property);
		} catch(NumberFormatException nfx) {
			if (showStacktraces) {
				nfx.printStackTrace();
			}
		}
		return defaultValue;
	}
	
	/**
	 * Get a property as a {@code BigInteger} value.
	 * @param key The key used to refer to the property to be retrieved.
	 * @return The property retrieved, or {@code BigInteger#ZERO} if the property could not be translated to a {@code BigInteger}.
	 * @see BigInteger#ZERO
	 */
	public BigInteger getBigIntegerProperty(String key) {
		return getBigIntegerProperty(key, BigInteger.ZERO);
	}

	/**
	 * Get a property as a {@code BigInteger} value.
	 * @param key The key used to refer to the property to be retrieved.
	 * @param defaultValue The value to be returned in case the key is not found or the property could not be translated.
	 * @return The property retrieved, or {@code defaultValue} if the property could not be translated to a {@code BigInteger}.
	 */
	public BigInteger getBigIntegerProperty(String key, BigInteger defaultValue) {
		String property = getProperty(key);
		try {
			return new BigInteger(property);
		} catch(NumberFormatException nfx) {
			if (showStacktraces) {
				nfx.printStackTrace();
			}
		}
		return defaultValue;
	}
	
	/**
	 * Get a property as a {@code double} value.
	 * @param key The key used to refer to the property to be retrieved.
	 * @return The property retrieved, or {@code 0.0} if the property could not be translated to a {@code double}.
	 */
	public double getDecimalProperty(String key) {
		return getDecimalProperty(key, 0.0);
	}

	/**
	 * Get a property as a {@code double} value.
	 * @param key The key used to refer to the property to be retrieved.
	 * @param defaultValue The value to be returned in case the key is not found or the property could not be translated.
	 * @return The property retrieved, or {@code defaultValue} if the property could not be translated to a {@code double}.
	 */
	public double getDecimalProperty(String key, double defaultValue) {
		String property = getProperty(key);
		try {
			return Double.parseDouble(property);
		} catch(NumberFormatException nfx) {
			if (showStacktraces) {
				nfx.printStackTrace();
			}
		}
		return defaultValue;
	}
	
	/**
	 * Get a property as a {@code BigDecimal} value.
	 * @param key The key used to refer to the property to be retrieved.
	 * @return The property retrieved, or {@code BigDecimal#ZERO} if the property could not be translated to a {@code BigDecimal}.
	 * @see BigDecimal#ZERO
	 */
	public BigDecimal getBigDecimalProperty(String key) {
		return getBigDecimalProperty(key, BigDecimal.ZERO);
	}

	/**
	 * Get a property as a {@code BigDecimal} value.
	 * @param key The key used to refer to the property to be retrieved.
	 * @param defaultValue The value to be returned in case the key is not found or the property could not be translated.
	 * @return The property retrieved, or {@code defaultValue} if the property could not be translated to a {@code BigDecimal}.
	 */
	public BigDecimal getBigDecimalProperty(String key, BigDecimal defaultValue) {
		String property = getProperty(key);
		try {
			return new BigDecimal(property);
		} catch(NumberFormatException nfx) {
			if (showStacktraces) {
				nfx.printStackTrace();
			}
		}
		return defaultValue;
	}

	/**
	 * @return Whether or not stack traces are being printed when conversion errors
	 * or resource loading/interaction errors are occurring.
	 */
	public boolean showStacktraces() {
		return showStacktraces;
	}

	/**
	 * Used to enable/disable debug stack trace behavior.
	 * @param showStacktraces Whether or not stack traces should be printed.
	 */
	public void setShowStacktraces(boolean showStacktraces) {
		this.showStacktraces = showStacktraces;
	}
}
