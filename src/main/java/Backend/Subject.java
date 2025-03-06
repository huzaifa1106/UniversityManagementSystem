/**
 *  File: Subject.java
 *  Description: This class is for storing information regarding a subjeect for course offering
 *  such as subject names, and subject codes, this is helpful when we'd like to register a
 *  series of courses under a Subject.
 *  when implementing UI components
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 *  */



package Backend;

//Import Statements
import java.util.List;
import java.util.Scanner;

public class Subject {

    private String subjectName;
    private String subjectCode;

    // Constructor
    public Subject(String name, String code) {
        this.subjectName = name;
        this.subjectCode = code;
    }

    //Verifying if a duplicate exists
    public static boolean checkDuplicate(String subject, List<Subject> subjects) {

        for (Subject s : subjects) {
            if (s.getSubjectName().equalsIgnoreCase(subject)) {
                return true;
            }
        }
        return false;
    }

    //Adding a subject to a subject list
    public static void addCourse(List<Subject> subjects) {
        Scanner scanner = new Scanner(System.in);
        //Temp Subject Name & Code
        String subjectN;
        String subjectC;

        // Prompt user until valid inputs are provided
        while (true) {
            //Temp untill UI interface is designed
            System.out.print("Enter course name: ");
            subjectN = scanner.nextLine();

            //Checks if the courseName is empty as neither field can remain empty
            if (subjectN.isEmpty()) {
                System.out.println("Subject name is empty. Please enter Subject name again.");
                continue;
            }
            //Checks if the Course Exists
            if (checkDuplicate(subjectN, subjects)) {
                System.out.println("This course already exists! Please enter a different course.");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Enter course code: ");
            subjectC = scanner.nextLine();

            if (subjectC.isEmpty()) {
                System.out.println("Subject code cannot be empty. Please enter Subject Code again.");
                continue;
            }
            break;
        }

        // Add new course to the list
        subjects.add(new Subject(subjectN, subjectC));
        System.out.println("Course added successfully: " + subjectN + " (" + subjectC + ")");
    }

    //Getter Functions
    public String getSubjectName() {
        return this.subjectName;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    //Setter Functions
    public void setSubjectName(String name) {
        this.subjectName = name;
    }

    public void setSubjectCode(String code) {
        this.subjectCode = code;
    }

    //Method to display subject details
    public void display() {
        System.out.println("Subject Name: " + this.subjectName);
        System.out.println("Subject Code: " + this.subjectCode);
    }

}
