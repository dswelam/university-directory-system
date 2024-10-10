package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseNameValidatorTest {

	/** Instance of CourseNameValidator to be used in tests */
	private CourseNameValidator validator;

	/**
	 * Sets up the test by initializing the CourseNameValidator instance.
	 */
	@BeforeEach
	public void setUp() {
		validator = new CourseNameValidator();
	}

	@Test
	public void testInitialWithLetter() throws InvalidTransitionException {
		assertTrue(validator.isValid("C123"));
		assertTrue(validator.isValid("CS123"));
		assertTrue(validator.isValid("CSC123"));
		assertTrue(validator.isValid("CSCA123"));
	}

	@Test
	public void testInitialWithDigit() {
		try {
			validator.isValid("123");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must start with a letter.", e.getMessage());
		}
	}

	@Test
	public void testInitialWithInvalidCharacter() {
		try {
			validator.isValid("C S123");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateLWithLetter() throws InvalidTransitionException {
		assertTrue(validator.isValid("CS123"));
	}

	@Test
	public void testStateLWithDigit() throws InvalidTransitionException {
		assertTrue(validator.isValid("C123"));
	}

	@Test
	public void testStateLWithInvalidCharacter() {
		try {
			validator.isValid("C 123");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateLLWithLetter() throws InvalidTransitionException {
		assertTrue(validator.isValid("CSC123"));
	}

	@Test
	public void testStateLLWithDigit() throws InvalidTransitionException {
		assertTrue(validator.isValid("CS123"));
	}

	@Test
	public void testStateLLWithInvalidCharacter() {
		try {
			validator.isValid("CS 123");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateLLLWithLetter() throws InvalidTransitionException {
		assertTrue(validator.isValid("CSCA123"));
	}

	@Test
	public void testStateLLLWithDigit() throws InvalidTransitionException {
		assertTrue(validator.isValid("CSC123"));
	}

	@Test
	public void testStateLLLWithInvalidCharacter() {
		try {
			validator.isValid("CSC 123");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateLLLLWithDigit() throws InvalidTransitionException {
		assertTrue(validator.isValid("CSCA123"));
	}

	@Test
	public void testStateLLLLWithLetter() {
		try {
			validator.isValid("CSCAA123");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
		}
	}

	@Test
	public void testStateLLLLWithInvalidCharacter() {
		try {
			validator.isValid("CSCA 123");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateDWithDigit() throws InvalidTransitionException {
		assertTrue(validator.isValid("CS123"));
	}

	@Test
	public void testStateDWithLetter() {
		try {
			validator.isValid("CSCA1A3");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
	}

	@Test
	public void testStateDWithInvalidCharacter() {
		try {
			validator.isValid("CSCA1 23");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateDDWithDigit() throws InvalidTransitionException {
		assertTrue(validator.isValid("CS123"));
	}

	@Test
	public void testStateDDWithLetter() {
		try {
			validator.isValid("CSCA12AA3");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
	}

	@Test
	public void testStateDDWithInvalidCharacter() {
		try {
			validator.isValid("CSCA12 3");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateDDDWithLetter() throws InvalidTransitionException {
		assertTrue(validator.isValid("CSCA123A"));
	}

	@Test
	public void testStateDDDWithDigit() {
		try {
			validator.isValid("CSCA1234");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}
	}

	@Test
	public void testStateDDDWithInvalidCharacter() {
		try {
			validator.isValid("CSCA123 ");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateSuffixWithLetter() {
		try {
			validator.isValid("CSCA123AA");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}
	}

	@Test
	public void testStateSuffixWithDigit() {
		try {
			validator.isValid("CSCA123A4");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}
	}

	@Test
	public void testStateSuffixWithInvalidCharacter() {
		try {
			validator.isValid("CSCA123A ");
			fail("InvalidTransitionException expected");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}
}