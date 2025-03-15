/**
 *  File: LoginController.java
 *  Description: This class is used for the controlling the flow of the
 *   Login Window for the application, we use "@FXML" because these are event handles that
 *  were defined in the FXML file. Helping Route through windows for a seemingless experience
 *  It retreives the username and password upon the click of the login button verifies authentication
 *  and reroutes where the user should go according to role
 *  Date: March 2nd, 2025
 *  */

package GUI.CompanyLogin;

//Import Statements
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

    //Attributes
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final UserAuthenticator authenticator = new UserAuthenticator(); // Instance of authentication class

    //Gets credentials from the textFeild
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

    //If either is empty then login failed
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Failed", "Username and password cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        //Finding if the user is found what is its rule.
        String userRole = authenticator.login(username, password);

        //Reroute to Admin Dashboard
        if ("admin".equals(userRole)) {
            navigateTo("ADashboard.fxml", "Admin Dashboard");
        //Reroute to Course Management
        } else if ("user".equals(userRole)) {
            navigateTo("USubjectManagement.fxml", "User Home");
        } else {
            showAlert("Login Failed", "Invalid username or password.", Alert.AlertType.ERROR);
        }
    }
    //Helping navigate to screens
    private void navigateTo(String fxmlFile, String title) {
        try {
            URL resource = getClass().getResource("/GUI/CompanyLogin/" + fxmlFile);
            if (resource == null) {
                throw new IOException("Error: " + fxmlFile + " not found. Check the file path.");
            }

            //Retrieving the FXML
            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));//Changes the scene
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Unable to load screen: " + fxmlFile, Alert.AlertType.ERROR);
        }
    }

    //Helps prompt user the alert on the screen
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
