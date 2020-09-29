package javax.jmail.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.Flags;
import javax.mail.Message;

/**
 * The Interface MessageBean.
 *
 * @author Abhishek Bansal
 */
public interface MessageBean {

	/**
	 * Gets the uid.
	 *
	 * @return the uid
	 */
	public long getUid();
	
	/**
	 * Gets the msgnum.
	 *
	 * @return the msgnum
	 */
	public int getMsgnum();

	/**
	 * Gets the msgsize.
	 *
	 * @return the msgsize
	 */
	public int getMsgsize();
	
	/**
	 * Checks if is bulk.
	 *
	 * @return true, if is bulk
	 */
	public boolean isBulk();
	
	/**
	 * Gets the received date.
	 *
	 * @return the received date
	 */
	public Date getReceivedDate();
	
	
	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType();
	
	/**
	 * Gets the flags.
	 *
	 * @return the flags
	 */
	public Flags getFlags() ;

	/**
	 * Gets the sender.
	 *
	 * @return the sender
	 */
	public String getSender() ;
	
	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject();
	
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() ;
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public Message getMessage();
	
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
	 * Gets the reply to.
	 *
	 * @return the reply to
	 */
	public List<String> getReplyTo();
	
	/**
	 * Gets the attachments.
	 *
	 * @return the attachments
	 */
	public Map<String, String> getAttachments();
	
}
