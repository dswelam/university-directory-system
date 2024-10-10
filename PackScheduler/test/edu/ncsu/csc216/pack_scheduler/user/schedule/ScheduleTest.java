package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for Schedule class.
 * 
 * @author Dania Swelam
 */
public class ScheduleTest {

	/** The schedule of the student */
    private Schedule schedule;
    /** Sample course for testing */
    private Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100);
	/** Sample course for testing */
    private Course c2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);

    /**
     * Sets up tests with new schedule array.
     * 
     * @throws Exception for test methods to pass.
     */
    @Before
    public void setUp() throws Exception {
    	
        schedule = new Schedule();
    }

    /**
     * Test for Schedule class Constructor.
     */
    @Test
    public void testScheduleConstructor() {
        assertEquals("My Schedule", schedule.getTitle());
        assertEquals(0, schedule.getScheduledCourses().length);
    }

    /**
     * Test for addCourseToSchedule().
     */
    @Test
    public void testAddCourseToSchedule() {
    	
        assertTrue(schedule.addCourseToSchedule(c1));
        assertEquals(1, schedule.getScheduledCourses().length);

        // Test duplicate course
        try {
            schedule.addCourseToSchedule(c1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("You are already enrolled in CSC116", e.getMessage());
        }

        // Test course conflict
        Course c3 = new Course("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 10, "M", 1040, 1230);
        try {
            schedule.addCourseToSchedule(c3);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("The course cannot be added due to a conflict.", e.getMessage());
        }
    }

    /**
     * Test for removeCourseFromSchedule().
     */
    @Test
    public void testRemoveCourseFromSchedule() {
    	
        schedule.addCourseToSchedule(c1);
        schedule.addCourseToSchedule(c2);
        assertTrue(schedule.removeCourseFromSchedule(c1));
        assertEquals(1, schedule.getScheduledCourses().length);
    }

    /**
     * Test for resetSchedule().
     */
    @Test
    public void testResetSchedule() {
        schedule.addCourseToSchedule(c1);
        schedule.resetSchedule();
        assertEquals("My Schedule", schedule.getTitle());
        assertEquals(0, schedule.getScheduledCourses().length);
    }

    /**
     * Test for getScheduledCourses().
     */
    @Test
    public void testGetScheduledCourses() {
       
    	schedule.addCourseToSchedule(c1);
        schedule.addCourseToSchedule(c2);
        
        String[][] scheduledCourses = schedule.getScheduledCourses();
        assertEquals(2, scheduledCourses.length);
        assertEquals("CSC116", scheduledCourses[0][0]);
        assertEquals("001", scheduledCourses[0][1]);
        assertEquals("Intro to Programming - Java", scheduledCourses[0][2]);
        assertEquals("MW 9:10AM-11:00AM", scheduledCourses[0][3]);

        assertEquals("CSC216", scheduledCourses[1][0]);
        assertEquals("001", scheduledCourses[1][1]);
        assertEquals("Software Development Fundamentals", scheduledCourses[1][2]);
        assertEquals("TH 1:30PM-2:45PM", scheduledCourses[1][3]);
    }

    /**
     * Test for setTitle().
     */
    @Test
    public void testSetTitle() {
        schedule.setTitle("Fall 2024");
        assertEquals("Fall 2024", schedule.getTitle());

        try {
            schedule.setTitle(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid title.", e.getMessage());
        }
    }
    
    /**
     * Test for getScheduleCredits().
     */
    @Test
    public void testGetScheduleCredits() {
        Course course1 = new Course("CSC216", "Software Development", "001", 4, "instructor", 10, "MW", 1330, 1445);
        Course course2 = new Course("CSC226", "Discrete Math", "001", 3, "instructor", 10, "TH", 1330, 1445);

        assertEquals(0, schedule.getScheduleCredits());
        schedule.addCourseToSchedule(course1);
        assertEquals(4, schedule.getScheduleCredits());
        schedule.addCourseToSchedule(course2);
        assertEquals(7, schedule.getScheduleCredits());
    }

    /**
     * Test for canAdd().
     */
    @Test
    public void testCanAdd() {
        Course course1 = new Course("CSC216", "Software Development", "001", 4, "instructor", 10, "MW", 1330, 1445);
        Course course2 = new Course("CSC226", "Discrete Math", "001", 3, "instructor", 10, "TH", 1330, 1445);
        Course course3 = new Course("CSC230", "C and Software Tools", "001", 3, "instructor", 10, "MW", 1330, 1445);

        assertTrue(schedule.canAdd(course1));
        schedule.addCourseToSchedule(course1);
        assertFalse(schedule.canAdd(course1)); // Duplicate course
        assertTrue(schedule.canAdd(course2));
        schedule.addCourseToSchedule(course2);
        assertFalse(schedule.canAdd(course3)); // Conflict course
    }
}
