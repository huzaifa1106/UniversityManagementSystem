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
        File xlsxFile = new File("E:/Excel/UMS_Data.xlsx");

        // Call each method to read data from individual sheets
        readSubjectsSheet(xlsxFile);
        readCoursesSheet(xlsxFile);
        readStudentsSheet(xlsxFile);
        readFacultiesSheet(xlsxFile);
        readEventsSheet(xlsxFile);
    }

    // Method to read from the "Subjects" sheet
    public static void readSubjectsSheet(File xlsxFile) {
        // Try-with-resources ensures the file and workbook are automatically closed
        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFile))) {
            // Grab the "Subjects" sheet by name
            XSSFSheet sheet = workbook.getSheet("Subjects");

            System.out.println("\n Subjects ");
            // Loop through all rows (skip header at row 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i); // Get the row
                String code = row.getCell(0).getStringCellValue();  // Subject Code
                String name = row.getCell(1).getStringCellValue();  // Subject Name

                // Print the row data
                System.out.println(code + " - " + name);
            }
        } catch (IOException e) {
            // Print error if anything goes wrong
            System.out.println("Something went wrong while reading 'Subjects'");
            e.printStackTrace();
        }
    }

    // Method to read from the Courses sheet
    public static void readCoursesSheet(File xlsxFile) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFile))) {
            XSSFSheet sheet = workbook.getSheet("Courses"); // Get the sheet

            System.out.println("\n Courses ");
            // Loop through each row (starting after header)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);

                // Get selected course data
                String name = row.getCell(1).getStringCellValue();     // Course Name
                String section = row.getCell(3).getStringCellValue();  // Section
                String teacher = row.getCell(8).getStringCellValue();  // Teacher

                // Print course info
                System.out.println(name + " | " + section + " | " + teacher);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while reading 'Courses'");
            e.printStackTrace();
        }
    }

    // Method to read from the Students sheet
    public static void readStudentsSheet(File xlsxFile) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFile))) {
            XSSFSheet sheet = workbook.getSheet("Students ");

            System.out.println("\n Students ");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);

                // Pull student info
                String id = row.getCell(0).getStringCellValue();       // Student ID
                String name = row.getCell(1).getStringCellValue();     // Student Name
                String email = row.getCell(5).getStringCellValue();    // Email
                String subjects = row.getCell(8).getStringCellValue(); // Subjects Registered

                // Print the student info
                System.out.println(name + " | " + email + "  Subjects: " + subjects);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while reading 'Students'");
            e.printStackTrace();
        }
    }

    // Method to read from the Faculties
    public static void readFacultiesSheet(File xlsxFile) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFile))) {
            XSSFSheet sheet = workbook.getSheet("Faculties ");

            System.out.println("\n== Faculties ==");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);

                // Grab faculty info
                String id = row.getCell(0).getStringCellValue();        // Faculty ID
                String name = row.getCell(1).getStringCellValue();      // Name
                String degree = row.getCell(2).getStringCellValue();    // Degree
                String email = row.getCell(4).getStringCellValue();     // Email

                // Print faculty info
                System.out.println(name  + degree  + email);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while reading 'Faculties'");
            e.printStackTrace();
        }
    }

    // Method to read from the Events
    public static void readEventsSheet(File xlsxFile) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFile))) {
            XSSFSheet sheet = workbook.getSheet("Events ");

            System.out.println("\n== Events ==");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);

                // Pull event details
                String eventName = row.getCell(1).getStringCellValue();   // Event Name
                String date = row.getCell(4).toString();                  // Date
                String attendees = row.getCell(7).getStringCellValue();   // Registered Students

                // Print event info
                System.out.println(eventName + " Date: " + date + "  Attendees: " + attendees);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while reading 'Events'");
            e.printStackTrace();
        }
    }
}
