/**
 *  File: ASubjectManagementController.java
 *  Description: This controller handles admin-side subject management functionality — adding,
 *  editing, deleting, and viewing academic subjects offered at the institution.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

import Backend.ReadExcelFile;
import Backend.Subject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ASubjectManagementController {

    @FXML private TableView<Subject> subjectTable;
    @FXML private TextField subjectNameField, subjectCodeField;
    @FXML private Button addButton, editButton, deleteButton, viewButton;

    @FXML private TableColumn<Subject, String> colSubjectCode;
    @FXML private TableColumn<Subject, String> colSubjectName;

    private Subject selectedSubject = null;

    // Initializes the table and selection logic
    @FXML
    private void initialize() {
        colSubjectCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectCode()));
        colSubjectName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectName()));

        loadSubjectsIntoTable();

        subjectTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedSubject = newVal;
            if (newVal != null) fillFormWithSubjectData(newVal);
        });
    }

    // Loads all subjects into the table
    private void loadSubjectsIntoTable() {
        subjectTable.setItems(FXCollections.observableArrayList(Subject.getSubjectList()));
    }

    // Fills the form with selected subject data
    private void fillFormWithSubjectData(Subject subject) {
        subjectNameField.setText(subject.getSubjectName());
        subjectCodeField.setText(subject.getSubjectCode());
    }

    // Adds a new subject to the list
    @FXML
    private void addSubject() {
        String name = subjectNameField.getText().trim();
        String code = subjectCodeField.getText().trim();

        if (name.isEmpty()) {
            showAlert("Missing Field", "Subject name is required.", Alert.AlertType.WARNING);
            return;
        }

        if (code.isEmpty()) {
            code = generateSubjectCode(name);
        }

        if (Subject.findSubject(name) != null) {
            showAlert("Duplicate", "Subject already exists.", Alert.AlertType.ERROR);
            return;
        }

        Subject newSubject = new Subject(code, name);
        Subject.addSubject(newSubject);  // <-- This is what's missing
        ReadExcelFile.writeToExcel();
        loadSubjectsIntoTable();
        clearFields();
        showAlert("Success", "Subject added successfully.", Alert.AlertType.INFORMATION);
    }

    // Edits the selected subject's data
    @FXML
    private void editSubject() {
        if (selectedSubject == null) {
            showAlert("No Selection", "Please select a subject to edit.", Alert.AlertType.WARNING);
            return;
        }

        String newName = subjectNameField.getText().trim();
        String newCode = subjectCodeField.getText().trim();

        if (newName.isEmpty()) {
            showAlert("Missing Field", "Subject name is required.", Alert.AlertType.WARNING);
            return;
        }

        selectedSubject.setSubjectName(newName);
        if (!newCode.isEmpty()) selectedSubject.setSubjectCode(newCode);

        ReadExcelFile.writeToExcel();
        loadSubjectsIntoTable();
        clearFields();
        showAlert("Success", "Subject updated successfully.", Alert.AlertType.INFORMATION);
    }

    // Deletes the selected subject
    @FXML
    private void deleteSubject() {
        if (selectedSubject == null) {
            showAlert("No Selection", "Please select a subject to delete.", Alert.AlertType.WARNING);
            return;
        }

        Subject.getSubjectList().remove(selectedSubject);
        ReadExcelFile.writeToExcel();
        loadSubjectsIntoTable();
        clearFields();
        showAlert("Success", "Subject deleted successfully.", Alert.AlertType.INFORMATION);
    }

    // Displays information about the selected subject
    @FXML
    private void viewSubjects() {
        if (selectedSubject != null) {
            showAlert("Subject Info", "Subject: " + selectedSubject.getSubjectName(), Alert.AlertType.INFORMATION);
        } else {
            showAlert("No Selection", "Please select a subject to view.", Alert.AlertType.WARNING);
        }
    }

    // Clears all input fields in the form
    private void clearFields() {
        subjectNameField.clear();
        subjectCodeField.clear();
        selectedSubject = null;
    }

    // Generates a random subject code based on the name
    private String generateSubjectCode(String subjectName) {
        String base = subjectName.replaceAll("\\s+", "").substring(0, Math.min(3, subjectName.length())).toUpperCase();
        int randomNum = (int) (Math.random() * 900 + 100);
        return base + randomNum;
    }

    // Utility to show alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigation Handlers
    @FXML private void loadDashboard() { Router.navigate("ADashboard.fxml", "Admin Dashboard"); }
    @FXML private void loadSubjectManagement() { Router.navigate("ASubjectManagement.fxml", "Admin Subject Management"); }
    @FXML private void loadCourseManagement() { Router.navigate("ACourseManagement.fxml", "Admin Course Management"); }
    @FXML private void loadStudentManagement() { Router.navigate("AStudentManagement.fxml", "Admin Student Management"); }
    @FXML private void loadFacultyManagement() { Router.navigate("AFacultyManagement.fxml", "Admin Faculty Management"); }
    @FXML private void loadEventManagement() { Router.navigate("AEventManagement.fxml", "Admin Event Management"); }
}