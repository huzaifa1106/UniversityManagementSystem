package GUI.CompanyLogin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class AEventManagementController {

    @FXML private Label monthYearLabel;
    @FXML private GridPane calendarGrid;
    @FXML private TextField eventNameField;
    @FXML private TextField eventCodeField;
    @FXML private TextField eventLocationField;
    @FXML private TextField eventDateTimeField;
    @FXML private TextField eventCapacityField;
    @FXML private TextField eventCostField;
    @FXML private Button addEventButton;
    @FXML private Button editEventButton;
    @FXML private Button deleteEventButton;
    @FXML private Button viewEventsButton;

    @FXML
    private void loadSubjectManagement() {
        System.out.println("Loading Subject Management...");
    }

    @FXML
    private void loadCourseManagement() {
        System.out.println("Loading Course Management...");
    }

    @FXML
    private void loadStudentManagement() {
        System.out.println("Loading Student Management...");
    }

    @FXML
    private void loadFacultyManagement() {
        System.out.println("Loading Faculty Management...");
    }

    @FXML
    private void loadEventManagement() {
        System.out.println("Loading Event Management...");
    }

    @FXML
    private void previousYear() {
        System.out.println("Navigating to previous year...");
    }

    @FXML
    private void previousMonth() {
        System.out.println("Navigating to previous month...");
    }

    @FXML
    private void nextMonth() {
        System.out.println("Navigating to next month...");
    }

    @FXML
    private void nextYear() {
        System.out.println("Navigating to next year...");
    }

    @FXML
    private void addEvent() {
        String name = eventNameField.getText();
        String code = eventCodeField.getText();
        String location = eventLocationField.getText();
        String dateTime = eventDateTimeField.getText();
        String capacity = eventCapacityField.getText();
        String cost = eventCostField.getText();

        if (name.isEmpty() || code.isEmpty() || location.isEmpty() || dateTime.isEmpty() || capacity.isEmpty() || cost.isEmpty()) {
            System.out.println("Please fill all fields before adding an event.");
        } else {
            System.out.println("Event Added: " + name + " at " + location);
        }
    }

    @FXML
    private void editEvent() {
        System.out.println("Editing selected event...");
    }

    @FXML
    private void deleteEvent() {
        System.out.println("Deleting selected event...");
    }

    @FXML
    private void viewEvents() {
        System.out.println("Displaying all events...");
    }
}
