package javax.jmail.system;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jmail.config.MailProperties;
import javax.jmail.label.GeneralLabelManager;
import javax.jmail.label.LabelManager;
import javax.jmail.message.*;
import javax.jmail.utils.EMailValidator;
import javax.jmail.utils.MailSession;
import javax.mail.Session;


/**
 * The Class MailSystem.
 *
 * @author Abhishek Bansal
 */
public class MailSystem {

    /**
     * The session reader.
     */
    private Session sessionSender, sessionReader;

    /**
     * The label manager.
     */
    private LabelManager labelManager;

    /**
     * The sender.
     */
    private MailSender sender;


    static {
        Logger logger = LogManager.getLogger("javax.mail.logger");
        GeneralLabelManager.logger = logger;
        MailSenderImp.logger=logger;
        MessageDeleterImp.logger=logger;
    }

    /**
     * Instantiates a new mail system for Gmail host.
     *
     * @param id   the id
     * @param pass the pass
     */
    public MailSystem(String id, String pass) {
        this(MailProperties.GMAIL_HOST, id, pass);
    }

    /**
     * Instantiates a new mail system for Gmail host.
     *
     * @param id    the id
     * @param pass  the pass
     * @param debug the debug
     */
    public MailSystem(String id, String pass, boolean debug) {
        this(MailProperties.GMAIL_HOST, id, pass, debug);
    }

    /**
     * Instantiates a new mail system.
     *
     * @param hostConfig the host config
     * @param id         the id
     * @param pass       the pass
     */
    public MailSystem(MailProperties.HostConfig hostConfig, String id, String pass) {
        this(hostConfig, id, pass, false);
    }

    /**
     * Instantiates a new mail system.
     *
     * @param hostConfig the host config
     * @param id         the id
     * @param pass       the pass
     * @param debug      the debug
     */
    public MailSystem(MailProperties.HostConfig hostConfig, String id, String pass, boolean debug) {

        if (!EMailValidator.validate(id)) {
            throw new RuntimeException("Invalid mail id.");
        }

        sessionReader = MailSession.getAuthenticatedSession(id, pass,
                MailProperties.getInMailProperties(hostConfig, debug));

        sessionSender = MailSession.getAuthenticatedSession(id, pass,
                MailProperties.getOutMailProperties(hostConfig, debug));
        try {
            labelManager = new LabelManager(sessionReader, id, pass, "inbox");
        } catch (Exception e) {
            throw new RuntimeException("Unable to login.\n" + e);
        }

        sender = new MailSenderImp(sessionSender, id);
    }

    /**
     * Gets the label manager.
     *
     * @return the label manager
     */
    public LabelManager getLabelManager() {
        return labelManager;
    }

    /**
     * Gets the mail sender.
     *
     * @return the mail sender
     */
    public MailSender getMailSender() {
        return sender;
    }

    /**
     * Open folder.
     *
     * @param name the name
     */
    public void openFolder(String name) {

        if (name == null)
            throw new NullPointerException();
        try {
            labelManager.openFolder(name);
            if (!labelManager.exist())
                throw new RuntimeException("Invalid folder. Folder not exist");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the mail reader.
     *
     * @return the mail reader
     */
    public MessageReader getMailReader() {
        try {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new MessageReaderImp(labelManager.getIMAPFolder());
    }

    /**
     * Gets the deleter.
     *
     * @return the deleter
     */
    public MessageDeleter getDeleter() {
        return new MessageDeleterImp(labelManager.getIMAPFolder());
    }

    /**
     * Logout.
     */
    public void logout() {
        sessionReader = null;
        sessionSender = null;
        sender = null;
        labelManager.close();
    }

}
