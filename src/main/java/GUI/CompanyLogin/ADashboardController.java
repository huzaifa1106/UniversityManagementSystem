/**
 *  File: ADashboardController.java
 *  Description: This class is used for the controlling the flow of the
 *  Admin Dashboard Window for the application, we use "@FXML" because these are event handles that
 *  were defined in the FXML file. Helping Route through windows for a seemingless experience
 *  Date: March 2nd, 2025
 *  */

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

    //Rerouting from window to dashboard
    @FXML
    private void loadDashboard() {
        navigateTo("ADashboard.fxml");
    }
    //Rerouting from window to SubjectManagement
    @FXML
    private void loadSubjectManagement() {
        navigateTo("ASubjectManagement.fxml");
    }

    //Rerouting from window to CourseManagement
    @FXML
    private void loadCourseManagement() {
        navigateTo("ACourseManagement.fxml");
    }

    //Rerouting from window to StudentManagement
    @FXML
    private void loadStudentManagement() {
        navigateTo("AStudentManagement.fxml");
    }

    //Rerouting from window to FacultyManagement
    @FXML
    private void loadFacultyManagement() {
        navigateTo("AFacultyManagement.fxml");
    }

    //Rerouting from window to EventManagement
    @FXML
    private void loadEventManagement() {
        navigateTo("AEventManagement.fxml");
    }

    //Method to switch between windows
    private void navigateTo(String fxmlFile) {
        try {
            //Loading the FXML file to wrap window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            // Check if window open, and update its scene
            Window currentWindow = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            if (currentWindow instanceof Stage) {
                Stage stage = (Stage) currentWindow;
                stage.setScene(new Scene(root));// Swap Scenes
                stage.setTitle(fxmlFile.replace(".fxml", " - Admin Panel"));
                stage.show();//Display window
            } else {
                System.out.println("Error: No active window found.");
            }
        } catch (IOException e) {
            e.printStackTrace(); //Print stack trace in case of an error
        }
    }
}
