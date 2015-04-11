package Backend;

/**
 * 
 * @author Brendan Casey
 *
 */

public class UserData {
	private String firstName;
	private String lastName;
	private String fullName;
	private String email;
	private String phone;
	private String role;

	public UserData(String firstName, String lastName, String email,
			String phone, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = firstName + " " + lastName;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return fullName;
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
