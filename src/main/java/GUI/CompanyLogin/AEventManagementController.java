/**
 *  File: EventManagementController.java
 *  Description: This class is used for the controlling the flow of the
 *  Admin EventManagement Window for the application, we use "@FXML" because these are event handles that
 *  were defined in the FXML file. Helping Route through windows for a seemingless experience
 *  Date: March 2nd, 2025
 *  */

package GUI.CompanyLogin;

//Import Statements
import javafx.fxml.FXML;

public class AEventManagementController extends ASubjectManagementController {

    //Method to switch window to dashboard
    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    //Add event button clicked
    @FXML
    private void addEvent() {
        System.out.println("Add Event");
    }

    //Edit event button clicked
    @FXML
    private void editEvent() {
        System.out.println("Edit Event ");
    }

    //Delete event button clicked
    @FXML
    private void deleteEvent() {
        System.out.println("Delete Event ");
    }

    //View event button clicked
    @FXML
    private void viewEvents() {
        System.out.println("View Events ");
    }
}
