/**
 *  File: Course.java
 *  Description: This class is for the course offerings by UofG,
 *  we will use this later on when enrolling, removing or viewing
 *  important information regrading a course.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 *  */

package Backend;

//Import statements
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Course {
    private int courseID;
    private String courseName;
    private String subjectName;
    private int sectionNumber;
    private String teacherName;
    private int courseCapacity;
    private String location;
    private String lectureDay;
    private int lectureStartTime; // Stored in Military Time
    private int lectureEndTime; //Stored in Military Time
    private LocalDateTime finalExamDate;
    private List<Student> enrolledStudents;
    private int grade;

    //Constructor for Course Object if Grade Provided
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
    }
    //Constructor for Course Object if Grade not Provided
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
        this.grade = -1;//-1 Means ungraded
    }

    // Method to Prevent over-enrollment
    public boolean enrollStudent(Student student) {
        if (enrolledStudents.size() >= courseCapacity) {
            System.out.println("Enrollment Failed " + student.getUsername() + ". Course is full!");
            return false;
        }

        else {
        enrolledStudents.add(student);
        System.out.println(student.getUsername() + " has been enrolled in " + courseName);
        return true;
        }
    }

    //Method to remove student from system.
    public void removeStudent(Student student) {
        if (enrolledStudents.remove(student)) {
            System.out.println(student.getUsername() + " has been removed from " + courseName);
        } else {
            System.out.println(student.getUsername() + " is not enrolled in this course.");
        }
    }

    //Method for removing a course, from a course list.
    public static void deleteCourse(Course newCourse, List<Course> existingCourses) {
        existingCourses.remove(newCourse);
    }

    // Method for detecting schedule conflicts
    public static boolean checkConflict(Course newCourse, List<Course> existingCourses) {
        //Looping each course
        for (Course existing : existingCourses) {
            if (existing.lectureDay.equalsIgnoreCase(newCourse.lectureDay)) {
                //Checking if new course starts at the time or after the lecture time while ending before the existing class ends or if the new course had a start time before the existing course ends and ends before the previous course lecture begins.
                if ((newCourse.lectureStartTime >= existing.lectureStartTime && newCourse.lectureStartTime < existing.lectureEndTime) || (newCourse.lectureEndTime > existing.lectureStartTime && newCourse.lectureEndTime <= existing.lectureEndTime)) {
                    //Conflict Exists
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
    // Getter Methods
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

    //Setter Methods
    public void changeCourseName(String courseName) { this.courseName = courseName; }
    public void changeTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void changeCourseCapacity(int courseCapacity) { this.courseCapacity = courseCapacity; }
    public void changeLocation(String location) { this.location = location; }
    public void setGrade(int grade) { this.grade = grade; }
}
