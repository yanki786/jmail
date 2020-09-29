package javax.jmail.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.mail.Flags;
/**
 * The Class MessageBean.
 *
 * @author Abhishek Bansal (17 BCS 3289)
 */
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.imap.IMAPMessage;

/**
 * The Class MessageBean.
 * @author Abhishek Bansal
 */
public class MessageBeanImp implements Serializable, MessageSenderBean, MessageBean {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The uid. */
	private long uid;
	
	/** The message. */
	private IMAPMessage message;
	
	/** The subject. */
	private String subject;
	
	/** The content type. */
	private String contentType;
	
	/** The content. */
	private String content;
	
	/** The sender. */
	private String sender;
	
	/** The to. */
	private final List<String> to = new ArrayList<>();
	
	/** The cc. */
	private final List<String> cc = new ArrayList<>();
	
	/** The bcc. */
	private final List<String> bcc = new ArrayList<>();
	
	/** The reply to. */
	private final List<String> replyTo = new ArrayList<>();
	
	/** The attachments. */
	private final Map<String, String> attachments = new HashMap<String, String>();

	/**
	 * Instantiates a new message bean imp.
	 */
	public MessageBeanImp() {
	}

	/**
	 * Instantiates a new message bean imp.
	 *
	 * @param message the message
	 */
	public MessageBeanImp(Message message) {
		this.setMessage(message);
	}

	/**
	 * Sets the message.
	 *
	 * @param message the message
	 * @return the message bean
	 */
	private MessageBean setMessage(Message message) {
		final StringJoiner joiner = new StringJoiner(",");
		this.message = (IMAPMessage) message;

		try {
			Arrays.stream(message.getFrom()).forEach(x -> joiner.add(x.toString()));
			sender = joiner.toString();

			Arrays.stream(message.getRecipients(RecipientType.TO)).forEach(x -> to.add(x.toString()));

			if (message.getReplyTo() != null)
				Arrays.stream(message.getReplyTo()).forEach(x -> replyTo.add(x.toString()));
			if (message.getRecipients(RecipientType.CC) != null)
				Arrays.stream(message.getRecipients(RecipientType.CC)).forEach(x -> cc.add(x.toString()));
			if (message.getRecipients(RecipientType.BCC) != null)
				Arrays.stream(message.getRecipients(RecipientType.BCC)).forEach(x -> bcc.add(x.toString()));
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return this;
	}
	
	

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * Sets the UID.
	 *
	 * @param uid the uid
	 * @return the message bean
	 */
	public MessageBean setUID(long uid) {
		this.uid = uid;
		return this;
	}

	/**
	 * Gets the uid.
	 *
	 * @return the uid
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * Gets the msgnum.
	 *
	 * @return the msgnum
	 */
	public int getMsgnum() {
		return message.getMessageNumber();
	}

	/**
	 * Gets the msgsize.
	 *
	 * @return the msgsize
	 */
	public int getMsgsize() {
		try {
			return message.getSize();
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Checks if is bulk.
	 *
	 * @return true, if is bulk
	 */
	public boolean isBulk() {
		try {
			return message.getHeader("Precedence")[0].equals("bulk");
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Gets the received date.
	 *
	 * @return the received date
	 */
	public Date getReceivedDate() {
		try {
			return message.getReceivedDate();
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType() {
		try {
			if (message != null)
				return message.getContentType();
			else
				return contentType;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the flags.
	 *
	 * @return the flags
	 */
	public Flags getFlags() {
		try {
			return message.getFlags();
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}



	/**
	 * Gets the sender.
	 *
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Sets the sender.
	 *
	 * @param sender the new sender
	 */
	@Override
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public List<String> getTo() {
		return to;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		try {
			if (message != null)
				return message.getSubject();
			else
				return subject;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		try {
			if (message != null)
				return message.getContent().toString();
			else
				return content;
		} catch (IOException | MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * Gets the cc.
	 *
	 * @return the cc
	 */
	public List<String> getCc() {
		return cc;
	}

	/**
	 * Gets the bcc.
	 *
	 * @return the bcc
	 */
	public List<String> getBcc() {
		return bcc;
	}

	/**
	 * Gets the reply to.
	 *
	 * @return the reply to
	 */
	public List<String> getReplyTo() {
		return replyTo;
	}

	/**
	 * Gets the attachments.
	 *
	 * @return the attachments
	 */
	public Map<String, String> getAttachments() {
		return attachments;
	}

	@Override
	public String toString() {
		return "MessageBean { uid : " + getUid() + ", msgnum : " + getMsgnum() + ", msgsize : "
				+ getMsgsize() + ", isBulk : " + isBulk() + ", "
				+ (getReceivedDate() != null ? "receivedDate : " + getReceivedDate() + ", " : "")
				+ (getFlags() != null ? " flag : " + getFlags() + ", " : "")
				+ (getSender() != null ? "sender : " + getSender() + ", " : "")
				+ (getTo() != null ? "to :" + getTo() + ", " : "")
				+ (getSubject() != null ? "sub : " + getSubject() : "") + "}";
	}

}
