package Backend;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.text.*;

/**
 * ReadExcelFile is responsible for reading and writing data to and from an Excel file.
 * It initializes static lists with the data of various entities such as Students, Courses, Subjects, Faculties, and Events.
 * Author: Huzaifa A. & Group
 * Date:April 2025
 */
public class ReadExcelFile {

    // Static lists to store data loaded from the Excel file
    public static ArrayList<Student> studentList = new ArrayList<>();
    public static ArrayList<Course> courseList = new ArrayList<>();
    public static ArrayList<Subject> subjectList = new ArrayList<>();
    public static ArrayList<Faculty> facultyList = new ArrayList<>();
    public static ArrayList<Event> eventList = new ArrayList<>();

    // Path to the Excel file
    public static String path = "src/UMS_Data.xlsx";

    /**
     * Main method which initiates the data loading process and performs a quick data read test.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Initialize data from the Excel file (if it exists) or create new data
        initializeData();

        // Output the sizes of the loaded lists
        System.out.println("Faculties loaded: " + facultyList.size());
        System.out.println("Events loaded: " + eventList.size());

        // Test and print the data to verify successful loading
        testDataRead();
    }

    /**
     * Reads the "Subjects" sheet from the Excel workbook and returns a list of Subject objects.
     * @param workbook The workbook containing the Excel file
     * @return A list of Subject objects populated from the "Subjects" sheet
     */
    public static ArrayList<Subject> readSubjectsSheet(XSSFWorkbook workbook) {
        ArrayList<Subject> subjects = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet("Subjects");

        // If the "Subjects" sheet doesn't exist, return an empty list
        if (sheet == null) return subjects;

        // Iterate over the rows in the "Subjects" sheet and extract the subject name and code
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                String name = getCellValue(row, 0).trim(); // Subject name
                String code = getCellValue(row, 1).trim(); // Subject code

                // Create a new Subject object and add it to the list
                Subject subject = new Subject(name, code);
                subjects.add(subject);

                // Print each subject loaded for debugging purposes
                System.out.println("Loaded Subject: [" + code + "] (" + name + ")");
            }
        }

        return subjects;
    }

    /**
     * Prints the full data read from the Excel file to the console for verification purposes.
     */
    public static void testDataRead() {
        System.out.println("\n===== FULL DATA FROM EXCEL =====");

        // Output all the subjects
        System.out.println("\n--- Subjects ---");
        for (Subject s : subjectList) {
            System.out.println("Subject Code: " + s.getSubjectCode() + ", Name: " + s.getSubjectName());
        }

        // Output all the courses
        System.out.println("\n--- Courses ---");
        for (Course c : courseList) {
            System.out.println("ID: " + c.getCourseID() + ", Name: " + c.getCourseName() +
                    ", Subject: " + c.getSubjectName() + ", Section: " + c.getSectionNumber() +
                    ", Instructor: " + c.getTeacherName() + ", Capacity: " + c.getCourseCapacity() +
                    ", Room: " + c.getLocation() + ", Schedule: " + c.getLectureDay() +
                    ", Start: " + c.getLectureStartTime() + ", End: " + c.getLectureEndTime() +
                    ", Exam: " + c.getFinalExamDate());
        }

        // Output all the students
        System.out.println("\n--- Students ---");
        for (Student s : studentList) {
            System.out.println("ID: " + s.getStudentID() + ", Name: " + s.getFullName() +
                    ", Email: " + s.getEmailAddress() + ", Phone: " + s.getTelephone() +
                    ", Address: " + s.getAddress() + ", Password: " + s.getPassword() +
                    ", Tuition Annual: $" + s.getTuitionAnnual() + ", Balance: $" + s.getTuitionBalance() +
                    ", Average: " + s.getAverage() + ", Semester: " + s.getSemester() +
                    ", Level: " + s.getAcademicLevel() + ", Thesis: " + s.getThesisTitle() +
                    ", Progress: " + s.getProgress());

            // Output the subjects and courses enrolled by the student
            System.out.println("Enrolled Subjects: " + s.getEnrolledSubjects());
            System.out.println("Enrolled Courses: " + s.getEnrolledCourses());
        }

        // Output all the faculties
        System.out.println("\n--- Faculties ---");
        for (Faculty f : facultyList) {
            System.out.println("ID: " + f.getFacultyID() + ", Name: " + f.getName() +
                    ", Degree: " + f.getDegree() + ", Specialty: " + f.getResearchInterest() +
                    ", Email: " + f.getEmail() + ", Office: " + f.getOfficeLocation() +
                    ", Courses: " + f.getCoursesOffered());
        }

        // Output all the events
        System.out.println("\n--- Events ---");
        for (Event e : eventList) {
            System.out.println("Code: " + e.getEventCode() + ", Name: " + e.getEventName() +
                    ", Description: " + e.getDescription() + ", Location: " + e.getLocation() +
                    ", Date: " + (e.getDateTime() != null ? e.getDateTime().toString() : "N/A") +
                    ", Capacity: " + e.getCapacity() + ", Fee: $" + e.getCost() +
                    ", Attendees: " + e.getRegisteredStudents());
        }

        System.out.println("\n===== END OF DATA TEST =====");
    }

    /**
     * Initializes the data by reading it from the Excel file if it exists, or writing default data if not.
     */
    public static void initializeData() {
        File file = new File("src/UMS_Data.xlsx");

        if (file.exists()) {
            try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("src/UMS_Data.xlsx"))) {
                // Read the data from the Excel file
                subjectList = readSubjectsSheet(workbook);
                for (Subject s : subjectList) {
                    Subject.addSubject(s); // Add each subject to the static list
                }

                courseList = readCoursesSheet(workbook);
                studentList = readStudentsSheet(workbook, subjectList, courseList);
                facultyList = readFacultiesSheet(workbook);
                eventList = readEventsSheet(workbook);

                // Set the static lists in their respective classes
                Course.setCourseList(courseList);
                Student.setStudentList(studentList);
                Faculty.setFacultyList(facultyList);
                Event.setEventList(eventList);

                System.out.println("Excel data loaded successfully.");
            } catch (IOException e) {
                System.err.println("Failed to load Excel file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Excel file not found. Writing default data.");
            writeToExcel(); // Write default data to the Excel file if the file doesn't exist
        }
    }

    /**
     * Reads the "Courses" sheet from the Excel file and returns a list of Course objects.
     * @param workbook The workbook containing the Excel file
     * @return A list of Course objects loaded from the "Courses" sheet
     */
    public static ArrayList<Course> readCoursesSheet(XSSFWorkbook workbook) {
        ArrayList<Course> courses = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet("Courses");

        if (sheet == null) return courses; // Return an empty list if the "Courses" sheet doesn't exist

        // Iterate over the rows of the "Courses" sheet to extract course details
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                String examDateString = getCellValue(row, 10);
                LocalDateTime finalExamDate = null;
                if (!examDateString.equalsIgnoreCase("N/A") && !examDateString.isBlank()) {
                    try {
                        finalExamDate = LocalDateTime.parse(examDateString);
                    } catch (Exception e) {
                        System.err.println("Invalid exam date format in row " + i + ": " + examDateString);
                    }
                }

                // Create a new Course object and add it to the list
                courses.add(new Course(
                        (int) Double.parseDouble(getCellValue(row, 0)),
                        getCellValue(row, 1),
                        getCellValue(row, 2),
                        (int) Double.parseDouble(getCellValue(row, 3)),
                        getCellValue(row, 4),
                        (int) Double.parseDouble(getCellValue(row, 5)),
                        getCellValue(row, 6),
                        getCellValue(row, 7),
                        (int) Double.parseDouble(getCellValue(row, 8)),
                        (int) Double.parseDouble(getCellValue(row, 9)),
                        finalExamDate
                ));
            }
        }
        return courses;
    }
    /**
     * Reads the "Students" sheet from the provided Excel workbook and returns a list of Student objects.
     * This method maps the relevant data from the sheet into Student, Subject, and Course objects.
     *
     * @param workbook The Excel workbook containing the data.
     * @param subjects The list of all available subjects to map codes to actual subjects.
     * @param courses  The list of all available courses to map course IDs to actual courses.
     * @return A list of Student objects populated with data from the Excel sheet.
     */
    public static ArrayList<Student> readStudentsSheet(XSSFWorkbook workbook, ArrayList<Subject> subjects, ArrayList<Course> courses) {
        ArrayList<Student> students = new ArrayList<>(); // List to store student data
        XSSFSheet sheet = workbook.getSheet("Students"); // Fetch the "Students" sheet from the workbook

        // If the sheet is not found, return an empty list
        if (sheet == null) return students;

        // Iterate over all rows in the "Students" sheet (starting from row 1, skipping header)
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue; // Skip if the row is null

            // Extract enrolled subjects from the row (comma-separated codes in column 13)
            String rawSubjectCodes = getCellValue(row, 13);
            ArrayList<Subject> enrolledSubjects = new ArrayList<>();

            // Split the subject codes by commas, find the corresponding Subject objects, and add them to the list
            for (String code : rawSubjectCodes.split(",\\s*")) {
                code = code.trim(); // Trim leading/trailing spaces from the code
                Subject found = Subject.findSubjectByCode(code); // Try to find subject by code
                if (found == null) {
                    found = Subject.findSubject(code); // Fallback to find by name if not found by code
                }
                if (found != null) {
                    enrolledSubjects.add(found); // Add the subject to the list
                }
            }

            // Extract enrolled courses from the row (comma-separated course IDs in column 14)
            ArrayList<Course> enrolledCourses = new ArrayList<>();
            for (String id : getCellValue(row, 14).split(",\\s*")) {
                if (!id.isEmpty()) {
                    try {
                        // Try to parse the course ID from the string
                        int courseId = (int) Double.parseDouble(id);
                        // Find the course by ID and add to enrolled courses
                        courses.stream()
                                .filter(c -> c.getCourseID() == courseId)
                                .findFirst()
                                .ifPresent(enrolledCourses::add);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid course ID format: " + id); // Log invalid course ID format
                    }
                }
            }

            // Create a new Student object from the data extracted from the row
            Student student = new Student(
                    (int) Double.parseDouble(getCellValue(row, 0)), // Student ID
                    getCellValue(row, 1), // Full Name
                    getCellValue(row, 2), // Password (though sensitive information)
                    null, // Profile photo (not provided in the sheet)
                    getCellValue(row, 3), // Address
                    (long) Double.parseDouble(getCellValue(row, 4)), // Telephone
                    (int) Double.parseDouble(getCellValue(row, 5)), // Annual Tuition
                    (int) Double.parseDouble(getCellValue(row, 6)), // Balance
                    getCellValue(row, 7), // Email Address
                    (int) Double.parseDouble(getCellValue(row, 8)), // Average grade
                    getCellValue(row, 9), // Semester
                    getCellValue(row, 10), // Academic Level
                    getCellValue(row, 11), // Thesis title (if applicable)
                    (int) Double.parseDouble(getCellValue(row, 12)), // Progress (if applicable)
                    enrolledCourses, // List of enrolled courses
                    enrolledSubjects, // List of enrolled subjects
                    false // Flag for enrollment status (initially false)
            );

            // Sync: Add this student to the enrolled courses' enrolled student lists
            for (Course course : enrolledCourses) {
                if (!course.getEnrolledStudents().contains(student)) {
                    course.getEnrolledStudents().add(student); // Add student to the course's enrolled students
                }
            }

            students.add(student); // Add the newly created student to the students list
        }

        return students; // Return the list of students
    }

    /**
     * Reads the "Faculties" sheet from the provided Excel workbook and returns a list of Faculty objects.
     * This method maps the relevant data from the sheet into Faculty objects.
     *
     * @param workbook The Excel workbook containing the data.
     * @return A list of Faculty objects populated with data from the Excel sheet.
     */
    public static ArrayList<Faculty> readFacultiesSheet(XSSFWorkbook workbook) {
        ArrayList<Faculty> faculties = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet("Faculties");

        // If the sheet is not found, return an empty list
        if (sheet == null) return faculties;

        // Iterate over all rows in the "Faculties" sheet (starting from row 1, skipping header)
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                // Create a new Faculty object and populate with data from the row
                faculties.add(new Faculty(
                        getCellValue(row, 0), // Faculty ID
                        getCellValue(row, 1), // Name
                        null, // Profile photo (not provided in the sheet)
                        getCellValue(row, 2), // Degree
                        getCellValue(row, 3), // Research Interest
                        List.of(getCellValue(row, 4).split(",\\s*")), // Courses taught (split by comma)
                        getCellValue(row, 5), // Email
                        getCellValue(row, 6) // Office location
                ));
            }
        }

        return faculties; // Return the list of faculties
    }

    /**
     * Reads the "Events" sheet from the provided Excel workbook and returns a list of Event objects.
     * This method maps the relevant data from the sheet into Event objects.
     *
     * @param workbook The Excel workbook containing the data.
     * @return A list of Event objects populated with data from the Excel sheet.
     */
    public static ArrayList<Event> readEventsSheet(XSSFWorkbook workbook) {
        ArrayList<Event> events = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet("Events");

        // If the sheet is not found, return an empty list
        if (sheet == null) return events;

        // Iterate over all rows in the "Events" sheet (starting from row 1, skipping header)
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                // Create a new Event object and populate with data from the row
                events.add(new Event(
                        getCellValue(row, 0), // Event Name
                        getCellValue(row, 1), // Event Code
                        getCellValue(row, 2), // Description
                        null, // Header image (not provided in the sheet)
                        getCellValue(row, 3), // Location
                        createDate(getCellValue(row, 4)), // Event date
                        (int) Double.parseDouble(getCellValue(row, 5)), // Capacity
                        Double.parseDouble(getCellValue(row, 6)), // Fee
                        new ArrayList<>(List.of(getCellValue(row, 7).split(",\\s*"))) // Registered students
                ));
            }
        }

        return events; // Return the list of events
    }

    public static void writeToExcel() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            List<Subject> subjects = Subject.getSubjectList();
            List<Course> courses = Course.getCourseList();
            List<Student> students = Student.getStudentList();
            List<Faculty> faculties = Faculty.getFacultyList();
            List<Event> events = Event.getEventList();

            System.out.println("📤 Writing to Excel file at: " + path);
            System.out.println("Subjects: " + subjects.size() + ", Courses: " + courses.size() + ", Students: " + students.size());
            System.out.println("Faculties: " + faculties.size() + ", Events: " + events.size());

            // === Subjects Sheet ===
            XSSFSheet subjectSheet = workbook.createSheet("Subjects");
            Row subjectHeader = subjectSheet.createRow(0);
            subjectHeader.createCell(0).setCellValue("Subject Code");
            subjectHeader.createCell(1).setCellValue("Subject Name");
            for (int i = 0; i < subjects.size(); i++) {
                Subject s = subjects.get(i);
                Row row = subjectSheet.createRow(i + 1);
                row.createCell(0).setCellValue(s.getSubjectCode());
                row.createCell(1).setCellValue(s.getSubjectName());
            }

            // === Courses Sheet ===
            XSSFSheet courseSheet = workbook.createSheet("Courses");
            String[] courseHeaders = {"Course ID", "Course Name", "Subject Code", "Section", "Instructor", "Capacity", "Room", "Schedule", "Start Time", "End Time", "Exam Date"};
            Row courseHeader = courseSheet.createRow(0);
            for (int i = 0; i < courseHeaders.length; i++) courseHeader.createCell(i).setCellValue(courseHeaders[i]);
            for (int i = 0; i < courses.size(); i++) {
                Course c = courses.get(i);
                Row row = courseSheet.createRow(i + 1);
                row.createCell(0).setCellValue(c.getCourseID());
                row.createCell(1).setCellValue(c.getCourseName());
                row.createCell(2).setCellValue(c.getSubjectName());
                row.createCell(3).setCellValue(c.getSectionNumber());
                row.createCell(4).setCellValue(c.getTeacherName());
                row.createCell(5).setCellValue(c.getCourseCapacity());
                row.createCell(6).setCellValue(c.getLocation());
                row.createCell(7).setCellValue(c.getLectureDay());
                row.createCell(8).setCellValue(c.getLectureStartTime());
                row.createCell(9).setCellValue(c.getLectureEndTime());
                row.createCell(10).setCellValue(
                        c.getFinalExamDate() != null ? c.getFinalExamDate().toString() : "N/A"
                );

            }

            // === Students Sheet ===
            XSSFSheet studentSheet = workbook.createSheet("Students");
            String[] studentHeaders = {"ID", "Full Name", "Password", "Address", "Phone", "Annual Tuition", "Balance", "Email", "Average", "Semester", "Level", "Thesis", "Progress", "Subjects", "Courses"};
            Row studentHeader = studentSheet.createRow(0);
            for (int i = 0; i < studentHeaders.length; i++) studentHeader.createCell(i).setCellValue(studentHeaders[i]);
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                Row row = studentSheet.createRow(i + 1);
                row.createCell(0).setCellValue(s.getStudentID());
                row.createCell(1).setCellValue(s.getFullName());
                row.createCell(2).setCellValue(s.getPassword());
                row.createCell(3).setCellValue(s.getAddress());
                row.createCell(4).setCellValue(s.getTelephone());
                row.createCell(5).setCellValue(s.getTuitionAnnual());
                row.createCell(6).setCellValue(s.getTuitionBalance());
                row.createCell(7).setCellValue(s.getEmailAddress());
                row.createCell(8).setCellValue(s.getAverage());
                row.createCell(9).setCellValue(s.getSemester());
                row.createCell(10).setCellValue(s.getAcademicLevel());
                row.createCell(11).setCellValue(s.getThesisTitle());
                row.createCell(12).setCellValue(s.getProgress());
                row.createCell(13).setCellValue(String.join(", ", s.getEnrolledSubjects().stream().map(Subject::getSubjectCode).toList()));
                row.createCell(14).setCellValue(String.join(", ", s.getEnrolledCourses().stream().map(c -> String.valueOf(c.getCourseID())).toList()));
            }

            // === Faculties Sheet ===
            XSSFSheet facultySheet = workbook.createSheet("Faculties");
            String[] facultyHeaders = {"ID", "Name", "Degree", "Specialty", "Courses Taught", "Email", "Office"};
            Row facultyHeader = facultySheet.createRow(0);
            for (int i = 0; i < facultyHeaders.length; i++) facultyHeader.createCell(i).setCellValue(facultyHeaders[i]);
            for (int i = 0; i < faculties.size(); i++) {
                Faculty f = faculties.get(i);
                Row row = facultySheet.createRow(i + 1);
                row.createCell(0).setCellValue(f.getFacultyID());
                row.createCell(1).setCellValue(f.getName());
                row.createCell(2).setCellValue(f.getDegree());
                row.createCell(3).setCellValue(f.getResearchInterest());
                row.createCell(4).setCellValue(String.join(", ", f.getCoursesOffered()));
                row.createCell(5).setCellValue(f.getEmail());
                row.createCell(6).setCellValue(f.getOfficeLocation());
            }

            // === Events Sheet ===
            XSSFSheet eventSheet = workbook.createSheet("Events");
            String[] eventHeaders = {"Title", "Code", "Description", "Location", "Date", "Capacity", "Fee", "Attendees"};
            Row eventHeader = eventSheet.createRow(0);
            for (int i = 0; i < eventHeaders.length; i++) eventHeader.createCell(i).setCellValue(eventHeaders[i]);
            for (int i = 0; i < events.size(); i++) {
                Event e = events.get(i);
                Row row = eventSheet.createRow(i + 1);
                row.createCell(0).setCellValue(e.getEventName());
                row.createCell(1).setCellValue(e.getEventCode());
                row.createCell(2).setCellValue(e.getDescription());
                row.createCell(3).setCellValue(e.getLocation());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//Ensuring in readable/parsable format
                row.createCell(4).setCellValue(e.getDateTime() != null ? sdf.format(e.getDateTime()) : "N/A");
                row.createCell(5).setCellValue(e.getCapacity());
                row.createCell(6).setCellValue(e.getCost());
                row.createCell(7).setCellValue(String.join(", ", e.getRegisteredStudents()));
            }

            // === Save to File ===
            try (FileOutputStream out = new FileOutputStream(path)) {
                workbook.write(out);
                System.out.println("✅ Excel file written successfully to " + path);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static String getCellValue(XSSFRow row, int cellIndex) {
        try {
            if (row.getCell(cellIndex) == null) return "";
            Cell cell = row.getCell(cellIndex);
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue().trim();
                case NUMERIC -> String.valueOf(cell.getNumericCellValue());
                case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                default -> cell.toString().trim();
            };
        } catch (Exception e) {
            return "";
        }
    }

    private static Date createDate(String input) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(input);
        } catch (Exception e) {
            return new Date();
        }
    }

}