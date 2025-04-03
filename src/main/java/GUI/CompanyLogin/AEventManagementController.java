/**
 *  File: AEventManagementController.java
 *  Description: This controller handles all functionality for managing university events,
 *  including adding, editing, deleting events and displaying them on a calendar.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

import Backend.Event;
import Backend.ReadExcelFile;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AEventManagementController {

    @FXML private TextField eventNameField, eventCodeField, eventLocationField,
            eventDateTimeField, eventCapacityField, eventCostField;

    @FXML private GridPane calendarGrid;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private Event selectedEvent = null;

    // Initializes the calendar and loads events
    @FXML
    private void initialize() {
        populateCalendarWithEvents();
    }

    // Populates calendar grid with events
    private void populateCalendarWithEvents() {
        clearCalendar();
        List<Event> events = Event.getEventList();

        for (Event event : events) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(event.getDateTime());
            int day = cal.get(Calendar.DAY_OF_MONTH);

            for (javafx.scene.Node node : calendarGrid.getChildren()) {
                if (GridPane.getRowIndex(node) == null || GridPane.getColumnIndex(node) == null) continue;
                StackPane pane = (StackPane) node;
                if (!pane.getChildren().isEmpty() && pane.getChildren().get(0) instanceof Label label) {
                    if (label.getText().equals(String.valueOf(day))) {
                        Text eventText = new Text(event.getEventName());
                        eventText.setStyle("-fx-fill: #1565C0; -fx-font-size: 10px;");
                        pane.getChildren().add(eventText);

                        pane.setOnMouseClicked(evt -> {
                            selectedEvent = event;
                            fillEventForm(event);
                        });
                    }
                }
            }
        }
    }

    // Fills the form with selected event details
    private void fillEventForm(Event e) {
        eventNameField.setText(e.getEventName());
        eventCodeField.setText(e.getEventCode());
        eventLocationField.setText(e.getLocation());
        eventDateTimeField.setText(formatter.format(e.getDateTime()));
        eventCapacityField.setText(String.valueOf(e.getCapacity()));
        eventCostField.setText(String.valueOf(e.getCost()));
    }

    // Clears previous events from calendar
    private void clearCalendar() {
        for (javafx.scene.Node node : calendarGrid.getChildren()) {
            if (node instanceof StackPane pane && pane.getChildren().size() > 1) {
                pane.getChildren().remove(1, pane.getChildren().size());
                pane.setOnMouseClicked(null);
            }
        }
    }

    // Handles adding a new event to the calendar
    @FXML
    private void addEvent() {
        try {
            String name = eventNameField.getText();
            String code = eventCodeField.getText();
            String location = eventLocationField.getText();
            Date date = formatter.parse(eventDateTimeField.getText());
            int capacity = Integer.parseInt(eventCapacityField.getText());
            double cost = Double.parseDouble(eventCostField.getText());

            Event newEvent = new Event(name, code, "", null, location, date, capacity, cost, new ArrayList<>());
            Event.addEvent(newEvent);
            ReadExcelFile.writeToExcel();
            clearForm();
            populateCalendarWithEvents();
            showAlert("Success", "Event added successfully!", Alert.AlertType.INFORMATION);
        } catch (ParseException | NumberFormatException e) {
            showAlert("Error", "Invalid input. Check date format (yyyy-MM-dd), capacity, and cost.", Alert.AlertType.ERROR);
        }
    }

    // Handles editing an existing selected event
    @FXML
    private void editEvent() {
        if (selectedEvent != null) {
            try {
                selectedEvent.setEventName(eventNameField.getText());
                selectedEvent.setEventCode(eventCodeField.getText());
                selectedEvent.setLocation(eventLocationField.getText());
                selectedEvent.setDateTime(formatter.parse(eventDateTimeField.getText()));
                selectedEvent.setCapacity(Integer.parseInt(eventCapacityField.getText()));
                selectedEvent.setCost(Double.parseDouble(eventCostField.getText()));
                ReadExcelFile.writeToExcel();
                populateCalendarWithEvents();
                clearForm();
                selectedEvent = null;

                showAlert("Success", "Event updated successfully!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", "Invalid input. Please check values.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("No Event Selected", "Click a date with an event to edit.", Alert.AlertType.WARNING);
        }
    }

    // Handles deleting a selected event
    @FXML
    private void deleteEvent() {
        if (selectedEvent != null) {
            Event.removeEvent(selectedEvent.getEventCode());
            ReadExcelFile.writeToExcel();
            populateCalendarWithEvents();
            clearForm();
            selectedEvent = null;
            showAlert("Deleted", "Event removed from calendar.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("No Event Selected", "Click a date with an event to delete.", Alert.AlertType.WARNING);
        }
    }

    // Placeholder for viewing event logic
    @FXML private void viewEvents() {
        showAlert("Info", "View events clicked (not yet implemented).", Alert.AlertType.INFORMATION);
    }

    // Clears all form fields
    private void clearForm() {
        eventNameField.clear();
        eventCodeField.clear();
        eventLocationField.clear();
        eventDateTimeField.clear();
        eventCapacityField.clear();
        eventCostField.clear();
    }

    // Displays an alert dialog
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Navigation methods to switch between panels
    @FXML private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    @FXML private void loadSubjectManagement() {
        Router.navigate("ASubjectManagement.fxml", "Subject Management");
    }

    @FXML private void loadCourseManagement() {
        Router.navigate("ACourseManagement.fxml", "Course Management");
    }

    @FXML private void loadStudentManagement() {
        Router.navigate("AStudentManagement.fxml", "Student Management");
    }

    @FXML private void loadFacultyManagement() {
        Router.navigate("AFacultyManagement.fxml", "Faculty Management");
    }

    @FXML private void loadEventManagement() {
        Router.navigate("AEventManagement.fxml", "Event Management");
    }
}