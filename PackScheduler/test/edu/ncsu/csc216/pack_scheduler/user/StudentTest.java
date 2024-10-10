package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Test for Student Class
 * 
 * @author Dania Swelam
 */
class StudentTest {

	/** Test Student's first name. */
	private static final String FIRST_NAME = "first";
	/** Test Student's last name */
	private static final String LAST_NAME = "last";
	/** Test Student's id */
	private static final String ID = "flast";
	/** Test Student's email */
	private static final String EMAIL = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private static final String PASSWORD = "pw";
	/** Test Student's max credits. */
	private static final int MAX_CREDITS = 18;

	/**
	 * Tests constructing a Student with max credits.
	 */
	@Test
	void testStudentWithMaxCredits() {
		// Test a valid construction
		Student s = assertDoesNotThrow(() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS),
				"Should not throw exception");

		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "Invalid max credits"));
	}

	/**
	 * Tests constructing a Student without max credits.
	 */
	@Test
	void testStudentWithoutMaxCredits() {
		Student s = assertDoesNotThrow(() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 18),
				"Should not throw exception");

		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(18, s.getMaxCredits(), "Invalid max credits"));
	}

	/**
	 * Tests setFirstName().
	 */
	@Test
	void testSetFirstNameValid() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);

		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "Invalid max credits"));

		// Valid set
		s.setFirstName("first");
		assertAll("Student", () -> assertEquals("first", s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "Invalid max credits"));
	}

	/**
	 * Test Invalid First Name.
	 * 
	 * @param invalidName invalid input for the test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	void testSetFirstNameInvalid(String invalidName) {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(invalidName, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid first name", e1.getMessage(),
				"Incorrect exception thrown with invalid first name - " + invalidName);
		assertEquals("first", s.getFirstName()); // Check that last name didn't change
	}

	/**
	 * Tests setLastName()
	 */
	@Test
	void testSetLastNameValid() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);

		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "Invalid max credits"));

		// Valid set
		s.setLastName("last");
		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals("last", s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "Invalid max credits"));
	}

	/**
	 * Test Invalid Last Name.
	 * 
	 * @param invalidName invalid input for the test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	void testSetLastNameInvalid(String invalidName) {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, invalidName, ID, EMAIL, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid last name", e1.getMessage(),
				"Incorrect exception thrown with invalid last name - " + invalidName);
		assertEquals("last", s.getLastName()); // Check that last name didn't change
	}

	/**
	 * Tests setId(). This can ONLY be done through the Student constructor. The
	 * test only considers valid values.
	 * 
	 * @param studentId valid Student Id to test
	 */
	@ParameterizedTest
	@ValueSource(strings = { "zking", "lberg", "ahicks", "gstone", "efrost" })
	public void testSetIdValid(String studentId) {

		// Testing valid names
		Student student = assertDoesNotThrow(
				() -> new Student(FIRST_NAME, LAST_NAME, studentId, EMAIL, PASSWORD, MAX_CREDITS),
				"Should not throw exception");
		assertEquals(studentId, student.getId(), "Failed test with valid student id - " + studentId);
	}

	/**
	 * Tests setId(). This can ONLY be done through the Student constructor. The
	 * test only considers invalid values, which should throw
	 * IllegalArgumentExceptions.
	 * 
	 * @param invalidStudentId invalid Student Id to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetIdInvalid(String invalidStudentId) {
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, invalidStudentId, EMAIL, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid id", e1.getMessage(),
				"Incorrect exception thrown with invalid student id - " + invalidStudentId);
	}

	/**
	 * Tests setEmail().
	 */
	@Test
	void testSetEmailValid() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);

		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "Invalid max credits"));

		// Valid set
		s.setEmail("flast@ncsu.edu");
		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals("flast@ncsu.edu", s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "Invalid max credits"));
	}

	/**
	 * Tests Invalid Email.
	 * 
	 * @param invalidEmail invalid input for the test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "first.last@address", ".flast@address", "flast.ncsu", "flast@ncsu" })
	void testSetEmailInvalid(String invalidEmail) {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.setEmail(invalidEmail));
		assertEquals("Invalid email", e1.getMessage(),
				"Incorrect exception thrown with invalid input - " + invalidEmail);
	}

	/**
	 * Tests setPassword().
	 */
	@Test
	void testSetPasswordValid() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);

		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "Invalid max credits"));

		// Valid set
		s.setPassword("pw");
		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals("pw", s.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "Invalid max credits"));
	}

	/**
	 * Test Invalid Password.
	 * 
	 * @param invalid invalid input for the test
	 */

	@ParameterizedTest
	@NullAndEmptySource
	void testSetPasswordInvalid(String invalid) {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, invalid, MAX_CREDITS));
		assertEquals("Invalid password", e1.getMessage(),
				"Incorrect exception thrown with invalid password - " + invalid);
		assertEquals("pw", s.getPassword()); // Check that last name didn't change
	}

	/**
	 * Tests setMaxCredits().
	 */
	@Test
	void testSetMaxCreditsValid() {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);

		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_CREDITS, s.getMaxCredits(), "Invalid max credits"));

		// Valid set
		s.setMaxCredits(18);
		assertAll("Student", () -> assertEquals(FIRST_NAME, s.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, s.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, s.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, s.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, s.getPassword(), "Invalid password"),
				() -> assertEquals(18, s.getMaxCredits(), "Invalid max credits"));
	}

	/**
	 * Tests Invalid Max Credits.
	 * 
	 * @param invalidCredits invalid input for the test
	 */
	@ParameterizedTest
	@ValueSource(ints = { 19, 2 })
	void testSetMaxCreditsInvalid(int invalidCredits) {
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, invalidCredits));
		assertEquals("Invalid max credits", e1.getMessage(),
				"Incorrect exception thrown with invalid max credits - " + invalidCredits);
		assertEquals(18, s.getMaxCredits()); // Check that last name didn't change
	}

	/**
	 * Tests that Hash Code is correct.
	 */
	@Test
	void testHashCode() {

		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s3 = new Student("Rami", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s4 = new Student(FIRST_NAME, "Alaraj", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s5 = new Student(FIRST_NAME, LAST_NAME, "ralaraj", EMAIL, PASSWORD, MAX_CREDITS);
		Student s6 = new Student(FIRST_NAME, LAST_NAME, ID, "ralaraj@ncsu.edu", PASSWORD, MAX_CREDITS);
		Student s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 11);

		// Test for the same hash code for the same values
		assertEquals(s1.hashCode(), s2.hashCode());

		// Test for each of the fields
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
	}

	/**
	 * Test EqualsObject() method.
	 */
	@Test
	void testEqualsObject() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 18);
		Student s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 18);
		Student s3 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 17);
		Student s4 = null;
		Student s5 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD, 18);
		Object obj = new Object();

		assertEquals(s1, s2);
		assertEquals(s2, s1);
		assertEquals(s1.getMaxCredits(), s2.getMaxCredits());

		assertNotEquals(s1, s5);
		assertNotEquals(s1, s4);

		assertFalse(s1.equals(obj));
		assertTrue(s1.equals(s1));
		assertTrue(s1.equals(s2));
		assertFalse(s1.equals(s3));
	}

	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		assertEquals("first,last,flast,first_last@ncsu.edu," + PASSWORD + ",18", s1.toString());
	}

	/**
	 * Test that the compareTo() method works correctly by adding a new test method
	 * to StudentTest. Students should be ordered by last name, then first name,
	 * then their unity id.
	 */
	@Test
	public void compareTo() {
		// Create Student instances
		Student student1 = new Student("John", "Doe", "jdoe", "jdoe@example.com", "password123", 18);
		Student student2 = new Student("Jane", "Doe", "jadoe", "jadoe@example.com", "password123", 18);
		Student student3 = new Student("John", "Smith", "jsmith", "jsmith@example.com", "password123", 18);
		Student student4 = new Student("Alice", "Doe", "adoe", "adoe@example.com", "password123", 18);
		Student student5 = new Student("John", "Doe", "adoe", "adoe@example.com", "password123", 18);

		// Compare by last name, then first name, then ID
		assertTrue(student1.compareTo(student2) > 0); // "Doe" == "Doe", "John" > "Jane"
		assertTrue(student2.compareTo(student3) < 0); // "Doe" < "Smith"
		assertTrue(student1.compareTo(student4) > 0); // "Doe" == "Doe", "John" > "Alice"
		assertTrue(student1.compareTo(student5) > 0); // "Doe" == "Doe", "John" == "John", "jdoe" > "adoe"
		assertTrue(student2.compareTo(student4) > 0); // "Doe" == "Doe", "Jane" > "Alice"

		// Compare equal students
		Student anotherStudent1 = new Student("John", "Doe", "jdoe", "jdoe@example.com", "password123", 18);
		assertEquals(0, student1.compareTo(anotherStudent1));

		// Check transitivity
		assertTrue(student1.compareTo(student3) < 0); // "Doe" < "Smith"
		assertTrue(student3.compareTo(student2) > 0); // "Smith" > "Doe"
		assertTrue(student1.compareTo(student3) < 0); // "Doe" < "Smith"

		// Tests for specified object being null
		try {
			student1.compareTo(null);
			fail("Comparing to null should throw NullPointerException");
		} catch (NullPointerException e) {
			// Expected exception
		}
	}

	/**
     * Test for canAdd().
     */
    @Test
    public void testCanAdd() {
        Course course1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100);
        Course course2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
        Course course3 = new Course("CSC230", "C and Software Tools", "001", 3, "dbsturgi", 10, "MW", 1145, 1300);
        Course course7 = new Course("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 10, "F", 1040, 1230);

        Student student = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 12);

        assertTrue(student.canAdd(course1));

        assertTrue(student.canAdd(course2));

        assertTrue(student.canAdd(course3));

        assertTrue(student.canAdd(course7));

    }
}
