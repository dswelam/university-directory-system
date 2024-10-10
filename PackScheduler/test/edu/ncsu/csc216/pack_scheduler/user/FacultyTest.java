/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * The Faculty class represents an individual faculty record. Faculty objects
 * have a number of courses they can teach in a given semester (between 1 and 3
 * inclusive) rather keeping track of credits as Students do.
 */
class FacultyTest {

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
	private static final int MAX_COURSES = 2;

	/**
	 * Test method for Faculty Constructor
	 */
	@Test
	void testFaculty() {
		Faculty f = assertDoesNotThrow(() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES),
				"Should not throw exception");

		assertAll("Faculty", () -> assertEquals(FIRST_NAME, f.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, f.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, f.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, f.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, f.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_COURSES, f.getMaxCourses(), "Invalid max courses"));
	}

	/**
	 * Test method for setMaxCourses().
	 */
	@Test
	void testSetMaxCourses() {
		Faculty f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);

		assertAll("Faculty", () -> assertEquals(FIRST_NAME, f.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, f.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, f.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, f.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, f.getPassword(), "Invalid password"),
				() -> assertEquals(MAX_COURSES, f.getMaxCourses(), "Invalid max courses"));

		// Valid set
		f.setMaxCourses(2);
		assertAll("Faculty", () -> assertEquals(FIRST_NAME, f.getFirstName(), "Invalid first name"),
				() -> assertEquals(LAST_NAME, f.getLastName(), "Invalid last name"),
				() -> assertEquals(ID, f.getId(), "Invalid id"),
				() -> assertEquals(EMAIL, f.getEmail(), "Invalid email"),
				() -> assertEquals(PASSWORD, f.getPassword(), "Invalid password"),
				() -> assertEquals(2, f.getMaxCourses(), "Invalid max courses"));
	}

	/**
	 * Tests Invalid Max Courses.
	 * 
	 * @param invalidCredits invalid input for the test
	 */
	@ParameterizedTest
	@ValueSource(ints = { 4, 0 })
	void testSetMaxCoursesInvalid(int invalidCredits) {
		Faculty f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, invalidCredits));
		assertEquals("Invalid max courses", e1.getMessage(),
				"Incorrect exception thrown with invalid max credits - " + invalidCredits);
		assertEquals(2, f.getMaxCourses()); // Check that last name didn't change
	}

	/**
	 * Test method for toString().
	 */
	@Test
	void testToString() {
		Faculty f1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		assertEquals("first,last,flast,first_last@ncsu.edu," + PASSWORD + ",2", f1.toString());
	}

	/**
	 * Test method for hashCode().
	 */
	@Test
	void testHashCode() {
		Faculty f1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		Faculty f2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		Faculty f3 = new Faculty("Rami", LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		Faculty f4 = new Faculty(FIRST_NAME, "Alaraj", ID, EMAIL, PASSWORD, MAX_COURSES);
		Faculty f5 = new Faculty(FIRST_NAME, LAST_NAME, "ralaraj", EMAIL, PASSWORD, MAX_COURSES);
		Faculty f6 = new Faculty(FIRST_NAME, LAST_NAME, ID, "ralaraj@ncsu.edu", PASSWORD, MAX_COURSES);
		Faculty f7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 3);

		// Test for the same hash code for the same values
		assertEquals(f1.hashCode(), f2.hashCode());

		// Test for each of the fields
		assertNotEquals(f1.hashCode(), f3.hashCode());
		assertNotEquals(f1.hashCode(), f4.hashCode());
		assertNotEquals(f1.hashCode(), f5.hashCode());
		assertNotEquals(f1.hashCode(), f6.hashCode());
		assertNotEquals(f1.hashCode(), f7.hashCode());
	}

	/**
	 * Test method for equals().
	 */
	@Test
	void testEqualsObject() {
		Faculty f1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 2);
		Faculty f2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 2);
		Faculty f3 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 1);
		Faculty f4 = null;
		Faculty f5 = new Faculty(FIRST_NAME, "different", ID, EMAIL, PASSWORD, 2);
		Object obj = new Object();

		assertEquals(f1, f2);
		assertEquals(f2, f1);
		assertEquals(f1.getMaxCourses(), f2.getMaxCourses());

		assertNotEquals(f1, f5);
		assertNotEquals(f1, f4);

		assertFalse(f1.equals(obj));
		assertTrue(f1.equals(f1));
		assertTrue(f1.equals(f2));
		assertFalse(f1.equals(f3));
	}

	/**
	 * Test method for isOverloaded().
	 */
	@Test
	void testIsOverloaded() {
		 // Create a new Faculty object
	    Faculty f = new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", 2);
	    FacultySchedule schedule = f.getSchedule();

	    // Ensure the faculty is not overloaded initially
	    assertFalse(f.isOverloaded(), "Faculty should not be overloaded initially");

	    // Add first course to the schedule
	    Course csc216 = new Course("CSC216", "Software Development Fundamentals", "001", 3, null, 50, "MW", 1330, 1445);
	    schedule.addCourseToSchedule(csc216);
	    assertFalse(f.isOverloaded(), "Faculty should not be overloaded after adding the first course");

	    // Add second course to the schedule
	    Course csc226 = new Course("CSC226", "Discrete Mathematics", "001", 3, null, 50, "TH", 1330, 1445);
	    schedule.addCourseToSchedule(csc226);
	    assertFalse(f.isOverloaded(), "Faculty should not be overloaded after adding the second course");

	    // Add third course to the schedule
	    Course csc316 = new Course("CSC316", "Data Structures", "001", 3, null, 50, "MWF", 1130, 1220);
	    schedule.addCourseToSchedule(csc316);
	    assertTrue(f.isOverloaded(), "Faculty should be overloaded after adding the third course");
	}
}
