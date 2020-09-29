package javax.jmail.beans;

import java.util.List;
import java.util.Map;

/**
 * The Interface MessageSenderBean.
 * @author Abhishek Bansal
 */
public interface MessageSenderBean {

	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject);

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject();

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content);

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent();

	/**
	 * Sets the Sender.
	 *
	 * @param Sender the new Sender
	 */
	public void setSender(String Sender);

	/**
	 * Gets the Sender.
	 *
	 * @return the Sender
	 */
	public String getSender();

	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public List<String> getTo();

	/**
	 * Gets the cc.
	 *
	 * @return the cc
	 */
	public List<String> getCc();

	/**
	 * Gets the bcc.
	 *
	 * @return the bcc
	 */
	public List<String> getBcc();

	/**
	 * Gets the attachments.
	 *
	 * @return the attachments
	 */
	public Map<String, String> getAttachments();
}
