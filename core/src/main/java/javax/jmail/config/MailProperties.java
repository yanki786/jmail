package javax.jmail.config;

import java.util.Properties;

/**
 * The Class MailProperties. will provide properties required to send or read
 * mail. Now user will not need to remember properties.
 * 
 * It also provide configuration for GMails, YahooMails, OfficeMails,
 * Cloud_Mails and more can be added as per user requirement.
 *
 * @author Abhishek Bansal
 */
public class MailProperties {

	/**
	 * The Class HostConfig Used to create host properties.
	 */
	public static class HostConfig {

		/** The imap host. */
		private String imapHost;

		/** The smtp host. */
		private String smtpHost;

		/**
		 * Instantiates a new host config.
		 *
		 * @param imapHost the imap host
		 * @param smtpHost the smtp host
		 */
		public HostConfig(String imapHost, String smtpHost) {
			this.imapHost = imapHost;
			this.smtpHost = smtpHost;
		}

		/**
		 * Gets the imap host.
		 *
		 * @return the imap host
		 */
		public String getImapHost() {
			return imapHost;
		}

		/**
		 * Gets the smtp host.
		 *
		 * @return the smtp host
		 */
		public String getSmtpHost() {
			return smtpHost;
		}

	}

	/** The Constant GMAIL_HOST. */
	public static final HostConfig GMAIL_HOST = new HostConfig("imap.gmail.com", "smtp.gmail.com");

	/** The Constant OFFICE_HOST. */
	public static final HostConfig OFFICE_HOST = new HostConfig("outlook.office365.com", "smtp.office365.com");

	/** The Constant YAHOO_HOST. */
	public static final HostConfig YAHOO_HOST = new HostConfig("imap.mail.yahoo.com", "smtp.mail.yahoo.com");

	/** The Constant ICLOUD_HOST. */
	public static final HostConfig ICLOUD_HOST = new HostConfig("imap.mail.me.com", "smtp.mail.me.com");

	/** The Constant AOL_HOST. */
	public static final HostConfig AOL_HOST = new HostConfig("imap.aol.com", "smtp.aol.com");

	/** The Constant GMX_HOST. */
	public static final HostConfig GMX_HOST = new HostConfig("imap.gmx.com", "mail.gmx.com");

	/**
	 * It will return properties, used for sending mails. It will use
	 * <i><b>SMTP</b></i> to send mail over <i><b>465</b></i> port number by
	 * default.
	 * 
	 * @param config {@link HostConfig} to get SMTP host detail.
	 * @param debug  will switch on/off mail debugging
	 * @return the out mail properties
	 */
	public static Properties getOutMailProperties(HostConfig config, boolean debug) {
		int port = 465;// 25,465,587
		Properties prop = new Properties();
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.socketFactory.port", port);
		prop.put("mail.smtp.socketFactory.fallback", "false");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.host", config.getSmtpHost());
		prop.putIfAbsent("mail.debug", "" + debug);

		return prop;
	}

	/**
	 * It will return properties, used for incoming mails. It will use
	 * <i><b>IMAP</b></i> to send mail over <i><b>993</b></i> port number by
	 * default.
	 *
	 * @param config {@link HostConfig} to get IMAP host detail.
	 * @param debug  will switch on/off mail debugging
	 * @return the in mail properties
	 */
	public static Properties getInMailProperties(HostConfig config, boolean debug) {

		Properties prop = new Properties();
		prop.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.imap.socketFactory.port", "993");
		prop.put("mail.imap.socketFactory.fallback", "false");
		prop.put("mail.imap.port", "993");
		prop.put("mail.store.protocol", "imaps");
		prop.put("mail.imap.host", config.getImapHost());
		prop.putIfAbsent("mail.debug", "" + debug);
		return prop;
	}

	/**
	 * It will return properties, used for sending mails and for incoming mails. It
	 * will use <i><b>SMTP</b></i> to send mail over <i><b>587</b></i> port number
	 * by default. It will use <i><b>IMAP</b></i> to send mail over
	 * <i><b>993</b></i> port number by default.
	 * 
	 * @param config {@link HostConfig} to get IMAP and SMTP host detail.
	 * @param debug  will switch on/off mail debugging
	 * @return the mail properties for both sending and receiving mails.
	 */
	public static Properties getMailProperties(HostConfig config, boolean debug) {

		Properties prop = new Properties();

		getOutMailProperties(config, debug).forEach(prop::put);
		getInMailProperties(config, debug).forEach(prop::putIfAbsent);
		return prop;

	}

}
