package javax.jmail.message;

import javax.jmail.beans.MessageSenderBean;
import javax.jmail.utils.ExcelFileReader;
import java.util.HashMap;
import java.util.Map;


/**
 * The Interface MailSender.
 * 
 * @author Abhishek Bansal
 */
 public interface MailSender {

	
	/**
	 * Enable html content type.
	 *
	 * @return the mail sender
	 */
	 MailSender enableHtmlContentType();

	/**
	 * Sets the message credential.
	 *
	 * @param receivers the receivers
	 * @param subject   the subject
	 * @param content   the content
	 * @return the mail sender
	 */
	 MailSender setMessageCredential( String receivers, String subject, String content);

	/**
	 * Sets the message credential use to add attachments too.
	 *
	 * @param receivers   the receivers
	 * @param subject     the subject
	 * @param content     the content
	 * @param attachments the attachments
	 * @return the mail sender
	 */
	 MailSender setMessageCredential( String receivers, String subject, String content, Map<String, String> attachments);
	
	/**
	 * Sets the message credential use to add cc and bcc.
	 *
	 * @param receivers the receivers
	 * @param subject   the subject
	 * @param content   the content
	 * @param cc        the cc
	 * @param bcc       the bcc
	 * @return the mail sender
	 */
	 MailSender setMessageCredential( String receivers, String subject, String content, String cc,
			String bcc);

	
	/**
	 * Sets the message credential use to add attachments too.
	 *
	 * @param receivers   the receivers
	 * @param subject     the subject
	 * @param content     the content
	 * @param cc          the cc
	 * @param bcc         the bcc
	 * @param attachments the attachments
	 * @return the mail sender
	 */
	 MailSender setMessageCredential( String receivers, String subject, String content, String cc,
			String bcc, Map<String, String> attachments);

	/**
	 * Sets the message credential.
	 *
	 * @param msg the new message credential
	 * @return the mail sender
	 */
	 MailSender setMessageCredential(MessageSenderBean msg);

	/**
	 * Sets the message credential.
	 *
	 * @param receivers the receivers
	 * @param subject  the subject
	 * @param content  the content
	 * @return MailSender instance
	 * @throws Exception the exception
	 */
	 MailSender setMessageCredential(ExcelFileReader receivers,  String subject, String content)
			throws Exception;

	
	/**
	 * Sets the message credential.
	 *
	 * @param receivers    the receivers
	 * @param subject     the subject
	 * @param content     the content
	 * @param attachments the attachments
	 * @return the mail sender
	 * @throws Exception the exception
	 */
	 MailSender setMessageCredential(ExcelFileReader receivers, String subject, String content, Map<String, String> attachments)
			throws Exception;

	
	/**
	 * Send after setting message credential it will send message.
	 *
	 * @return true, if successful
	 */
	 boolean send();

	/**
	 * Send messages.
	 *
	 * @param reader the reader
	 * @return the hash map
	 * @throws Exception the exception
	 */
	 HashMap<Integer, Exception> sendMessages(ExcelFileReader reader) throws Exception;


}
