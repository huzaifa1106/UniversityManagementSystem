/*
 * File: AEventManagementController.java
 * Purpose: Admin-side controller for managing university events.
 * Features: Calendar view, event creation, editing, deletion, and enrollment management.
 * Author: Group 10
 * Date: April 2025
 */

package GUI.CompanyLogin;

import Backend.Event;
import Backend.ReadExcelFile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;

public class AEventManagementController {

    @FXML private Label monthYearLabel;
    @FXML private GridPane calendarGrid;

    @FXML private TextField eventNameField, eventCodeField, eventLocationField, eventCapacityField, eventCostField;
    @FXML private DatePicker eventDatePicker;

    private YearMonth currentYearMonth;
    private Event selectedEvent;

    @FXML
    public void initialize() {
        currentYearMonth = YearMonth.now();
        updateCalendar();
    }

    // Renders calendar grid with day cells
    private void updateCalendar() {
        calendarGrid.getChildren().clear();

        LocalDate firstDay = currentYearMonth.atDay(1);
        int daysInMonth = currentYearMonth.lengthOfMonth();
        int startDay = firstDay.getDayOfWeek().getValue();

        monthYearLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentYearMonth.getYear());

        int dayCounter = 1;
        int columns = 5;

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < columns; col++) {
                if (dayCounter > daysInMonth) return;

                LocalDate date = currentYearMonth.atDay(dayCounter);
                Date convertedDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

                StackPane dayCell = new StackPane();
                dayCell.setPrefSize(80, 80);
                dayCell.setStyle("-fx-border-color: lightgray; -fx-background-color: white;");
                dayCell.getChildren().add(new Text(String.valueOf(dayCounter)));

                for (Event event : Event.getEventList()) {
                    if (isSameDay(event.getDateTime(), convertedDate)) {
                        dayCell.setStyle("-fx-background-color: #d4edda; -fx-border-color: gray;");
                        Tooltip.install(dayCell, new Tooltip(event.getEventName() + " @ " + event.getLocation()));
                        dayCell.setOnMouseClicked(e -> loadEventToForm(event));
                        break;
                    }
                }

                calendarGrid.add(dayCell, col, row);
                dayCounter++;
            }
        }
    }

    private boolean isSameDay(Date d1, Date d2) {
        return d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                .equals(d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    private void loadEventToForm(Event event) {
        selectedEvent = event;
        eventNameField.setText(event.getEventName());
        eventCodeField.setText(event.getEventCode());
        eventLocationField.setText(event.getLocation());
        eventDatePicker.setValue(event.getDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        eventCapacityField.setText(String.valueOf(event.getCapacity()));
        eventCostField.setText(String.valueOf(event.getCost()));
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

    @FXML
    private void addEvent() {
        if (!validateFields()) return;

        try {
            Event newEvent = new Event(
                    eventNameField.getText(),
                    eventCodeField.getText(),
                    "Added via UI",
                    null,
                    eventLocationField.getText(),
                    Date.from(eventDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Integer.parseInt(eventCapacityField.getText()),
                    Double.parseDouble(eventCostField.getText()),
                    new ArrayList<>()
            );

            Event.addEvent(newEvent);
            ReadExcelFile.writeToExcel();
            updateCalendar();
            clearForm();
            showAlert("Success", "Event added successfully.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Capacity and cost must be numeric.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editEvent() {
        if (selectedEvent == null) {
            showAlert("No Selection", "Click a calendar day with an event to edit.", Alert.AlertType.WARNING);
            return;
        }

        try {
            selectedEvent.setEventName(eventNameField.getText());
            selectedEvent.setEventCode(eventCodeField.getText());
            selectedEvent.setLocation(eventLocationField.getText());
            selectedEvent.setDateTime(Date.from(eventDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            selectedEvent.setCapacity(Integer.parseInt(eventCapacityField.getText()));
            selectedEvent.setCost(Double.parseDouble(eventCostField.getText()));

            ReadExcelFile.writeToExcel();
            updateCalendar();
            clearForm();
            selectedEvent = null;
            showAlert("Success", "Event updated.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            showAlert("Error", "Could not update event. Check input values.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deleteEvent() {
        if (selectedEvent == null) {
            showAlert("No Selection", "Click a calendar day with an event to delete.", Alert.AlertType.WARNING);
            return;
        }

        Event.removeEvent(selectedEvent.getEventCode());
        ReadExcelFile.writeToExcel();
        updateCalendar();
        clearForm();
        selectedEvent = null;
        showAlert("Deleted", "Event successfully removed.", Alert.AlertType.INFORMATION);
    }


    @FXML
    private void openStudentEnrollment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/AEventEnrollment.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Manage Event Enrollment");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            showAlert("Error", "Could not load enrollment view.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void clearForm() {
        eventNameField.clear();
        eventCodeField.clear();
        eventLocationField.clear();
        eventDatePicker.setValue(null);
        eventCapacityField.clear();
        eventCostField.clear();
        selectedEvent = null;
    }

    private boolean validateFields() {
        if (eventNameField.getText().isEmpty() ||
                eventCodeField.getText().isEmpty() ||
                eventLocationField.getText().isEmpty() ||
                eventDatePicker.getValue() == null ||
                eventCapacityField.getText().isEmpty() ||
                eventCostField.getText().isEmpty()) {
            showAlert("Missing Fields", "Please complete all fields.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigation buttons (admin sidebar)
    @FXML private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    @FXML private void loadSubjectManagement() {
        Router.navigate("ASubjectManagement.fxml", "Admin Subject Management");
    }

    @FXML private void loadCourseManagement() {
        Router.navigate("ACourseManagement.fxml", "Admin Course Management");
    }

    @FXML private void loadStudentManagement() {
        Router.navigate("AStudentManagement.fxml", "Admin Student Management");
    }

    @FXML private void loadFacultyManagement() {
        Router.navigate("AFacultyManagement.fxml", "Admin Faculty Management");
    }

    @FXML private void loadEventManagement() {
        Router.navigate("AEventManagement.fxml", "Admin Event Management");
    }
}
