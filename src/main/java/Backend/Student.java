/**
 * This class represents a student in the university system. It stores various personal, academic, and contact details,
 * as well as information about the courses and subjects the student is enrolled in. The class also includes methods
 * for enrollment, course management, and interacting with backend components like the course and subject management systems.
 * Author: Huzaifa A. & Group
 * Date:April 2025
 */

package Backend;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.*;

/**
 * The Student class inherits from User and represents a student entity in the university system.
 */
public class Student extends User {

    // Static list to keep track of all students in the system
    private static List<Student> students = new ArrayList<>();

    // Constants for undergraduate and graduate tuition fees
    private static final int UGRADFEE = 5000;
    private static final int GRADFEE = 4000;

    // Properties for student's information
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
    private List<Course> enrolledCourses;  // List of courses the student is enrolled in
    private List<Subject> enrolledSubjects; // List of subjects the student is enrolled in

    static {
        // Initialize demo students when the class is loaded
        initializeStudents();
    }

    /**
     * Constructor with manual student details including an ID.
     */
    public Student(int studentID, String fullName, String password, Image profilePicture, String address, long telephone,
                   int tuitionAnnual, int tuitionBalance, String emailAddress, int average, String semester,
                   String academicLevel, String thesisTitle) {

        super(String.valueOf(studentID), password, "student");  // Initialize parent class (User)
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
        this.progress = new SimpleIntegerProperty(-1);  // Progress is initially undefined
        this.enrolledCourses = new ArrayList<>();
        this.enrolledSubjects = new ArrayList<>();

        // Register the new user in the UserAuthenticator and add to the student list
        UserAuthenticator.newUser(this.getUsername(), this.getPassword());
        students.add(this);
    }

    /**
     * Default constructor for a Student with empty values.
     */
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

    /**
     * Constructor with auto-generated student ID. Also enrolls the student in courses if needed.
     */
    public Student(String fullName, String password, Image profilePicture, String address, long telephone,
                   int tuitionAnnual, int tuitionBalance, String emailAddress, int average, String semester,
                   String academicLevel, String thesisTitle) {

        super(String.valueOf(generateStudentID()), password, "student");  // Initialize parent class (User)
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
        this.progress = new SimpleIntegerProperty(-1);  // Progress is initially undefined
        this.enrolledCourses = new ArrayList<>();
        this.enrolledSubjects = new ArrayList<>();

        // Register the new user in the UserAuthenticator and add to the student list
        UserAuthenticator.newUser(this.getUsername(), this.getPassword());
        students.add(this);
    }


    public Student(int studentID, String fullName, String password, Image profilePicture, String address, long telephone,
                   int tuitionAnnual, int tuitionBalance, String emailAddress, int average, String semester,
                   String academicLevel, String thesisTitle, int progress,
                   ArrayList<Course> enrolledCourses, ArrayList<Subject> enrolledSubjects,
                   boolean register) {

        super(String.valueOf(studentID), password, "student");  // Initialize parent class (User)

        this.studentID = new SimpleIntegerProperty(studentID);
        this.fullName = new SimpleStringProperty(fullName);
        this.profilePicture = new SimpleObjectProperty<>(null);  // Delay loading until JavaFX is ready
        this.address = new SimpleStringProperty(address);
        this.telephone = new SimpleLongProperty(telephone);
        this.tuitionAnnual = new SimpleIntegerProperty(tuitionAnnual);
        this.tuitionBalance = new SimpleIntegerProperty(tuitionBalance);
        this.emailAddress = new SimpleStringProperty(emailAddress);
        this.average = new SimpleIntegerProperty(average);
        this.semester = new SimpleStringProperty(semester);
        this.academicLevel = new SimpleStringProperty(academicLevel);
        this.thesisTitle = new SimpleStringProperty(thesisTitle);
        this.progress = new SimpleIntegerProperty(progress);
        this.enrolledCourses = enrolledCourses != null ? enrolledCourses : new ArrayList<>();
        this.enrolledSubjects = enrolledSubjects != null ? enrolledSubjects : new ArrayList<>();

        if (register) {
            // Check for username uniqueness before adding the student
            if (!isUsernameTaken(this.getUsername())) {
                UserAuthenticator.newUser(this.getUsername(), this.getPassword());
                students.add(this);
            } else {
                System.out.println("Error: Username already exists.");
            }
        }
    }

    // Check if username already exists
    private boolean isUsernameTaken(String username) {
        for (Student student : students) {
            if (student.getUsername().equals(username)) {
                return true;  // Username already exists
            }
        }
        return false;
    }

    // Lazy-loads the default profile image only when JavaFX is initialized
    public void loadDefaultProfilePicture() {
        if (this.profilePicture.get() == null) {
            try {
                Image img = new Image(getClass().getResource("/GUI/CompanyLogin/ProfilePicture.jpg").toExternalForm());
                this.profilePicture.set(img);
            } catch (Exception e) {
                System.err.println("Failed to load default profile picture.");
            }
        }
    }


