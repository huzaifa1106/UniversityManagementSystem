/**
 * File: Course.java
 * Description: Represents a course offered at the university. Includes full metadata about the course
 * such as instructor, lecture time, location, enrolled students, and final exam date.
 * Also provides utility methods for course lookup, conflict checking, and static initialization of sample courses.
 * Author: Huzaifa A. & Group
 * Date: March 2nd, 2025
 */

package Backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private static List<Course> courseList = new ArrayList<>();
    private static int courseIDCounter = 11; // Starting from 11 (assuming IDs 1–10 are manually set)

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

    // Constructor with predefined ID (used during file loading or manual creation)
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

    // Constructor for new courses (auto-generates unique ID)
    public Course(String courseName, String subjectName, int sectionNumber,
                  String teacherName, int courseCapacity, String location, String lectureDay,
                  int lectureStartTime, int lectureEndTime, LocalDateTime finalExamDate) {
        this(courseIDCounter++, courseName, subjectName, sectionNumber,
                teacherName, courseCapacity, location, lectureDay,
                lectureStartTime, lectureEndTime, finalExamDate);
    }

    // Returns a course by its ID
    public static Course findCourseByID(int courseID) {
        for (Course course : courseList) {
            if (course.getCourseID() == courseID) {
                return course;
            }
        }
        return null;
    }

    // Static operations
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

    public static void setCourseList(List<Course> courses) {
        courseList = courses;
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
    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public int getGrade() { return grade; }

    public String getFinalExamDateAsString() {
        return finalExamDate != null ? finalExamDate.toString() : "";
    }

    // Setters
    public void setLectureStartTime(int startTime) {
        this.lectureStartTime = startTime;
    }

    public void setLectureEndTime(int endTime) {
        this.lectureEndTime = endTime;
    }

    public void setLectureDay(String lectureDay) {
        this.lectureDay = lectureDay;
    }

    public void setFinalExamDate(LocalDateTime finalExamDate) {
        this.finalExamDate = finalExamDate;
    }

    public void changeCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void changeTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void changeCourseCapacity(int courseCapacity) {
        this.courseCapacity = courseCapacity;
    }

    public void changeLocation(String location) {
        this.location = location;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
