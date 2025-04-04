/**
 * File: LoginController.java
 * Description: Handles login logic for Admins, Students, and Faculty.
 *              Authenticates users, determines their role, and redirects to the correct dashboard.
 * Author: Group 10
 * Date: April 2025
 */

package GUI.CompanyLogin;

import Backend.Faculty;
import Backend.Student;
import Backend.UserAuthenticator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final UserAuthenticator authenticator = new UserAuthenticator();

    /**
     * Called when the login button is pressed.
     * Verifies input and authenticates credentials.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Simple empty field validation
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Failed", "Username and password cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        // Authenticate the credentials and get the user role
        String userRole = authenticator.login(username, password);

        switch (userRole) {
            case "admin":
                // Navigate to admin dashboard
                navigateTo("ADashboard.fxml", "Admin Dashboard");
                break;

            case "user":
                // User is a student - validate and load their dashboard
                try {
                    int studentID = Integer.parseInt(username);
                    Student loggedInStudent = Student.getStudent(studentID);

                    if (loggedInStudent == null) {
                        showAlert("Error", "Student not found. Ensure student ID matches users.txt.", Alert.AlertType.ERROR);
                        return;
                    }

                    // Load the student course management scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/UCourseManagement.fxml"));
                    Parent root = loader.load();

                    UCourseManagementController controller = loader.getController();
                    controller.setStudent(loggedInStudent);

                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Student Portal");
                    stage.show();

                } catch (NumberFormatException e) {
                    showAlert("Login Error", "Student ID must be numeric.", Alert.AlertType.ERROR);
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Navigation Error", "Failed to load student dashboard.", Alert.AlertType.ERROR);
                }
                break;

            case "faculty":
                // User is a faculty member - validate and load their dashboard
                try {
                    Faculty loggedInFaculty = Faculty.findByID(username);

                    if (loggedInFaculty == null) {
                        showAlert("Error", "Faculty not found. Check the name spelling or faculty list.", Alert.AlertType.ERROR);
                        return;
                    }

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FCourseManagement.fxml"));
                    Parent root = loader.load();

                    FCourseManagementController controller = loader.getController();
                    controller.setFaculty(loggedInFaculty);

                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Faculty Portal");
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Navigation Error", "Failed to load faculty portal.", Alert.AlertType.ERROR);
                }
                break;

            default:
                // Invalid credentials or unknown role
                showAlert("Login Failed", "Invalid username or password.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Helper method to check for matching faculty entry in a backup file.
     * Not actively used, but kept for potential fallback auth strategy.
     */
    private boolean isFaculty(String username, String password) {
        try {
            Path path = Path.of("faculty.txt");
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].trim().equals(username) && parts[1].trim().equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read faculty.txt: " + e.getMessage());
        }

        return false;
    }

    /**
     * Generic screen loader utility. Takes the FXML file name and page title.
     */
    private void navigateTo(String fxmlFile, String title) {
        try {
            URL resource = getClass().getResource("/GUI/CompanyLogin/" + fxmlFile);
            if (resource == null) throw new IOException("Error: " + fxmlFile + " not found.");

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Unable to load screen: " + fxmlFile, Alert.AlertType.ERROR);
        }
    }

    /**
     * Utility method to show alert popups.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
