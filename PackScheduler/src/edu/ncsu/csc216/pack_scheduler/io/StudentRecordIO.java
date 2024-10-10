package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Student records from text files and writes a set of Student records to a
 * file.
 * 
 * @author Dania Swelam
 */
public class StudentRecordIO {

    /**
     * Reads student records from a file and generates a list of valid students. Any
     * invalid Students are ignored. If the file to read cannot be found or the
     * permissions are incorrect a FileNotFoundException is thrown.
     * 
     * @param fileName the file to read Student records from
     * @return a list of valid Students
     * @throws FileNotFoundException if the file cannot be found or read
     */
    public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileInputStream(fileName));
        SortedList<Student> students = new SortedList<Student>();
        while (fileReader.hasNextLine()) {
            try {
                // Read a single line and construct a Student object from it
                Student student = processStudent(fileReader.nextLine());

                // Check for duplicates
                boolean duplicate = false;
                for (int i = 0; i < students.size(); i++) {
                    Student current = students.get(i);
                    if (student.getFirstName().equals(current.getFirstName())
                            && student.getLastName().equals(current.getLastName())) {
                        duplicate = true;
                        break;
                    }
                }

                // If not a duplicate, add the student to the list
                if (!duplicate) {
                    students.add(student);
                }
            } catch (IllegalArgumentException e) {
                // Ignore invalid records
            }
        }
        fileReader.close();
        return students;
    }

    /**
     * Processes student information from a string and constructs a Student object.
     *
     * @param nextLine the string containing Student information
     * @return a Student object constructed from the provided information
     * @throws InputMismatchException if the input does not match the expected format
     * @throws FileNotFoundException  if the input string cannot be processed
     */
    private static Student processStudent(String nextLine) throws InputMismatchException, FileNotFoundException {
        try (Scanner fileReader = new Scanner(nextLine)) {
            fileReader.useDelimiter(",");

            String firstName = fileReader.next();
            String lastName = fileReader.next();
            String id = fileReader.next();
            String email = fileReader.next();
            String password = fileReader.next();
            int maxCredits = fileReader.nextInt();

            return new Student(firstName, lastName, id, email, password, maxCredits);

        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Invalid values in the input string.", e);
        }
    }

    /**
     * Writes the given list of Students to a file.
     * 
     * @param fileName the file to write the list of Students to
     * @param students the list of Students to write
     * @throws IOException if the file cannot be written to
     */
    public static void writeStudentRecords(String fileName, SortedList<Student> students) throws IOException {
        PrintStream fileWriter = new PrintStream(new File(fileName));

        for (int i = 0; i < students.size(); i++) {
            fileWriter.println(students.get(i).toString());
        }

        fileWriter.close();
    }
    
}
