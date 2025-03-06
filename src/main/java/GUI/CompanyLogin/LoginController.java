package GUI.CompanyLogin;

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

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final UserAuthenticator authenticator = new UserAuthenticator(); // Instance of authentication class

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
            navigateTo("UCourseManagement.fxml", "User Home");
        } else {
            showAlert("Login Failed", "Invalid username or password.", Alert.AlertType.ERROR);
        }
    }

    private void navigateTo(String fxmlFile, String title) {
        try {
            URL resource = getClass().getResource("/GUI/CompanyLogin/" + fxmlFile);
            if (resource == null) {
                throw new IOException("Error: " + fxmlFile + " not found. Check the file path.");
            }

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

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
