package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for InvalidTransitionException().
 * 
 * @author Dania Swelam
 */
class InvalidTransitionTest {

	/**
	 * Test for InvalidTransitionException() string to make sure appropriate message
	 * is present.
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException e = new InvalidTransitionException("Custom exception message");
		assertEquals("Custom exception message", e.getMessage());
	}

	/**
	 * Test for InvalidTransitionException() with default message.
	 */
	@Test
	void testInvalidTransitionException() {
		InvalidTransitionException e = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", e.getMessage());
	}
}
