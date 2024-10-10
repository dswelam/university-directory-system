package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Activity class to check for conflicts between different activities.
 */
class ActivityTest {

	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course enrollment cap */
	private static final int ENROLLMENT_CAP = 10;
	/** Course meeting days */
	private static final String MEETING_DAYS = "MW";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	
	/**
	 * Test method for @CheckConflict. Structure: assertDoesNotThrow(() ->
	 * activityReference.checkConflict(otherActivityReference));
	 */
	@Test
	public void testCheckConflict() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		Activity a5 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "A");
		Activity a6 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "A");

		assertDoesNotThrow(() -> a1.checkConflict(a2));
		assertDoesNotThrow(() -> a2.checkConflict(a1));
		assertDoesNotThrow(() -> a5.checkConflict(a6));
		assertDoesNotThrow(() -> a6.checkConflict(a5));
	}

	/**
	 * Tests method for @CheckConflict for invalid input that could result in a
	 * scheduling conflict between Courses.
	 * 
	 */
	@Test
	public void testCheckConflictWithCourseConflicts() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWHF", 1400, 1430);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWHF", 1330, 1445);
		Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWHF", 1445, 1530);
		Activity a4 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWHF", 1400, 1430);

		Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
		assertEquals("Schedule conflict.", e1.getMessage());

		Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
		assertEquals("Schedule conflict.", e2.getMessage());

		Exception e3 = assertThrows(ConflictException.class, () -> a2.checkConflict(a3));
		assertEquals("Schedule conflict.", e3.getMessage());

		Exception e4 = assertThrows(ConflictException.class, () -> a3.checkConflict(a2));
		assertEquals("Schedule conflict.", e4.getMessage());

		Exception e5 = assertThrows(ConflictException.class, () -> a1.checkConflict(a4));
		assertEquals("Schedule conflict.", e5.getMessage());

		Exception e6 = assertThrows(ConflictException.class, () -> a4.checkConflict(a1));
		assertEquals("Schedule conflict.", e6.getMessage());

	}
	
	@Test
	public void testOtherDaysOfTheWeek() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWHF", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "T", 1445, 1600);
		Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "W", 1445, 1600);
		Activity a4 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "H", 1445, 1600);
		Activity a5 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "F", 1445, 1600);
		
		Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
		assertEquals("Schedule conflict.", e1.getMessage());
		
		Exception e2 = assertThrows(ConflictException.class, () -> a1.checkConflict(a3));
		assertEquals("Schedule conflict.", e2.getMessage());
		
		Exception e3 = assertThrows(ConflictException.class, () -> a1.checkConflict(a4));
		assertEquals("Schedule conflict.", e3.getMessage());
		
		Exception e4 = assertThrows(ConflictException.class, () -> a1.checkConflict(a5));
		assertEquals("Schedule conflict.", e4.getMessage());
	}
	
	@Test
	public void testOtherDaysOfTheWeeks() {
	    // Activities with meeting days that do not overlap
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWHF", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "T", 1500, 1600);
	    Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "W", 1500, 1600);
	    Activity a4 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "H", 1500, 1600);
	    Activity a5 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "F", 1500, 1600);
	    
	    // Each activity has a different meeting day, so there should be no conflicts
	    assertDoesNotThrow(() -> a1.checkConflict(a2));
	    assertDoesNotThrow(() -> a2.checkConflict(a1));
	    assertDoesNotThrow(() -> a1.checkConflict(a3));
	    assertDoesNotThrow(() -> a3.checkConflict(a1));
	    assertDoesNotThrow(() -> a1.checkConflict(a4));
	    assertDoesNotThrow(() -> a4.checkConflict(a1));
	    assertDoesNotThrow(() -> a1.checkConflict(a5));
	    assertDoesNotThrow(() -> a5.checkConflict(a1));
	}
	
	@Test
	public void testHashCode() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWHF", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWHF", 1330, 1445);
		Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTW", 1200, 1445);
		
		assertEquals(a1.hashCode(), a2.hashCode());
		assertNotEquals(a1.hashCode(), a3.hashCode());
	}
	
	@Test
	public void testEqualsMethod() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWH", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWH", 1330, 1445);
		Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWH", 1331, 1445);
		Activity a5 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTW", 1330, 1445);
		Activity a6 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MTWH", 1330, 1446);
		Object obj = new Object();
		
		assertEquals(a1, a2);
		assertEquals(a2, a1);
		assertEquals(a1, a1);
		
		assertNotEquals(a1, a3);
		assertNotEquals(a1, a5);
		assertNotEquals(a1, a6);
		
		assertFalse(a1.equals(a3));
		assertFalse(a1.equals(obj));
		assertTrue(a1.equals(a1));
		assertTrue(a1.equals(a2));
		
	}

	/**
	 * Tests that toString returns the correct comma-separated value.
	 */
	@Test
	public void testToString() {
		Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		String s1 = "CSC216,Software Development Fundamentals,001,3,sesmith5,10,MW,1330,1445";
		assertEquals(s1, c1.toString());

		Course c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "A");
		String s2 = "CSC216,Software Development Fundamentals,001,3,sesmith5,10,A";
		assertEquals(s2, c2.toString());
	}

}
