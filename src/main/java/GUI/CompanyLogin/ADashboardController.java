/**
 *  File: ADashboardController.java
 *  Description: This controller manages navigation between different admin panels in the system.
 *  It includes routing to dashboard, course, student, subject, faculty, and event management views.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

//Importing Packages
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;

public class ADashboardController {

    // Navigates to the dashboard view
    @FXML
    private void loadDashboard() {
        navigateTo("ADashboard.fxml");
    }

    // Navigates to the subject management view
    @FXML
    private void loadSubjectManagement() {
        navigateTo("ASubjectManagement.fxml");
    }

    // Navigates to the course management view
    @FXML
    private void loadCourseManagement() {
        navigateTo("ACourseManagement.fxml");
    }

    // Navigates to the student management view
    @FXML
    private void loadStudentManagement() {
        navigateTo("AStudentManagement.fxml");
    }

    // Navigates to the faculty management view
    @FXML
    private void loadFacultyManagement() {
        navigateTo("AFacultyManagement.fxml");
    }

    // Navigates to the event management view
    @FXML
    private void loadEventManagement() {
        navigateTo("AEventManagement.fxml");
    }

    // Utility method to load and switch scenes between FXML windows
    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            Window currentWindow = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            if (currentWindow instanceof Stage) {
                Stage stage = (Stage) currentWindow;
                stage.setScene(new Scene(root));
                stage.setTitle(fxmlFile.replace(".fxml", " - Admin Panel"));
                stage.show();
            } else {
                System.out.println("Error: No active window found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
