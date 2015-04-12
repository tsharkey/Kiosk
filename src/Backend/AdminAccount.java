package Backend;

import java.io.Serializable;

/**
 * AdminAccount class handles the access and modification of username and password for the administrator account
 */
public class AdminAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	String username;
	String password;

	//constructor, initialization of the fields
	public AdminAccount(String user, String pass)
	{
		username = user;
		password = pass;
	}

	//getters and setters of the class

	//gets the password for the administrator account
	public String getPassword() {
		return password;
	}

	//sets the password for the administrator account
	public void setPassword(String password) {
		this.password = password;
	}

	//gets the user name of the administrator
	public String getUsername() {
		return username;
	}

	//set the user name of the administrator account to a new user name
	public void setUsername(String username) {
		this.username = username;
	}

	//prints the state of the administrator account
	public String toString() {
		return username + " " + password;
	}

}
