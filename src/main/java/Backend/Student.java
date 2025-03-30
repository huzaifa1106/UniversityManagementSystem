/**
 * File: Student.java
 * Description: Represents a student user in the university system. Stores full academic and contact details,
 * enrollment status, tuition information, and provides utility methods for initialization, course enrollment,
 * and interaction with the course and subject backend.
 *
 * Author: Huzaifa A. & Group
 * Date: March 2nd, 2025
 */

package Backend;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import java.util.*;

public class Student extends User {

    private static List<Student> students = new ArrayList<>();
    private static final int UGRADFEE = 5000;
    private static final int GRADFEE = 4000;

    private SimpleStringProperty fullName;
    private SimpleIntegerProperty studentID;
    private SimpleObjectProperty<Image> profilePicture;
    private SimpleStringProperty address;
    private SimpleLongProperty telephone;
    private SimpleIntegerProperty tuitionAnnual;
    private SimpleIntegerProperty tuitionBalance;
    private SimpleStringProperty emailAddress;
    private SimpleIntegerProperty average;
    private SimpleStringProperty semester;
    private SimpleStringProperty academicLevel;
    private SimpleStringProperty thesisTitle;
    private SimpleIntegerProperty progress;
    private List<Course> enrolledCourses;
    private List<Subject> enrolledSubjects;

    static {
        initializeStudents();
    }

    // Manual constructor with ID
    public Student(int studentID, String fullName, String password, Image profilePicture, String address, long telephone,
                   int tuitionAnnual, int tuitionBalance, String emailAddress, int average, String semester,
                   String academicLevel, String thesisTitle) {

        super(String.valueOf(studentID), password, "student");
        this.studentID = new SimpleIntegerProperty(studentID);
        this.fullName = new SimpleStringProperty(fullName);
        this.profilePicture = new SimpleObjectProperty<>(profilePicture);
        this.address = new SimpleStringProperty(address);
        this.telephone = new SimpleLongProperty(telephone);
        this.tuitionAnnual = new SimpleIntegerProperty(tuitionAnnual);
        this.tuitionBalance = new SimpleIntegerProperty(tuitionBalance);
        this.emailAddress = new SimpleStringProperty(emailAddress);
        this.average = new SimpleIntegerProperty(average);
        this.semester = new SimpleStringProperty(semester);
        this.academicLevel = new SimpleStringProperty(academicLevel);
        this.thesisTitle = new SimpleStringProperty(thesisTitle);
        this.progress = new SimpleIntegerProperty(-1);
        this.enrolledCourses = new ArrayList<>();
        this.enrolledSubjects = new ArrayList<>();

        UserAuthenticator.newUser(this.getUsername(), this.getPassword());
        students.add(this);
    }

    // Default constructor
    public Student() {
        super("", "", "");
        this.fullName = new SimpleStringProperty("");
        this.studentID = new SimpleIntegerProperty(0);
        this.profilePicture = new SimpleObjectProperty<>(null);
        this.address = new SimpleStringProperty("");
        this.telephone = new SimpleLongProperty(0);
        this.tuitionAnnual = new SimpleIntegerProperty(0);
        this.tuitionBalance = new SimpleIntegerProperty(0);
        this.emailAddress = new SimpleStringProperty("");
        this.average = new SimpleIntegerProperty(0);
        this.semester = new SimpleStringProperty("");
        this.academicLevel = new SimpleStringProperty("");
        this.thesisTitle = new SimpleStringProperty("");
        this.progress = new SimpleIntegerProperty(0);
        this.enrolledCourses = new ArrayList<>();
        this.enrolledSubjects = new ArrayList<>();
    }

    // Auto-ID Constructor
    public Student(String fullName, String password, Image profilePicture, String address, long telephone,
                   int tuitionAnnual, int tuitionBalance, String emailAddress, int average, String semester,
                   String academicLevel, String thesisTitle) {

        super(String.valueOf(generateStudentID()), password, "student");
        this.studentID = new SimpleIntegerProperty(Integer.parseInt(this.getUsername()));
        this.fullName = new SimpleStringProperty(fullName);
        this.profilePicture = new SimpleObjectProperty<>(profilePicture);
        this.address = new SimpleStringProperty(address);
        this.telephone = new SimpleLongProperty(telephone);
        this.tuitionAnnual = new SimpleIntegerProperty(tuitionAnnual);
        this.tuitionBalance = new SimpleIntegerProperty(tuitionBalance);
        this.emailAddress = new SimpleStringProperty(emailAddress);
        this.average = new SimpleIntegerProperty(average);
        this.semester = new SimpleStringProperty(semester);
        this.academicLevel = new SimpleStringProperty(academicLevel);
        this.thesisTitle = new SimpleStringProperty(thesisTitle);
        this.progress = new SimpleIntegerProperty(-1);
        this.enrolledCourses = new ArrayList<>();
        this.enrolledSubjects = new ArrayList<>();

        UserAuthenticator.newUser(this.getUsername(), this.getPassword());
        students.add(this);
    }

