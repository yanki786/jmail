package javax.jmail.label;

import com.sun.mail.imap.IMAPStore;
import org.apache.logging.log4j.Logger;

import javax.mail.Folder;
import javax.mail.Session;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * The Class GeneralLabelManager provide functionality
 * to create,rename,delete,exist label
 * or to get list of label without opening folder.
 * <p>
 * Moreover, It don't throw Checked Exceptions.
 *
 * @author Abhishek Bansal
 */
public class GeneralLabelManager {
    public static Logger logger;
    protected String id;
    private String password;
    private IMAPStore store;

    /**
     * Gets the store.
     *
     * @return the store
     */
    public IMAPStore getIMAPStore() {
        return store;
    }


    protected Folder getFolder(String folderName) {
        if (!store.isConnected()) {
            logger.error("IMAPStore is not connected, Exception : {}", "NoStoreConnectionException");
            throw new RuntimeException("NoStoreConnectionException");
        }
        if (folderName == null || folderName.length() == 0) {
            logger.error("folder can't created, Exception : {}", "InvalidFolderNameException");
            throw new RuntimeException("InvalidFolderNameException");
        }
        try {
            return store.getFolder(folderName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Instantiates a new label manager impl.
     *
     * @param id       the id
     * @param password the password
     */
    public GeneralLabelManager(String id, String password) {
        this.id = id;
        this.password = password;
    }

    /**
     * Instantiates a new label manager impl.
     *
     * @param id       the id
     * @param password the password
     * @param session  the session
     */
    public GeneralLabelManager(Session session, String id, String password) {
        this(id, password);
        this.connectStore(session);
    }

    /**
     * Connect store by imaps protocol
     * and host to imap if not found then smtp
     *
     * @param session the session
     */
    private void connectStore(Session session) {

        String protocol = "imaps";
        String host = session.getProperty("mail.imap.host");
        if (host == null)
            host = session.getProperty("mail.smtp.host");

        logger.info("Request to establish IMAPStore connection by {}", id);
        logger.debug("protocol : {} host : {} ", protocol, host);
        try {
            store = (IMAPStore) session.getStore(protocol);
            store.connect(host, id, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        logger.info("IMAPStore connection is established");
    }

    /**
     * Fetch labels.
     *
     * @return the array list of available label names
     */
    public ArrayList<String> fetchLabels() {

        if (!store.isConnected()) {
            logger.error("IMAPStore is not connected, Exception : {} ", "NoStoreConnectionException");
            throw new RuntimeException("NoStoreConnectionException");
        }

        logger.info("Fetching labels for {}", id);
        final ArrayList<String> folders = new ArrayList<>();
        try {
            Arrays.stream(store.getDefaultFolder().list("*")).forEach(a -> folders.add(a.getFullName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return folders;
    }

    /**
     * Creates the label.
     *
     * @param folderName the folder name
     * @return true, if successfully created
     */
    public boolean createLabel(String folderName) {
        logger.info("Request to create label : {}  for {}", folderName, id);
        Folder folderToUse = getFolder(folderName);
        try {
            if (!folderToUse.exists()) return folderToUse.create(Folder.HOLDS_FOLDERS | Folder.HOLDS_MESSAGES);
            else return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Update label.
     *
     * @param folderName    the folder name
     * @param newFolderName the new folder name
     * @return true, if successfully updated
     * @throws RuntimeException saying NoStoreConnectionException or
     *                          InvalidFolderNameException
     */
    public boolean updateLabel(String folderName, String newFolderName) {
        logger.info("Request to update folder name from {} to {} ", folderName, newFolderName);

        Folder folderToUpdate = getFolder(folderName);
        Folder newFolder = getFolder(newFolderName);

        if (folderName.equals(newFolderName)) {
            logger.info("Ops! provided same name of both folder.");
            return false;
        }

        try {

            if (folderToUpdate == null) {
                logger.error("folder :{} dose not exist, Exception : {}.", folderName, "NoSuchFolderException");
                throw new RuntimeException("NoSuchFolderException");
            }

            if (newFolder.exists()) {
                logger.error("folder :{} already exist, Exception : {}.", newFolderName, "NoSuchFolderException");
                throw new RuntimeException("DuplicateFolderNameException");
            }

            return folderToUpdate.renameTo(newFolder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Delete label.
     *
     * @param folderName the folder name
     * @return true, if successfully deleted
     */
    public boolean deleteLabel(String folderName) {

        logger.info("Request to delete label : {}  for {}", folderName, id);
        Folder folderToDelete = getFolder(folderName);
        if (folderToDelete == null) throw new RuntimeException("NoSuchFolderException");
        try {
            return folderToDelete.delete(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * @return boolean value indicating label exist or not
     */
    public boolean exist(String folderName) {
        try {
            return getFolder(folderName).exists();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
