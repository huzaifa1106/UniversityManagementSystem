package GUI.CompanyLogin;
import javafx.fxml.FXML;

public class AEventManagementController extends ASubjectManagementController {

    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    @FXML
    private void addEvent() {
        System.out.println("Add Event clicked");
    }

    @FXML
    private void editEvent() {
        System.out.println("Edit Event clicked");
    }

    @FXML
    private void deleteEvent() {
        System.out.println("Delete Event clicked");
    }

    @FXML
    private void viewEvents() {
        System.out.println("View Events clicked");
    }
}
