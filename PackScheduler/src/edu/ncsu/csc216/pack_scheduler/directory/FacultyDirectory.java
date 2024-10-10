package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Maintains a directory of all faculty at NC State. All faculty have
 * a unique id.
 * 
 * @author Dania Swelam
 */
public class FacultyDirectory {

    /** List of faculty in the directory */
    private LinkedList<Faculty> facultyDirectory;
    /** Hashing algorithm */
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Creates an empty faculty directory.
     */
    public FacultyDirectory() {
        newFacultyDirectory();
    }

    /**
     * Creates an empty faculty directory. All faculty in the previous list are
     * list unless saved by the user.
     */
    public void newFacultyDirectory() {
        facultyDirectory = new LinkedList<>();
    }

    /**
     * Constructs the faculty directory by reading faculty records from the file.
     * 
     * @param fileName the file to read faculty records from
     * @throws IllegalArgumentException if the file is not found
     */
    public void loadFacultyFromFile(String fileName) {
        try (Scanner fileScanner = new Scanner(new FileInputStream(fileName))) {
            while (fileScanner.hasNextLine()) {
                try {
                    Faculty faculty = processFaculty(fileScanner.nextLine());
                    facultyDirectory.add(faculty);
                } catch (IllegalArgumentException e) {
                    // Skip the invalid faculty
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to read file " + fileName);
        }
    }

    /**
     * Processes a single line of faculty data.
     * 
     * @param line the line of faculty data
     * @return the faculty object
     * @throws IllegalArgumentException if the line does not contain valid faculty data
     */
    private Faculty processFaculty(String line) {
    	  String[] tokens = line.split(",");
          if (tokens.length != 6) {
              throw new IllegalArgumentException("Invalid line: " + line);
          }

          String firstName = tokens[0];
          String lastName = tokens[1];
          String id = tokens[2];
          String email = tokens[3];
          String password = tokens[4];
          int maxCourses;

          try {
              maxCourses = Integer.parseInt(tokens[5]);
          } catch (NumberFormatException e) {
              throw new IllegalArgumentException("Invalid maxCourses: " + tokens[5]);
          }

          return new Faculty(firstName, lastName, id, email, password, maxCourses);
      }

    /**
     * Adds a Faculty to the directory. Returns true if the faculty is added and
     * false if the faculty is unable to be added because their id matches another
     * faculty's id.
     * 
     * This method also hashes the faculty's password for internal storage.
     * 
     * @param firstName      faculty's first name
     * @param lastName       faculty's last name
     * @param id             faculty's id
     * @param email          faculty's email
     * @param password       faculty's password
     * @param repeatPassword faculty's repeated password
     * @param maxCourses     faculty's max courses.
     * @return true if added
     * @throws IllegalArgumentException if the password is invalid or does not match
     *                                  the repeated password
     */
    public boolean addFaculty(String firstName, String lastName, String id, String email,
                              String password, String repeatPassword, int maxCourses) {
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

        Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);

        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            if (f.getId().equals(faculty.getId())) {
                return false;
            }
        }
        
        return facultyDirectory.add(faculty);
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
     * Removes the faculty with the given id from the list of faculty with the
     * given id. Returns true if the faculty is removed and false if the faculty is
     * not in the list.
     * 
     * @param facultyId faculty's id
     * @return true if removed
     */
    public boolean removeFaculty(String facultyId) {
        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            if (f.getId().equals(facultyId)) {
                facultyDirectory.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all faculty in the directory with a column for first name, last
     * name, and id.
     * 
     * @return String array containing faculty first name, last name, and id.
     */
    public String[][] getFacultyDirectory() {
        String[][] directory = new String[facultyDirectory.size()][3];
        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            directory[i][0] = f.getFirstName();
            directory[i][1] = f.getLastName();
            directory[i][2] = f.getId();
        }
        return directory;
    }

    /**
     * Saves all faculty in the directory to a file.
     * 
     * @param fileName name of file to save faculty to.
     * @throws IllegalArgumentException if unable to write to the file
     */
    public void saveFacultyDirectory(String fileName) {
        try {
            FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to write to file " + fileName, e);
        }
    }

    /**
     * Gets faculty information based on the faculty's id.
     * 
     * @param id the faculty's unique id
     * @return the faculty's information for registration
     */
    public Faculty getFacultyById(String id) {
        for (int i = 0; i < facultyDirectory.size(); i++) {
            Faculty f = facultyDirectory.get(i);
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }
}
