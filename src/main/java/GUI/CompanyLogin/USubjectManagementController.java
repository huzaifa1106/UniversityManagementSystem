package GUI.CompanyLogin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class USubjectManagementController {

    @FXML
    private void loadSubjectManagement() { Router.navigate("USubjectManagement.fxml", "Subject Management"); }

    @FXML
    private void loadCourseManagement() { Router.navigate("UCourseManagement.fxml", "Course Management"); }

    @FXML
    private void loadStudentManagement() { Router.navigate("UStudentManagement.fxml", "Student Management"); }

    @FXML
    private void loadFacultyManagement() { Router.navigate("UFacultyManagement.fxml", "Faculty Management"); }

    @FXML
    private void loadEventManagement() { Router.navigate("UEventManagement.fxml", "Event Management"); }

    // ✅ Fix: Add the missing `addSubject` method
    @FXML
    private void addSubject() {
        showAlert("Add Subject", "Feature not implemented yet!", Alert.AlertType.INFORMATION);
    }

    // ✅ Fix: Add placeholder methods for other buttons
    @FXML
    private void editSubject() {
        showAlert("Edit Subject", "Feature not implemented yet!", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void deleteSubject() {
        showAlert("Delete Subject", "Feature not implemented yet!", Alert.AlertType.INFORMATION);
    }

    // Utility method for alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
