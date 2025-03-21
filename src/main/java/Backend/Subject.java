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

    // Static block to preload some subjects
    static {
        subjects.add(new Subject("Mathematics", "MATH001"));
        subjects.add(new Subject("English", "ENG101"));
        subjects.add(new Subject("Computer Science", "CS201"));
        subjects.add(new Subject("Chemistry", "CHEM200"));
        subjects.add(new Subject("Biology", "BIO300"));
    }

    // Constructor with provided subject name and code
    public Subject(String name, String code) {
        this.subjectName = name;
        this.subjectCode = code;
        this.courses = new ArrayList<>();
        subjects.add(this);
    }

    // Constructor with only name, auto-generates subject code
    public Subject(String name) {
        this.subjectName = name;
        this.subjectCode = generateUniqueSubjectCode();
        this.courses = new ArrayList<>();
        subjects.add(this);
    }

    // Generate a unique subject code
    private static String generateUniqueSubjectCode() {
        int count = subjects.size() + 1;
        return "SUBJ" + count; // e.g., SUBJ6
    }

    // Find subject by name
    public static Subject findSubject(String subjectName) {
        for (Subject subject : subjects) {
            if (subject.subjectName.equalsIgnoreCase(subjectName)) {
                return subject;
            }
        }
        return null;
    }

    // Find subject by code
    public static Subject findSubjectByCode(String subjectCode) {
        for (Subject subject : subjects) {
            if (subject.subjectCode.equalsIgnoreCase(subjectCode)) {
                return subject;
            }
        }
        return null;
    }

    // Return all registered subjects
    public static List<Subject> getSubjectList() {
        return subjects;
    }

    // Getters
    public String getSubjectName() {
        return this.subjectName;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public List<Course> getCourses() {
        return courses;
    }

    // Setters
    public void setSubjectName(String name) {
        this.subjectName = name;
    }

    public void setSubjectCode(String code) {
        this.subjectCode = code;
    }

    // Add a course to this subject
    public void addCourse(Course course) {
        courses.add(course);
    }
}
