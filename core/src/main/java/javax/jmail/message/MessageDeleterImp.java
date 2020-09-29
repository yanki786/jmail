package javax.jmail.message;

import java.util.Arrays;

import javax.jmail.beans.MessageBean;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.protocol.FLAGS;
import org.apache.logging.log4j.Logger;

/**
 * The Class MessageDeleted. This class is use to delete message.
 *
 * @author Abhishek Bansal
 */
public class MessageDeleterImp implements MessageDeleter {

    /**
     * The Constant logger.
     */
    public static Logger logger;
    /**
     * The folder.
     */
    private final IMAPFolder folder;

    /**
     * Instantiates a new message deleted.
     *
     * @param folder the folder
     */
    public MessageDeleterImp(IMAPFolder folder) {
        this.folder = folder;
        openFolder(folder);
    }

    /**
     * Open folder is to change mode to Folder.READ_WRITE if open in any other mode
     * else if it is close it will open the folder.
     *
     * @param folder the folder
     */
    private void openFolder(Folder folder) {
        boolean flag = false;
        if (folder.isOpen()) {
            flag = folder.getMode() == Folder.READ_WRITE;
            try {
                if (!flag)
                    folder.close(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            if (!flag)
                folder.open(Folder.READ_WRITE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete.
     *
     * @param msg the msg
     */
    private void delete(Message msg) {
        try {
            msg.setFlag(FLAGS.Flag.DELETED, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete message.
     *
     * @param msg the message
     */
    public void deleteMessage(Message msg) {
        try {
            logger.info("Delete  messages request");
            delete(msg);
            folder.expunge();
            logger.info("Messages deleted.");
        } catch (Exception e) {
            logger.error("unable to delete message. {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete message.
     *
     * @param msg the message
     */
    public void deleteMessage(MessageBean msg) {
        try {
            logger.info("Delete  messages request");
            delete(msg.getMessage());
            folder.expunge();
            logger.info("Messages deleted.");
        } catch (Exception e) {
            logger.error("unable to delete message. {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete message by message index in folder.
     *
     * @param messageNumber the message number
     */
    public void deleteMessage(int messageNumber) {
        try {
            logger.info("Delete  messages request message number {}", messageNumber);
            deleteMessage(folder.getMessage(messageNumber));
            folder.expunge();
            logger.info("Messages deleted.");
        } catch (MessagingException e) {
            logger.error("unable to delete message. {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete message by id.
     *
     * @param messageId the message id
     */
    public void deleteMessageById(long messageId) {
        try {
            logger.info("Delete  message request id {}", messageId);
            deleteMessage(folder.getMessageByUID(messageId));
            folder.expunge();
            logger.info("Messages deleted.");
        } catch (MessagingException e) {
            logger.error("unable to delete message. {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete messages.
     *
     * @param msgnums the array of message number
     */
    public void deleteMessages(int[] msgnums) {

        try {
            logger.info("Delete  messages request");
            Arrays.stream(folder.getMessages(msgnums)).forEach(this::delete);
            folder.expunge();
            logger.info("Messages deleted.");
        } catch (Exception e) {
            logger.error("unable to delete messages. {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete messages.
     *
     * @param start the start
     * @param end   the end
     */
    public void deleteMessages(int start, int end) {
        try {
            logger.info("Delete  messages request from {} to {}", start, end);
            Arrays.stream(folder.getMessages(start, end)).forEach(this::delete);
            folder.expunge();
            logger.info("Messages deleted.");
        } catch (Exception e) {
            logger.error("unable to delete messages. {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the messages.
     *
     * @param ids the array of id
     */
    public void getMessages(long[] ids) {
        try {
            logger.info("Delete  messages request");
            Arrays.stream(folder.getMessagesByUID(ids)).forEach(this::delete);
            logger.info("Messages deleted.");
        } catch (Exception e) {
            logger.error("unable to delete messages. {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete all messages.
     */
    public void deleteAllMessages() {
        try {
            logger.info("Delete all messages request");
            deleteAll(folder);
            folder.expunge();
            logger.info("Deleted all messages.");
        } catch (Exception e) {
            logger.error("unable to delete all messages. {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Empty Gmail spam.
     */
    public void emptyGmailSpam() {
        try {
            Folder folder2 = folder.getStore().getDefaultFolder().getFolder("[Gmail]/Spam");
            logger.info("Try to empty Spam Folder");
            if (folder2.exists()) {
                folder2.open(Folder.READ_WRITE);
                deleteAll(folder2);
                folder2.expunge();
                logger.info("Spam has cleared");
            } else {
                logger.debug("Spam folder dose not exist");
            }
            folder2.close(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Empty Gmail trash.
     */
    public void emptyGmailTrash() {
        try {
            Folder folder2 = folder.getStore().getDefaultFolder().getFolder("[Gmail]/Trash");
            logger.info("Try to empty Trash Folder");
            if (folder2.exists()) {
                folder2.open(Folder.READ_WRITE);
                deleteAll(folder2);
                folder2.expunge();
                logger.info("Trash has cleared");
            } else {
                logger.debug("Trash folder dose not exist");
            }
            folder2.close(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Empty office deleted folder.
     */
    public void emptyOfficeDeletedFolder() {
        try {
            Folder folder2 = folder.getStore().getDefaultFolder().getFolder("Deleted");
            logger.info("Try to empty Deleted Folder");
            if (folder2.exists()) {
                folder2.open(Folder.READ_WRITE);
                deleteAll(folder2);
                folder2.expunge();
                logger.info("Deleted has cleared");
            } else {
                logger.debug("Deleted folder dose not exist");
            }
            folder2.close(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Empty office junk.
     */
    public void emptyOfficeJunk() {
        try {
            Folder folder2 = folder.getStore().getDefaultFolder().getFolder("Junk");
            logger.info("Try to empty Junk Folder");
            if (folder2.exists()) {
                folder2.open(Folder.READ_WRITE);
                deleteAll(folder2);
                folder2.expunge();
                logger.info("Junk has cleared");
            } else {
                logger.debug("Junk folder dose not exist");
            }
            folder2.close(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete all message by using extra thread.
     *
     * @param folder the folder
     * @throws Exception the exception
     */
    private void deleteAll(final Folder folder) throws Exception {
        int count = folder.getMessageCount();
        if (count == 0)
            return;
        if (count < 3) {
            Arrays.stream(folder.getMessages()).forEach(this::delete);
        } else {
            int mid = count / 2;
            Thread delete = new Thread(() -> {
                try {
                    Arrays.stream(folder.getMessages(1, mid)).forEach(this::delete);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, "Deleting Msg Thread");

            delete.start();
            Arrays.stream(folder.getMessages(mid + 1, count)).forEach(this::delete);
            delete.join();
        }
    }

}
