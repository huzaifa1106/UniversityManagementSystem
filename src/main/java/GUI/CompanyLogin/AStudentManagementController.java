/**
 *  File: AStudentManagementController.java
 *  Description: This controller manages student records for the admin panel,
 *  providing functionality to add, edit, delete, and view student information.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

import Backend.Student;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AStudentManagementController extends ASubjectManagementController {

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> colStudentID, colTuitionAnnual, colTuitionBalance, colProgress;

    @FXML
    private TableColumn<Student, String> colName, colEmail, colPhone, colAddress, colSemester, colAcademicLevel;

    @FXML
    private TextField studentNameField, studentIDField, studentEmailField, studentPhoneField,
            studentAddressField, studentSemesterField, studentLevelField;

    @FXML
    private Button addStudentButton, editStudentButton, deleteStudentButton, viewStudentProfileButton, manageEnrollmentButton;

    private Student selectedStudent = null;

    // Initializes table bindings and selection logic
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

        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedStudent = newVal;
            if (newVal != null) fillFormWithStudentData(newVal);
        });
    }

    // Loads all students into the table
    private void loadStudentsIntoTable() {
        studentTable.getItems().setAll(Student.getStudentList());
    }

    // Fills the input form with selected student data
    private void fillFormWithStudentData(Student student) {
        studentNameField.setText(student.getFullName());
        studentIDField.setText(String.valueOf(student.getStudentID()));
        studentEmailField.setText(student.getEmailAddress());
        studentPhoneField.setText(String.valueOf(student.getTelephone()));
        studentAddressField.setText(student.getAddress());
        studentSemesterField.setText(student.getSemester());
        studentLevelField.setText(student.getAcademicLevel());
    }

    // Adds a new student to the table
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
            loadStudentsIntoTable();
            clearFields();
            showAlert("Success", "Student added successfully!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Make sure Student ID and Phone are numbers.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Edits selected student data
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

    // Deletes the selected student from the list
    @FXML
    private void deleteStudent() {
        if (selectedStudent != null) {
            Student.removeStudent(selectedStudent.getStudentID());
            loadStudentsIntoTable();
            clearFields();
            selectedStudent = null;
            showAlert("Success", "Student deleted successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("No Selection", "Please select a student to delete.", Alert.AlertType.WARNING);
        }
    }

    // Shows selected student's profile (placeholder)
    @FXML
    private void viewStudentProfile() {
        if (selectedStudent != null) {
            showAlert("Student Selected", selectedStudent.getFullName() + "'s profile selected.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("No Selection", "Please select a student to view.", Alert.AlertType.WARNING);
        }
    }

    // Handles clicking the manage enrollments button (placeholder)
    @FXML
    private void manageEnrollments() {
        showAlert("Info", "Manage enrollments clicked.", Alert.AlertType.INFORMATION);
    }

    // Clears all input fields in the form
    private void clearFields() {
        studentNameField.clear();
        studentIDField.clear();
        studentEmailField.clear();
        studentPhoneField.clear();
        studentAddressField.clear();
        studentSemesterField.clear();
        studentLevelField.clear();
    }

    // 🔔 Show alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
