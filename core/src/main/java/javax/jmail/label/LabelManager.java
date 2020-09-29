package javax.jmail.label;

import javax.mail.*;

import com.sun.mail.imap.IMAPFolder;

/**
 * The Class LabelManager provide same functionality
 * as GeneralLabelManager without providing name of label
 * everytime to perform task.
 * <p>
 * At time of instantiation open folder in READ_ONLY mode
 *
 * @author Abhishek Bansal
 */
public class LabelManager extends GeneralLabelManager implements AutoCloseable {

    private JFolder jFolder;

    public LabelManager(Session session, String id, String password, String folderName) {
        super(session, id, password);
        openFolder(folderName);
    }

    /**
     * openFolder if not opened and will close other folder
     * if any other folder is opened only one folder will remain open
     *
     * @param folderName name of folder
     */
    public void openFolder(String folderName) {
        logger.info("Opening folder : {}  for {}", folderName, id);
        if (jFolder != null && jFolder.getFolder().getName().equals(folderName)) return;
        else if (jFolder != null) jFolder.close();
        jFolder = new JFolder(getFolder(folderName));
    }

    /**
     * updateLabel if folder is opened by using {@link #openFolder(String)}
     *
     * @return boolean value indicating is folder name is updated
     */
    public boolean updateLabel(String newFolderName) {

        logger.info("Request to update folder name from {} to {} ", jFolder.getFolder().getName(), newFolderName);

        Folder folderToUpdate = jFolder.getFolder();
        Folder newFolder = getFolder(newFolderName);

        if (folderToUpdate.getName().equals(newFolderName)) {
            logger.info("Ops! provided same name of both folder.");
            return false;
        }

        try {
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
     * deleteLabel if folder is opened by using {@link #openFolder(String)}
     *
     * @return boolean value indicating is folder deleted
     */
    public boolean deleteLabel() {
        try {
            return jFolder.getFolder().delete(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @return boolean value folder exist
     */
    public boolean exist() {
        try {
            return jFolder.getFolder().exists();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public IMAPFolder getIMAPFolder() {
        return jFolder.getFolder();
    }

    public JFolder getJFolder() {
        return jFolder;
    }

    /**
     * close the open folder
     */
    public void closeFolder() {
        jFolder.close();
        jFolder = null;
    }

    public void close() {
        try {
            closeFolder();
            getIMAPStore().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public class JFolder {
        private IMAPFolder folder;

        protected JFolder(Folder folder) {
            this.folder = (IMAPFolder) folder;
            try {
                this.folder.open(Folder.READ_ONLY);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        public boolean hasNewMessages() {
            try {
                return folder.hasNewMessages();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        public int getNewMessageCount() {
            try {
                return folder.getNewMessageCount();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public int getDeletedMessageCount() {
            try {
                return folder.getDeletedMessageCount();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public int getMessageCount() {
            try {
                return folder.getMessageCount();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        public int getUnreadMessageCount() {
            try {
                return folder.getUnreadMessageCount();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public IMAPFolder getFolder() {
            return folder;
        }

        protected void close() {
            try {
                folder.close(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
