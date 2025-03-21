/**
 *  File: LoginController.java
 *  Description: This class is used to control the flow of the
 *  Login Window for the application. It retrieves the username and password
 *  upon login button click, verifies authentication, and reroutes to the correct
 *  dashboard depending on the role (Admin or Student).
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

// Import Statements
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

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final UserAuthenticator authenticator = new UserAuthenticator();

    // Handle login logic
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Failed", "Username and password cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        String userRole = authenticator.login(username, password);

        if ("admin".equals(userRole)) {
            navigateTo("ADashboard.fxml", "Admin Dashboard");

        } else if ("user".equals(userRole)) {
            try {
                int studentID = Integer.parseInt(username);
                Student loggedInStudent = Student.getStudent(studentID);

                if (loggedInStudent == null) {
                    showAlert("Error", "Student not found. Make sure the student ID matches the users.txt entry.", Alert.AlertType.ERROR);
                    return;
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/USubjectManagement.fxml"));
                Parent root = loader.load();

                // 🎯 Pass student to the subject management controller
                USubjectManagementController controller = loader.getController();
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

        } else {
            showAlert("Login Failed", "Invalid username or password.", Alert.AlertType.ERROR);
        }
    }

    // Navigation helper for admin dashboard
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

    // Show alert box
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
