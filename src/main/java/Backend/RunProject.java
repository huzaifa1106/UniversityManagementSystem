package Backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RunProject {
    public static void main(String[] args) {

        // Generate subjects and courses first
        List<Subject> subjects = generateSubjectsAndCourses();

        // Generate students ONLY IF they are not already loaded
        if (Student.getStudentList().isEmpty()) {
            List<Student> students = generateStudents();
        } else {
            Student.loadStudents();
        }

        // Assign students to courses
        assignStudentToCourse(subjects, Student.getStudentList());

        // Display student data
        Student.printStudents();
    }

    public static void assignStudentToCourse(List<Subject> subjects, List<Student> students) {
        Random rand = new Random();

        for (Student student : students) {
            int coursesAssigned = 0;
            List<Course> assignedCourses = new ArrayList<>();

            while (coursesAssigned < 2) {
                Subject randomSubject = subjects.get(rand.nextInt(subjects.size()));
                List<Course> courses = randomSubject.getCourses();

                if (!courses.isEmpty()) {
                    Course randomCourse = courses.get(rand.nextInt(courses.size()));

                    if (!student.checkDuplicateCourse(randomCourse) && randomCourse.enrollStudent(student)) {
                        student.updateEnrolledCourses(randomCourse);
                        assignedCourses.add(randomCourse);
                        System.out.println(student.getFullName() + " enrolled in " + randomCourse.getCourseName());
                        coursesAssigned++;
                    }
                }

                // Prevent infinite loops
                if (assignedCourses.size() >= subjects.size()) {
                    break;
                }
            }
        }

        // Save student enrollments
        Student.saveAllStudents();
    }

    public static List<Student> generateStudents() {
        List<Student> students = new ArrayList<>();
        Random rand = new Random();

        String[] names = {"John Smith", "Jane Doe", "Alice Brown", "Bob Williams", "Charlie Davis",
                "Emma Johnson", "Michael Wilson", "Sophia Lee", "Daniel Martinez", "Olivia Garcia"};
        String[] addresses = {"123 Main St", "456 Oak Ave", "789 Pine Rd", "321 Maple Dr", "654 Elm St"};
        String[] semesters = {"Fall 2024", "Spring 2025", "Summer 2025"};
        String[] academicLevels = {"Undergraduate", "Graduate"};

        for (int i = 0; i < 25; i++) {
            String fullName = names[rand.nextInt(names.length)];
            String password = "pass" + (rand.nextInt(9000) + 1000);
            String address = addresses[rand.nextInt(addresses.length)];
            int telephone = rand.nextInt(900000000) + 100000000;
            int tuitionAnnual = rand.nextBoolean() ? 5000 : 4000;
            int tuitionBalance = rand.nextInt(tuitionAnnual);
            String emailAddress = "student" + (i + 1) + "@university.com";
            int average = rand.nextInt(101);
            String semester = semesters[rand.nextInt(semesters.length)];
            String academicLevel = academicLevels[rand.nextInt(academicLevels.length)];
            String thesisTitle = academicLevel.equals("Graduate") ? "Graduate Research" : "N/A";

            Student student = new Student(fullName, password, null, address, telephone, tuitionAnnual, tuitionBalance, emailAddress, average, semester, academicLevel, thesisTitle);

            students.add(student);
        }

        // Only call addStudent() once after all students are created
        for (Student student : students) {
            Student.addStudent(student);
        }

        return students;
    }

    public static List<Subject> generateSubjectsAndCourses() {
        List<Subject> subjects = new ArrayList<>();

        Subject cs = new Subject("Computer Science", "CS101");
        Subject math = new Subject("Mathematics", "MATH101");
        Subject physics = new Subject("Physics", "PHYS101");
        Subject chemistry = new Subject("Chemistry", "CHEM101");
        Subject economics = new Subject("Economics", "ECON101");

        cs.addCourse(new Course(1, "Intro to Programming", 1, "Dr. Smith", 30, "Room 101", "Monday", 900, 1030, LocalDateTime.of(2025, 5, 10, 9, 0), -1));
        cs.addCourse(new Course(2, "Data Structures", 1, "Dr. Lee", 35, "Room 102", "Wednesday", 1000, 1130, LocalDateTime.of(2025, 5, 12, 10, 0), -1));
        cs.addCourse(new Course(3, "Algorithms", 1, "Dr. Brown", 40, "Room 103", "Friday", 1100, 1230, LocalDateTime.of(2025, 5, 15, 11, 0), -1));

        math.addCourse(new Course(4, "Calculus I", 1, "Prof. Jones", 40, "Room 201", "Tuesday", 800, 930, LocalDateTime.of(2025, 5, 18, 8, 0), -1));
        math.addCourse(new Course(5, "Linear Algebra", 1, "Prof. Green", 35, "Room 202", "Thursday", 1000, 1130, LocalDateTime.of(2025, 5, 20, 10, 0), -1));
        math.addCourse(new Course(6, "Statistics", 1, "Prof. White", 30, "Room 203", "Monday", 1200, 1330, LocalDateTime.of(2025, 5, 22, 12, 0), -1));

        physics.addCourse(new Course(7, "Classical Mechanics", 1, "Dr. Maxwell", 30, "Room 301", "Wednesday", 1300, 1430, LocalDateTime.of(2025, 5, 25, 13, 0), -1));
        physics.addCourse(new Course(8, "Electromagnetism", 1, "Dr. Faraday", 35, "Room 302", "Friday", 900, 1030, LocalDateTime.of(2025, 5, 27, 9, 0), -1));
        physics.addCourse(new Course(9, "Quantum Physics", 1, "Dr. Bohr", 40, "Room 303", "Tuesday", 1100, 1230, LocalDateTime.of(2025, 5, 29, 11, 0), -1));

        chemistry.addCourse(new Course(10, "Organic Chemistry", 1, "Dr. Curie", 30, "Room 401", "Thursday", 1400, 1530, LocalDateTime.of(2025, 6, 2, 14, 0), -1));
        chemistry.addCourse(new Course(11, "Inorganic Chemistry", 1, "Dr. Lavoisier", 35, "Room 402", "Monday", 800, 930, LocalDateTime.of(2025, 6, 4, 8, 0), -1));
        chemistry.addCourse(new Course(12, "Biochemistry", 1, "Dr. Pasteur", 40, "Room 403", "Wednesday", 1200, 1330, LocalDateTime.of(2025, 6, 6, 12, 0), -1));

        economics.addCourse(new Course(13, "Microeconomics", 1, "Prof. Keynes", 30, "Room 501", "Tuesday", 900, 1030, LocalDateTime.of(2025, 6, 9, 9, 0), -1));
        economics.addCourse(new Course(14, "Macroeconomics", 1, "Prof. Smith", 35, "Room 502", "Thursday", 1100, 1230, LocalDateTime.of(2025, 6, 11, 11, 0), -1));

        subjects.add(cs);
        subjects.add(math);
        subjects.add(physics);
        subjects.add(chemistry);
        subjects.add(economics);

        return subjects;
    }
}
