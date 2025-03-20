/**
 *  File: Subject.java
 *  Description: This class is for storing information regarding a subjeect for course offering
 *  such as subject names, and subject codes, this is helpful when we'd like to register a
 *  series of courses under a Subject.
 *  when implementing UI components
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 *  */

package Backend;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private static List<Subject> subjects = new ArrayList<>();

    private String subjectName;
    private String subjectCode;
    private List<Course> courses;


    static {
        subjects.add(new Subject("MATH001", "Mathematics"));
        subjects.add(new Subject("ENG101", "English"));
        subjects.add(new Subject("CS201", "Computer Science"));
        subjects.add(new Subject("CHEM200", "Chemistry"));
        subjects.add(new Subject("BIO300", "Biology"));
    }

    public Subject(String name, String code) {
        this.subjectName = name;
        this.subjectCode = code;
        this.courses = new ArrayList<>();
        subjects.add(this);
    }

    public static Subject findSubject(String subjectName) {
        for (Subject subject : subjects) {
            if (subject.subjectName.equalsIgnoreCase(subjectName)) {
                return subject;
            }
        }
        return null;
    }

    public static boolean checkDuplicateSubject(String subject, ArrayList<Subject> subjects) {
        for (Subject sub : subjects) {
            if (sub.getSubjectName().equalsIgnoreCase(subject)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDuplicateCourse(Course course) {
        for (Course c : this.courses) {
            if (c.getCourseName().equalsIgnoreCase(course.getCourseName())) {
                return true;
            }
        }
        return false;
    }

    public void addCourse(Course course) {
        if (checkDuplicateCourse(course)) {
            System.out.println("Course '" + course.getCourseName() + "' already exists under " + subjectName);
            return;
        }
        courses.add(course);
        System.out.println("Course '" + course.getCourseName() + "' added under " + this.subjectName);
    }

    public static Subject retrieveSubject(String subjectCode) {
        for (Subject sub : subjects) {
            if (sub.subjectCode.equals(subjectCode)) {
                return sub;
            }
        }
        return null; // Return null if not found
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectName(String name) {
        this.subjectName = name;
    }

    public void setSubjectCode(String code) {
        this.subjectCode = code;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void display() {
        System.out.println("Subject Name: " + this.subjectName);
        System.out.println("Subject Code: " + this.subjectCode);
    }
}