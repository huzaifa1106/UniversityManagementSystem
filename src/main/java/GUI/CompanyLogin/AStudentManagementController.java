/**
 *  File: AStudentManagementController.java
 *  Description: This class is used for the controlling the flow of the
 *  Admin StudentManagement Window for the application, we use "@FXML" because these are event handles that
 *  were defined in the FXML file. Helping Route through windows for a seemingless experience
 *  Date: March 2nd, 2025
 *  */

package GUI.CompanyLogin;

import javafx.fxml.FXML;

public class AStudentManagementController extends ASubjectManagementController {

    //Method to switch window to dashboard
    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    //Add student Button clicked
    @FXML
    private void addStudent() {
        System.out.println("Add Student clicked");
    }

    //Edit student Button clicked
    @FXML
    private void editStudent() {
        System.out.println("Edit Student clicked");
    }

    //Delete student Button clicked
    @FXML
    private void deleteStudent() {
        System.out.println("Delete Student clicked");
    }

    //View student Button clicked
    @FXML
    private void viewStudentProfile() {
        System.out.println("View Student Profile clicked");
    }

    //Manage student enrollment Button clicked
    @FXML
    private void manageEnrollments() {
        System.out.println("Manage Enrollments clicked");
    }
}
