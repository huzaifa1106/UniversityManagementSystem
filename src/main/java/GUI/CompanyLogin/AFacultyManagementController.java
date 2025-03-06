package GUI.CompanyLogin;

import javafx.fxml.FXML;

public class AFacultyManagementController extends ASubjectManagementController {

    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    @FXML
    private void addFaculty() {
        System.out.println("Add Faculty clicked");
    }

    @FXML
    private void editFaculty() {
        System.out.println("Edit Faculty clicked");
    }

    @FXML
    private void deleteFaculty() {
        System.out.println("Delete Faculty clicked");
    }

    @FXML
    private void viewFacultyProfile() {
        System.out.println("View Faculty Profile clicked");
    }

    @FXML
    private void assignCourses() {
        System.out.println("Assign Courses clicked");
    }
}
