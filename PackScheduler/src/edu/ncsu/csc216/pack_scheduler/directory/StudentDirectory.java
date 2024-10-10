package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.io.StudentRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Maintains a directory of all students enrolled at NC State. All students have
 * a unique id.
 * 
 * @author Sarah Heckman
 */
public class StudentDirectory {

	/** List of students in the directory */
	private SortedList<Student> studentDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Creates an empty student directory.
	 */
	public StudentDirectory() {
		newStudentDirectory();
	}

	/**
	 * Creates an empty student directory. All students in the previous list are
	 * list unless saved by the user.
	 */
	public void newStudentDirectory() {
		studentDirectory = new SortedList<>();
	}

	 /**
     * Constructs the student directory by reading student records from the file.
     * 
     * @param fileName the file to read student records from
     * @throws IllegalArgumentException if the file is not found
     */
    public void loadStudentsFromFile(String fileName) {
        try (Scanner fileScanner = new Scanner(new FileInputStream(fileName))) {
            while (fileScanner.hasNextLine()) {
                try {
                    Student student = processStudent(fileScanner.nextLine());
                    studentDirectory.add(student);
                } catch (IllegalArgumentException e) {
                    // Skip the invalid student
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to read file " + fileName);
        }
    }

    /**
     * Processes a single line of student data.
     * 
     * @param line the line of student data
     * @return the student object
     * @throws IllegalArgumentException if the line does not contain valid student data
     */
    private Student processStudent(String line) {
    	 String[] tokens = line.split(",");
         if (tokens.length != 6) {
             throw new IllegalArgumentException("Invalid line: " + line);
         }

         String firstName = tokens[0];
         String lastName = tokens[1];
         String id = tokens[2];
         String email = tokens[3];
         String password = tokens[4];
         int maxCredits;

         try {
             maxCredits = Integer.parseInt(tokens[5]);
         } catch (NumberFormatException e) {
             throw new IllegalArgumentException("Invalid maxCredits: " + tokens[5]);
         }

         return new Student(firstName, lastName, id, email, password, maxCredits);
    }

	/**
	 * Adds a Student to the directory. Returns true if the student is added and
	 * false if the student is unable to be added because their id matches another
	 * student's id.
	 * 
	 * This method also hashes the student's password for internal storage.
	 * 
	 * @param firstName      student's first name
	 * @param lastName       student's last name
	 * @param id             student's id
	 * @param email          student's email
	 * @param password       student's password
	 * @param repeatPassword student's repeated password
	 * @param maxCredits     student's max credits.
	 * @return true if added
	 * @throws IllegalArgumentException if the password is invalid or does not match
	 *                                  the repeated password
	 */
	public boolean addStudent(String firstName, String lastName, String id, String email, String password,
			String repeatPassword, int maxCredits) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}

		hashPW = hashString(password);
		repeatHashPW = hashString(repeatPassword);

		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		Student student;
		if (maxCredits < 3 || maxCredits > Student.MAX_CREDITS) {
			student = new Student(firstName, lastName, id, email, hashPW);
		} else {
			student = new Student(firstName, lastName, id, email, hashPW, maxCredits);
		}

		for (int i = 0; i < studentDirectory.size(); i++) {
			Student s = studentDirectory.get(i);
			if (s.getId().equals(student.getId())) {
				return false;
			}
		}
		
		return studentDirectory.add(student);
	}

	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in
	 * base64 encoding. This allows the encoded digest to be safely copied, as it
	 * only uses [a-zA-Z0-9+/=].
	 * 
	 * @param password the String to hash
	 * @return the encoded digest of the hash algorithm in base64
	 * @throws IllegalArgumentException if the hashing algorithm is not available
	 */
	private static String hashString(String password) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(password.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password", e);
		}
	}

	/**
	 * Removes the student with the given id from the list of students with the
	 * given id. Returns true if the student is removed and false if the student is
	 * not in the list.
	 * 
	 * @param studentId student's id
	 * @return true if removed
	 */
	public boolean removeStudent(String studentId) {
		for (int i = 0; i < studentDirectory.size(); i++) {
			Student s = studentDirectory.get(i);
			if (s.getId().equals(studentId)) {
				studentDirectory.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns all students in the directory with a column for first name, last
	 * name, and id.
	 * 
	 * @return String array containing students first name, last name, and id.
	 */
	public String[][] getStudentDirectory() {
		String[][] directory = new String[studentDirectory.size()][3];
		for (int i = 0; i < studentDirectory.size(); i++) {
			Student s = studentDirectory.get(i);
			directory[i][0] = s.getFirstName();
			directory[i][1] = s.getLastName();
			directory[i][2] = s.getId();
		}
		return directory;
	}

	/**
	 * Saves all students in the directory to a file.
	 * 
	 * @param fileName name of file to save students to.
	 * @throws IllegalArgumentException if unable to write to the file
	 */
	public void saveStudentDirectory(String fileName) {
		try {
			StudentRecordIO.writeStudentRecords(fileName, studentDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName, e);
		}
	}

	/**
	 * Gets student information based on the student's id.
	 * 
	 * @param id the student's unique id
	 * @return the student's information for registration
	 */
	public Student getStudentById(String id) {
	    for (int i = 0; i < studentDirectory.size(); i++) {
	        Student s = studentDirectory.get(i);
	        if (s.getId().equals(id)) {
	            return s;
	        }
	    }
	    return null;
	}
}