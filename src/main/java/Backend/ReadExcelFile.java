package Backend;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.text.*;


public class ReadExcelFile {

    public static ArrayList<Student> studentList = new ArrayList<>();
    public static ArrayList<Course> courseList = new ArrayList<>();
    public static ArrayList<Subject> subjectList = new ArrayList<>();
    public static ArrayList<Faculty> facultyList = new ArrayList<>();
    public static ArrayList<Event> eventList = new ArrayList<>();

    public static String path = "src/UMS_Data.xlsx";





    public static void main(String[] args) {


            initializeData(); // <-- Handles writing if the file doesn't exist

            System.out.println("📊 Faculties loaded: " + facultyList.size());
            System.out.println("📊 Events loaded: " + eventList.size());

            testDataRead();

    }

    public static ArrayList<Subject> readSubjectsSheet(XSSFWorkbook workbook) {
        ArrayList<Subject> subjects = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet("Subjects");
        if (sheet == null) return subjects;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null)
                subjects.add(new Subject(getCellValue(row, 0), getCellValue(row, 1)));
        }
        return subjects;
    }public static void testDataRead() {
        System.out.println("\n===== ✅ FULL DATA FROM EXCEL =====");

        // 📘 Subjects
        System.out.println("\n--- 📘 Subjects ---");
        for (Subject s : subjectList) {
            System.out.println("Subject Code: " + s.getSubjectCode() + ", Name: " + s.getSubjectName());
        }

        // 📕 Courses
        System.out.println("\n--- 📕 Courses ---");
        for (Course c : courseList) {
            System.out.println("ID: " + c.getCourseID() +
                    ", Name: " + c.getCourseName() +
                    ", Subject: " + c.getSubjectName() +
                    ", Section: " + c.getSectionNumber() +
                    ", Instructor: " + c.getTeacherName() +
                    ", Capacity: " + c.getCourseCapacity() +
                    ", Room: " + c.getLocation() +
                    ", Schedule: " + c.getLectureDay() +
                    ", Start: " + c.getLectureStartTime() +
                    ", End: " + c.getLectureEndTime() +
                    ", Exam: " + c.getFinalExamDate());
        }

        // 📗 Students
        System.out.println("\n--- 📗 Students ---");
        for (Student s : studentList) {
            System.out.println("ID: " + s.getStudentID() +
                    ", Name: " + s.getFullName() +
                    ", Email: " + s.getEmailAddress() +
                    ", Phone: " + s.getTelephone() +
                    ", Address: " + s.getAddress() +
                    ", Password: " + s.getPassword() +
                    ", Tuition Annual: $" + s.getTuitionAnnual() +
                    ", Balance: $" + s.getTuitionBalance() +
                    ", Average: " + s.getAverage() +
                    ", Semester: " + s.getSemester() +
                    ", Level: " + s.getAcademicLevel() +
                    ", Thesis: " + s.getThesisTitle() +
                    ", Progress: " + s.getProgress());

            System.out.println("   Enrolled Subjects: " +
                    s.getEnrolledSubjects().stream().map(Subject::getSubjectCode).toList());

            System.out.println("   Enrolled Courses: " +
                    s.getEnrolledCourses().stream().map(Course::getCourseID).toList());
        }

        // 📙 Faculties
        System.out.println("\n--- 📙 Faculties ---");
        for (Faculty f : facultyList) {
            System.out.println("ID: " + f.getFacultyID() +
                    ", Name: " + f.getName() +
                    ", Degree: " + f.getDegree() +
                    ", Specialty: " + f.getResearchInterest() +
                    ", Email: " + f.getEmail() +
                    ", Office: " + f.getOfficeLocation() +
                    ", Courses: " + f.getCoursesOffered());
        }

        // 📓 Events
        System.out.println("\n--- 📓 Events ---");
        for (Event e : eventList) {
            System.out.println("Code: " + e.getEventCode() +
                    ", Name: " + e.getEventName() +
                    ", Description: " + e.getDescription() +
                    ", Location: " + e.getLocation() +
                    ", Date: " + (e.getDateTime() != null ? e.getDateTime().toString() : "N/A") +
                    ", Capacity: " + e.getCapacity() +
                    ", Fee: $" + e.getCost() +
                    ", Attendees: " + e.getRegisteredStudents());
        }

        System.out.println("\n===== ✅ END OF DATA TEST =====\n");
    }

    public static void initializeData() {


        File file = new File("src/UMS_Data.xlsx");
        if (file.exists()) {
            try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("src/UMS_Data.xlsx"))) {
                subjectList = readSubjectsSheet(workbook);
                courseList = readCoursesSheet(workbook);
                studentList = readStudentsSheet(workbook, subjectList, courseList);
                facultyList = readFacultiesSheet(workbook);
                eventList = readEventsSheet(workbook);

                Subject.setSubjectList(subjectList);
                Course.setCourseList(courseList);
                Student.setStudentList(studentList);
                Faculty.setFacultyList(facultyList);
                Event.setEventList(eventList);


                System.out.println("📥 Excel data loaded successfully.");
            } catch (IOException e) {
                System.err.println("❌ Failed to load Excel file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("📄 Excel file not found. Writing default data.");
            writeToExcel();
        }
    }
    public static ArrayList<Course> readCoursesSheet(XSSFWorkbook workbook) {
        ArrayList<Course> courses = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet("Courses");
        if (sheet == null) return courses;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                String examDateString = getCellValue(row, 10);
                LocalDateTime finalExamDate = null;
                if (!examDateString.equalsIgnoreCase("N/A") && !examDateString.isBlank()) {
                    try {
                        finalExamDate = LocalDateTime.parse(examDateString);
                    } catch (Exception e) {
                        System.err.println("⚠️ Invalid exam date format in row " + i + ": " + examDateString);
                    }
                }

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

    public static ArrayList<Student> readStudentsSheet(XSSFWorkbook workbook, ArrayList<Subject> subjects, ArrayList<Course> courses) {
        ArrayList<Student> students = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet("Students");
        if (sheet == null) return students;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;

            String rawSubjectCodes = getCellValue(row, 13);
            System.out.println("\n🔍 Reading Row " + i + " | Raw Subject Codes: " + rawSubjectCodes);

            ArrayList<Subject> enrolledSubjects = new ArrayList<>();
            for (String code : rawSubjectCodes.split(",\\s*")) {
                code = code.trim();
                Subject found = Subject.findSubjectByCode(code);
                if (found != null) {
                    enrolledSubjects.add(found);
                    System.out.println(" ✅ Matched subject: " + code);
                } else {
                    System.out.println(" ⚠️ Subject not found: " + code);
                }
            }

            ArrayList<Course> enrolledCourses = new ArrayList<>();
            for (String id : getCellValue(row, 14).split(",\\s*")) {
                if (!id.isEmpty()) {
                    try {
                        int courseId = (int) Double.parseDouble(id);
                        courses.stream()
                                .filter(c -> c.getCourseID() == courseId)
                                .findFirst()
                                .ifPresent(enrolledCourses::add);
                    } catch (NumberFormatException e) {
                        System.err.println("⚠️ Invalid course ID format: " + id);
                    }
                }
            }

            students.add(new Student(
                    (int) Double.parseDouble(getCellValue(row, 0)),
                    getCellValue(row, 1),
                    getCellValue(row, 2),
                    null,
                    getCellValue(row, 3),
                    (long) Double.parseDouble(getCellValue(row, 4)),
                    (int) Double.parseDouble(getCellValue(row, 5)),
                    (int) Double.parseDouble(getCellValue(row, 6)),
                    getCellValue(row, 7),
                    (int) Double.parseDouble(getCellValue(row, 8)),
                    getCellValue(row, 9),
                    getCellValue(row, 10),
                    getCellValue(row, 11),
                    (int) Double.parseDouble(getCellValue(row, 12)),
                    enrolledCourses,
                    enrolledSubjects,
                    false // <--- don't register to static list again
            ));

        }

        return students;
    }



    public static ArrayList<Faculty> readFacultiesSheet(XSSFWorkbook workbook) {
        ArrayList<Faculty> faculties = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet("Faculties");
        if (sheet == null) return faculties;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null)
                faculties.add(new Faculty(
                        getCellValue(row, 0),
                        getCellValue(row, 1),
                        null,
                        getCellValue(row, 2),
                        getCellValue(row, 3),
                        List.of(getCellValue(row, 4).split(",\\s*")),
                        getCellValue(row, 5),
                        getCellValue(row, 6)
                ));
        }
        return faculties;
    }

    public static ArrayList<Event> readEventsSheet(XSSFWorkbook workbook) {
        ArrayList<Event> events = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheet("Events");
        if (sheet == null) return events;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                events.add(new Event(
                        getCellValue(row, 0),
                        getCellValue(row, 1),
                        getCellValue(row, 2),
                        null,
                        getCellValue(row, 3),
                        createDate(getCellValue(row, 4)),
                        (int) Double.parseDouble(getCellValue(row, 5)),
                        Double.parseDouble(getCellValue(row, 6)),
                        new ArrayList<>(List.of(getCellValue(row, 7).split(",\\s*")))
                ));
            }
        }
        return events;
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
                row.createCell(4).setCellValue(e.getDateTime() != null ? e.getDateTime().toString() : "N/A");
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
            return new Date(); // fallback
        }
    }

}