package javax.jmail.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jmail.beans.MessageBean;
import javax.jmail.beans.MessageBeanImp;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.UIDFolder;

import com.sun.mail.imap.IMAPFolder;

/**
 * The Class MessageReaderImp use to read message details from IMAPFolder .
 *
 * @author Abhishek Bansal.
 */
public class MessageReaderImp implements MessageReader {

    /**
     * The folder.
     */
    private IMAPFolder folder;

    /**
     * The uid folder.
     */
    private UIDFolder uidFolder;

    /**
     * Instantiates a new message reader.
     *
     * @param folder the folder
     */
    public MessageReaderImp(IMAPFolder folder) {
        this.folder = folder;
        uidFolder = (UIDFolder) folder;
        folderValidator();
    }

    /**
     * Gets the Folder instance.
     *
     * @return the folder
     */
    public Folder getFolder() {
        return folder;
    }

    /**
     * Gets the msg.
     *
     * @param msg the msg
     * @return the msg
     */
    private MessageBean getMsg(Message msg) {
        try {
            return new MessageBeanImp(msg).setUID(uidFolder.getUID(msg));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the message count.
     *
     * @return the message count
     */
    public int getMessageCount() {
        try {
            return folder.getMessageCount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is use internally to validate IMAPFolder object;.
     */
    private void folderValidator() {
        if (folder == null)
            throw new RuntimeException("IMAPFolder cant be null.");
        try {
            if (!folder.exists())
                throw new RuntimeException("  " + folder.getName() + " IMAPFolder don't exist");
            if (!folder.isOpen())
                folder.open(Folder.READ_ONLY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method will return MessageBeanGetter object, message that are present in
     * IMAPFolder by mid;.
     *
     * @param id the id
     * @return the message by ID
     */
    public MessageBean getMessageByID(long id) {
        try {
            return getMsg(folder.getMessageByUID(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the message.
     *
     * @param msgnum the msgnum
     * @return the message
     */
    public MessageBean getMessage(int msgnum) {
        try {
            return getMsg(folder.getMessage(msgnum));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the messages.
     *
     * @param msgnums the msgnums
     * @return the messages
     */
    public List<MessageBean> getMessages(int[] msgnums) {
        List<MessageBean> msgs = new ArrayList<>();
        try {
            Arrays.asList(folder.getMessages(msgnums)).forEach(x -> msgs.add(getMsg(x)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return msgs;
    }

    /**
     * Gets the messages.
     *
     * @param start the start
     * @param end   the end
     * @return the messages
     */
    public List<MessageBean> getMessages(int start, int end) {
        List<MessageBean> msgs = new ArrayList<>();
        try {
            Arrays.asList(folder.getMessages(start, end)).forEach(x -> msgs.add(getMsg(x)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return msgs;
    }

    /**
     * Gets the messages.
     *
     * @param ids the ids
     * @return the messages
     */
    public List<MessageBean> getMessages(long[] ids) {
        List<MessageBean> msgs = new ArrayList<>();
        try {
            Arrays.asList(folder.getMessagesByUID(ids)).forEach(x -> msgs.add(getMsg(x)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return msgs;
    }

    /**
     * This method will return java.util.List of MessageBeanGetter, All messages
     * that are present in IMAPFolder;
     *
     * @return the messages
     */
    public List<MessageBean> getMessages() {
        List<MessageBean> msgs = new ArrayList<>();
        try {
            if (folder.getMessages() == null)
                return msgs;

            Arrays.asList(folder.getMessages()).forEach(x -> msgs.add(getMsg(x)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return msgs;
    }

    /**
     * Gets the unread messages.
     *
     * @return the unread messages
     */
    public List<MessageBean> getUnreadMessages() {
        List<MessageBean> msgs = new ArrayList<>();
        try {
            if (folder.getMessages() == null)
                return msgs;
            Arrays.stream(folder.getMessages()).filter(x -> {
                try {
                    return !x.getFlags().contains(Flags.Flag.SEEN);
                } catch (Exception e) {
                    return false;
                }
            }).forEach(x -> msgs.add(getMsg(x)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return msgs;
    }

}
