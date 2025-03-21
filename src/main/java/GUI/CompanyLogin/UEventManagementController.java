/**
 *  File: UEventManagementController.java
 *  Description: Controls the User Event Management Window. Displays a static calendar and allows event registration.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

import Backend.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UEventManagementController {

    @FXML private TextField eventCodeField; // Input field for entering event codes

    private Student loggedInStudent; // Holds the current student

    // Method to receive and store the logged-in student
    public void setStudent(Student student) {
        this.loggedInStudent = student;
        System.out.println("Student Loaded in Event Management: " + student.getFullName());
    }

    // Called automatically by JavaFX after the FXML is loaded
    @FXML
    private void initialize() {
        // Placeholder: Add any setup or listeners here
    }

    // Triggered when the Register button is clicked
    @FXML
    private void onRegisterButtonClicked() {
        String eventCode = eventCodeField.getText().trim();

        if (eventCode.isEmpty()) {
            showAlert("Input Error", "Please enter an event code to register.", Alert.AlertType.WARNING);
        } else {
            // Simulated registration (hook up to real Event logic if needed)
            showAlert("Success", "Successfully registered for event: " + eventCode, Alert.AlertType.INFORMATION);
            eventCodeField.clear();
        }
    }

    // Utility method for switching scenes while passing along the logged-in student
    private void navigateTo(String fxmlFile, String title, String controllerKey) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            // Based on destination, cast and pass student to correct controller
            switch (controllerKey) {
                case "subject":
                    USubjectManagementController subjectController = loader.getController();
                    subjectController.setStudent(loggedInStudent);
                    break;
                case "course":
                    UCourseManagementController courseController = loader.getController();
                    courseController.setStudent(loggedInStudent);
                    break;
                case "student":
                    UStudentManagementController studentController = loader.getController();
                    studentController.setStudent(loggedInStudent);
                    break;
                case "faculty":
                    UFacultyManagementController facultyController = loader.getController();
                    facultyController.setStudent(loggedInStudent);
                    break;
            }

            // Set the new scene
            Stage stage = (Stage) eventCodeField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Failed to load: " + fxmlFile, Alert.AlertType.ERROR);
        }
    }

    // Navigation button actions
    @FXML private void loadSubjectManagement() {
        navigateTo("USubjectManagement.fxml", "Subject Management", "subject");
    }

    @FXML private void loadCourseManagement() {
        navigateTo("UCourseManagement.fxml", "Course Management", "course");
    }

    @FXML private void loadStudentManagement() {
        navigateTo("UStudentManagement.fxml", "Student Management", "student");
    }

    @FXML private void loadFacultyManagement() {
        navigateTo("UFacultyManagement.fxml", "Faculty Management", "faculty");
    }

    @FXML private void loadEventManagement() {
        // Already on this page — refresh logic can be added here if needed
    }

    // Displays alert popups
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
