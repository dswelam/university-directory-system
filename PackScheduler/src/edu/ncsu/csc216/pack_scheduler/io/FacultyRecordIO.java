package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Reads Faculty records from text files and writes a set of Faculty records to a file.
 * 
 * @author Dania Swelam
 */
public class FacultyRecordIO {

    /**
     * Reads Faculty records from a file and generates a list of valid Faculty. Any
     * invalid Faculty are ignored. If the file to read cannot be found or the
     * permissions are incorrect a FileNotFoundException is thrown.
     * 
     * @param fileName the file to read Faculty records from
     * @return a list of valid Faculty
     * @throws FileNotFoundException if the file cannot be found or read
     */
    public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileInputStream(fileName));
        LinkedList<Faculty> faculties = new LinkedList<Faculty>();
        while (fileReader.hasNextLine()) {
            try {
                // Read a single line and construct a Faculty object from it
                Faculty faculty = processFaculty(fileReader.nextLine());

                // Check for duplicates
                boolean duplicate = false;
                for (int i = 0; i < faculties.size(); i++) {
                    Faculty current = faculties.get(i);
                    if (faculty.getFirstName().equals(current.getFirstName())
                            && faculty.getLastName().equals(current.getLastName())) {
                        duplicate = true;
                        break;
                    }
                }

                // If not a duplicate, add the Faculty to the list
                if (!duplicate) {
                    faculties.add(faculty);
                }
            } catch (IllegalArgumentException e) {
                // Ignore invalid records
            }
        }
        fileReader.close();
        return faculties;
    }

    /**
     * Processes Faculty information from a string and constructs a Faculty object.
     *
     * @param nextLine the string containing Faculty information
     * @return a Faculty object constructed from the provided information
     * @throws InputMismatchException if the input does not match the expected format
     * @throws FileNotFoundException  if the input string cannot be processed
     */
    private static Faculty processFaculty(String nextLine) throws InputMismatchException, FileNotFoundException {
        try (Scanner fileReader = new Scanner(nextLine)) {
            fileReader.useDelimiter(",");

            String firstName = fileReader.next();
            String lastName = fileReader.next();
            String id = fileReader.next();
            String email = fileReader.next();
            String password = fileReader.next();
            int maxCourses = fileReader.nextInt();

            return new Faculty(firstName, lastName, id, email, password, maxCourses);

        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Invalid values in the input string.", e);
        }
    }

    /**
     * Writes the given list of Faculty to a file.
     * 
     * @param fileName the file to write the list of Faculty to
     * @param faculties the list of Faculty members to write
     * @throws IOException if the file cannot be written to
     */
    public static void writeFacultyRecords(String fileName, LinkedList<Faculty> faculties) throws IOException {
        PrintStream fileWriter = new PrintStream(new File(fileName));

        for (int i = 0; i < faculties.size(); i++) {
            fileWriter.println(faculties.get(i).toString());
        }

        fileWriter.close();
    }
    
}
