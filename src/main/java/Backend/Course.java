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

    public Course(int courseID, String courseName, String subjectName, int sectionNumber, String teacherName, int courseCapacity, String location, String lectureDay, int lectureStartTime, int lectureEndTime, LocalDateTime finalExamDate, int grade) {
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
        this.grade = grade;
        courseList.add(this);
    }

    public Course(int courseID, String courseName, int sectionNumber, String teacherName, int courseCapacity, String location, String lectureDay, int lectureStartTime, int lectureEndTime, LocalDateTime finalExamDate, int grade) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.sectionNumber = sectionNumber;
        this.teacherName = teacherName;
        this.courseCapacity = courseCapacity;
        this.location = location;
        this.lectureDay = lectureDay;
        this.lectureStartTime = lectureStartTime;
        this.lectureEndTime = lectureEndTime;
        this.finalExamDate = finalExamDate;
        this.enrolledStudents = new ArrayList<>();
        this.grade = grade;
        courseList.add(this);
    }

    public Course(int courseID, String courseName, String subjectName, int sectionNumber, String teacherName, int courseCapacity, String location, String lectureDay, int lectureStartTime, int lectureEndTime, LocalDateTime finalExamDate) {
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

    public boolean enrollStudent(Student student) {
        if (enrolledStudents.size() >= courseCapacity) {
            System.out.println("Enrollment Failed " + student.getUsername() + ". Course is full!");
            return false;
        } else {
            enrolledStudents.add(student);
            System.out.println(student.getUsername() + " has been enrolled in " + courseName);
            return true;
        }
    }
    public static Course findCourse(String courseName) {
        for (Course course : courseList) {
            if (course.getCourseName().equalsIgnoreCase(courseName.trim())) {
                return course;
            }
        }
        return null;
    }

    public void removeStudent(Student student) {
        if (enrolledStudents.remove(student)) {
            System.out.println(student.getUsername() + " has been removed from " + courseName);
        } else {
            System.out.println(student.getUsername() + " is not enrolled in this course.");
        }
    }

    public static void deleteCourse(Course newCourse) {
        courseList.remove(newCourse);
    }

    public static boolean checkConflict(Course newCourse) {
        for (Course existing : courseList) {
            if (existing.lectureDay.equalsIgnoreCase(newCourse.lectureDay)) {
                if ((newCourse.lectureStartTime >= existing.lectureStartTime && newCourse.lectureStartTime < existing.lectureEndTime) ||
                        (newCourse.lectureEndTime > existing.lectureStartTime && newCourse.lectureEndTime <= existing.lectureEndTime)) {
                    return true;
                }
            }
        }
        return false;
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

    public void changeCourseName(String courseName) { this.courseName = courseName; }
    public void changeTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void changeCourseCapacity(int courseCapacity) { this.courseCapacity = courseCapacity; }
    public void changeLocation(String location) { this.location = location; }
    public void setGrade(int grade) { this.grade = grade; }
}
