package Backend;

// Import basic file handling and Excel functionality using Apache POI
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner; // For getting input from the user

import org.apache.poi.xssf.usermodel.XSSFRow;       // Represents a row in Excel
import org.apache.poi.xssf.usermodel.XSSFSheet;     // Represents a sheet in Excel
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  // Represents the whole workbook (Excel file)

public class ReadExcelFile {

    // This is where your program startss
    public static void main(String[] args) {
        // Create a File object pointing to the Excel file (adjust path if needed))
        File xlsxFile = new File("UMS_Data.xlsx");

        // Try-with-resources to open the workbook once and pass it to methods
        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFile));
             Scanner scanner = new Scanner(System.in)) { // Scanner for user input

            // Reading all existing sheets
            readSubjectsSheet(workbook);
            readCoursesSheet(workbook);
            readStudentsSheet(workbook);
            readFacultiesSheet(workbook);
            readEventsSheet(workbook);

            // Ask the user if they want to write to a sheet
            System.out.println("\nDo you want to add new data?");
            System.out.println("1. Subject");
            System.out.println("2. Course");
            System.out.println("3. Student");
            System.out.println("4. Faculty");
            System.out.println("5. Event");
            System.out.print("Enter your choice (1-5): ");
            int choice = scanner.nextInt(); // Read number
            scanner.nextLine(); // Clear newline

            // Call appropriate method based on input
            switch (choice) {
                case 1 -> addSubject(workbook, scanner);
                case 2 -> addCourse(workbook, scanner);
                case 3 -> addStudent(workbook, scanner);
                case 4 -> addFaculty(workbook, scanner);
                case 5 -> addEvent(workbook, scanner);
                default -> System.out.println("Invalid choice.");
            }

            // Save the workbook to file after making changes
            try (FileOutputStream out = new FileOutputStream(xlsxFile)) {
                workbook.write(out); // Write updated data back to the file
                System.out.println(" Data successfully written to file.");
            }

        } catch (IOException e) {
            // Handle file reading/writing errors
            System.out.println("Error opening workbook");
            e.printStackTrace();
        }
    }

    // Method to read from the "Subjects" sheet
    public static void readSubjectsSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Subjects"); // Get the "Subjects" sheet
        System.out.println("\n== Subjects ==");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Loop through rows (skip header row)
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue; // Skip empty rows

            String code = getCellValue(row, 0);  // Subject Code
            String name = getCellValue(row, 1);  // Subject Name

            System.out.printf("%s - %s%n", code, name); // Clean output
        }
    }

    // Method to read from the Courses sheet
    public static void readCoursesSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Courses"); // Get the "Courses" sheet
        System.out.println("\n== Courses ==");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Loop through rows
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue; // Skip empty rows

            String name = getCellValue(row, 1);     // Course Name
            String section = getCellValue(row, 3);  // Section
            String teacher = getCellValue(row, 8);  // Teacher

            System.out.printf("%s | Section: %s | Teacher: %s%n", name, section, teacher); // Output nicely
        }
    }

    // Method to read from the Students sheet
    public static void readStudentsSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Students "); // Watch the space!
        System.out.println("\n== Students ==");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Loop through rows
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue; // Skip empty

            String id = getCellValue(row, 0);       // Student ID
            String name = getCellValue(row, 1);     // Student Name
            String email = getCellValue(row, 5);    // Email
            String subjects = getCellValue(row, 8); // Subjects Registered

            System.out.printf("%s | %s | Subjects: %s%n", name, email, subjects); // Output nicely
        }
    }

    // Method to read from the Faculties
    public static void readFacultiesSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Faculties "); // Note the space!
        System.out.println("\n== Faculties ==");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Loop through rows
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue; // Skip empty

            String id = getCellValue(row, 0);        // Faculty ID
            String name = getCellValue(row, 1);      // Name
            String degree = getCellValue(row, 2);    // Degree
            String email = getCellValue(row, 4);     // Email

            System.out.printf("%s | %s | %s%n", name, degree, email); // Output nicely
        }
    }

    // Method to read from the Events
    public static void readEventsSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Events "); // Again, space is intentional
        System.out.println("\n== Events ==");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Loop rows
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue; // Skip empty

            String eventName = getCellValue(row, 1);   // Event Name
            String date = getCellValue(row, 4);        // Date
            String attendees = getCellValue(row, 7);   // Registered Students

            System.out.printf("%s | Date: %s | Attendees: %s%n", eventName, date, attendees); // Output nicely
        }
    }


    // Add new subject via user input
    public static void addSubject(XSSFWorkbook workbook, Scanner scanner) {
        XSSFSheet sheet = workbook.getSheet("Subjects"); // Get the correct sheet
        int rowNum = sheet.getLastRowNum() + 1; // Append to the bottom
        XSSFRow row = sheet.createRow(rowNum); // Create new row

        // Get data from user
        System.out.print("Enter Subject Code: ");
        String code = scanner.nextLine();

        System.out.print("Enter Subject Name: ");
        String name = scanner.nextLine();

        // Write values to cells
        row.createCell(0).setCellValue(code);
        row.createCell(1).setCellValue(name);
    }

    // Add new course
    public static void addCourse(XSSFWorkbook workbook, Scanner scanner) {
        XSSFSheet sheet = workbook.getSheet("Courses");
        int rowNum = sheet.getLastRowNum() + 1;
        XSSFRow row = sheet.createRow(rowNum);

        // Get input
        System.out.print("Enter Course Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Section: ");
        String section = scanner.nextLine();

        System.out.print("Enter Teacher Name: ");
        String teacher = scanner.nextLine();

        // Write to sheet
        row.createCell(1).setCellValue(name);
        row.createCell(3).setCellValue(section);
        row.createCell(8).setCellValue(teacher);
    }

    // Add new student
    public static void addStudent(XSSFWorkbook workbook, Scanner scanner) {
        XSSFSheet sheet = workbook.getSheet("Students ");
        int rowNum = sheet.getLastRowNum() + 1;
        XSSFRow row = sheet.createRow(rowNum);

        // Get student data
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Subjects (comma-separated): ");
        String subjects = scanner.nextLine();

        // Write data
        row.createCell(0).setCellValue(id);
        row.createCell(1).setCellValue(name);
        row.createCell(5).setCellValue(email);
        row.createCell(8).setCellValue(subjects);
    }

    // Add new faculty
    public static void addFaculty(XSSFWorkbook workbook, Scanner scanner) {
        XSSFSheet sheet = workbook.getSheet("Faculties ");
        int rowNum = sheet.getLastRowNum() + 1;
        XSSFRow row = sheet.createRow(rowNum);

        // Get faculty info
        System.out.print("Enter Faculty ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Faculty Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Degree: ");
        String degree = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        // Write to sheet
        row.createCell(0).setCellValue(id);
        row.createCell(1).setCellValue(name);
        row.createCell(2).setCellValue(degree);
        row.createCell(4).setCellValue(email);
    }

    // Add new event
    public static void addEvent(XSSFWorkbook workbook, Scanner scanner) {
        XSSFSheet sheet = workbook.getSheet("Events ");
        int rowNum = sheet.getLastRowNum() + 1;
        XSSFRow row = sheet.createRow(rowNum);

        // Event info
        System.out.print("Enter Event Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter Attendees (comma-separated IDs): ");
        String attendees = scanner.nextLine();

        // Fill cells
        row.createCell(1).setCellValue(name);
        row.createCell(4).setCellValue(date);
        row.createCell(7).setCellValue(attendees);
    }

    // Helper method to safely get a string value from a celll
    private static String getCellValue(XSSFRow row, int cellIndex) {
        try {
            // Check if the requested cell is null (missing or empty)
            if (row.getCell(cellIndex) == null) return "";

            // Convert the cell value to string and return it
            return row.getCell(cellIndex).toString();
        } catch (Exception e) {
            // In case of error (e.g., format mismatch), return empty string
            return "";
        }
    }
}
