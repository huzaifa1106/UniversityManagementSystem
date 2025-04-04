/**
 *  File: AStudentManagementController.java
 *  Description: This controller manages student records for the admin panel,
 *  providing functionality to add, edit, delete, and view student information.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

import Backend.ReadExcelFile;
import Backend.Student;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AStudentManagementController extends ASubjectManagementController {

    @FXML private TableView<Student> studentTable;

    @FXML private TableColumn<Student, Integer> colStudentID, colTuitionAnnual, colTuitionBalance, colProgress;
    @FXML private TableColumn<Student, String> colName, colEmail, colPhone, colAddress, colSemester, colAcademicLevel;

    @FXML private TextField studentNameField, studentIDField, studentEmailField, studentPhoneField,
            studentAddressField, studentSemesterField, studentLevelField;

    @FXML private Button addStudentButton, editStudentButton, deleteStudentButton, viewStudentProfileButton, manageEnrollmentButton;

    private Student selectedStudent = null;

    // Setup column-cell bindings and row selection
    @FXML
    private void initialize() {
        colStudentID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStudentID()).asObject());
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmailAddress()));
        colPhone.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTelephone())));
        colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        colSemester.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSemester()));
        colAcademicLevel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAcademicLevel()));
        colTuitionAnnual.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTuitionAnnual()).asObject());
        colTuitionBalance.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTuitionBalance()).asObject());
        colProgress.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProgress()).asObject());

        loadStudentsIntoTable();

        // When a row is selected, populate form fields
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedStudent = newVal;
            if (newVal != null) fillFormWithStudentData(newVal);
        });
    }

    // Load students into the table view
    private void loadStudentsIntoTable() {
        studentTable.getItems().setAll(Student.getStudentList());
    }

    // Populate the form with the selected student's data
    private void fillFormWithStudentData(Student student) {
        studentNameField.setText(student.getFullName());
        studentIDField.setText(String.valueOf(student.getStudentID()));
        studentEmailField.setText(student.getEmailAddress());
        studentPhoneField.setText(String.valueOf(student.getTelephone()));
        studentAddressField.setText(student.getAddress());
        studentSemesterField.setText(student.getSemester());
        studentLevelField.setText(student.getAcademicLevel());
    }

    // Add a new student (uses fields from form)
    @FXML
    private void addStudent() {
        try {
            if (studentNameField.getText().isEmpty() || studentEmailField.getText().isEmpty() ||
                    studentPhoneField.getText().isEmpty() || studentAddressField.getText().isEmpty() ||
                    studentSemesterField.getText().isEmpty() || studentLevelField.getText().isEmpty()) {
                showAlert("Missing Fields", "Please fill in all required fields.", Alert.AlertType.WARNING);
                return;
            }

            int studentID = Integer.parseInt(studentIDField.getText());
            long phone = Long.parseLong(studentPhoneField.getText());

            if (Student.getStudent(studentID) != null) {
                showAlert("Duplicate Student ID", "Student with this ID already exists.", Alert.AlertType.ERROR);
                return;
            }

            Student newStudent = new Student(
                    studentNameField.getText(),
                    "password123",
                    null,
                    studentAddressField.getText(),
                    phone,
                    5000,
                    0,
                    studentEmailField.getText(),
                    0,
                    studentSemesterField.getText(),
                    studentLevelField.getText(),
                    ""
            );

            Student.addStudent(newStudent);
            ReadExcelFile.writeToExcel();

            loadStudentsIntoTable();
            clearFields();
            showAlert("Success", "Student added successfully!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Make sure Student ID and Phone are numbers.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Edit the selected student record
    @FXML
    private void editStudent() {
        if (selectedStudent != null) {
            try {
                selectedStudent.setFullName(studentNameField.getText());
                selectedStudent.setEmailAddress(studentEmailField.getText());
                long phone = Long.parseLong(studentPhoneField.getText());
                selectedStudent.setAddress(studentAddressField.getText());
                selectedStudent.setSemester(studentSemesterField.getText());
                selectedStudent.setAcademicLevel(studentLevelField.getText());

                ReadExcelFile.writeToExcel();
                loadStudentsIntoTable();
                clearFields();

                showAlert("Success", "Student updated successfully!", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Phone number must be numeric.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("No Selection", "Please select a student to edit.", Alert.AlertType.WARNING);
        }
    }

    // Remove the selected student
    @FXML
    private void deleteStudent() {
        if (selectedStudent != null) {
            Student.removeStudent(selectedStudent.getStudentID());
            ReadExcelFile.writeToExcel();
            loadStudentsIntoTable();
            clearFields();
            selectedStudent = null;
            showAlert("Success", "Student deleted successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("No Selection", "Please select a student to delete.", Alert.AlertType.WARNING);
        }
    }


    // Open student enrollment management screen
    @FXML
    private void manageEnrollments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/AStudentManageEnrollment.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Enrollments");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not load enrollment screen.").showAndWait();
        }
    }

    // Reset the form fields
    private void clearFields() {
        studentNameField.clear();
        studentIDField.clear();
        studentEmailField.clear();
        studentPhoneField.clear();
        studentAddressField.clear();
        studentSemesterField.clear();
        studentLevelField.clear();
    }

    // Reusable method to show alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
