package javax.jmail.beans;

import java.io.Serializable;

/**
 * The Class UserBean.
 *
 * @author Abhishek Bansal
 */
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The mail id. */
	private String mailId;

	/** The password. */
	transient private String password;

	/**
	 * Instantiates a new user bean.
	 * 
	 * @param id       the id
	 * @param password the password
	 */
	public UserBean(String id, String password) {
		this.mailId = id;
		this.password = password;
	}

	/**
	 * Gets the mail id.
	 * 
	 * @return the mailId
	 */
	public String getMailId() {
		return mailId;
	}

	/**
	 * Sets the mail id.
	 * 
	 * @param mailId the mailId to set
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
