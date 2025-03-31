package Backend;

// Import basic file handling and Excel functionality using Apache POI
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;       // Represents a row in Excel
import org.apache.poi.xssf.usermodel.XSSFSheet;     // Represents a sheet in Excel
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  // Represents the whole workbook (Excel file)

public class ReadExcelFile {

    // This is where your program starts
    public static void main(String[] args) {
        // Create a File object pointing to the Excel file (adjust path if needed)
        File xlsxFile = new File("UMS_Data.xlsx");

        // Try-with-resources to open the workbook once and pass it to methods
        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFile))) {
            readSubjectsSheet(workbook);
            readCoursesSheet(workbook);
            readStudentsSheet(workbook);
            readFacultiesSheet(workbook);
            readEventsSheet(workbook);
        } catch (IOException e) {
            System.out.println("Error opening workbook");
            e.printStackTrace();
        }
    }

    // Method to read from the "Subjects" sheet
    public static void readSubjectsSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Subjects");
        System.out.println("\n== Subjects ==");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;

            String code = getCellValue(row, 0);  // Subject Code
            String name = getCellValue(row, 1);  // Subject Name

            System.out.printf("%s - %s%n", code, name); // Clean output
        }
    }

    // Method to read from the Courses sheet
    public static void readCoursesSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Courses");
        System.out.println("\n== Courses ==");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;

            String name = getCellValue(row, 1);     // Course Name
            String section = getCellValue(row, 3);  // Section
            String teacher = getCellValue(row, 8);  // Teacher

            System.out.printf("%s | Section: %s | Teacher: %s%n", name, section, teacher);
        }
    }

    // Method to read from the Students sheet
    public static void readStudentsSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Students ");
        System.out.println("\n== Students ==");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;

            String id = getCellValue(row, 0);       // Student ID
            String name = getCellValue(row, 1);     // Student Name
            String email = getCellValue(row, 5);    // Email
            String subjects = getCellValue(row, 8); // Subjects Registered

            System.out.printf("%s | %s | Subjects: %s%n", name, email, subjects);
        }
    }

    // Method to read from the Faculties
    public static void readFacultiesSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Faculties ");
        System.out.println("\n== Faculties ==");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;

            String id = getCellValue(row, 0);        // Faculty ID
            String name = getCellValue(row, 1);      // Name
            String degree = getCellValue(row, 2);    // Degree
            String email = getCellValue(row, 4);     // Email

            System.out.printf("%s | %s | %s%n", name, degree, email);
        }
    }

    // Method to read from the Events
    public static void readEventsSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Events ");
        System.out.println("\n== Events ==");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;

            String eventName = getCellValue(row, 1);   // Event Name
            String date = getCellValue(row, 4);        // Date
            String attendees = getCellValue(row, 7);   // Registered Students

            System.out.printf("%s | Date: %s | Attendees: %s%n", eventName, date, attendees);
        }
    }

    // Helper method to safely get a string value from a cell
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
