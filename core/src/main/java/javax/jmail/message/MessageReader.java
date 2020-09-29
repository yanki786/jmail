package javax.jmail.message;

import java.util.List;


import javax.jmail.beans.MessageBean;

/**
 * The Interface MessageReader.
 * @author Abhishek Bansal.
 */
 public interface MessageReader {
	

	/**
	 * Gets the message count.
	 *
	 * @return the message count
	 */
	 int getMessageCount();

	/**
	 * Gets the message by ID.
	 *
	 * @param id the id
	 * @return the message by ID
	 */
	 MessageBean getMessageByID(long id);

	/**
	 * Gets the message.
	 *
	 * @param msgnum the msgnum
	 * @return the message
	 */
	 MessageBean getMessage(int msgnum);

	/**
	 * Gets the messages.
	 *
	 * @param msgnums the msgnums
	 * @return the messages
	 */
	 List<MessageBean> getMessages(int[] msgnums);

	/**
	 * Gets the messages.
	 *
	 * @param ids the ids
	 * @return the messages
	 */
	 List<MessageBean> getMessages(long[] ids);

	/**
	 * Gets the messages.
	 *
	 * @param start the start
	 * @param end   the end
	 * @return the messages
	 */
	 List<MessageBean> getMessages(int start, int end);

	/**
	 * Gets the messages.
	 *
	 * @return the messages
	 */
	 List<MessageBean> getMessages();

	/**
	 * Gets the unread messages.
	 *
	 * @return the unread messages
	 */
	 List<MessageBean> getUnreadMessages();

}
