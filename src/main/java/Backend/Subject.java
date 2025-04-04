/**
 * File: Subject.java
 * Description: Represents a university subject (e.g., Math, Chemistry) and stores metadata such as
 * its name, code, and associated course offerings. Supports lookup by name or code, and auto-generates codes if needed.
 *
 * Author: Huzaifa A. & Group
 * Date: March 2nd, 2025
 */

package Backend;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private static List<Subject> subjects = new ArrayList<>();

    private String subjectName;
    private String subjectCode;
    private List<Course> courses;

    // Constructor with provided subject name and code
    public Subject(String name, String code) {
        this.subjectName = name;
        this.subjectCode = code;
        this.courses = new ArrayList<>();
    }

    // Constructor with only name, auto-generates subject code
    public Subject(String name) {
        this.subjectName = name;
        this.subjectCode = generateUniqueSubjectCode();
        this.courses = new ArrayList<>();
    }

    // Generate a unique subject code
    private static String generateUniqueSubjectCode() {
        int count = subjects.size() + 1;
        return "SUBJ" + count; // e.g., SUBJ6
    }


    /**
     * Adds a new subject object directly (used for programmatic calls).
     */
    public static boolean addSubject(Subject subject) {
        if (isDuplicate(subject.getSubjectName(), subject.getSubjectCode())) {
            return false;
        }

        subjects.add(subject);
        System.out.println("✅ Subject added successfully: " + subject.getSubjectName() + " (" + subject.getSubjectCode() + ")");
        return true;
    }

    /**
     * Validates if a subject with the same name or code already exists.
     */
    private static boolean isDuplicate(String name, String code) {
        for (Subject subject : subjects) {
            if (subject.getSubjectName().equalsIgnoreCase(name)) {
                System.out.println("❌ Error: A subject with the name '" + name + "' already exists.");
                return true;
            }
            if (subject.getSubjectCode().equalsIgnoreCase(code)) {
                System.out.println("❌ Error: A subject with the code '" + code + "' already exists.");
                return true;
            }
        }
        return false;
    }

    // === Lookup Methods ===

    public static Subject findSubject(String subjectName) {
        for (Subject subject : subjects) {
            if (subject.subjectName.equalsIgnoreCase(subjectName)) {
                return subject;
            }
        }
        return null;
    }

    public static Subject findSubjectByCode(String subjectCode) {
        for (Subject subject : subjects) {
            if (subject.subjectCode.equalsIgnoreCase(subjectCode)) {
                return subject;
            }
        }
        return null;
    }

    // === Getters & Setters ===

    public static List<Subject> getSubjectList() {
        return subjects;
    }

    public static void setSubjectList(List<Subject> list) {
        subjects = list;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setSubjectName(String name) {
        this.subjectName = name;
    }

    public void setSubjectCode(String code) {
        this.subjectCode = code;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
}
