package Backend;
import java.util.List;
import java.util.Scanner;

public class Subject {

    private String subjectName;
    private String subjectCode;

    public Subject(String name, String code) {
        this.subjectName = name;
        this.subjectCode = code;
    }

    public static boolean checkDuplicate(String subject, List<Subject> subjects) {

        for (Subject s : subjects) {
            if (s.getSubjectName().equalsIgnoreCase(subject)) {
                return true;
            }
        }
        return false;
    }

    public static void addCourse(List<Subject> subjects) {
        Scanner scanner = new Scanner(System.in);
        String subjectN;
        String subjectC;

        // Prompt user until valid inputs are provided
        while (true) {
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

    public String getSubjectName() {
        return this.subjectName;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectName(String name) {
        this.subjectName = name;
    }

    public void setSubjectCode(String code) {
        this.subjectCode = code;
    }

    public void display() {
        System.out.println("Subject Name: " + this.subjectName);
        System.out.println("Subject Code: " + this.subjectCode);
    }

}
