package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests for the CourseRoll class, including constructor, setEnrollmentCap, enroll, and drop functionality.
 * 
 * @author Dania Swelam
 */
public class CourseRollTest {

    /** The course for testing */
    private Course course;
    /** The course roll for testing */
    private CourseRoll roll;
    /** Student for testing enrollment and drop functionality */
    private Student zahir;
    /** Student for testing enrollment and drop functionality */
    private Student cassandra;
    /** Student for testing enrollment and drop functionality */
    private Student shannon;
    /** Student for testing enrollment and drop functionality */
    private Student demetrius;
    /** Student for testing enrollment and drop functionality */
    private Student raymond;
    /** Student for testing enrollment and drop functionality */
    private Student emerald;
    /** Student for testing enrollment and drop functionality */
    private Student lane;
    /** Student for testing enrollment and drop functionality */
    private Student griffith;
    /** Student for testing enrollment and drop functionality */
    private Student althea;
    /** Student for testing enrollment and drop functionality */
    private Student dylan;

    /**
     * Sets up the CourseRoll and Student objects before each test.
     * 
     * @throws Exception if there is an error during setup
     */
    @BeforeEach
    public void setUp() throws Exception {
        course = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
        roll = course.getCourseRoll();
        zahir = new Student("Zahir", "King", "zking", "zking@ncsu.edu", "pw", 15);
        cassandra = new Student("Cassandra", "Davis", "cdavis", "cdavis@ncsu.edu", "pw", 15);
        shannon = new Student("Shannon", "Doe", "sdoe", "sdoe@ncsu.edu", "pw", 15);
        demetrius = new Student("Demetrius", "Austin", "daustin", "daustin@ncsu.edu", "pw", 15);
        raymond = new Student("Raymond", "Brennan", "rbrennan", "rbrennan@ncsu.edu", "pw", 15);
        emerald = new Student("Emerald", "Frost", "efrost", "efrost@ncsu.edu", "pw", 15);
        lane = new Student("Lane", "Berg", "lberg", "lberg@ncsu.edu", "pw", 15);
        griffith = new Student("Griffith", "Stone", "gstone", "gstone@ncsu.edu", "pw", 15);
        althea = new Student("Althea", "Hicks", "ahicks", "ahicks@ncsu.edu", "pw", 15);
        dylan = new Student("Dylan", "Nolan", "dnolan", "dnolan@ncsu.edu", "pw", 15);
    }

    /**
     * Tests the CourseRoll constructor.
     */
    @Test
    public void testCourseRollConstructor() {
        assertNotNull(roll);
        assertEquals(10, roll.getEnrollmentCap());
        assertEquals(10, roll.getOpenSeats());
        assertEquals(0, roll.getNumberOnWaitlist());
    }

    /**
     * Tests the setEnrollmentCap() method.
     */
    @Test
    public void testSetEnrollmentCap() {
    	 roll.setEnrollmentCap(15);
         assertEquals(15, roll.getEnrollmentCap(), "Enrollment cap should be 15.");
         roll.setEnrollmentCap(10);
         assertEquals(10, roll.getEnrollmentCap(), "Enrollment cap should be 10.");
     }

