// Copyright (c) 2003-2009, Jodd Team (jodd.org). All Rights Reserved.

package jodd.util;

import jodd.io.StreamUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Misc java.util.Properties utils.
 */
public class PropertiesUtil {

	// ---------------------------------------------------------------- to/from files

	/**
	 * Create properties from the file.
	 *
	 * @param fileName properties file name to load
	 *
	 * @exception IOException
	 */
	public static Properties createFromFile(String fileName) throws IOException {
		return createFromFile(new File(fileName));
	}

	/**
	 * Create properties from the file.
	 *
	 * @param file properties file to load
	 *
	 * @exception IOException
	 */
	public static Properties createFromFile(File file) throws IOException {
		Properties prop = new Properties();
		loadFromFile(prop, file);
		return prop;
	}

	/**
	 * Loads properties from the file. Properties are appended to the existing
	 * properties object.
	 *
	 * @param p        properties to fill in
	 * @param fileName properties file name to load
	 *
	 * @exception IOException
	 */
	public static void loadFromFile(Properties p, String fileName) throws IOException {
		loadFromFile(p, new File(fileName));
	}

	/**
	 * Loads properties from the file. Properties are appended to the existing
	 * properties object.
	 *
	 * @param p      properties to fill in
	 * @param file   file to read properties from
	 *
	 * @exception IOException
	 */
	public static void loadFromFile(Properties p, File file) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			p.load(fis);
		} finally {
			StreamUtil.close(fis);
		}
	}


	/**
	 * Writes properties to a file.
	 *
	 * @param p        properties to write to file
	 * @param fileName destination file name
	 *
	 * @exception IOException
	 */
	public static void writeToFile(Properties p, String fileName) throws IOException {
		writeToFile(p, new File(fileName), null);
	}

	/**
	 * Writes properties to a file.
	 *
	 * @param p        properties to write to file
	 * @param fileName destination file name
	 * @param header   optional header
	 *
	 * @exception IOException
	 */
	public static void writeToFile(Properties p, String fileName, String header) throws IOException {
		writeToFile(p, new File(fileName), header);
	}

	/**
	 * Writes properties to a file.
	 *
	 * @param p      properties to write to file
	 * @param file   destination file
	 *
	 * @exception IOException
	 */
	public static void writeToFile(Properties p, File file) throws IOException {
		writeToFile(p, file, null);
	}

	/**
	 * Writes properties to a file.
	 *
	 * @param p      properties to write to file
	 * @param file   destination file
	 * @param header optional header
	 *
	 * @exception IOException
	 */
	public static void writeToFile(Properties p, File file, String header) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			p.store(fos, header);
		} finally {
			StreamUtil.close(fos);
		}
	}


	// ---------------------------------------------------------------- subsets

	/**
	 * Creates new Properties object from the original one, by copying
	 * those properties that have specified first part of the key name.
	 * Prefix may be optionally stripped during this process.
	 *
	 * @param p         source properties, from which new object will be created
	 * @param prefix    key names prefix 
	 *
	 * @return subset properties
	 */
	public static Properties subset(Properties p, String prefix, boolean stripPrefix) {
		if (StringUtil.isBlank(prefix) == true) {
			return p;
		}
		if (prefix.endsWith(StringPool.DOT) == false) {
			prefix += '.';
		}
		Properties result = new Properties();
		int baseLen = prefix.length();
		for (Object o : p.keySet()) {
			String key = (String) o;
			if (key.startsWith(prefix) == true) {
				result.setProperty(stripPrefix == true ? key.substring(baseLen) : key, p.getProperty(key));
			}
		}
		return result;
	}

}