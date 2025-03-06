package GUI.CompanyLogin;
import javafx.fxml.FXML;

public class ACourseManagementController extends ASubjectManagementController {

    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    @FXML
    private void addCourse() {
        System.out.println("Add Course clicked");
    }

    @FXML
    private void editCourse() {
        System.out.println("Edit Course clicked");
    }

    @FXML
    private void deleteCourse() {
        System.out.println("Delete Course clicked");
    }

    @FXML
    private void viewCourses() {
        System.out.println("View Courses clicked");
    }

    @FXML
    private void assignFaculty() {
        System.out.println("Assign Faculty clicked");
    }

    @FXML
    private void manageEnrollments() {
        System.out.println("Manage Enrollments clicked");
    }
}
