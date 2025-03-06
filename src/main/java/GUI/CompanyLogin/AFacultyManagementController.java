/**
 *  File: AFacultyManagementController.java
 *  Description: This class is used for the controlling the flow of the
 *  Admin FacultyManagement Window for the application, we use "@FXML" because these are event handles that
 *  were defined in the FXML file. Helping Route through windows for a seemingless experience
 *  Date: March 2nd, 2025
 *  */


package GUI.CompanyLogin;

//Important Statement
import javafx.fxml.FXML;

public class AFacultyManagementController extends ASubjectManagementController {

    //Method to switch window to dashboard
    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    //Add faculty button clicked
    @FXML
    private void addFaculty() {
        System.out.println("Add Faculty clicked");
    }
    //Edit faculty button clicked
    @FXML
    private void editFaculty() {
        System.out.println("Edit Faculty clicked");
    }
    //Delete faculty button clicked
    @FXML
    private void deleteFaculty() {
        System.out.println("Delete Faculty clicked");
    }
    //View faculty button clicked
    @FXML
    private void viewFacultyProfile() {
        System.out.println("View Faculty Profile clicked");
    }
    //Assign faculty a course button clicked
    @FXML
    private void assignCourses() {
        System.out.println("Assign Courses clicked");
    }
}
