/**
 * File: Course.java
 * Description: Represents a course offered at the university. Includes full metadata about the course
 * such as instructor, lecture time, location, enrolled students, and final exam date.
 * Also provides utility methods for course lookup, conflict checking, and static initialization of sample courses.
 *
 * Author: Huzaifa A. & Group
 * Date: March 2nd, 2025
 */

package Backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private static List<Course> courseList = new ArrayList<>();
    private static int courseIDCounter = 11; // Start from 11 since 1–10 are manually created

    private int courseID;
    private String courseName;
    private String subjectName;
    private int sectionNumber;
    private String teacherName;
    private int courseCapacity;
    private String location;
    private String lectureDay;
    private int lectureStartTime;
    private int lectureEndTime;
    private LocalDateTime finalExamDate;
    private List<Student> enrolledStudents;
    private int grade;

    // Full Constructor with course ID
    public Course(int courseID, String courseName, String subjectName, int sectionNumber,
                  String teacherName, int courseCapacity, String location, String lectureDay,
                  int lectureStartTime, int lectureEndTime, LocalDateTime finalExamDate) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.subjectName = subjectName;
        this.sectionNumber = sectionNumber;
        this.teacherName = teacherName;
        this.courseCapacity = courseCapacity;
        this.location = location;
        this.lectureDay = lectureDay;
        this.lectureStartTime = lectureStartTime;
        this.lectureEndTime = lectureEndTime;
        this.finalExamDate = finalExamDate;
        this.enrolledStudents = new ArrayList<>();
        this.grade = -1;
    }

    // Auto-incremented constructor for new courses
    public Course(String courseName, String subjectName, int sectionNumber,
                  String teacherName, int courseCapacity, String location, String lectureDay,
                  int lectureStartTime, int lectureEndTime, LocalDateTime finalExamDate) {
        this(courseIDCounter++, courseName, subjectName, sectionNumber,
                teacherName, courseCapacity, location, lectureDay,
                lectureStartTime, lectureEndTime, finalExamDate);
    }

    // Check if a new course conflicts with any existing one
    public static boolean checkConflict(Course newCourse) {
        for (Course existing : courseList) {
            if (existing.lectureDay.equalsIgnoreCase(newCourse.lectureDay)) {
                if ((newCourse.lectureStartTime >= existing.lectureStartTime && newCourse.lectureStartTime < existing.lectureEndTime) ||
                        (newCourse.lectureEndTime > existing.lectureStartTime && newCourse.lectureEndTime <= existing.lectureEndTime) ||
                        (newCourse.lectureStartTime <= existing.lectureStartTime && newCourse.lectureEndTime >= existing.lectureEndTime)) {
                    return true;// conflict found
                }
            }
        }
        return false; // no conflict
    }

    //Method to enroll a student in the course
    public boolean enrollStudent(Student student) {
        // Check if student is already enrolled or if there is a time conflict
        for (Course course : student.getEnrolledCourses()) {
            if (checkConflict(course)) {
                System.out.println("Cannot enroll in " + this.courseName + " due to a time conflict with " + course.getCourseName());
                return false;  // Enrollment failed due to time conflict
            }
        }

        // If no conflict, add student to this course and course to student's enrolled courses
        if (enrolledStudents.size() < courseCapacity) {
            enrolledStudents.add(student);
            student.getEnrolledCourses().add(this);
            System.out.println("Successfully enrolled in " + this.courseName);
            return true;  // Enrollment successful
        } else {
            System.out.println("Course is full. Cannot enroll.");
            return false;  // Enrollment failed due to full capacity
        }
    }

    // Add course if not duplicate
    public static boolean Course(Course newCourse) {
        if (isDuplicate(newCourse.getCourseName(), newCourse.getSectionNumber())) {
            return false;
        }
        courseList.add(newCourse);
        System.out.println("Course added successfully.");
        return true;
    }

    /**
     * Validates if course with same name and section exists..
     */
    private static boolean isDuplicate(String name, int section) {
        for (Course course : courseList) {
            if (course.getCourseName().equalsIgnoreCase(name) &&
                    course.getSectionNumber() == section) {
                System.out.println("Course already exists with same name and section.");
                return true;
            }
        }
        return false;
    }

    // Find course by name
    public static Course findCourse(String courseName) {
        for (Course course : courseList) {
            if (course.getCourseName().equalsIgnoreCase(courseName.trim())) {
                return course;
            }
        }
        return null;
    }

    // Find course by ID
    public static Course findCourseByID(int courseID) {
        for (Course course : courseList) {
            if (course.getCourseID() == courseID) {
                return course;
            }
        }
        return null;
    }

    // Print course details (console)
    public void displayCourse() {
        System.out.println("Course ID: " + courseID);
        System.out.println("Course Name: " + courseName);
        System.out.println("Subject Name: " + subjectName);
        System.out.println("Section Number: " + sectionNumber);
        System.out.println("Teacher Name: " + teacherName);
        System.out.println("Course Capacity: " + courseCapacity);
        System.out.println("Location: " + location);
        System.out.println("Lecture Time: " + lectureDay + " " + lectureStartTime + " - " + lectureEndTime);
        System.out.println("Final Exam: " + (finalExamDate != null ? finalExamDate : "Not scheduled"));
        System.out.println("Enrolled: " + enrolledStudents.size());
    }

    // Course list operations
    public static List<Course> getCourseList() {
        return courseList;
    }

    public static void addCourse(Course course) {
        courseList.add(course);
        ReadExcelFile.writeToExcel();
        System.out.println("Course added: " + course.getCourseName());
    }

    public static void removeCourse(int courseID) {
        courseList.removeIf(course -> course.getCourseID() == courseID);
        System.out.println("Course removed: " + courseID);
    }

    // Getters
    public int getCourseID() { return courseID; }
    public String getCourseName() { return courseName; }
    public String getSubjectName() { return subjectName; }
    public int getSectionNumber() { return sectionNumber; }
    public String getTeacherName() { return teacherName; }
    public int getCourseCapacity() { return courseCapacity; }
    public String getLocation() { return location; }
    public String getLectureDay() { return lectureDay; }
    public int getLectureStartTime() { return lectureStartTime; }
    public int getLectureEndTime() { return lectureEndTime; }
    public LocalDateTime getFinalExamDate() { return finalExamDate; }
    public String getFinalExamDateAsString() {
        return finalExamDate != null ? finalExamDate.toString() : "";
    }
    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public int getGrade() { return grade; }

    public static void setCourseList(List<Course> courses) {
        courseList = courses;
    }

    // Setters
    public void changeCourseName(String courseName) { this.courseName = courseName; }
    public void changeTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void changeCourseCapacity(int courseCapacity) { this.courseCapacity = courseCapacity; }
    public void changeLocation(String location) { this.location = location; }
    public void setGrade(int grade) { this.grade = grade; }

}

    public void setFinalExamDate(LocalDateTime finalExamDate) { this.finalExamDate = finalExamDate; }
}
