package javax.jmail.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jmail.beans.MessageSenderBean;
import javax.jmail.utils.ExcelFileReader;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.Logger;

/**
 * The Class MailSenderImp. This class is use to send message using basic Auth.
 *
 * @author Abhishek Bansal
 */
public class MailSenderImp implements MailSender {

	/** The Constant logger. */
	public static  Logger logger;

	/** The attachments.<Path,FileName> */
	private Map<String, String> attachments;

	/** The sender email id. */
	private String sender;

	/** The receivers. For multiple receivers id1,id2 */
	private String receivers;

	/** The cc. For multiple cc id1,id2 */
	private String cc;

	/** The bcc. For multiple bcc id1,id2 */
	private String bcc;

	/** The subject. */
	private String subject;

	/** The content. */
	private String content;

	/** The session. */
	private Session session;

	private boolean htmlContent;

	/**
	 * Instantiates a new mail sender imp.
	 *
	 * @param session the session
	 * @param id      the id
	 */
	public MailSenderImp(Session session,String id) {
		this.session = session;
		sender=id;
	}

	/**
	 * Sets the message credential. Use to set simple detail require to send mail.
	 *
	 * @param receivers the receivers
	 * @param subject   the subject
	 * @param content   the content
	 * @return MailSender instance
	 */
	@Override
	public MailSender setMessageCredential( String receivers, String subject, String content) {
		this.receivers = receivers;
		this.subject = subject;
		this.content = content;
		return this;
	}

	/**
	 * Sets the message credential use to add attachments too.
	 *
	 * @param receivers   the receivers
	 * @param subject     the subject
	 * @param content     the content
	 * @param attachments the attachments
	 * @return the mail sender
	 */
	public MailSender setMessageCredential( String receivers, String subject, String content,
			Map<String, String> attachments) {
		this.setMessageCredential( receivers, subject, content);
		this.attachments = attachments;
		return this;
	}

	/**
	 * Sets the message credential. Use to set simple detail require to send mail
	 * with cc and bcc.
	 *
	 * @param receivers the receivers
	 * @param subject   the subject
	 * @param content   the content
	 * @param cc        the cc
	 * @param bcc       the bcc
	 * @return MailSender instance
	 */
	@Override
	public MailSender setMessageCredential( String receivers, String subject, String content, String cc,
			String bcc) {
		this.setMessageCredential(receivers, subject, content);
		this.cc = cc;
		this.bcc = bcc;
		return this;
	}

	/**
	 * Sets the message credential. will allow to add attachments.
	 *
	 * @param receivers   the receivers
	 * @param subject     the subject
	 * @param content     the content
	 * @param cc          the cc
	 * @param bcc         the bcc
	 * @param attachments the attachments
	 * @return MailSender instance
	 */
	@Override
	public MailSender setMessageCredential( String receivers, String subject, String content, String cc,
			String bcc, Map<String, String> attachments) {
		this.setMessageCredential( receivers, subject, content, cc, bcc);
		this.attachments = attachments;
		return this;
	}

	/**
	 * Sets the message credential.
	 *
	 * @param msg the new message credential
	 * @return MailSender instance
	 */
	@Override
	public MailSender setMessageCredential(MessageSenderBean msg) {
		String subject = msg.getSubject();
		String content = msg.getContent();

		StringJoiner receiver = new StringJoiner(",");
		msg.getTo().forEach(receiver::add);

		StringJoiner cc = new StringJoiner(",");
		msg.getCc().forEach(cc::add);

		StringJoiner bcc = new StringJoiner(",");
		msg.getBcc().forEach(bcc::add);

		setMessageCredential( receiver.toString(), subject, content, cc.toString(), bcc.toString(),
				msg.getAttachments());
		return this;
	}

	/**
	 * Sets the message credential.
	 *
	 * @param reader  the reader
	 * @param subject the subject
	 * @param msg     the msg
	 * @throws Exception the exception
	 */
	@Override
	public MailSender setMessageCredential(ExcelFileReader reader, String subject, String msg)
			throws Exception {
		this.content = msg;
		this.subject = subject;

		StringJoiner receiver = new StringJoiner(",");
		reader.readAsGroup().forEach(receiver::add);
		this.receivers = receiver.toString();
		return this;
	}

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
	@Override
	public MailSender setMessageCredential(ExcelFileReader receivers, String subject, String content,
			Map<String, String> attachments) throws Exception {
		this.setMessageCredential(receivers, subject, content);
		this.attachments = attachments;
		return this;
	}
	
	/**
	 * Enable html content type.
	 *
	 * @return the mail sender
	 */
	public MailSender enableHtmlContentType(){
		htmlContent=true;
		return this;
	}

	/**
	 * Prepare content. use to prepare message body(content,attachments)
	 * 
	 * @return the multipart
	 * @throws MessagingException the messaging exception
	 */
	private Multipart prepareContent() throws MessagingException {
		final Multipart parts = new MimeMultipart();

		MimeBodyPart msgBody = new MimeBodyPart();
		if(htmlContent)
			msgBody.setContent(content, "text/html");
		else
			msgBody.setText(content);
		parts.addBodyPart(msgBody);

		if (attachments == null)
			return parts;

		Set<String> keySet = attachments.keySet();
		Iterator<String> iterator = keySet.iterator();

		while (iterator.hasNext()) {

			String key = iterator.next();
			String value = attachments.get(key);

			MimeBodyPart attachment = new MimeBodyPart();
			DataSource source = new FileDataSource(key);// path
			attachment.setDataHandler(new DataHandler(source));
			attachment.setFileName(value);// filename
			parts.addBodyPart(attachment);

		}
		return parts;
	}

	/**
	 * Prepare message header use to set message header
	 * portion(sender,to,cc,bcc,sub).
	 *
	 * @param message the message
	 * @throws MessagingException the messaging exception
	 */
	private void prepareMessageHeader(Message message) throws MessagingException {

		message.setFrom(new InternetAddress(sender));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receivers));
		if (cc != null)
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
		if (bcc != null)
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
		message.setSubject(subject);

	}

	/**
	 * Send email after preparing all details and will return true if mail is
	 * successfully send using simple authentication SMTP protocol. Make sure that
	 * Less secure application access is on and two step verification is off before
	 * sending mail from your mail account.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean send() {
		logger.info("Its request to send message by {}", sender);
		final Message message = new MimeMessage(session);
		try {
			prepareMessageHeader(message);
			logger.info("Message Header is prepared.");


			message.setContent(prepareContent());
			logger.info("Message content is prepared.");

			
			Transport.send(message);
			logger.info("Message is sent successfully to receiver {}", receivers);
			return true;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/** The index. */
	private int index = 0;

	/**
	 * Send messages.
	 *
	 * @param reader the reader
	 * @return the hash map
	 * @throws Exception the exception
	 */
	@Override
	public HashMap<Integer, Exception> sendMessages(ExcelFileReader reader) throws Exception {
		HashMap<Integer, Exception> result = new HashMap<>();
		reader.readAsIndividual().forEach(msg -> {
			receivers = msg.get(0);
			subject = msg.get(1);
			content = msg.get(2);
			try {
				send();
				result.put(index, null);
			} catch (Exception e) {
				result.put(index, e);
			} finally {
				index++;
			}
		});
		return result;
	}

}
