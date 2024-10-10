package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for CourseNameValidatorFSM.
 * 
 * @author Dania Swelam
 */
class CourseNameValidatorFSMTest {

	/** Instance of CourseNameValidatorFSM to be used in tests */
    private CourseNameValidatorFSM validator;

    /**
     * Sets up the test by initializing the CourseNameValidatorFSM instance.
     */
    @BeforeEach
    public void setUp() {
        validator = new CourseNameValidatorFSM();
    }

    /**
     * Tests valid transitions from the initial state with letters.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testInitialWithLetter() throws InvalidTransitionException {
        assertTrue(validator.isValid("C123"));
        assertTrue(validator.isValid("CS123"));
        assertTrue(validator.isValid("CSC123"));
        assertTrue(validator.isValid("CSCA123"));
    }

    /**
     * Tests invalid transitions from the initial state with digits.
     */
    @Test
    public void testInitialWithDigit() {
        try {
            validator.isValid("123");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name must start with a letter.", e.getMessage());
        }
    }

    /**
     * Tests invalid transitions from the initial state with invalid characters.
     */
    @Test
    public void testInitialWithInvalidCharacter() {
        try {
            validator.isValid("C S123");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only contain letters and digits.", e.getMessage());
        }
    }

    /**
     * Tests valid transitions from state L with letters.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testStateLWithLetter() throws InvalidTransitionException {
        assertTrue(validator.isValid("CS123"));
    }

    /**
     * Tests valid transitions from state L with digits.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testStateLWithDigit() throws InvalidTransitionException {
        assertTrue(validator.isValid("C123"));
    }

    /**
     * Tests invalid transitions from state L with invalid characters.
     */
    @Test
    public void testStateLWithInvalidCharacter() {
        try {
            validator.isValid("C 123");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only contain letters and digits.", e.getMessage());
        }
    }

    /**
     * Tests valid transitions from state LL with letters.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testStateLLWithLetter() throws InvalidTransitionException {
        assertTrue(validator.isValid("CSC123"));
    }

    /**
     * Tests valid transitions from state LL with digits.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testStateLLWithDigit() throws InvalidTransitionException {
        assertTrue(validator.isValid("CS123"));
    }

    /**
     * Tests invalid transitions from state LL with invalid characters.
     */
    @Test
    public void testStateLLWithInvalidCharacter() {
        try {
            validator.isValid("CS 123");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only contain letters and digits.", e.getMessage());
        }
    }

    /**
     * Tests valid transitions from state LLL with letters.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testStateLLLWithLetter() throws InvalidTransitionException {
        assertTrue(validator.isValid("CSCA123"));
    }

    /**
     * Tests valid transitions from state LLL with digits.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testStateLLLWithDigit() throws InvalidTransitionException {
        assertTrue(validator.isValid("CSC123"));
    }

    /**
     * Tests invalid transitions from state LLL with invalid characters.
     */
    @Test
    public void testStateLLLWithInvalidCharacter() {
        try {
            validator.isValid("CSC 123");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only contain letters and digits.", e.getMessage());
        }
    }

    /**
     * Tests valid transitions from state LLLL with digits.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testStateLLLLWithDigit() throws InvalidTransitionException {
        assertTrue(validator.isValid("CSCA123"));
    }

    /**
     * Tests invalid transitions from state LLLL with letters.
     */
    @Test
    public void testStateLLLLWithLetter() {
        try {
            validator.isValid("CSCAA123");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
        }
    }

    /**
     * Tests invalid transitions from state LLLL with invalid characters.
     */
    @Test
    public void testStateLLLLWithInvalidCharacter() {
        try {
            validator.isValid("CSCA 123");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only contain letters and digits.", e.getMessage());
        }
    }

    /**
     * Tests valid transitions from state D with digits.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testStateDWithDigit() throws InvalidTransitionException {
        assertTrue(validator.isValid("CS123"));
    }

    /**
     * Tests invalid transitions from state D with letters.
     */
    @Test
    public void testStateDWithLetter() {
        try {
            validator.isValid("CSCA1A3");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name must have 3 digits.", e.getMessage());
        }
    }

    /**
     * Tests invalid transitions from state D with invalid characters.
     */
    @Test
    public void testStateDWithInvalidCharacter() {
        try {
            validator.isValid("CSCA1 23");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only contain letters and digits.", e.getMessage());
        }
    }

    /**
     * Tests valid transitions from state DD with digits.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testStateDDWithDigit() throws InvalidTransitionException {
        assertTrue(validator.isValid("CS123"));
    }

    /**
     * Tests invalid transitions from state DD with letters.
     */
    @Test
    public void testStateDDWithLetter() {
        try {
            validator.isValid("CSCA12AA3");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name must have 3 digits.", e.getMessage());
        }
    }

    /**
     * Tests invalid transitions from state DD with invalid characters.
     */
    @Test
    public void testStateDDWithInvalidCharacter() {
        try {
            validator.isValid("CSCA12 3");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only contain letters and digits.", e.getMessage());
        }
    }

    /**
     * Tests valid transitions from state DDD with letters.
     * 
     * @throws InvalidTransitionException if a valid transition throws an exception
     */
    @Test
    public void testStateDDDWithLetter() throws InvalidTransitionException {
        assertTrue(validator.isValid("CSCA123A"));
    }

    /**
     * Tests invalid transitions from state DDD with digits.
     */
    @Test
    public void testStateDDDWithDigit() {
        try {
            validator.isValid("CSCA1234");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only have 3 digits.", e.getMessage());
        }
    }

    /**
     * Tests invalid transitions from state DDD with invalid characters.
     */
    @Test
    public void testStateDDDWithInvalidCharacter() {
        try {
            validator.isValid("CSCA123 ");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only contain letters and digits.", e.getMessage());
        }
    }

    /**
     * Tests invalid transitions from state Suffix with letters.
     */
    @Test
    public void testStateSuffixWithLetter() {
        try {
            validator.isValid("CSCA123AA");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
        }
    }

    /**
     * Tests invalid transitions from state Suffix with digits.
     */
    @Test
    public void testStateSuffixWithDigit() {
        try {
            validator.isValid("CSCA123A4");
            fail("InvalidTransitionException expected");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
        }
    }

    /**
     * Tests invalid transitions from state Suffix with invalid characters.
     */
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