/**
 * File: FEventManagementController.java
 * Description: Faculty-side controller for viewing and registering for events.
 *              Displays a monthly calendar, allows event registration/unregistration,
 *              and saves updates to the Excel sheet for persistence.
 * Author: Group 10
 * Date: April 2025
 */

package GUI.CompanyLogin;

import Backend.Event;
import Backend.Faculty;
import Backend.ReadExcelFile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FEventManagementController {

    @FXML private Label monthYearLabel;
    @FXML private GridPane calendarGrid;
    @FXML private TextField eventCodeField;

    private YearMonth currentYearMonth;
    private Faculty loggedInFaculty;

    /**
     * Set the faculty context for this view.
     * Called when faculty logs in and is directed to event management.
     */
    public void setFaculty(Faculty faculty) {
        this.loggedInFaculty = faculty;
        System.out.println("Faculty Loaded: " + faculty.getName());
        updateCalendar();
    }

    /**
     * Initializes the calendar view to the current month.
     */
    @FXML
    private void initialize() {
        currentYearMonth = YearMonth.now();
    }

    /**
     * Renders the calendar for the current month and highlights days with events.
     * Also color-codes whether the faculty is registered for each day's event.
     */
    private void updateCalendar() {
        calendarGrid.getChildren().clear();
        LocalDate firstDay = currentYearMonth.atDay(1);
        int daysInMonth = currentYearMonth.lengthOfMonth();

        monthYearLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentYearMonth.getYear());

        int dayCounter = 1;
        int colCount = 5;

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < colCount; col++) {
                if (dayCounter > daysInMonth) return;

                LocalDate localDate = currentYearMonth.atDay(dayCounter);
                Date actualDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                // Create day cell
                StackPane dayCell = new StackPane();
                dayCell.setPrefSize(80, 80);
                dayCell.setStyle("-fx-border-color: lightgray; -fx-background-color: white;");

                // Add date number
                Text dateText = new Text(String.valueOf(dayCounter));
                dayCell.getChildren().add(dateText);

                // Highlight if there's an event on that day
                for (Event event : Event.getEventList()) {
                    if (isSameDay(event.getDateTime(), actualDate)) {
                        String facultyID = loggedInFaculty.getFacultyID();
                        boolean isRegistered = event.getRegisteredStudents().contains(facultyID);

                        // Green = registered, Yellow = available
                        String bgColor = isRegistered ? "#d4edda" : "#fceabb";
                        dayCell.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: gray;");

                        // Tooltip shows event name/location
                        Tooltip tooltip = new Tooltip(event.getEventName() + " @ " + event.getLocation());
                        Tooltip.install(dayCell, tooltip);

                        // Clicking the day sets the event code field
                        dayCell.setOnMouseClicked(e -> eventCodeField.setText(event.getEventCode()));
                        break;
                    }
                }

                calendarGrid.add(dayCell, col, row);
                dayCounter++;
            }
        }
    }

    /**
     * Compares if two Date objects fall on the same calendar day.
     */
    private boolean isSameDay(Date d1, Date d2) {
        return d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                .equals(d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @FXML
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateCalendar();
    }

    @FXML
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateCalendar();
    }

    /**
     * Registers the faculty member for the event entered in the text field.
     * Saves the update to file.
     */
    @FXML
    private void onRegisterButtonClicked() {
        String eventCode = eventCodeField.getText().trim();
        if (eventCode.isEmpty()) {
            showAlert("Missing Input", "Please enter an event code to register.", Alert.AlertType.WARNING);
            return;
        }

        Event event = Event.getEventList().stream()
                .filter(e -> e.getEventCode().equalsIgnoreCase(eventCode))
                .findFirst()
                .orElse(null);

        if (event == null) {
            showAlert("Not Found", "No event found with that code.", Alert.AlertType.ERROR);
            return;
        }

        String facultyID = loggedInFaculty.getFacultyID();
        if (event.getRegisteredStudents().contains(facultyID)) {
            showAlert("Already Registered", "You are already registered for this event.", Alert.AlertType.INFORMATION);
            return;
        }

        event.registerStudent(facultyID);
        ReadExcelFile.writeToExcel(); // Persist data
        showAlert("Success", "You have been registered for: " + event.getEventName(), Alert.AlertType.INFORMATION);
        eventCodeField.clear();
        updateCalendar();
    }

    /**
     * Unenrolls the faculty member from the selected event and saves.
     */
    @FXML
    private void onUnenrollButtonClicked() {
        String eventCode = eventCodeField.getText().trim();
        if (eventCode.isEmpty()) {
            showAlert("Missing Input", "Please enter an event code to unenroll.", Alert.AlertType.WARNING);
            return;
        }

        Event event = Event.getEventList().stream()
                .filter(e -> e.getEventCode().equalsIgnoreCase(eventCode))
                .findFirst()
                .orElse(null);

        if (event == null) {
            showAlert("Not Found", "No event found with that code.", Alert.AlertType.ERROR);
            return;
        }

        String facultyID = loggedInFaculty.getFacultyID();
        if (!event.getRegisteredStudents().contains(facultyID)) {
            showAlert("Not Registered", "You're not registered for this event.", Alert.AlertType.INFORMATION);
            return;
        }

        event.getRegisteredStudents().remove(facultyID);
        ReadExcelFile.writeToExcel(); // Save changes
        showAlert("Unenrolled", "You have been unenrolled from: " + event.getEventName(), Alert.AlertType.INFORMATION);
        eventCodeField.clear();
        updateCalendar();
    }

    /**
     * Utility method to display alerts for info, warnings, errors, etc.
     */
    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Sidebar navigation handlers
    @FXML
    private void loadUserManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FUserManagement.fxml"));
            Parent root = loader.load();

            FUserManagementController controller = loader.getController();
            controller.setFaculty(loggedInFaculty);

            Stage stage = (Stage) monthYearLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Faculty Profile");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not load User Management screen.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void loadEventManagement() {
        updateCalendar(); // Refreshes calendar
    }

    @FXML
    private void loadCourseManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FCourseManagement.fxml"));
            Parent root = loader.load();

            FCourseManagementController controller = loader.getController();
            controller.setFaculty(loggedInFaculty);

            Stage stage = (Stage) monthYearLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Course Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not load Course Management screen.", Alert.AlertType.ERROR);
        }
    }
}
