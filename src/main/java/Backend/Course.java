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

    //  Static Initialization Block: Preloads demo courses
    static {
        Course[] courses = {
                new Course(1, "Calculus I", "MATH001", 1, "Dr. Alan Turing", 30, "Room 101", "Mon/Wed", 900, 1100, LocalDateTime.of(2025, 12, 15, 9, 0)),
                new Course(2, "Literature Basics", "ENG101", 1, "Prof. Emily Brontë", 25, "Room 102", "Tue/Thu", 1000, 1200, LocalDateTime.of(2025, 12, 16, 10, 0)),
                new Course(3, "Literature Basics", "ENG101", 2, "Prof. Emily Brontë", 25, "Room 102", "Mon/Wed", 1000, 1200, LocalDateTime.of(2025, 12, 16, 10, 0)),
                new Course(4, "Introduction to Programming", "CS201", 1, "Dr. Grace Hopper", 42, "Room 103", "Tue/Thu", 1200, 1400, LocalDateTime.of(2025, 12, 16, 12, 30)),
                new Course(5, "Introduction to Chemistry", "CHEM200", 1, "Dr. Lakyn Copeland", 50, "Room 201", "Mon/Thu", 1500, 1600, LocalDateTime.of(2025, 12, 14, 16, 0)),
                new Course(6, "Introduction to Chemistry", "CHEM200", 2, "Dr. Lakyn Copeland", 50, "Room 202", "Mon/Tue", 1700, 1800, LocalDateTime.of(2025, 12, 14, 17, 0)),
                new Course(7, "Introduction to Chemistry", "CHEM200", 3, "Dr. Lakyn Copeland", 50, "Room 203", "Fri/Thu", 1400, 1500, LocalDateTime.of(2025, 12, 14, 14, 0)),
                new Course(8, "Introduction to French", "ENG101", 1, "Prof. Emily Brontë", 25, "Room 104", "Tue/Thu", 1630, 1730, LocalDateTime.of(2025, 12, 17, 16, 30)),
                new Course(9, "Introduction to French", "ENG101", 2, "Prof. Emily Brontë", 25, "Room 105", "Tue/Thu", 1730, 1830, LocalDateTime.of(2025, 12, 17, 17, 30)),
                new Course(10, "Water Resources", "ENGG402", 1, "Albozr Gharabaghi", 50, "Room 301", "Mon/Fri", 900, 1030, LocalDateTime.of(2025, 12, 18, 9, 0))
        };
        for (Course course : courses) {
            courseList.add(course);
        }
    }

    //  Full Constructor with course ID
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

    //  Auto-incremented constructor for new courses
    public Course(String courseName, String subjectName, int sectionNumber,
                  String teacherName, int courseCapacity, String location, String lectureDay,
                  int lectureStartTime, int lectureEndTime, LocalDateTime finalExamDate) {
        this(courseIDCounter++, courseName, subjectName, sectionNumber,
                teacherName, courseCapacity, location, lectureDay,
                lectureStartTime, lectureEndTime, finalExamDate);
    }

    //  Check if a new course conflicts with any existing one
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

    //  Add course if not duplicate
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

    //  Find course by ID
    public static Course findCourseByID(int courseID) {
        for (Course course : courseList) {
            if (course.getCourseID() == courseID) {
                return course;
            }
        }
        return null;
    }

    //  Print course details (console)
    public void displayCourse() {
        System.out.println("Course ID: " + courseID);
        System.out.println("Course Name: " + courseName);
        System.out.println("Subject Name: " + subjectName);
        System.out.println("Section Number: " + sectionNumber);
        System.out.println("Teacher Name: " + teacherName);
        System.out.println("Course Capacity: " + courseCapacity);
        System.out.println("Location: " + location);
        System.out.println("Lecture Time: " + lectureDay + " " + lectureStartTime + " - " + lectureEndTime);
        System.out.println("Final Exam: " + finalExamDate);
        System.out.println("Enrolled: " + enrolledStudents.size());
    }

    //  Course list operations
    public static List<Course> getCourseList() {
        return courseList;
    }

    public static void addCourse(Course course) {
        courseList.add(course);
        System.out.println("Course added: " + course.getCourseName());
    }

    public static void removeCourse(int courseID) {
        courseList.removeIf(course -> course.getCourseID() == courseID);
        System.out.println("Course removed: " + courseID);
    }

    //  Getters
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

    //  Setters
    public void changeCourseName(String courseName) { this.courseName = courseName; }
    public void changeTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void changeCourseCapacity(int courseCapacity) { this.courseCapacity = courseCapacity; }
    public void changeLocation(String location) { this.location = location; }
    public void setGrade(int grade) { this.grade = grade; }
}
