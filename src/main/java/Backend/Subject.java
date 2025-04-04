/**
 * File: Subject.java
 * Description: Represents a university subject (e.g., Math, Chemistry) and stores metadata such as
 * its name, code, and associated course offerings. Supports lookup by name or code, and auto-generates codes if needed.
 * Author: Huzaifa A. & Group
 * Date: April 2025
 */

package Backend;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    // Static list to hold all subjects in the system
    private static List<Subject> subjects = new ArrayList<>();

    private String subjectName; // Name of the subject (e.g., Mathematics)
    private String subjectCode; // Unique code for the subject (e.g., MATH101)
    private List<Course> courses; // List of courses associated with the subject

    /**
     * Constructor to create a new subject with a specified name and code.

     */
    public Subject(String name, String code) {
        this.subjectName = name;
        this.subjectCode = code;
        this.courses = new ArrayList<>(); // Initialize an empty list for courses
    }

    /**
     * Constructor to create a new subject with only a name.
     */
    public Subject(String name) {
        this.subjectName = name;
        this.subjectCode = generateUniqueSubjectCode(); // Generate a unique code
        this.courses = new ArrayList<>(); // Initialize an empty list for courses
    }

    /**
     * Generates a unique subject code based on the total number of existing subjects.
     */
    private static String generateUniqueSubjectCode() {
        int count = subjects.size() + 1;  // Use the size of the existing list to generate a new ID
        return "SUBJ" + count; // e.g., SUBJ6
    }

    /**
     * Adds a new subject to the list of subjects, ensuring no duplicate subject names or codes.
     */
    public static boolean addSubject(Subject subject) {
        if (isDuplicate(subject.getSubjectName(), subject.getSubjectCode())) {
            return false; // Return false if the subject already exists
        }

        subjects.add(subject); // Add the new subject to the list
        System.out.println("Subject added successfully: " + subject.getSubjectName() + " (" + subject.getSubjectCode() + ")");
        return true;
    }

    /**
     * Checks if a subject with the same name or code already exists in the list.
     *
     * @param name The subject name to check
     * @param code The subject code to check
     * @return True if a duplicate is found, false otherwise
     */
    private static boolean isDuplicate(String name, String code) {
        for (Subject subject : subjects) {
            // Check for duplicate name or code
            if (subject.getSubjectName().equalsIgnoreCase(name)) {
                System.out.println("Error: A subject with the name '" + name + "' already exists.");
                return true;
            }
            if (subject.getSubjectCode().equalsIgnoreCase(code)) {
                System.out.println("Error: A subject with the code '" + code + "' already exists.");
                return true;
            }
        }
        return false;
    }

    // === Lookup Methods ===

    /**
     * Finds a subject by its name.
     */
    public static Subject findSubject(String subjectName) {
        for (Subject subject : subjects) {
            if (subject.subjectName.equalsIgnoreCase(subjectName)) {
                return subject; // Return the subject if found
            }
        }
        return null; // Return null if not found
    }

    /**
     * Finds a subject by its unique code.
     */
    public static Subject findSubjectByCode(String subjectCode) {
        for (Subject subject : subjects) {
            if (subject.subjectCode.equalsIgnoreCase(subjectCode)) {
                return subject; // Return the subject if found
            }
        }
        return null; // Return null if not found
    }

    // === Getters & Setters ===

    /**
     * Returns the list of all subjects.
     */
    public static List<Subject> getSubjectList() {
        return subjects;
    }

    /**
     * Sets the list of subjects (used for reloading predefined data).
     */
    public static void setSubjectList(List<Subject> list) {
        subjects = list;
    }

    /**
     * Gets the name of the subject.
     */
    public String getSubjectName() {
        return this.subjectName;
    }

    /**
     * Gets the code of the subject
     */
    public String getSubjectCode() {
        return this.subjectCode;
    }

    /**
     * Gets the list of courses associated with the subject.
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the name of the subject.
     */
    public void setSubjectName(String name) {
        this.subjectName = name;
    }

    /**
     * Sets the code of the subject.
     */
    public void setSubjectCode(String code) {
        this.subjectCode = code;
    }

}
