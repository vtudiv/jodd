// Copyright (c) 2003-2009, Jodd Team (jodd.org). All Rights Reserved.

package jodd.util;

import jodd.typeconverter.StringArrayConverter;
import static jodd.util.StringPool.EMPTY;

import java.io.UnsupportedEncodingException;


/**
 * Various String utilities.
 */
public class StringUtil {

	// ---------------------------------------------------------------- replace

	/**
	 * Replaces all occurrences of a certain pattern in a string with a
	 * replacement string. This is the fastest replace function known to author.
	 *
	 * @param s      string to be inspected
	 * @param sub    string pattern to be replaced
	 * @param with   string that should go where the pattern was
	 */
	public static String replace(String s, String sub, String with) {
		int c = 0;
		int i = s.indexOf(sub, c);
		if (i == -1) {
			return s;
		}
		int sLen = s.length();
		StringBuilder buf = new StringBuilder(sLen + with.length());
		do {
			 buf.append(s.substring(c,i));
			 buf.append(with);
			 c = i + sub.length();
		 } while ((i = s.indexOf(sub, c)) != -1);
		 if (c < sLen) {
			 buf.append(s.substring(c, sLen));
		 }
		 return buf.toString();
	}

	/**
	 * Replaces all occurrences of a character in a string.
	 *
	 * @param s      input string
	 * @param sub    character to replace
	 * @param with   character to replace with
	 */
	public static String replaceChar(String s, char sub, char with) {
		char[] str = s.toCharArray();
		for (int i = 0; i < str.length; i++) {
			if (str[i] == sub) {
				str[i] = with;
			}
		}
		return new String(str);
	}

	/**
	 * Replaces all occurrences of a characters in a string.
	 *
	 * @param s      input string
	 * @param sub    characters to replace
	 * @param with   characters to replace with
	 */
	public static String replaceChars(String s, char[] sub, char[] with) {
		char[] str = s.toCharArray();
		for (int i = 0; i < str.length; i++) {
			char c = str[i];
			for (int j = 0; j < sub.length; j++) {
			    if (c == sub[j]) {
					str[i] = with[j];
					break;
				}
			}
		}
		return new String(str);
	}

	/**
	 * Replaces the very first occurrence of a substring with supplied string.
	 *
	 * @param s      source string
	 * @param sub    substring to replace
	 * @param with   substring to replace with
	 */
	public static String replaceFirst(String s, String sub, String with) {
		int i = s.indexOf(sub);
		if (i == -1) {
			return s;
		}
		return s.substring(0, i) + with + s.substring(i + sub.length());
	}

	/**
	 * Replaces the very first occurrence of a character in a string.
	 *
	 * @param s      string
	 * @param sub    char to replace
	 * @param with   char to replace with
	 */
	public static String replaceFirst(String s, char sub, char with) {
		char[] str = s.toCharArray();
		for (int i = 0; i < str.length; i++) {
			if (str[i] == sub) {
				str[i] = with;
				break;
			}
		}
		return new String(str);
	}

	/**
	 * Replaces the very last occurrence of a substring with supplied string.
	 *
	 * @param s      source string
	 * @param sub    substring to replace
	 * @param with   substring to replace with
	 */
	public static String replaceLast(String s, String sub, String with) {
		int i = s.lastIndexOf(sub);
		if (i == -1) {
			return s;
		}
		return s.substring(0, i) + with + s.substring(i + sub.length());
	}

	/**
	 * Replaces the very last occurrence of a character in a string.
	 *
	 * @param s      string
	 * @param sub    char to replace
	 * @param with   char to replace with
	 */
	public static String replaceLast(String s, char sub, char with) {
		char[] str = s.toCharArray();
		for (int i = str.length - 1; i >= 0; i--) {
			if (str[i] == sub) {
				str[i] = with;
				break;
			}
		}
		return new String(str);
	}

	// ---------------------------------------------------------------- remove

	/**
	 * Removes all substring occurrences from the string.
	 *
	 * @param s      source string
	 * @param sub    substring to remove
	 */
	public static String remove(String s, String sub) {
		int c = 0;
		int sublen = sub.length();
		if (sublen == 0) {
			return s;
		}
		int i = s.indexOf(sub, c);
		if (i == -1) {
			return s;
		}
		StringBuilder buf = new StringBuilder(s.length());
		do {
			 buf.append(s.substring(c, i));
			 c = i + sublen;
		 } while ((i = s.indexOf(sub, c)) != -1);
		 if (c < s.length()) {
			 buf.append(s.substring(c, s.length()));
		 }
		 return buf.toString();
	}

	/**
	 * Removes all characters contained in provided string.
	 *
	 * @param src    source string
	 * @param chars  string containing characters to remove
	 */
	public static String removeChars(String src, String chars) {
		int i = src.length();
		StringBuilder stringbuffer = new StringBuilder(i);
		for (int j = 0; j < i; j++) {
			char c = src.charAt(j);
			if (chars.indexOf(c) == -1) {
				stringbuffer.append(c);
			}
		}
		return stringbuffer.toString();
	}


	/**
	 * Removes set of characters from string.
	 *
	 * @param src    string
	 * @param chars  character to remove
	 */
	public static String removeChars(String src, char[] chars) {
		int i = src.length();
		StringBuilder stringbuffer = new StringBuilder(i);
		mainloop:
		for (int j = 0; j < i; j++) {
			char c = src.charAt(j);
			for (char aChar : chars) {
				if (c == aChar) {
					continue mainloop;
				}
			}
			stringbuffer.append(c);
		}
		return stringbuffer.toString();
	}

