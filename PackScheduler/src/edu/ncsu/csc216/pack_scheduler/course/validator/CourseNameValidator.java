package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Validates course names using a state pattern. The course name must be in the
 * format of 1-4 letters, exactly 3 digits, and an optional letter suffix.
 * 
 * @author Dania Swelam
 */
public class CourseNameValidator {

    /** The current state of the FSM */
    private State currentState;
    /** State for when letters are read */
    private State letterState = new LetterState();
    /** Initial state */
    private State initialState = new InitialState();
    /** State for when digits are read */
    private State numberState = new NumberState();
    /** State for when a suffix letter is read */
    private State suffixState = new SuffixState();
    /** Indicates if the current state is a valid end state */
    private boolean validEndState;
    /** Count of letters read */
    private int letterCount;
    /** Count of digits read */
    private int digitCount;

    /**
     * Validates if the given course name is valid.
     * 
     * @param courseName the course name to validate
     * @return true if valid, false otherwise
     * @throws InvalidTransitionException if an invalid transition occurs
     */
    public boolean isValid(String courseName) throws InvalidTransitionException {
        currentState = initialState;
        validEndState = false;
        letterCount = 0;
        digitCount = 0;
        
        for (int i = 0; i < courseName.length(); i++) {
            char c = courseName.charAt(i);
            if (Character.isLetter(c)) {
                currentState.onLetter(c);
            } else if (Character.isDigit(c)) {
                currentState.onDigit(c);
            } else {
                currentState.onOther();
            }
        }
        
        return validEndState;
    }

    /**
     * Abstract state class for FSM.
     */
    private abstract class State {
        /**
         * Handles letter input.
         * 
         * @param c the letter character
         * @throws InvalidTransitionException if an invalid transition occurs
         */
        abstract void onLetter(char c) throws InvalidTransitionException;

        /**
         * Handles digit input.
         * 
         * @param c the digit character
         * @throws InvalidTransitionException if an invalid transition occurs
         */
        abstract void onDigit(char c) throws InvalidTransitionException;

        /**
         * Handles non-letter and non-digit input.
         * 
         * @throws InvalidTransitionException if an invalid transition occurs
         */
        void onOther() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only contain letters and digits.");
        }
    }

    /**
     * Initial state of the FSM.
     */
    private class InitialState extends State {
        @Override
        void onLetter(char c) {
            letterCount++;
            currentState = letterState;
        }

        @Override
        void onDigit(char c) throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name must start with a letter.");
        }
    }

    /**
     * State for when one or more letters are read.
     */
    private class LetterState extends State {
        
        /** The maximum count of letters in prefix. */
        private static final int MAX_PREFIX_LETTERS = 4;

        @Override
        void onLetter(char c) throws InvalidTransitionException {
            letterCount++;
            if (letterCount > MAX_PREFIX_LETTERS) {
                throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
            }
        }

        @Override
        void onDigit(char c) {
            digitCount++;
            currentState = numberState;
        }
    }

    /**
     * State for when one or more digits are read.
     */
    private class NumberState extends State {
        
        /** The exact count of digits needed in course name. */
        private static final int COURSE_NUMBER_LENGTH = 3;

        @Override
        void onLetter(char c) throws InvalidTransitionException {
            if (digitCount == COURSE_NUMBER_LENGTH) {
                validEndState = true;
                currentState = suffixState;
            } else {
                throw new InvalidTransitionException("Course name must have 3 digits.");
            }
        }

        @Override
        void onDigit(char c) throws InvalidTransitionException {
            digitCount++;
            if (digitCount > COURSE_NUMBER_LENGTH) {
                throw new InvalidTransitionException("Course name can only have 3 digits.");
            }
            if (digitCount == COURSE_NUMBER_LENGTH) {
                validEndState = true;
            }
        }
    }

    /**
     * State for when a suffix letter is read.
     */
    private class SuffixState extends State {
        @Override
        void onLetter(char c) throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
        }

        @Override
        void onDigit(char c) throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
        }
    }
    
}
