package GUI.CompanyLogin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ADashboardController {


    @FXML
    private void loadDashboard() {
        navigateTo("ADashboard.fxml");
    }

    @FXML
    private void loadSubjectManagement() {
        navigateTo("ASubjectManagement.fxml");
    }

    @FXML
    private void loadCourseManagement() {
        navigateTo("ACourseManagement.fxml");
    }

    @FXML
    private void loadStudentManagement() {
        navigateTo("AStudentManagement.fxml");
    }

    @FXML
    private void loadFacultyManagement() {
        navigateTo("AFacultyManagement.fxml");
    }

    @FXML
    private void loadEventManagement() {
        navigateTo("AEventManagement.fxml");
    }

    /**
     Method to switch between windows
     */
    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            // Check if window open
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
