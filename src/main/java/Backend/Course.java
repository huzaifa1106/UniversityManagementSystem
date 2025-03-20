/**
 *  File: Course.java
 *  Description: This class is for the course offerings by UofG,
 *  we will use this later on when enrolling, removing or viewing
 *  important information regrading a course.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 *  */
package Backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private static List<Course> courseList = new ArrayList<>();

    // Static block to initialize courses
    static {
        Course[] courses = {
                new Course(1, "Calculus I", "MATH001", 1, "Dr. Alan Turing", 30, "Room 101", "Mon/Wed", 900, 1100, LocalDateTime.of(2025, 12, 15, 9, 0)),
                new Course(2, "Literature Basics", "ENG101", 1, "Prof. Emily Brontë", 25, "Room 102", "Tue/Thu", 1000, 1200, LocalDateTime.of(2025, 12, 16, 10, 0)),
                new Course(3, "Literature Basics", "ENG101", 2, "Prof. Emily Brontë", 25, "Room 102", "Mon/Wed", 1000, 1200, LocalDateTime.of(2025, 12, 16, 10, 0)),
                new Course(4, "Introduction to Programming", "CS201", 1, "Prof. Bahar Nozari", 42, "Room 103", "Tue/Thu", 1200, 1400, LocalDateTime.of(2025, 12, 16, 12, 30)),
                new Course(5, "Introduction to Chemistry", "CHEM200", 1, "Dr. Lucka Lucku", 50, "Room 201", "Mon/Thu", 1500, 1600, LocalDateTime.of(2025, 12, 14, 16, 0)),
                new Course(6, "Introduction to Chemistry", "CHEM200", 2, "Dr. Lucka Lucku", 50, "Room 202", "Mon/Tue", 1700, 1800, LocalDateTime.of(2025, 12, 14, 17, 0)),
                new Course(7, "Introduction to Chemistry", "CHEM200", 3, "Dr. Lucka Lucku", 50, "Room 203", "Fri/Thu", 1400, 1500, LocalDateTime.of(2025, 12, 14, 14, 0)),
                new Course(8, "Introduction to French", "ENG101", 1, "Prof. Jean-Luc Picard", 25, "Room 104", "Tue/Thu", 1630, 1730, LocalDateTime.of(2025, 12, 17, 16, 30)),
                new Course(9, "Introduction to French", "ENG101", 2, "Prof. Jean-Luc Picard", 25, "Room 105", "Tue/Thu", 1730, 1830, LocalDateTime.of(2025, 12, 17, 17, 30)),
                new Course(10, "Water Resources", "ENGG402", 1, "Dr. Sarah Connor", 50, "Room 301", "Mon/Fri", 900, 1030, LocalDateTime.of(2025, 12, 18, 9, 0))
        };

        // Add courses to the courseList
        for (Course course : courses) {
            courseList.add(course);
        }
    }

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
        courseList.add(this);
    }

    public static boolean checkConflict(Course newCourse) {
        for (Course existing : courseList) {
            if (existing.lectureDay.equalsIgnoreCase(newCourse.lectureDay)) {
                if ((newCourse.lectureStartTime >= existing.lectureStartTime && newCourse.lectureStartTime < existing.lectureEndTime) ||
                        (newCourse.lectureEndTime > existing.lectureStartTime && newCourse.lectureEndTime <= existing.lectureEndTime) ||
                        (newCourse.lectureStartTime <= existing.lectureStartTime && newCourse.lectureEndTime >= existing.lectureEndTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Course findCourse(String courseName) {
        for (Course course : courseList) {
            if (course.getCourseName().equalsIgnoreCase(courseName.trim())) {
                return course; // Found course, return it
            }
        }
        return null; // Course not found
    }

    public void displayCourse() {
        System.out.println("Course ID: " + courseID);
        System.out.println("Course Name: " + courseName);
        System.out.println("Subject Name: " + subjectName);
        System.out.println("Section Number: " + sectionNumber);
        System.out.println("Teacher Name: " + teacherName);
        System.out.println("Course Capacity: " + courseCapacity);
        System.out.println("Location: " + location);
        System.out.println("Lecture Day: " + lectureDay);
        System.out.println("Lecture Time: " + lectureStartTime + " - " + lectureEndTime);
        System.out.println("Final Exam Date: " + finalExamDate);
        System.out.println("# Enrolled Students: " + enrolledStudents.size());
    }

    // Getters
    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public int getCourseCapacity() {
        return courseCapacity;
    }

    public String getLocation() {
        return location;
    }

    public String getLectureDay() {
        return lectureDay;
    }

    public int getLectureStartTime() {
        return lectureStartTime;
    }

    public int getLectureEndTime() {
        return lectureEndTime;
    }

    public LocalDateTime getFinalExamDate() {
        return finalExamDate;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public int getGrade() {
        return grade;
    }

    // Setters
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