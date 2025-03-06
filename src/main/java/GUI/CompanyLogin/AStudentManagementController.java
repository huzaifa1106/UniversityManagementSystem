package GUI.CompanyLogin;

import javafx.fxml.FXML;

public class AStudentManagementController extends ASubjectManagementController {

    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    @FXML
    private void addStudent() {
        System.out.println("Add Student clicked");
    }

    @FXML
    private void editStudent() {
        System.out.println("Edit Student clicked");
    }

    @FXML
    private void deleteStudent() {
        System.out.println("Delete Student clicked");
    }

    @FXML
    private void viewStudentProfile() {
        System.out.println("View Student Profile clicked");
    }

    @FXML
    private void manageEnrollments() {
        System.out.println("Manage Enrollments clicked");
    }
}