	/**
	 * Removes a single character from string.
	 *
	 * @param src    source string
	 * @param chars  character to remove
	 */
	public static String remove(String src, char chars) {
		int i = src.length();
		StringBuilder stringbuffer = new StringBuilder(i);
		for (int j = 0; j < i; j++) {
			char c = src.charAt(j);
			if (c == chars) {
				continue;
			}
			stringbuffer.append(c);
		}
		return stringbuffer.toString();
	}

	// ---------------------------------------------------------------- miscellaneous

	/**
	 * Compares 2 strings. If one of the strings is <code>null</code>, <code>false</code> is returned. if
	 * both string are <code>null</code>, <code>true</code> is returned.
	 *
	 * @param s1     first string to compare
	 * @param s2     second string
	 *
	 * @return <code>true</code> if strings are equal, otherwise <code>false</code>
	 */
	public static boolean equals(String s1, String s2) {
		return ObjectUtil.equals(s1, s2);
	}

	/**
	 * Determines if a string is empty (<code>null</code> or zero-length).
	 */
	public static boolean isEmpty(String string) {
		return ((string == null) || (string.length() == 0));
	}

	/**
	 * Determines if a string is blank (<code>null</code> or {@link #containsOnlyWhitespaces(String)}).
	 */
	public static boolean isBlank(String string) {
		return ((string == null) || containsOnlyWhitespaces(string));
	}

