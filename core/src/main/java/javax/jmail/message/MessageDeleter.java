package javax.jmail.message;

import javax.jmail.beans.MessageBean;
import javax.mail.Message;


/**
 * The Interface MessageDeleter.
 * 
 * @author Abhishek Bansal
 */
 public interface MessageDeleter {

	/**
	 * Delete message.
	 *
	 * @param msg the msg
	 */
	 void deleteMessage(Message msg);

	/**
	 * Delete message.
	 *
	 * @param msg the msg
	 */
	 void deleteMessage(MessageBean msg);

	/**
	 * Delete message.
	 *
	 * @param messageNumber the message number
	 */
	 void deleteMessage(int messageNumber);

	/**
	 * Delete message by id.
	 *
	 * @param messageId the message id
	 */
	 void deleteMessageById(long messageId);

	/**
	 * Delete messages.
	 *
	 * @param msgnums the msgnums
	 */
	 void deleteMessages(int[] msgnums);

	/**
	 * Delete messages.
	 *
	 * @param start the start
	 * @param end   the end
	 */
	 void deleteMessages(int start, int end);

	/**
	 * Gets the messages.
	 *
	 * @param ids the ids
	 */
	 void getMessages(long[] ids);

	/**
	 * Delete all messages.
	 */
	 void deleteAllMessages();

	/**
	 * Empty Gmail spam.
	 */
	 void emptyGmailSpam();

	/**
	 * Empty Gmail trash.
	 */
	 void emptyGmailTrash();

	/**
	 * Empty office deleted folder.
	 */
	 void emptyOfficeDeletedFolder();

	/**
	 * Empty office junk.
	 */
	 void emptyOfficeJunk();

}
