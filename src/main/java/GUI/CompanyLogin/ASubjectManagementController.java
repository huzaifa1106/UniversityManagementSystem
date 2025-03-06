package GUI.CompanyLogin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class ASubjectManagementController {

    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    @FXML
    private void loadSubjectManagement() {
        Router.navigate("ASubjectManagement.fxml", "Admin Subject Management");
    }

    @FXML
    private void loadCourseManagement() {
        Router.navigate("ACourseManagement.fxml", "Admin Course Management");
    }

    @FXML
    private void loadStudentManagement() {
        Router.navigate("AStudentManagement.fxml", "Admin Student Management");
    }

    @FXML
    private void loadFacultyManagement() {
        Router.navigate("AFacultyManagement.fxml", "Admin Faculty Management");
    }

    @FXML
    private void loadEventManagement() {
        Router.navigate("AEventManagement.fxml", "Admin Event Management");
    }

    // ✅ Added missing method
    @FXML
    private void viewSubjects() {
        System.out.println("View Subjects clicked");
    }

    @FXML
    private void addSubject() {
        System.out.println("Add Subject clicked");
    }

    @FXML
    private void editSubject() {
        System.out.println("Edit Subject clicked");
    }

    @FXML
    private void deleteSubject() {
        System.out.println("Delete Subject clicked");
    }

}
