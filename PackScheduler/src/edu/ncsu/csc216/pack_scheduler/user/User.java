package edu.ncsu.csc216.pack_scheduler.user;

/**
 * User abstract class that constructs a User object that includes a students 
 * first name, last name, id, email, and password.
 * 
 * @author Dania Swelam
 */
public abstract class User {

	/** Student's first name. */
	private String firstName;
	/** Student's last name. */
	private String lastName;
	/** Student's Unity ID. */
	private String id;
	/** Student's email */
	private String email;
	/** Student's hashed password */
	private String password;

	/**
	 * Constructor for constructing a User object consisting of first name, last
	 * name, user ID, email, and hashed password.
	 * 
	 * @param firstName  the first name of the student
	 * @param lastName   the last name of the student
	 * @param id         the student's ID
	 * @param email      the email address of the student
	 * @param password   the hashed password of the student
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		super();
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Retrieves the first name of the student.
	 * 
	 * @return the first name of the student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the student.
	 * 
	 * @param firstName the first name to set
	 * @throws IllegalArgumentException if the first name is null or empty
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.isEmpty()) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Retrieves the last name of the student.
	 * 
	 * @return the last name of the student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the student.
	 * 
	 * @param lastName the last name to set
	 * @throws IllegalArgumentException if the last name is null or empty
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.isEmpty()) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Retrieves the student's ID.
	 * 
	 * @return the student's ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the student's ID.
	 * 
	 * @param id the student's ID to set
	 * @throws IllegalArgumentException if the student's ID is null or empty
	 */
	protected void setId(String id) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Retrieves the email address of the student.
	 * 
	 * @return the email address of the student
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the student.
	 * 
	 * @param email the email address to set
	 * @throws IllegalArgumentException if the email address is null, empty, or does
	 *                                  not meet the required format
	 */
	public void setEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Invalid email");
		}
	
		if (!email.contains("@")) {
			throw new IllegalArgumentException("Invalid email");
		}
	
		if (!email.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		}
	
		int periodIndex = email.lastIndexOf('.');
		int ampersandIndex = email.indexOf('@');
	
		if (periodIndex < ampersandIndex) {
			throw new IllegalArgumentException("Invalid email");
		}
	
		this.email = email;
	}

	/**
	 * Retrieves the hashed password of the student.
	 * 
	 * @return the hashed password of the student
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the hashed password of the student.
	 * 
	 * @param password the hashed password to set
	 * @throws IllegalArgumentException if the hashed password is null or empty
	 */
	public void setPassword(String password) {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * Computes the hash code for the User object.
	 * 
	 * @return The hash code value for this User object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param obj The reference object with which to compare.
	 * @return True if this User is the same as the obj argument; False
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}