package javax.jmail.utils;

import java.util.regex.Pattern;

/**
 * The Class EMailValidator.
 * 
 * this class is use to check weather id is valid or not
 */
public final class EMailValidator {

	/** The Constant PATTERN_ALL. */
	private static final String PATTERN_ALL = "[a-zA-Z0-9][a-zA-Z0-9_.]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";

	
	/**
	 * Validate all email id as per general 
	 * rule to specify any mail id.
	 * 
	 * @param emailId the email id
	 * @return true, if successful
	 */
	public static boolean validate(String emailId) {
		return Pattern.compile(PATTERN_ALL).matcher(emailId).find();
	}
}
