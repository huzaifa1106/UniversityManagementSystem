/**
 *  File: USubjectManagementController.java
 *  Description: Controls the User Subject Management Window, dynamically loads the subjects
 *  enrolled by the logged-in student and routes to different sections of the user dashboard.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

import Backend.Student;
import Backend.Subject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class USubjectManagementController {

    @FXML private TableView<Subject> subjectTable;
    @FXML private TableColumn<Subject, String> colSubjectCode;
    @FXML private TableColumn<Subject, String> colSubjectName;
    @FXML private Label studentNameLabel;

    private Student loggedInStudent;

    // Set by LoginController
    public void setStudent(Student student) {
        this.loggedInStudent = student;

        if (studentNameLabel != null) {
            studentNameLabel.setText("Welcome, " + student.getFullName());
        }

        System.out.println("✅ Student Loaded: " + student.getFullName());
        loadStudentSubjects();
    }

    // Load subjects into table
    private void loadStudentSubjects() {
        if (loggedInStudent == null) return;

        List<Subject> subjects = loggedInStudent.getEnrolledSubjects();

        colSubjectCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectCode()));
        colSubjectName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectName()));

        subjectTable.setItems(FXCollections.observableArrayList(subjects));
    }

    // Reusable navigation helper
    private void navigateTo(String fxmlFile, String title, TargetController target) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            switch (target) {
                case COURSE -> {
                    UCourseManagementController c = loader.getController();
                    c.setStudent(loggedInStudent);
                }
                case STUDENT -> {
                    UStudentManagementController c = loader.getController();
                    c.setStudent(loggedInStudent);
                }
                case FACULTY -> {
                    UFacultyManagementController c = loader.getController();
                    c.setStudent(loggedInStudent);
                }
                case EVENT -> {
                    UEventManagementController c = loader.getController();
                    c.setStudent(loggedInStudent);
                }
            }

            Stage stage = (Stage) subjectTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Failed to load: " + fxmlFile, Alert.AlertType.ERROR);
        }
    }

    // Enum to cleanly handle controller routing
    private enum TargetController {
        COURSE, STUDENT, FACULTY, EVENT
    }

    // Navigation buttons
    @FXML private void loadSubjectManagement() {
        loadStudentSubjects(); // Just refresh current view
    }

    @FXML private void loadCourseManagement() {
        navigateTo("UCourseManagement.fxml", "Course Management", TargetController.COURSE);
    }

    @FXML private void loadStudentManagement() {
        navigateTo("UStudentManagement.fxml", "Student Management", TargetController.STUDENT);
    }

    @FXML private void loadFacultyManagement() {
        navigateTo("UFacultyManagement.fxml", "Faculty Management", TargetController.FACULTY);
    }

    @FXML private void loadEventManagement() {
        navigateTo("UEventManagement.fxml", "Event Management", TargetController.EVENT);
    }

    // Utility alert
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
