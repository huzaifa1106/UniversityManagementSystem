/**
 *  File: USubjectManagementController.java
 *  Description: This class is used for the controlling the flow of the
 *  User Subject Management Window for the application, we use "@FXML" because these are event handles that
 *  were defined in the FXML file. Helping Route through windows for a seemingless experience
 *  Date: March 2nd, 2025
 *  */
package GUI.CompanyLogin;

//Import Statements
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class USubjectManagementController {

    //Method to reroute to SubjectManagement
    @FXML
    private void loadSubjectManagement() { Router.navigate("USubjectManagement.fxml", "Subject Management"); }

    //Method to reroute to CourseManagement
    @FXML
    private void loadCourseManagement() { Router.navigate("UCourseManagement.fxml", "Course Management"); }

    //Method to reroute to StudentManagement
    @FXML
    private void loadStudentManagement() { Router.navigate("UStudentManagement.fxml", "Student Management"); }

    //Method to reroute to FacultyManagement
    @FXML
    private void loadFacultyManagement() { Router.navigate("UFacultyManagement.fxml", "Faculty Management"); }

    //Method to reroute to EventManagement
    @FXML
    private void loadEventManagement() { Router.navigate("UEventManagement.fxml", "Event Management"); }


    // Utility method for alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
