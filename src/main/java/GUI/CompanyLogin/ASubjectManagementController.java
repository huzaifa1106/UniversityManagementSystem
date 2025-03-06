/**
 *  File: ASubjectManagementController.java
 *  Description: This class is used for the controlling the flow of the
 *  Admin Subject Management Window for the application, we use "@FXML" because these are event handles that
 *  were defined in the FXML file. Helping Route through windows for a seemingless experience
 *  Date: March 2nd, 2025
 *  */

package GUI.CompanyLogin;
//Import Statements
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class ASubjectManagementController {

    //Switching window to Admin Dashboard
    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    //Switching window to Admin SubjectManagement
    @FXML
    private void loadSubjectManagement() {
        Router.navigate("ASubjectManagement.fxml", "Admin Subject Management");
    }
    //Switching window to Admin CourseManagement
    @FXML
    private void loadCourseManagement() {
        Router.navigate("ACourseManagement.fxml", "Admin Course Management");
    }

    //Switching window to Admin StudentManagement
    @FXML
    private void loadStudentManagement() {
        Router.navigate("AStudentManagement.fxml", "Admin Student Management");
    }

    //Switching window to Admin FacultyManagement
    @FXML
    private void loadFacultyManagement() {
        Router.navigate("AFacultyManagement.fxml", "Admin Faculty Management");
    }

    //Switching window to Admin EventManagement
    @FXML
    private void loadEventManagement() {
        Router.navigate("AEventManagement.fxml", "Admin Event Management");
    }

    // View Subjects Button was clicked
    @FXML
    private void viewSubjects() {
        System.out.println("View Subjects clicked");
    }

    // Add Subjects Button was clicked
    @FXML
    private void addSubject() {
        System.out.println("Add Subject clicked");
    }

    // Edit Subjects Button was clicked
    @FXML
    private void editSubject() {
        System.out.println("Edit Subject clicked");
    }

    // Delete Subjects Button was clicked
    @FXML
    private void deleteSubject() {
        System.out.println("Delete Subject clicked");
    }

}
