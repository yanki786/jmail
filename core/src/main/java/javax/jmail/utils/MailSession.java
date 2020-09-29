package javax.jmail.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;


/**
 * The Class MailSession.
 * 
 * @author Abhishek Bansal
 */
public class MailSession {

	/**
	 * {@link Session}
	 * 
	 * Authenticated session will be created for you.
	 * These session are use at transporting mail.
	 * 
	 * 
	 * @param id       the email id
	 * @param password the password
	 * @param props    the props
	 * @return the authenticated session
	 */
	public static Session getAuthenticatedSession(String id, String password, Properties props) {
		return Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(id, password);
			}
		});
	}

	/**
	 * {@link Session}
	 * 
	 * If not exist one will be created for you.
	 * Can be used throw out application. 
	 *
	 * @param props the props
	 * @return the default session
	 */
	public static Session getDefaultSession(Properties props) {
		return Session.getDefaultInstance(props);
	}

}
