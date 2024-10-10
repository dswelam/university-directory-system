package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Tests the functionality of the RegistrationManager class.
 * 
 * @author Dania Swelam
 */
public class RegistrationManagerTest {

	/** Instance for RegistrationManager */
	private RegistrationManager manager;
	/** Registrar user name */
	private String registrarUsername;
	/** Registrar password */
	private String registrarPassword;
	/** Properties file */
	private static final String PROP_FILE = "registrar.properties";
	/** Properties file */
	private Properties prop;

	/**
	 * Sets up the CourseManager and clears the data.
	 * 
	 * @throws Exception if error
	 */
	@BeforeEach
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.logout();
		manager.clearData();

		prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			registrarUsername = prop.getProperty("id");
			registrarPassword = prop.getProperty("pw");
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot process properties file.");
		}
	}

	/**
	 * Tests the getCourseCatalog method to ensure that courses can be added and
	 * retrieved correctly.
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog cc = manager.getCourseCatalog();
		cc.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330,
				1445);
		String[][] catalogArray = cc.getCourseCatalog();
		assertEquals(1, catalogArray.length);
		assertEquals("CSC216", catalogArray[0][0]);
	}

	/**
	 * Tests the getStudentDirectory method to ensure that students can be added and
	 * retrieved correctly.
	 */
	@Test
	public void testGetStudentDirectory() {
		StudentDirectory studentDirectory = manager.getStudentDirectory();
		studentDirectory.addStudent("Demetrius", "Austin", "daustin", "Curabitur.egestas.nunc@placeratorcilacus.co.uk",
				"pw", "pw", 18);
		String[][] directoryArray = studentDirectory.getStudentDirectory();
		assertEquals(1, directoryArray.length);
		assertEquals("Demetrius", directoryArray[0][0]);
		assertEquals("Austin", directoryArray[0][1]);
		assertEquals("daustin", directoryArray[0][2]);
	}

	/**
	 * Tests the getFacultyDirectory method to ensure that faculty can be added and
	 * retrieved correctly.
	 */
	@Test
	public void testGetFacultyDirectory() {
		FacultyDirectory facultyDirectory = manager.getFacultyDirectory();
		facultyDirectory.addFaculty("Ashley", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		String[][] directoryArray = facultyDirectory.getFacultyDirectory();
		assertEquals(1, directoryArray.length);
		assertEquals("Ashley", directoryArray[0][0]);
		assertEquals("Witt", directoryArray[0][1]);
		assertEquals("awitt", directoryArray[0][2]);
	}

	/**
	 * Tests the login method to ensure that login works with correct credentials
	 * and fails with incorrect credentials.
	 */
	@Test
	public void testLogin() {
		String studentId = "daustin";
		String facultyId = "awitt";
		String password = "pw";
		String facultyPassword = "pw";

		// Add a student for testing
		StudentDirectory sd = manager.getStudentDirectory();
		sd.addStudent("Demetrius", "Austin", studentId, "Curabitur.egestas.nunc@placeratorcilacus.co.uk", password,
				password, 18);

		// Add a faculty for testing
		FacultyDirectory fd = manager.getFacultyDirectory();
		fd.addFaculty("Ashely", "Witt", facultyId, "mollis@Fuscealiquetmagna.net", facultyPassword, facultyPassword, 2);

		// Verify that the student was added correctly
		Student addedStudent = sd.getStudentById(studentId);
		assertNotNull(addedStudent, "Student should be added to the directory");

		// Verify that the faculty was added correctly
		Faculty addedFaculty = fd.getFacultyById(facultyId);
		assertNotNull(addedFaculty, "Faculty should be added to the directory");

		// Test successful login as student
		assertTrue(manager.login(studentId, password), "Login should succeed with correct credentials as student");
		assertEquals(studentId, manager.getCurrentUser().getId(), "Current user should be the student after login");

		// Test logout
		manager.logout();
		assertNull(manager.getCurrentUser(), "Current user should be null after logout");

		// Test successful login as faculty
		assertTrue(manager.login(facultyId, facultyPassword),
				"Login should succeed with correct credentials as faculty");
		assertEquals(facultyId, manager.getCurrentUser().getId(), "Current user should be the faculty after login");

		// Test invalid login
		assertFalse(manager.login(facultyId, "wrongPassword"), "Login should fail with incorrect password");
		assertFalse(manager.login("wrongId", facultyPassword), "Login should fail with incorrect ID");

		// Test that another user cannot log in while a user is already logged in
		assertFalse(manager.login(studentId, password), "Login should fail if another user is already logged in");

		// Logout and then test login with another user
		manager.logout();
		assertTrue(manager.login(studentId, password), "Login should succeed after logout with correct credentials");
	}

	/**
	 * Tests the logout method to ensure that the current user is null after logging
	 * out.
	 */
	@Test
	public void testLogout() {
		manager.logout();
		assertNull(manager.getCurrentUser(), "Current user should be null after logout");
	}

	/**
	 * Tests the getCurrentUser method to ensure that the correct user is returned
	 * after logging in, and that the current user is null after logging out.
	 */
	@Test
	public void testGetCurrentUser() {
		String id = prop.getProperty("id");
		String password = prop.getProperty("pw");

		// Perform login
		assertTrue(manager.login(id, password), "Login should succeed with correct credentials");

		// Test getting the current user
		assertNotNull(manager.getCurrentUser(), "Current user should not be null after login");
		assertEquals(id, manager.getCurrentUser().getId(), "Current user ID should match the logged in ID");

		// Test logout
		manager.logout();
		assertNull(manager.getCurrentUser(), "Current user should be null after logout");
	}

	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser(),
					"RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.");
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(registrarUsername, manager.getCurrentUser().getId(),
					"RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.");
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.");

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(3, scheduleFrost.getScheduleCredits(), "User: efrost\nCourse: CSC216-001\n");
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(1, scheduleFrostArray.length, "User: efrost\nCourse: CSC216-001\n");
		assertEquals("CSC216", scheduleFrostArray[0][0], "User: efrost\nCourse: CSC216-001\n");
		assertEquals("001", scheduleFrostArray[0][1], "User: efrost\nCourse: CSC216-001\n");
		assertEquals("Software Development Fundamentals", scheduleFrostArray[0][2],
				"User: efrost\nCourse: CSC216-001\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleFrostArray[0][3], "User: efrost\nCourse: CSC216-001\n");
		assertEquals("9", scheduleFrostArray[0][4], "User: efrost\nCourse: CSC216-001\n");

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits");

		// Check Student Schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(9, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length, "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC216", scheduleHicksArray[0][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[0][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Software Development Fundamentals", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		// assertEquals("9", scheduleHicksArray[0][4], "User: ahicks\nCourse:
		// CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC226", scheduleHicksArray[1][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[1][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[1][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC116", scheduleHicksArray[2][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("003", scheduleHicksArray[2][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[2][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser(),
					"RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.");
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(registrarUsername, manager.getCurrentUser().getId(),
					"RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.");
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.");

		assertFalse(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: drop\nUser: efrost\nCourse: CSC217-211\nResults: False - student not enrolled in the course");
		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True");

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits(), "User: efrost\nCourse: CSC226-001, then removed\n");
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length, "User: efrost\nCourse: CSC226-001, then removed\n");

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits");

		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(9, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length, "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC216", scheduleHicksArray[0][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[0][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Software Development Fundamentals", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[0][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC226", scheduleHicksArray[1][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("001", scheduleHicksArray[1][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[1][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("CSC116", scheduleHicksArray[2][0], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("003", scheduleHicksArray[2][1], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");
		assertEquals("9", scheduleHicksArray[2][4], "User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n");

		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: drop\nUser: ahicks\nCourse: CSC226-001\nResults: True");

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(6, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(2, scheduleHicksArray.length,
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("CSC216", scheduleHicksArray[0][0],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("001", scheduleHicksArray[0][1],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("Software Development Fundamentals", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("9", scheduleHicksArray[0][4],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("CSC116", scheduleHicksArray[1][0],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("003", scheduleHicksArray[1][1],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[1][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[1][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");
		assertEquals("9", scheduleHicksArray[1][4],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n");

		assertFalse(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: drop\nUser: ahicks\nCourse: CSC226-001\nResults: False - already dropped");

		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: drop\nUser: ahicks\nCourse: CSC216-001\nResults: True");

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(3, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(1, scheduleHicksArray.length,
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("CSC116", scheduleHicksArray[0][0],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("003", scheduleHicksArray[0][1],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("Intro to Programming - Java", scheduleHicksArray[0][2],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[0][3],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");
		assertEquals("9", scheduleHicksArray[0][4],
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n");

		assertTrue(manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: drop\nUser: ahicks\nCourse: CSC116-003\nResults: True");

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001, CSC116-003\n");
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length,
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001, CSC116-003\n");

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// Test if not logged in
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull(manager.getCurrentUser(),
					"RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.");
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(registrarUsername, manager.getCurrentUser().getId(),
					"RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.");
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")),
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.");

		manager.resetSchedule();
		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits(), "User: efrost\nCourse: CSC226-001, then schedule reset\n");
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length, "User: efrost\nCourse: CSC226-001, then schedule reset\n");
		assertEquals(10, catalog.getCourseFromCatalog("CSC226", "001").getCourseRoll().getOpenSeats(),
				"Course should have all seats available after reset.");

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict");
		assertTrue(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116");
		assertFalse(manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")),
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits");

		manager.resetSchedule();
		// Check Student schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits(),
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, then schedule reset\n");
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length,
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, then schedule reset\n");
		assertEquals(10, catalog.getCourseFromCatalog("CSC226", "001").getCourseRoll().getOpenSeats(),
				"Course should have all seats available after reset.");
		assertEquals(10, catalog.getCourseFromCatalog("CSC216", "001").getCourseRoll().getOpenSeats(),
				"Course should have all seats available after reset.");
		assertEquals(10, catalog.getCourseFromCatalog("CSC116", "003").getCourseRoll().getOpenSeats(),
				"Course should have all seats available after reset.");

		manager.logout();
	}

	/**
	 * Test for AddFacultyToCourse().
	 */
	@Test
	public void testAddFacultyToCourse() {
		manager.login(registrarUsername, registrarPassword);
		
		FacultyDirectory fd = manager.getFacultyDirectory();
		fd.addFaculty("Ashley", "Witt", "awitt", "awitt@ncsu.edu", "pw", "pw", 2);

		CourseCatalog cc = manager.getCourseCatalog();
		cc.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, null, 10, "TH", 1330,
				1445);

		Faculty faculty = fd.getFacultyById("awitt");
		Course course = cc.getCourseFromCatalog("CSC216", "001");

		manager.addFacultyToCourse(course, faculty);

		assertEquals(1, faculty.getSchedule().getNumScheduledCourses());
	}

	/**
	 * Test for RemoveFacultyFromCourse().
	 */
	@Test
	public void testRemoveFacultyFromCourse() {
		manager.login(registrarUsername, registrarPassword);

		FacultyDirectory fd = manager.getFacultyDirectory();
		fd.addFaculty("Ashley", "Witt", "awitt", "awitt@ncsu.edu", "pw", "pw", 2);

		CourseCatalog cc = manager.getCourseCatalog();
		cc.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, null, 10, "TH", 1330,
				1445);

		Faculty faculty = fd.getFacultyById("awitt");
		Course course = cc.getCourseFromCatalog("CSC216", "001");

		manager.addFacultyToCourse(course, faculty);
		manager.removeFacultyFromCourse(course, faculty);

		assertEquals(0, faculty.getSchedule().getNumScheduledCourses());
	}

	/**
	 * Test for ResetFacultySchedule().
	 */
	@Test
	public void testResetFacultySchedule() {
		manager.login(registrarUsername, registrarPassword);

		FacultyDirectory fd = manager.getFacultyDirectory();
		fd.addFaculty("Ashley", "Witt", "awitt", "awitt@ncsu.edu", "pw", "pw", 2);

		CourseCatalog cc = manager.getCourseCatalog();
		cc.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, null, 10, "TH", 1330,
				1445);
		cc.addCourseToCatalog("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, null, 10, "MWF",
				935, 1025);

		Faculty faculty = fd.getFacultyById("awitt");
		Course course1 = cc.getCourseFromCatalog("CSC216", "001");
		Course course2 = cc.getCourseFromCatalog("CSC226", "001");

		manager.addFacultyToCourse(course1, faculty);
		manager.addFacultyToCourse(course2, faculty);

		manager.resetFacultySchedule(faculty);

		assertEquals(0, faculty.getSchedule().getNumScheduledCourses());
	}
}
