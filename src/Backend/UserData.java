package Backend;

/**
 * This class stores information about a particular USER from the database.
 * 
 * @author Brendan Casey
 *
 */

public class UserData {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String role;

	/**
	 * Constructor to store all various information about a visit.
	 */
	public UserData(String firstName, String lastName, String email,
			String phone, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	/**
	 * Getters for details specific to USER.
	 * 
	 * @return
	 */
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getRole() {
		return role;
	}
}