    // Enroll student into random courses and update subjects
    public static void autoEnrollStudentInCourses(Student student) {
        List<Course> availableCourses = Course.getCourseList();
        Collections.shuffle(availableCourses);

        List<Course> selected = availableCourses.subList(0, Math.min(5, availableCourses.size()));
        Set<Subject> subjects = new HashSet<>();

        for (Course c : selected) {
            student.enrolledCourses.add(c);
            c.getEnrolledStudents().add(student);

            Subject s = Subject.findSubjectByCode(c.getSubjectName());
            if (s != null) subjects.add(s);
        }

        student.enrolledSubjects.addAll(subjects);

        // Debug logging
        System.out.println("Enrolled " + student.getFullName() + " in subjects:");
        for (Subject subj : student.getEnrolledSubjects()) {
            System.out.println(" - " + subj.getSubjectName() + " (" + subj.getSubjectCode() + ")");
        }
    }

    // Static initialization of demo students
    public static void initializeStudents() {
        if (students.isEmpty()) {
            Student[] studentArray = {
                    new Student(100001, "Alice Smith", "alicepass", null, "123 Maple St", 1234567890, 5000, 2000, "alice@example.edu", 85, "Fall 2025", "Undergraduate", ""),
                    new Student(100002, "Bob Johnson", "bobpass", null, "456 Oak Ave", 987654321, 4000, 1000, "bob@example.edu", 90, "Spring 2025", "Graduate", "AI Research"),
                    new Student(100003, "Carol Williams", "carolpass", null, "789 Pine Rd", 555666777, 4000, 3000, "carol@example.edu", 88, "Winter 2025", "Graduate", "Data Science"),
                    new Student(100004, "Lucka Racki", "luckapass", null, "321 Birch St", 444555666, 5000, 2500, "lucka@example.edu", 75, "Fall 2025", "Undergraduate", "")
            };

            for (Student s : studentArray) {
                autoEnrollStudentInCourses(s);
            }
        }
    }

    private static int generateStudentID() {
        return 100000 + new Random().nextInt(900000);
    }

    // Public static utility methods
    public static Student getStudent(int studentID) {
        for (Student student : students) {
            if (student.getStudentID() == studentID) return student;
        }
        return null;
    }

    public static void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added successfully: " + student.getFullName());
    }

    public static void removeStudent(int studentID) {
        students.removeIf(student -> student.getStudentID() == studentID);
    }

    public static List<Student> getStudentList() {
        return students;
    }

// code to check student valadationn
    public static void Student(Student student) {
        students.add(student);
        System.out.println("Student added successfully: " + student.getFullName());
    }

    public static boolean isDuplicate(Student student) {
        for (Student s : students) {
            if (s.getStudentID() == student.getStudentID() ||
                    s.getEmailAddress().equalsIgnoreCase(student.getEmailAddress())) {
                return true;
            }
        }
        return false;
    }

    // Accessors for course & subject lists
    public List<Course> getEnrolledCourses() { return enrolledCourses; }
    public List<Subject> getEnrolledSubjects() { return enrolledSubjects; }

    // Getters
    public String getFullName() { return fullName.get(); }
    public int getStudentID() { return studentID.get(); }
    public Image getProfilePicture() { return profilePicture.get(); }
    public String getAddress() { return address.get(); }
    public long getTelephone() { return telephone.get(); }
    public int getTuitionAnnual() { return tuitionAnnual.get(); }
    public int getTuitionBalance() { return tuitionBalance.get(); }
    public String getEmailAddress() { return emailAddress.get(); }
    public int getAverage() { return average.get(); }
    public String getSemester() { return semester.get(); }
    public String getAcademicLevel() { return academicLevel.get(); }
    public String getThesisTitle() { return thesisTitle.get(); }
    public int getProgress() { return progress.get(); }

    // Setters
    public void setFullName(String fullName) { this.fullName.set(fullName); }
    public void setProfilePicture(Image profilePicture) { this.profilePicture.set(profilePicture); }
    public void setAddress(String address) { this.address.set(address); }
    public void setTelephone(long telephone) { this.telephone.set(telephone); }
    public void setTuitionAnnual(int tuitionAnnual) { this.tuitionAnnual.set(tuitionAnnual); }
    public void setTuitionBalance(int tuitionBalance) { this.tuitionBalance.set(tuitionBalance); }
    public void setEmailAddress(String emailAddress) { this.emailAddress.set(emailAddress); }
    public void setAverage(int average) { this.average.set(average); }
    public void setSemester(String semester) { this.semester.set(semester); }
    public void setAcademicLevel(String academicLevel) { this.academicLevel.set(academicLevel); }
    public void setThesisTitle(String thesisTitle) { this.thesisTitle.set(thesisTitle); }
    public void setProgress(int progress) { this.progress.set(progress); }
}
