/**
 *  File: Student.java
 *  Description: This class is for storing important information, personal, academic,
 *  as well as financial as well, helps us retrieve more effectively through these methods
 *  when implementing UI components
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 *  */

package Backend;

//Important Statements
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student extends User {


    //Load the students when compiled
    static {
        loadStudents();
    }

    // Constants for tuition fees
    private static final int UGRADFEE = 5000;
    private static final int GRADFEE = 4000;

    private static final String STUDENT_FILE = "students.txt";
    //Array to store the students
    private static List<Student> students = new ArrayList<>();

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
    private List<Subject> enrolledSubjects;
    private String academicLevel;
    private String thesisTitle;
    private int progress;


    public Student(String hel) {
        super("","","");
        this.fullName = "";
        this.studentID = 0;
        this.address = "";
        this.telephone = 0;
        this.tuitionAnnual = 0;
        this.tuitionBalance = 0;
        this.enrolledCourses = new ArrayList<>();
        this.enrolledSubjects = new ArrayList<>();
        this.academicLevel = "";
        this.thesisTitle = "";
        this.progress = 0;
    }
    // Constructor
    public Student(String fullName, String password, Image profilePicture, String address, int telephone,
                   int tuitionAnnual, int tuitionBalance, String emailAddress, int average, String semester, String academicLevel,
                   String thesisTitle) {

        super(String.valueOf(generateStudentID()), password, "student");  // Username = Student ID (as String)
        this.studentID = Integer.parseInt(this.getUsername()); // Convert username back to int
        this.fullName = fullName;
        this.profilePicture = profilePicture;
        this.address = address;
        this.telephone = telephone;
        this.tuitionAnnual = tuitionAnnual;
        this.tuitionBalance = tuitionBalance;
        this.emailAddress = emailAddress;
        this.average = average;
        this.semester = semester;
        this.enrolledCourses = new ArrayList<>();
        this.enrolledSubjects = new ArrayList<>();
        this.academicLevel = academicLevel;
        this.thesisTitle = thesisTitle;
        this.progress = -1;
        UserAuthenticator.newUser(this.getUsername(), this.getPassword());
        students.add(this);

    }


    //Load the students: Format Written(fullName, password, profilePic, address, telephone, annual tuition, tuitionBalance, email adderess, average)
    public static void loadStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.clear();

        File file = new File(STUDENT_FILE);
        if (!file.exists()) {
            System.out.println("Student file not found. Creating new one.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 11) { // Ensure correct data structure
                    try {
                        Student student = new Student(
                                data[0], data[1], null, data[2], Integer.parseInt(data[3]),
                                Integer.parseInt(data[4]), Integer.parseInt(data[5]), data[6], Integer.parseInt(data[7]),
                                data[8], data[9], data[10]
                        );

                        // Load enrolled courses
                        if (data.length > 11 && !data[11].isEmpty() && !data[11].equals("[]")) {
                            String courseData = data[11].replace("[", "").replace("]", "");
                            for (String courseName : courseData.split(";")) {
                                Course foundCourse = Course.findCourse(courseName);
                                if (foundCourse != null) {
                                    student.updateEnrolledCourses(foundCourse);
                                }
                            }
                        }

                        // Load enrolled subjects
                        if (data.length > 12 && !data[12].isEmpty() && !data[12].equals("[]")) {
                            String subjectData = data[12].replace("[", "").replace("]", "");
                            for (String subjectName : subjectData.split(";")) {
                                Subject foundSubject = Subject.findSubject(subjectName);
                                if (foundSubject != null) {
                                    student.updateEnrolledSubjects(foundSubject);
                                }
                            }
                        }

                        students.add(student);
                    } catch (NumberFormatException e) {
                        System.err.println("Data formatting error in students.txt, skipping line.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save all students to file
    public static void saveAllStudents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_FILE))) {
            for (Student student : students) {
                writer.write(student.getFullName() + "," + student.getPassword() + "," + student.getAddress() + ","
                        + student.getTelephone() + "," + student.getTuitionAnnual() + "," + student.getTuitionBalance() + ","
                        + student.getEmailAddress() + "," + student.getAverage() + "," + student.getSemester() + ","
                        + student.getAcademicLevel() + "," + student.getThesisTitle() + ","
                        + student.getCourseNamesAsString() + "," // Use the new method
                        + student.getSubjectNamesAsString()); // Assuming subjects have a similar method

                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void printCourses(){

        for(Course courses: this.enrolledCourses){

            System.out.println(courses.getCourseName());

        }

    }


    private static void writeStudentToFile(Student student) {
        saveAllStudents();
    }

    // Method to generate a random 10-digit student ID
    private static int generateStudentID() {
        Random rand = new Random();
        return 1000000000 + rand.nextInt(2147483647 - 1000000000);

    }

    //Method to remove a student from student list
    public static void removeStudent(ArrayList<Student> students, Student s ){
        students.remove(s);
    }

    public String getCourseNamesAsString() {
        if (enrolledCourses.isEmpty()) return "[]"; // Handle empty case

        StringBuilder courseList = new StringBuilder("[");
        for (int i = 0; i < enrolledCourses.size(); i++) {
            courseList.append(enrolledCourses.get(i).getCourseName());
            if (i < enrolledCourses.size() - 1) {
                courseList.append("; "); // Separate courses properly
            }
        }
        courseList.append("]"); // Close brackets

        return courseList.toString();
    }

    // Get Subject Names for Saving
    public String getSubjectNamesAsString() {
        if (enrolledSubjects.isEmpty()) return "[]"; // Handle empty case

        StringBuilder subjectList = new StringBuilder("[");
        for (int i = 0; i < enrolledSubjects.size(); i++) {
            subjectList.append(enrolledSubjects.get(i).getSubjectName());
            if (i < enrolledSubjects.size() - 1) {
                subjectList.append("; "); // Separate courses properly
            }
        }
        subjectList.append("]"); // Close brackets

        return subjectList.toString();
    }

    //Method for printing out all details about the user
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

    //Method to edit the password and the profile picture of the student
    public void editProfile(String newPassword, Image newProfilePicture) {
        setPassword(newPassword);
        this.profilePicture = newProfilePicture;
        System.out.println("Profile updated successfully!");
    }

    public static void addStudent(Student student) {
        students.add(student);
        saveAllStudents();  // Save all students instead of just appending one
        System.out.println("Student added successfully: " + student.getFullName());
    }


    public static void removeStudent(int studentID) {
        students.removeIf(student -> student.getStudentID() == studentID);
        saveAllStudents(); // Rewrite the file
        System.out.println("Student removed successfully.");
    }

    public static Student getStudent(int studentID) {
        for (Student student : students) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null;
    }

    //Method for printing out the courses the student is enrolled in
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

    //Method for viewing grades
    public void viewGrades() {
        System.out.println("=== Academic Performance ===");

        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
            return;
        }

        //Looping through each course
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

    public boolean checkDuplicateCourse(Course course) {

        for (Course c : enrolledCourses) {
            if (c.getCourseName().equalsIgnoreCase(course.getCourseName())) {
                return true;
            }
        }
        return false;
    }

    public static void printStudents() {
        System.out.println("=== Student List ===");
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        for (Student student : students) {
            System.out.println("ID: " + student.getStudentID());
            System.out.println("Name: " + student.getFullName());
            System.out.println("Email: " + student.getEmailAddress());
            System.out.println("Address: " + student.getAddress());
            System.out.println("Phone: " + student.getTelephone());
            System.out.println("Academic Level: " + student.getAcademicLevel());
            System.out.println("Thesis Title: " + student.getThesisTitle());
            System.out.println("Semester: " + student.getSemester());
            System.out.println("Progress: " + student.getProgress() + "%");
            System.out.println("Tuition Annual: $" + student.getTuitionAnnual());
            System.out.println("Tuition Balance: $" + student.getTuitionBalance());

            System.out.print("Enrolled Courses: ");
            if (student.getEnrolledCourses().isEmpty()) {
                System.out.println("None");
            } else {
                for (Course course : student.getEnrolledCourses()) {
                    System.out.println("  - " + course.getCourseName());
                }
            }

            System.out.print("Enrolled Subjects: ");
            if (student.getEnrolledSubjects().isEmpty()) {
                System.out.println("None");
            } else {
                for (Subject subject : student.getEnrolledSubjects()) {
                    System.out.println("  - " + subject.getSubjectName());
                }
            }
            System.out.println("-----------------------------------");
        }
    }


    public boolean checkDuplicateSubject(Subject subject) {

        for (Subject s : enrolledSubjects) {
            if (s.getSubjectName().equalsIgnoreCase(subject.getSubjectName())) {
                return true;
            }
        }
        return false;
    }
    public void updateEnrolledCourses(Course c) {
        if (!checkDuplicateCourse(c)) {  // Only add if not already enrolled
            this.enrolledCourses.add(c);
            System.out.println("Added course: " + c.getCourseName());
        } else {
            System.out.println("Course already enrolled: " + c.getCourseName());
        }
    }

    public void updateEnrolledSubjects(Subject s) {
        if (!checkDuplicateSubject(s)) { // Fix the logic
            this.enrolledSubjects.add(s);
            System.out.println("Added subject: " + s.getSubjectName());
        } else {
            System.out.println("Subject already enrolled: " + s.getSubjectName());
        }
    }



    //Print out Financial Information
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
    public List<Subject> getEnrolledSubjects() { return enrolledSubjects; }
    public String getAcademicLevel() { return academicLevel; }
    public String getThesisTitle() { return thesisTitle; }
    public int getProgress() { return progress; }
    public static List<Student> getStudentList(){return Student.students;}

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
    public void setEnrolledSubjects(List<Subject> enrolledSubjects) { this.enrolledSubjects = enrolledSubjects; }
    public void setAcademicLevel(String academicLevel) { this.academicLevel = academicLevel; }
    public void setThesisTitle(String thesisTitle) { this.thesisTitle = thesisTitle; }
    public void setProgress(int progress) { this.progress = progress; }

}