	/**
	 * Returns <code>true</code> if string contains only spaces.
	 */
	public static boolean containsOnlyWhitespaces(String string) {
		int size = string.length();
		for (int i= 0; i < size; i++) {
			char c = string.charAt(i);
			if (CharUtil.isWhitespace(c) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines if a string is not empty.
	 */
	public static boolean isNotEmpty(String string) {
		return string != null && string.length() > 0;
	}


	/**
	 * Converts safely an object to a string. If object is <code>null</code> it will be
	 * not converted.
	 */
	public static String toString(Object obj) {
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	/**
	 * Converts safely an object to a string. If object is <code>null</code> an empty
	 * string is returned.
	 */
	public static String toNotNullString(Object obj) {
		if (obj == null) {
			return EMPTY;
		}
		return obj.toString();
	}

	/**
	 * Converts an object to a String Array.
	 */
	public static String[] toStringArray(Object obj) {
		return StringArrayConverter.valueOf(obj);
	}

	// ---------------------------------------------------------------- captialize

	/**
	 * Capitalizes a string, changing the first letter to
	 * upper case. No other letters are changed.
	 *
	 * @param str   string to capitalize, may be null
	 * @see #uncapitalize(String)
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(true, str);
	}

	/**
	 * Uncapitalizes a <code>String</code>, changing the first letter to
	 * lower case. No other letters are changed.
	 *
	 * @param str the String to uncapitalize, may be null
	 * @return the uncapitalized String, <code>null</code> if null
	 * @see #capitalize(String) 
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(false, str);
	}

	/**
	 * Internal method for changing the first character case. It is significantly
	 * faster using StringBuffers then just simply Strings.
	 */
	private static String changeFirstCharacterCase(boolean capitalize, String str) {
		int strLen = str.length();
		if (strLen == 0) {
			return str;
		}
		StringBuilder buf = new StringBuilder(strLen);
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}


	// ---------------------------------------------------------------- truncate


	/**
	 * Sets the maximum length of the string. Longer strings will be simply truncated.
	 */
	public static String truncate(String string, int length) {
		if (string.length() > length) {
			string = string.substring(0, length);
		}
		return string;
	}

	// ---------------------------------------------------------------- split

	/**
	 * Splits a string in several parts (tokens) that are separated by delimiter.
	 * Delimiter is <b>always</b> surrounded by two strings! If there is no
	 * content between two delimiters, empty string will be returned for that
	 * token. Therefore, the length of the returned array will always be:
	 * #delimiters + 1.
	 * <p>
	 * Method is much, much faster then regexp <code>String.split()</code>,
	 * and a bit faster then <code>StringTokenizer</code>.
	 *
	 * @param src       string to split
	 * @param delimeter split delimiter
	 *
	 * @return array of split strings
	 */
	public static String[] split(String src, String delimeter) {
		int maxparts = (src.length() / delimeter.length()) + 2;		// one more for the last
		int[] positions = new int[maxparts];
		int dellen = delimeter.length();

		int i, j = 0;
		int count = 0;
		positions[0] = - dellen;
		while ((i = src.indexOf(delimeter, j)) != -1) {
			count++;
			positions[count] = i;
			j = i + dellen;
		}
		count++;
		positions[count] = src.length();

		String[] result = new String[count];

		for (i = 0; i < count; i++) {
			result[i] = src.substring(positions[i] + dellen, positions[i + 1]);
		}
		return result;
	}

	/**
	 * Splits a string in several parts (tokens) that are separated by delimiter
	 * characters. Delimiter may contains any number of character, and it is
	 * always surrounded by two strings.
	 *
	 * @param src    source to examine
	 * @param d      string with delimiter characters
	 *
	 * @return array of tokens
	 */
	public static String[] splitc(String src, String d) {
		if ((d.length() == 0) || (src.length() == 0) ) {
			return new String[] {src};
		}
		char[] delimiters = d.toCharArray();
		char[] srcc = src.toCharArray();

		int maxparts = srcc.length + 1;
		int[] start = new int[maxparts];
		int[] end = new int[maxparts];

		int count = 0;

		start[0] = 0;
		int s = 0, e;
		if (CharUtil.equalsOne(srcc[0], delimiters) == true) {	// string starts with delimiter
			end[0] = 0;
			count++;
			s = CharUtil.findFirstDiff(srcc, 1, delimiters);
			if (s == -1) {							// nothing after delimiters
				return new String[] {EMPTY, EMPTY};
			}
			start[1] = s;							// new start
		}
		while (true) {
			// find new end
			e = CharUtil.findFirstEqual(srcc, s, delimiters);
			if (e == -1) {
				end[count] = srcc.length;
				break;
			}
			end[count] = e;

			// find new start
			count++;
			s = CharUtil.findFirstDiff(srcc, e, delimiters);
			if (s == -1) {
				start[count] = end[count] = srcc.length;
				break;
			}
			start[count] = s;
		}
		count++;
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = src.substring(start[i], end[i]);
		}
		return result;
	}

	/**
	 * Splits a string in several parts (tokens) that are separated by single delimiter
	 * characters. Delimiter may contains any number of character, and it is
	 * always surrounded by two strings.
	 *
	 * @param src           source to examine
	 * @param delimiter     delimiter character
	 *
	 * @return array of tokens
	 */
	public static String[] splitc(String src, char delimiter) {
		if (src.length() == 0) {
			return new String[] {EMPTY};
		}
		char[] srcc = src.toCharArray();

		int maxparts = srcc.length + 1;
		int[] start = new int[maxparts];
		int[] end = new int[maxparts];

		int count = 0;

		start[0] = 0;
		int s = 0, e;
		if (srcc[0] == delimiter) {	// string starts with delimiter
			end[0] = 0;
			count++;
			s = CharUtil.findFirstDiff(srcc, 1, delimiter);
			if (s == -1) {							// nothing after delimiters
				return new String[] {EMPTY, EMPTY};
			}
			start[1] = s;							// new start
		}
		while (true) {
			// find new end
			e = CharUtil.findFirstEqual(srcc, s, delimiter);
			if (e == -1) {
				end[count] = srcc.length;
				break;
			}
			end[count] = e;

			// find new start
			count++;
			s = CharUtil.findFirstDiff(srcc, e, delimiter);
			if (s == -1) {
				start[count] = end[count] = srcc.length;
				break;
			}
			start[count] = s;
		}
		count++;
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = src.substring(start[i], end[i]);
		}
		return result;
	}


	// ---------------------------------------------------------------- indexof and ignore cases

	/**
	 * Finds first occurrence of a substring in the given source but within limited range [start, end).
	 * It is fastest possible code, but still original <code>String.indexOf(String, int)</code>
	 * is much faster (since it uses char[] value directly) and should be used when no range is needed.
	 *
	 * @param src		source string for examination
	 * @param sub		substring to find
	 * @param startIndex	starting index
	 * @param endIndex		ending index
	 * @return index of founded substring or -1 if substring not found
	 */
	public static int indexOf(String src, String sub, int startIndex, int endIndex) {
		if (startIndex < 0) {
			startIndex = 0;
		}
		int srclen = src.length();
		if (endIndex > srclen) {
			endIndex = srclen;
		}
		int sublen = sub.length();
		if (sublen == 0) {
			return startIndex > srclen ? srclen : startIndex;
		}

		int total = endIndex - sublen + 1;
		char c = sub.charAt(0);
	mainloop:
		for (int i = startIndex; i < total; i++) {
			if (src.charAt(i) != c) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				if (sub.charAt(j) != src.charAt(k)) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}

	/**
	 * Finds the first occurrence of a character in the given source but within limited range (start, end].
	 */
	public static int indexOf(String src, char c, int startIndex, int endIndex) {
		if (startIndex < 0) {
			startIndex = 0;
		}
		int srclen = src.length();
		if (endIndex > srclen) {
			endIndex = srclen;
		}
		for (int i = startIndex; i < endIndex; i++) {
			if (src.charAt(i) == c) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds the first occurrence of a character in the given source but within limited range (start, end].
	 */
	public static int indexOfIgnoreCase(String src, char c, int startIndex, int endIndex) {
		if (startIndex < 0) {
			startIndex = 0;
		}
		int srclen = src.length();
		if (endIndex > srclen) {
			endIndex = srclen;
		}
		c = Character.toLowerCase(c);
		for (int i = startIndex; i < endIndex; i++) {
			if (Character.toLowerCase(src.charAt(i)) == c) {
				return i;
			}
		}
		return -1;
	}



	/**
	 * Finds first index of a substring in the given source string with ignored case.
	 *
	 * @param src    source string for examination
	 * @param subS   substring to find
	 *
	 * @return index of founded substring or -1 if substring is not found
	 * @see #indexOfIgnoreCase(String, String, int)
	 */
	public static int indexOfIgnoreCase(String src, String subS) {
		return indexOfIgnoreCase(src, subS, 0, src.length());
	}

	/**
	 * Finds first index of a substring in the given source string with ignored
	 * case. This seems to be the fastest way doing this, with common string
	 * length and content (of course, with no use of Boyer-Mayer type of
	 * algorithms). Other implementations are slower: getting char array first,
	 * lower casing the source string, using String.regionMatch etc.
	 *
	 * @param src        source string for examination
	 * @param subS       substring to find
	 * @param startIndex starting index from where search begins
	 *
	 * @return index of founded substring or -1 if substring is not found
	 */
	public static int indexOfIgnoreCase(String src, String subS, int startIndex) {
		return indexOfIgnoreCase(src, subS, startIndex, src.length());
	}
	/**
	 * Finds first index of a substring in the given source string and range with
	 * ignored case.
	 *
	 * @param src		source string for examination
	 * @param sub		substring to find
	 * @param startIndex	starting index from where search begins
	 * @param endIndex		endint index
	 * @return index of founded substring or -1 if substring is not found
	 * @see #indexOfIgnoreCase(String, String, int)
	 */
	public static int indexOfIgnoreCase(String src, String sub, int startIndex, int endIndex) {
		if (startIndex < 0) {
			startIndex = 0;
		}
		int srclen = src.length();
		if (endIndex > srclen) {
			endIndex = srclen;
		}

		int sublen = sub.length();
		if (sublen == 0) {
			return startIndex > srclen ? srclen : startIndex;
		}
		sub = sub.toLowerCase();
		int total = endIndex - sublen + 1;
		char c = sub.charAt(0);
	mainloop:
		for (int i = startIndex; i < total; i++) {
			if (Character.toLowerCase(src.charAt(i)) != c) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				char source = Character.toLowerCase(src.charAt(k));
				if (sub.charAt(j) != source) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}


	/**
	 * Finds last index of a substring in the given source string with ignored
	 * case.
	 *
	 * @param s      source string
	 * @param subS   substring to find
	 *
	 * @return last index of founded substring or -1 if substring is not found
	 * @see #indexOfIgnoreCase(String, String, int)
	 * @see #lastIndexOfIgnoreCase(String, String, int)
	 */
	public static int lastIndexOfIgnoreCase(String s, String subS) {
		return lastIndexOfIgnoreCase(s, subS, s.length(), 0);
	}

	/**
	 * Finds last index of a substring in the given source string with ignored
	 * case.
	 *
	 * @param src        source string for examination
	 * @param subS       substring to find
	 * @param startIndex starting index from where search begins
	 *
	 * @return last index of founded substring or -1 if substring is not found
	 * @see #indexOfIgnoreCase(String, String, int)
	 */
	public static int lastIndexOfIgnoreCase(String src, String subS, int startIndex) {
		return lastIndexOfIgnoreCase(src, subS, startIndex, 0);
	}
	/**
	 * Finds last index of a substring in the given source string with ignored
	 * case in specified range.
	 *
	 * @param src		source to examine
	 * @param sub		substring to find
	 * @param startIndex	starting index
	 * @param endIndex		end index
	 * @return last index of founded substring or -1 if substring is not found
	 */
	public static int lastIndexOfIgnoreCase(String src, String sub, int startIndex, int endIndex) {
		int sublen = sub.length();
		int srclen = src.length();
		if (sublen == 0) {
			return startIndex > srclen ? srclen : (startIndex < -1 ? -1 : startIndex);
		}
		sub = sub.toLowerCase();
		int total = srclen - sublen;
		if (total < 0) {
			return -1;
		}
		if (startIndex >= total) {
			startIndex = total;
		}
		if (endIndex < 0) {
			endIndex = 0;
		}
		char c = sub.charAt(0);
	mainloop:
		for (int i = startIndex; i >= endIndex; i--) {
			if (Character.toLowerCase(src.charAt(i)) != c) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				char source = Character.toLowerCase(src.charAt(k));
				if (sub.charAt(j) != source) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}

	/**
	 * Finds last index of a substring in the given source string in specified range [end, start]
	 * See {@link #indexOf(String, String, int, int)}  for details about the speed.
	 *
	 * @param src		source to examine
	 * @param sub		substring to find
	 * @param startIndex	starting index
	 * @param endIndex		end index
	 * @return last index of founded substring or -1 if substring is not found
	 */
	public static int lastIndexOf(String src, String sub, int startIndex, int endIndex) {
		int sublen = sub.length();
		int srclen = src.length();
		if (sublen == 0) {
			return startIndex > srclen ? srclen : (startIndex < -1 ? -1 : startIndex);
		}
		int total = srclen - sublen;
		if (total < 0) {
			return -1;
		}
		if (startIndex >= total) {
			startIndex = total;
		}
		if (endIndex < 0) {
			endIndex = 0;
		}
		char c = sub.charAt(0);
	mainloop:
		for (int i = startIndex; i >= endIndex; i--) {
			if (src.charAt(i) != c) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				if (sub.charAt(j) != src.charAt(k)) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}

	/**
	 * Finds last index of a character in the given source string in specified range [end, start]
	 */
	public static int lastIndexOf(String src, char c, int startIndex, int endIndex) {
		int total = src.length() - 1;
		if (total < 0) {
			return -1;
		}
		if (startIndex >= total) {
			startIndex = total;
		}
		if (endIndex < 0) {
			endIndex = 0;
		}
		for (int i = startIndex; i >= endIndex; i--) {
			if (src.charAt(i) == c) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds last index of a character in the given source string in specified range [end, start]
	 */
	public static int lastIndexOfIgnoreCase(String src, char c, int startIndex, int endIndex) {
		int total = src.length() - 1;
		if (total < 0) {
			return -1;
		}
		if (startIndex >= total) {
			startIndex = total;
		}
		if (endIndex < 0) {
			endIndex = 0;
		}
		c = Character.toLowerCase(c);
		for (int i = startIndex; i >= endIndex; i--) {
			if (Character.toLowerCase(src.charAt(i)) == c) {
				return i;
			}
		}
		return -1;
	}



	/**
	 * Tests if this string starts with the specified prefix with ignored case.
	 *
	 * @param src    source string to test
	 * @param subS   starting substring
	 *
	 * @return <code>true</code> if the character sequence represented by the argument is
	 *         a prefix of the character sequence represented by this string;
	 *         <code>false</code> otherwise.
	 */
	public static boolean startsWithIgnoreCase(String src, String subS) {
		return startsWithIgnoreCase(src, subS, 0);
	}

	/**
	 * Tests if this string starts with the specified prefix with ignored case
	 * and with the specified prefix beginning a specified index.
	 *
	 * @param src        source string to test
	 * @param subS       starting substring
	 * @param startIndex index from where to test
	 *
	 * @return <code>true</code> if the character sequence represented by the argument is
	 *         a prefix of the character sequence represented by this string;
	 *         <code>false</code> otherwise.
	 */
	public static boolean startsWithIgnoreCase(String src, String subS, int startIndex) {
		String sub = subS.toLowerCase();
		int sublen = sub.length();
		if (startIndex + sublen > src.length()) {
			return false;
		}
		int j = 0;
		int i = startIndex;
		while (j < sublen) {
			char source = Character.toLowerCase(src.charAt(i));
			if (sub.charAt(j) != source) {
				return false;
			}
			j++; i++;
		}
		return true;
	}

	/**
	 * Tests if this string ends with the specified suffix.
	 *
	 * @param src    String to test
	 * @param subS   suffix
	 *
	 * @return <code>true</code> if the character sequence represented by the argument is
	 *         a suffix of the character sequence represented by this object;
	 *         <code>false</code> otherwise.
	 */
	public static boolean endsWithIgnoreCase(String src, String subS) {
		String sub = subS.toLowerCase();
		int sublen = sub.length();
		int j = 0;
		int i = src.length() - sublen;
		if (i < 0) {
			return false;
		}
		while (j < sublen) {
			char source = Character.toLowerCase(src.charAt(i));
			if (sub.charAt(j) != source) {
				return false;
			}
			j++; i++;
		}
		return true;
	}


	// ---------------------------------------------------------------- count substrings

	/**
	 * Counts substring occurrences in a source string.
	 *
	 * @param source	source string
	 * @param sub		substring to count
	 * @return			number of substring occurrences
	 */
	public static int count(String source, String sub) {
		return count(source, sub, 0);
	}
	public static int count(String source, String sub, int start) {
		int count = 0;
		int j = start;
		int sublen = sub.length();
		if (sublen == 0) {
			return 0;
		}
		while (true) {
			int i = source.indexOf(sub, j);
			if (i == -1) {
				break;
			}
			count++;
			j = i + sublen;
		}
		return count;
	}

	public static int count(String source, char c) {
		return count(source, c, 0);
	}
	public static int count(String source, char c, int start) {
		int count = 0;
		int j = start;
		while (true) {
			int i = source.indexOf(c, j);
			if (i == -1) {
				break;
			}
			count++;
			j = i + 1;
		}
		return count;
	}



	/**
	 * Count substring occurrences in a source string, ignoring case.
	 *
	 * @param source	source string
	 * @param sub		substring to count
	 * @return			number of substring occurrences
	 */
	public static int countIgnoreCase(String source, String sub) {
		int count = 0;
		int j = 0;
		int sublen = sub.length();
		if (sublen == 0) {
			return 0;
		}
		while (true) {
			int i = indexOfIgnoreCase(source, sub, j);
			if (i == -1) {
				break;
			}
			count++;
			j = i + sublen;
		}
		return count;
	}

	// ---------------------------------------------------------------- string arrays

	/**
	 * Finds the very first index of a substring from the specified array. It
	 * returns an int[2] where int[0] represents the substring index and int[1]
	 * represents position where substring was found. Returns <code>null</code> if
	 * noting found.
	 *
	 * @param s      source string
	 * @param arr    string array
	 */
	public static int[] indexOf(String s, String arr[]) {
		return indexOf(s, arr, 0);
	}
	/**
	 * Finds the very first index of a substring from the specified array. It
	 * returns an int[2] where int[0] represents the substring index and int[1]
	 * represents position where substring was found. Returns <code>null</code>
	 * if noting found.
	 *
	 * @param s      source string
	 * @param arr    string array
	 * @param start  starting position
	 */
	public static int[] indexOf(String s, String arr[], int start) {
		int arrLen = arr.length;
		int index = Integer.MAX_VALUE;
		int last = -1;
		for (int j = 0; j < arrLen; j++) {
			int i = s.indexOf(arr[j], start);
			if (i != -1) {
				if (i < index) {
					index = i;
					last = j;
				}
			}
		}
		return last == -1 ? null : new int[] {last, index};
	}

	/**
	 * Finds the very first index of a substring from the specified array. It
	 * returns an int[2] where int[0] represents the substring index and int[1]
	 * represents position where substring was found. Returns <code>null</code>
	 * if noting found.
	 *
	 * @param s      source string
	 * @param arr    string array
	 */
	public static int[] indexOfIgnoreCase(String s, String arr[]) {
		return indexOfIgnoreCase(s, arr, 0);
	}
	/**
	 * Finds the very first index of a substring from the specified array. It
	 * returns an int[2] where int[0] represents the substring index and int[1]
	 * represents position where substring was found. Returns <code>null</code>
	 * if noting found.
	 *
	 * @param s      source string
	 * @param arr    string array
	 * @param start  starting position
	 */
	public static int[] indexOfIgnoreCase(String s, String arr[], int start) {
		int arrLen = arr.length;
		int index = Integer.MAX_VALUE;
		int last = -1;
		for (int j = 0; j < arrLen; j++) {
			int i = indexOfIgnoreCase(s, arr[j], start);
			if (i != -1) {
				if (i < index) {
					index = i;
					last = j;
				}
			}
		}
		return last == -1 ? null : new int[] {last, index};
	}

	/**
	 * Finds the very last index of a substring from the specified array. It
	 * returns an int[2] where int[0] represents the substring index and int[1]
	 * represents position where substring was found. Returns <code>null</code>
	 * if noting found.
	 *
	 * @param s      source string
	 * @param arr    string array
	 */
	public static int[] lastIndexOf(String s, String arr[]) {
		return lastIndexOf(s, arr, s.length());
	}
	/**
	 * Finds the very last index of a substring from the specified array. It
	 * returns an int[2] where int[0] represents the substring index and int[1]
	 * represents position where substring was found. Returns <code>null</code>
	 * if noting found.
	 *
	 * @param s         source string
	 * @param arr       string array
	 * @param fromIndex starting position
	 */
	public static int[] lastIndexOf(String s, String arr[], int fromIndex) {
		int arrLen = arr.length;
		int index = -1;
		int last = -1;
		for (int j = 0; j < arrLen; j++) {
			int i = s.lastIndexOf(arr[j], fromIndex);
			if (i != -1) {
				if (i > index) {
					index = i;
					last = j;
				}
			}
		}
		return last == -1 ? null : new int[] {last, index};
	}

	/**
	 * Finds the very last index of a substring from the specified array. It
	 * returns an int[2] where int[0] represents the substring index and int[1]
	 * represents position where substring was found. Returns <code>null</code>
	 * if noting found.
	 *
	 * @param s      source string
	 * @param arr    string array
	 *
	 * @return int[2]
	 */
	public static int[] lastIndexOfIgnoreCase(String s, String arr[]) {
		return lastIndexOfIgnoreCase(s, arr, s.length());
	}
	/**
	 * Finds the very last index of a substring from the specified array. It
	 * returns an int[2] where int[0] represents the substring index and int[1]
	 * represents position where substring was found. Returns <code>null</code>
	 * if noting found.
	 *
	 * @param s         source string
	 * @param arr       string array
	 * @param fromIndex starting position
	 */
	public static int[] lastIndexOfIgnoreCase(String s, String arr[], int fromIndex) {
		int arrLen = arr.length;
		int index = -1;
		int last = -1;
		for (int j = 0; j < arrLen; j++) {
			int i = lastIndexOfIgnoreCase(s, arr[j], fromIndex);
			if (i != -1) {
				if (i > index) {
					index = i;
					last = j;
				}
			}
		}
		return last == -1 ? null : new int[] {last, index};
	}

	/**
	 * Compares two string arrays.
	 *
	 * @param as     first string array
	 * @param as1    second string array
	 *
	 * @return <code>true</code> if all array elements matches
	 */
	public static boolean equals(String as[], String as1[]) {
	    if (as.length != as1.length) {
	        return false;
	    }
	    for (int i = 0; i < as.length; i++) {
	        if (as[i].equals(as1[i]) == false) {
	            return false;
	        }
	    }
	    return true;
	}
	/**
	 * Compares two string arrays.
	 *
	 * @param as     first string array
	 * @param as1    second string array
	 *
	 * @return true if all array elements matches
	 */
	public static boolean equalsIgnoreCase(String as[], String as1[]) {
		if (as.length != as1.length) {
			return false;
		}
		for (int i = 0; i < as.length; i++) {
			if (as[i].equalsIgnoreCase(as1[i]) == false) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Replaces many substring at once. Order of string array is important.
	 *
	 * @param s      source string
	 * @param sub    substrings array
	 * @param with   replace with array
	 *
	 * @return string with all occurrences of substrings replaced
	 */
	public static String replace(String s, String[] sub, String[] with) {
		if ((sub.length != with.length) || (sub.length == 0)) {
			return s;
		}
		int start = 0;
		StringBuilder buf = new StringBuilder(s.length());
		while (true) {
			int[] res = indexOf(s, sub, start);
			if (res == null) {
				break;
			}
			int end = res[1];
			buf.append(s.substring(start, end));
			buf.append(with[res[0]]);
			start = end + sub[res[0]].length();
		}
		buf.append(s.substring(start));
		return buf.toString();
	}

	/**
	 * Replaces many substring at once. Order of string array is important.
	 *
	 * @param s      source string
	 * @param sub    substrings array
	 * @param with   replace with array
	 *
	 * @return string with all occurrences of substrings replaced
	 */
	public static String replaceIgnoreCase(String s, String[] sub, String[] with) {
		if ((sub.length != with.length) || (sub.length == 0)) {
			return s;
		}
		int start = 0;
		StringBuilder buf = new StringBuilder(s.length());
		while (true) {
			int[] res = indexOfIgnoreCase(s, sub, start);
			if (res == null) {
				break;
			}
			int end = res[1];
			buf.append(s.substring(start, end));
			buf.append(with[res[0]]);
			start = end + sub[0].length();
		}
		buf.append(s.substring(start));
		return buf.toString();
	}


	// ---------------------------------------------------------------- the one

	/**
	 * Compares string with at least one from the provided array.
	 * If at least one equal string is found, returns its index.
	 * Otherwise, <code>-1</code> is returned.
	 */
	public static int equalsOne(String src, String[] dest) {
		for (int i = 0; i < dest.length; i++) {
			if (src.equals(dest[i])) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Compares string with at least one from the provided array, ignoring case.
	 * If at least one equal string is found, it returns its index.
	 * Otherwise, <code>-1</code> is returned.
	 */
	public static int equalsOneIgnoreCase(String src, String[] dest) {
		for (int i = 0; i < dest.length; i++) {
			if (src.equalsIgnoreCase(dest[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Checks if string starts with at least one string from the provided array.
	 * If at least one string is matched, it returns its index.
	 * Otherwise, <code>-1</code> is returned.
	 */
	public static int startsWithOne(String src, String[] dest) {
		for (int i = 0; i < dest.length; i++) {
			if (src.startsWith(dest[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Checks if string starts with at least one string from the provided array.
	 * If at least one string is matched, it returns its index.
	 * Otherwise, <code>-1</code> is returned.
	 */
	public static int startsWithOneIgnoreCase(String src, String[] dest) {
		for (int i = 0; i < dest.length; i++) {
			if (startsWithIgnoreCase(src, dest[i])) {
				return i;
			}
		}
		return -1;
	}


	/**
	 * Checks if string ends with at least one string from the provided array.
	 * If at least one string is matched, it returns its index.
	 * Otherwise, <code>-1</code> is returned.
	 */
	public static int endsWithOne(String src, String[] dest) {
		for (int i = 0; i < dest.length; i++) {
			if (src.endsWith(dest[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Checks if string ends with at least one string from the provided array.
	 * If at least one string is matched, it returns its index.
	 * Otherwise, <code>-1</code> is returned.
	 */
	public static int endsWithOneIgnoreCase(String src, String[] dest) {
		for (int i = 0; i < dest.length; i++) {
			if (endsWithIgnoreCase(src, dest[i])) {
				return i;
			}
		}
		return -1;
	}


	// ---------------------------------------------------------------- char based


	public static int indexOfChars(String string, String chars) {
		return indexOfChars(string, chars, 0);
	}

	/**
	 * Returns the very first index of any char from provided string, starting from specified index offset.
	 * Returns index of founded char, or <code>-1</code> if nothing found.
	 */
	public static int indexOfChars(String string, String chars, int startindex) {
		int stringLen = string.length();
		int charsLen = chars.length();
		if (startindex < 0) {
			startindex = 0;
		}
		for (int i = startindex; i < stringLen; i++) {
			char c = string.charAt(i);
			for (int j = 0; j < charsLen; j++) {
				if (c == chars.charAt(j)) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int indexOfChars(String string, char[] chars) {
		return indexOfChars(string, chars, 0);
	}

	/**
	 * Returns the very first index of any char from provided string, starting from specified index offset.
	 * Returns index of founded char, or <code>-1</code> if nothing found.
	 */
	public static int indexOfChars(String string, char[] chars, int startindex) {
		int stringLen = string.length();
		int charsLen = chars.length;
		for (int i = startindex; i < stringLen; i++) {
			char c = string.charAt(i);
			for (int j = 0; j < charsLen; j++) {
				if (c == chars[j]) {
					return i;
				}
			}
		}
		return -1;
	}



	// ---------------------------------------------------------------- strip

	/**
	 * Strips leading char if string starts with one.
	 */
	public static String stripLeadingChar(String string, char c) {
		if (string.length() > 0) {
			if (string.charAt(0) == c) {
				return string.substring(1);
			}
		}
		return string;
	}

	/**
	 * Strips trailing char if string ends with one.
	 */
	protected String stripTrailingChar(String string, char c) {
		if (string.length() > 0) {
			if (string.charAt(string.length() - 1) == c) {
				return string.substring(0, string.length() - 1);
			}
		}
		return string;
	}

	// ---------------------------------------------------------------- trim


	/**
	 * Trims array of strings. Null elements of the array are ignored.
	 */
	public static void trim(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			String string = strings[i];
			if (string != null) {
				strings[i] = string.trim();
			}

		}
	}

	/**
	 * Trims array of strings where empty strings are nulled.
	 * Null elements of the array are ignored.
	 * @see #trimNonEmpty(String)
	 */
	public static void trimNonEmpty(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			String string = strings[i];
			if (string != null) {
				strings[i] = trimNonEmpty(strings[i]);
			}
		}
	}


	/**
	 * Trims string where empty strings are returned as a <code>null</code>.
	 */
	public static String trimNonEmpty(String string) {
		if (string != null) {
			string = string.trim();
			if (string.length() == 0) {
				string = null;
			}
		}
		return string;
	}


	// ---------------------------------------------------------------- regions

	/**
	 * @see #indexOfRegion(String, String, String, int)
	 */
	public static int[] indexOfRegion(String string, String leftBoundary, String rightBoundary) {
		return indexOfRegion(string, leftBoundary, rightBoundary, 0);
	}


	/**
	 * Returns indexes of the first region without escaping character.
	 * @see #indexOfRegion(String, String, String, char, int)
	 */
	public static int[] indexOfRegion(String string, String leftBoundary, String rightBoundary, int offset) {
		int ndx = offset;
		int[] res = new int[4];
		ndx = string.indexOf(leftBoundary, ndx);
		if (ndx == -1) {
			return null;
		}
		res[0] = ndx;
		ndx += leftBoundary.length();
		res[1] = ndx;

		ndx = string.indexOf(rightBoundary, ndx);
		if (ndx == -1) {
			return null;
		}
		res[2] = ndx;
		res[3] = ndx + rightBoundary.length();
		return res;
	}


	/**
	 * @see #indexOfRegion(String, String, String, char, int)
	 */
	public static int[] indexOfRegion(String string, String leftBoundary, String rightBoundary, char escape) {
		return indexOfRegion(string, leftBoundary, rightBoundary, escape, 0);
	}

	/**
	 * Returns indexes of the first string region. Region is defined by its left and right boundary.
	 * Return value is an array of the following indexes:
	 * <ul>
	 * <li>start of left boundary index</li>
	 * <li>region start index, i.e. end of left boundary</li>
	 * <li>region end index, i.e. start of right boundary</li>
	 * <li>end of right boundary index</li> 
	 * </ul>
	 * <p>
	 * Escape character may be used to prefix boundaries so they can be ignored.
	 * If region is not founded, <code>null</code> is returned. 
	 */
	public static int[] indexOfRegion(String string, String leftBoundary, String rightBoundary, char escape, int offset) {
		int ndx = offset;
		int[] res = new int[4];
		while (true) {
			ndx = string.indexOf(leftBoundary, ndx);
			if (ndx == -1) {
				return null;
			}
			if (ndx > 0) {
				if (string.charAt(ndx - 1) == escape) {
					ndx += leftBoundary.length();
					continue;
				}
			}
			res[0] = ndx;
			ndx += leftBoundary.length();
			res[1] = ndx;

			while (true) {
				ndx = string.indexOf(rightBoundary, ndx);
				if (ndx == -1) {
					return null;
				}
				if (ndx > 0) {
					if (string.charAt(ndx - 1) == escape) {
						ndx += rightBoundary.length();
						continue;
					}
				}
				res[2] = ndx;
				res[3] = ndx + rightBoundary.length();
				return res;
			}
		}
	}


	// ---------------------------------------------------------------- join

	/**
	 * Joins array of strings into one string.
	 */
	public String join(String... parts) {
		StringBuilder sb = new StringBuilder();
		for (String part : parts) {
			sb.append(part);
		}
		return sb.toString();
	}

	// ---------------------------------------------------------------- charset

	/**
	 * Converts string charsets.
	 */
	public static String convertCharset(String source, String srcCharsetName, String newCharsetName) throws UnsupportedEncodingException {
		return new String(source.getBytes(srcCharsetName), newCharsetName);
	}


	// ---------------------------------------------------------------- chars

	/**
	 * Safely compares provided char with char on given location.
	 */
	public static boolean isCharAtEqual(String string, int index, char charToCompare) {
		if ((index < 0) || (index >= string.length())) {
			return false;
		}
		return string.charAt(index) == charToCompare;
	}


	// ---------------------------------------------------------------- surround

	/**
	 * @see #surround(String, String, String)
	 */
	public static String surround(String string, String fix) {
		return surround(string, fix, fix);
	}

	/**
	 * Surrounds the string with provided prefix and suffix if such missing from string.
	 */
	public static String surround(String string, String prefix, String suffix) {
		if (string.startsWith(prefix) == false) {
			string = prefix + string;
		}
		if (string.endsWith(suffix) == false) {
			string += suffix;
		}
		return string;
	}

	/**
	 * Inserts prefix if doesn't exist.
	 */
	public static String prefix(String string, String prefix) {
		if (string.startsWith(prefix) == false) {
			string = prefix + string;
		}
		return string;
	}

	/**
	 * Appends suffix if doesn't exist.
	 */
	public static String suffix(String string, String suffix) {
		if (string.endsWith(suffix) == false) {
			string += suffix;
		}
		return string;
	}

	// ---------------------------------------------------------------- cut

	/**
	 * Cuts the string from beginning to the first index of provided substring.
	 */
	public static String cutFromIndexOf(String string, String substring) {
		int i = string.indexOf(substring);
		if (i != -1) {
			string = string.substring(0, i);
		}
		return string;
	}

	public static String cutFromIndexOf(String string, char c) {
		int i = string.indexOf(c);
		if (i != -1) {
			string = string.substring(0, i);
		}
		return string;
	}

	public static String cutToIndexOf(String string, String substring) {
		int i = string.indexOf(substring);
		if (i != -1) {
			string = string.substring(i);
		}
		return string;
	}

	public static String cutToIndexOf(String string, char c) {
		int i = string.indexOf(c);
		if (i != -1) {
			string = string.substring(i);
		}
		return string;
	}

	/**
	 * Cuts prefix if exists.
	 */
	public static String cutPreffix(String string, String prefix) {
		if (string.startsWith(prefix)) {
			string = string.substring(prefix.length());
		}
		return string;
	}

	/**
	 * Cuts sufix if exists.
	 */
	public static String cutSuffix(String string, String suffix) {
		if (string.endsWith(suffix)) {
			string = string.substring(0, string.length() - suffix.length());
		}
		return string;
	}

	/**
	 * @see #cutSurrounding(String, String, String)
	 */
	public static String cutSurrounding(String string, String fix) {
		return cutSurrounding(string, fix, fix);
	}

	/**
	 * Removes surrounding prefix and suffixes.
	 */
	public static String cutSurrounding(String string, String prefix, String suffix) {
		int start = 0;
		int end = string.length();
		if (string.startsWith(prefix)) {
			start = prefix.length();
		}
		if (string.endsWith(suffix)) {
			end -= suffix.length();
		}

		return string.substring(start, end);
	}


	/**
	 * Cuts the last word from the string.
	 */
	public static String cutLastWord(String string) {
		return cutLastWord(string, false);
	}

	/**
	 * Cuts the last word from the string, but not if it is a first.
	 */
	public static String cutLastWordNotFirst(String string) {
		return cutLastWord(string, true);
	}

	private static String cutLastWord(String string, boolean preserveFirst) {
		int ndx = string.length() - 1;
		while (ndx >= 0) {
			if (Character.isUpperCase(string.charAt(ndx)) == true) {
				break;
			}
			ndx--;
		}
		if (ndx >= 0) {
			if ((ndx == 0) && (preserveFirst == true)) {
				return string;
			}
			string = string.substring(0, ndx);
		}
		return string;
	}


}