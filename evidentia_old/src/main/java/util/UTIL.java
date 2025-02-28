package util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import objects.Parm;

/**
 * Utility text handling class
 */
public final class UTIL {

	/*
	 * RESERVED_WORDS      List of system reserved words
	 * SYSTEM_KEYWORDS     List of system keywords
	 * VALID_IDENTIFIERS   Regex Pattern for a valid identifier
	 */
	private static final List<String> RESERVED_WORDS = Arrays.asList(
			"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
			"continue", "default", "double", "do", "else", "enum", "extends", "false", "final", "finally",
			"float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long",
			"native", "new", "null", "package", "private", "protected", "public", "return", "short", "static",
			"strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try",
			"void", "volatile", "while", "_\\b"
			);

	private static final String SYSTEM_KEYWORDS = String.join("|", RESERVED_WORDS);

	private static final Pattern VALID_IDENTIFIERS = Pattern.compile(
		    "(?!(" + SYSTEM_KEYWORDS + "))([A-Za-z_$][$\\w]*)");
    // "(?<=[^$\\w'\"\\\\])(?!(" + SYSTEM_KEYWORDS + "))([A-Za-z_$][$\\w]*)")
	
	private static final Pattern SPECIAL_XML_CHARS = Pattern.compile( "([&<>\"'])");
	
	
	/**
	 * Determines if a string is a valid identifier name
	 * @param name Name to be checked
	 */
	public static boolean isValidName( String name) {

		if ( name == null || name.isEmpty()|| name.isBlank() ) {
			return false;
		}
	
		return VALID_IDENTIFIERS.matcher(name).matches();
	}
	
	
	/**
	 * Builds an indentation string
	 * @param indentLevel Indentation level
	 * @return  indentation string
	 */
	public static String indentString( int indentLevel) {
		return " ".repeat(indentLevel * Parm.INDENT);
	}
	
	
	/**
	 * Determines if a presented string is a valid String value
	 * @param value  Value to examine
	 * @return true if the presented value is a valid String value; false otherwise
	 */
	public static boolean isValidStringValue( String value) {
		return !SPECIAL_XML_CHARS.matcher(value).find();
	}
	
	/**
	 * Determines if a presented string is empty
	 * @param text  Text to examine
	 * @return true if the string is null, empty or all blanks; false otherwise
	 */
	public static boolean isEmpty(String text) {
		return text == null || text.trim().length() == 0;
	}

}
