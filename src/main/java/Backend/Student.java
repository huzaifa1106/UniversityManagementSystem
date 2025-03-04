package Backend;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student extends User {

    // Constants for tuition fees
    private static final int UGRADFEE = 5000;
    private static final int GRADFEE = 4000;

    // Attributes
    private String fullName;
    private int studentID;
    private Image profilePicture;
    private String address;
    private int telephone;
    private int tuitionAnnual;
    private int tuitionBalance;
    private List<Course> enrolledCourses;
    private String emailAddress;
    private int average;
    private String semester;
    private List<Course> enrolledSubjects;
    private String academicLevel;
    private String thesisTitle;
    private int progress;

    // Constructor
    public Student(String fullName, String username, String password, Image profilePicture, String address, int telephone, int tuitionAnnual, int tuitionBalance, String emailAddress, int average, String semester,  List<Course> enrolledCourses, List<Course> enrolledSubjects, String academicLevel, String thesisTitle, int progress) {

        super(username, password, "student");
        this.fullName = fullName;
        this.studentID = generateStudentID();
        this.profilePicture = profilePicture;
        this.address = address;
        this.telephone = telephone;
        this.tuitionAnnual = tuitionAnnual;
        this.tuitionBalance = tuitionBalance;
        this.emailAddress = emailAddress;
        this.average = average;
        this.semester = semester;
        this.enrolledCourses = enrolledCourses;
        this.enrolledSubjects = enrolledSubjects;
        this.academicLevel = academicLevel;
        this.thesisTitle = thesisTitle;
        this.progress = progress;
    }

    // Method to generate a random 10-digit student ID
    private int generateStudentID() {
        Random rand = new Random();
        return 1000000000 + rand.nextInt(2147483647 - 1000000000);
    }

    public static void removeStudent(ArrayList<Student> students, Student s ){
        students.remove(s);
    }

    public void viewProfile() {
        System.out.println("=== Profile ===");
        System.out.println("Name: " + fullName);
        System.out.println("Student ID: " + studentID);
        System.out.println("Email: " + emailAddress);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + telephone);
        System.out.println("Academic Level: " + academicLevel);
        System.out.println("Thesis Title: " + thesisTitle);
        System.out.println("Semester: " + semester);
        System.out.println("Progress: " + progress + "%");
    }

    public void editProfile(String newPassword, Image newProfilePicture) {
        setPassword(newPassword);
        this.profilePicture = newProfilePicture;
        System.out.println("Profile updated successfully!");
    }

    public void viewEnrolledCourses() {
        System.out.println("=== Enrolled Courses ===");
        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses.");
        } else {
            for (Course course : enrolledCourses) {
                System.out.println(course.getCourseName() + " - " + course.getCourseID());
            }
        }
    }

    public void viewGrades() {
        System.out.println("=== Academic Performance ===");

        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
            return;
        }

        for (Course course : enrolledCourses) {
            double grade = course.getGrade();

            // Print course name and grade status
            if (grade == -1) {
                System.out.println(course.getCourseName() + " - Not Graded");
            } else {
                System.out.println(course.getCourseName() + " - " + grade);
            }
        }
    }

    public void viewTuitionStatus() {
        System.out.println("=== Tuition Information ===");
        System.out.println("Annual Tuition: $" + tuitionAnnual);
        System.out.println("Balance Due: $" + tuitionBalance);
    }



    // Getter Methods
    public String getFullName() { return fullName; }
    public int getStudentID() { return studentID; }
    public Image getProfilePicture() { return profilePicture; }
    public String getAddress() { return address; }
    public int getTelephone() { return telephone; }
    public int getTuitionAnnual() { return tuitionAnnual; }
    public int getTuitionBalance() { return tuitionBalance; }
    public String getEmailAddress() { return emailAddress; }
    public int getAverage() { return average; }
    public String getSemester() { return semester; }
    public List<Course> getEnrolledCourses() { return enrolledCourses; }
    public List<Course> getEnrolledSubjects() { return enrolledSubjects; }
    public String getAcademicLevel() { return academicLevel; }
    public String getThesisTitle() { return thesisTitle; }
    public int getProgress() { return progress; }

    //Setter Methods
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setProfilePicture(Image profilePicture) { this.profilePicture = profilePicture; }
    public void setAddress(String address) { this.address = address; }
    public void setTelephone(int telephone) { this.telephone = telephone; }
    public void setTuitionAnnual(int tuitionAnnual) { this.tuitionAnnual = tuitionAnnual; }
    public void setTuitionBalance(int tuitionBalance) { this.tuitionBalance = tuitionBalance; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public void setAverage(int average) { this.average = average; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setEnrolledCourses(List<Course> enrolledCourses) { this.enrolledCourses = enrolledCourses; }
    public void setEnrolledSubjects(List<Course> enrolledSubjects) { this.enrolledSubjects = enrolledSubjects; }
    public void setAcademicLevel(String academicLevel) { this.academicLevel = academicLevel; }
    public void setThesisTitle(String thesisTitle) { this.thesisTitle = thesisTitle; }
    public void setProgress(int progress) { this.progress = progress; }

}