    /**
     * Tests the enroll(Student) method.
     */
    @Test
    public void testEnroll() {
        roll.enroll(zahir);
        assertEquals(9, roll.getOpenSeats());
        assertEquals(0, roll.getNumberOnWaitlist());

        roll.enroll(cassandra);
        assertEquals(8, roll.getOpenSeats());
        assertEquals(0, roll.getNumberOnWaitlist());

        // Fill the roll to capacity
        for (int i = 0; i < 8; i++) {
            roll.enroll(new Student("First" + i, "Last" + i, "fl" + i, "fl" + i + "@ncsu.edu", "pw", 15));
        }
        assertEquals(0, roll.getOpenSeats());

        // Try to add another student, which should go to the waitlist
        roll.enroll(shannon);
        assertEquals(1, roll.getNumberOnWaitlist());

        // Try to add another student to the waitlist
        Student extraStudent = new Student("Extra", "Student", "extra", "extra@ncsu.edu", "pw", 15);
        roll.enroll(extraStudent);
        assertEquals(2, roll.getNumberOnWaitlist());

        // Attempt to enroll a null student
        assertThrows(IllegalArgumentException.class, () -> roll.enroll(null));

        // Attempt to enroll a student already enrolled
        assertThrows(IllegalArgumentException.class, () -> roll.enroll(zahir));

        // Fill the waitlist to capacity
        for (int i = 0; i < 8; i++) {
            roll.enroll(new Student("Wait" + i, "List" + i, "wl" + i, "wl" + i + "@ncsu.edu", "pw", 15));
        }
        assertEquals(10, roll.getNumberOnWaitlist());

        // Try to add another student, which should fail as waitlist is full
        assertThrows(IllegalArgumentException.class, () -> roll.enroll(new Student("Full", "Waitlist", "full", "full@ncsu.edu", "pw", 15)));
    }

    /**
     * Tests the drop(Student) method.
     */
    @Test
    public void testDrop() {
        // Enroll a student and check open seats
        roll.enroll(zahir);
        assertEquals(9, roll.getOpenSeats());

        // Drop the student and check open seats
        roll.drop(zahir);
        assertEquals(10, roll.getOpenSeats());

        // Attempt to drop a null student
        assertThrows(IllegalArgumentException.class, () -> roll.drop(null));

        // Enroll students to fill the roll and waitlist
        roll.enroll(zahir);
        roll.enroll(cassandra);
        roll.enroll(shannon);
        roll.enroll(demetrius);
        roll.enroll(raymond);
        roll.enroll(emerald);
        roll.enroll(lane);
        roll.enroll(griffith);
        roll.enroll(althea);
        roll.enroll(dylan);

        for (int i = 0; i < 10; i++) {
            roll.enroll(new Student("Wait" + i, "List" + i, "wl" + i, "wl" + i + "@ncsu.edu", "pw", 15));
        }
        assertEquals(0, roll.getOpenSeats());
        assertEquals(10, roll.getNumberOnWaitlist());

        // Drop a student from the roll and ensure a student from the waitlist is enrolled
        roll.drop(zahir);
        assertEquals(0, roll.getOpenSeats());
        assertEquals(9, roll.getNumberOnWaitlist());
        assertTrue(roll.canEnroll(zahir));

        // Drop a student from the waitlist
        Student waitlistStudent = new Student("Wait0", "List0", "wl0", "wl0@ncsu.edu", "pw", 15);
        roll.drop(waitlistStudent);
        assertEquals(8, roll.getNumberOnWaitlist());
    }
    
    /**
     * Tests getOpenSeat()
     */
    @Test
    public void testGetOpenSeats() {
        assertEquals(10, roll.getOpenSeats(), "There should be 10 open seats.");
        Student s = new Student("John", "Doe", "jdoe", "jdoe@example.com", "password", 15);
        roll.enroll(s);
        assertEquals(9, roll.getOpenSeats(), "There should be 9 open seats.");
    }

    /**
     * Tests the canEnroll(Student) method.
     */
    @Test
    public void testCanEnroll() {
        assertTrue(roll.canEnroll(zahir));

        roll.enroll(zahir);
        assertFalse(roll.canEnroll(zahir));

        roll.enroll(cassandra);
        roll.enroll(shannon);
        roll.enroll(demetrius);
        roll.enroll(raymond);
        roll.enroll(emerald);
        roll.enroll(lane);
        roll.enroll(griffith);
        roll.enroll(althea);
        roll.enroll(dylan);

        for (int i = 0; i < 10; i++) {
            roll.enroll(new Student("Wait" + i, "List" + i, "wl" + i, "wl" + i + "@example.com", "password"));
        }

        assertFalse(roll.canEnroll(new Student("First", "Student", "firstlastid", "overflow@example.com", "password")));
    }
}