    /**
     * This method enrolls the student in a set of randomly selected courses and updates the enrolled subjects list.
     * It also logs the enrollment details for debugging purposes.
     */
    public static void autoEnrollStudentInCourses(Student student) {
        List<Course> availableCourses = Course.getCourseList();
        Collections.shuffle(availableCourses);  // Shuffle courses to select random ones

        List<Course> selected = availableCourses.subList(0, Math.min(5, availableCourses.size()));  // Select up to 5 courses
        Set<Subject> subjects = new HashSet<>();

        // Enroll the student in the selected courses and add corresponding subjects
        for (Course c : selected) {
            student.enrolledCourses.add(c);
            c.getEnrolledStudents().add(student);  // Add student to course's enrolled list

            Subject s = Subject.findSubjectByCode(c.getSubjectName());
            if (s != null) subjects.add(s);
        }

        student.enrolledSubjects.addAll(subjects);  // Update the student's enrolled subjects

        // Log the enrolled subjects for the student
        System.out.println("Enrolled " + student.getFullName() + " in subjects:");
        for (Subject subj : student.getEnrolledSubjects()) {
            System.out.println(" - " + subj.getSubjectName() + " (" + subj.getSubjectCode() + ")");
        }
    }

    /**
     * Static method to initialize a set of demo students if the student list is empty.
     */
    public static void initializeStudents() {
        if (students.isEmpty()) {
            Student[] studentArray = {
                    new Student(100001, "Alice Smith", "alicepass", null, "123 Maple St", 1234567890, 5000, 2000, "alice@example.edu", 85, "Fall 2025", "Undergraduate", ""),
                    new Student(100002, "Bob Johnson", "bobpass", null, "456 Oak Ave", 987654321, 4000, 1000, "bob@example.edu", 90, "Spring 2025", "Graduate", "AI Research"),
                    new Student(100003, "Carol Williams", "carolpass", null, "789 Pine Rd", 555666777, 4000, 3000, "carol@example.edu", 88, "Winter 2025", "Graduate", "Data Science"),
                    new Student(100004, "Lucka Racki", "luckapass", null, "321 Birch St", 444555666, 5000, 2500, "lucka@example.edu", 75, "Fall 2025", "Undergraduate", "")
            };

            // Auto-enroll demo students in random courses
            for (Student s : studentArray) {
                autoEnrollStudentInCourses(s);
            }
        }
    }

    /**
     * Generates a random student ID for new students.
     */
    private static int generateStudentID() {
        return 100000 + new Random().nextInt(900000);  // Random 6-digit ID
    }

    // Public methods for managing students and enrollment

    /**
     * Retrieves a student based on their ID.
     */
    public static Student getStudent(int studentID) {
        for (Student student : students) {
            if (student.getStudentID() == studentID) return student;
        }
        return null;  // Return null if student is not found
    }

    /**
     * Adds a student to the system.
     */
    public static void addStudent(Student student) {
        System.out.println("Student added successfully: " + student.getFullName());
    }

    /**
     * Removes a student from the system by their student ID.
     */
    public static void removeStudent(int studentID) {
        students.removeIf(student -> student.getStudentID() == studentID);
    }

    /**
     * Gets the list of all students.
     */
    public static List<Student> getStudentList() {
        return students;
    }


    /**
     * Checks if enrolling in a new course would cause a scheduling conflict.
     */
    public boolean hasTimeConflictWith(Course newCourse) {
        for (Course enrolled : this.getEnrolledCourses()) {
            if (enrolled.getLectureDay().equalsIgnoreCase(newCourse.getLectureDay())) {
                boolean overlaps =
                        (newCourse.getLectureStartTime() >= enrolled.getLectureStartTime() && newCourse.getLectureStartTime() < enrolled.getLectureEndTime()) ||
                                (newCourse.getLectureEndTime() > enrolled.getLectureStartTime() && newCourse.getLectureEndTime() <= enrolled.getLectureEndTime()) ||
                                (newCourse.getLectureStartTime() <= enrolled.getLectureStartTime() && newCourse.getLectureEndTime() >= enrolled.getLectureEndTime());
                if (overlaps) {
                    return true;  // Return true if there's a time conflict
                }
            }
        }
        return false;  // No conflict
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
    public String getCourseNamesAsString() {if (enrolledCourses == null || enrolledCourses.isEmpty()) return "No courses enrolled.";StringBuilder sb = new StringBuilder();for (Course c : enrolledCourses) {sb.append("- ").append(c.getCourseName()).append("\n");}return sb.toString();}
    public boolean checkDuplicateCourse(Course course) {for (Course c : enrolledCourses) {if (c.getCourseName().equalsIgnoreCase(course.getCourseName())) {return true;}}return false;}
    public int getProgress() { return progress.get(); }


    public static void setStudentList(List<Student> list) {
        students = list;
    }


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
    public void updateEnrolledCourses(Course course) {if (!checkDuplicateCourse(course)) {enrolledCourses.add(course);if (!course.getEnrolledStudents().contains(this)) {course.getEnrolledStudents().add(this);}System.out.println("Course added: " + course.getCourseName());} else {System.out.println("Student already enrolled in: " + course.getCourseName());}}
    public void setAcademicLevel(String academicLevel) { this.academicLevel.set(academicLevel); }
    public void setThesisTitle(String thesisTitle) { this.thesisTitle.set(thesisTitle); }
    public void setProgress(int progress) { this.progress.set(progress); }
}
