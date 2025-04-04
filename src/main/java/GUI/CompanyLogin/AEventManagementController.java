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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AEventManagementController {

    @FXML private Label monthYearLabel;
    @FXML private GridPane calendarGrid;

    @FXML private TextField eventNameField;
    @FXML private TextField eventCodeField;
    @FXML private TextField eventLocationField;
    @FXML private DatePicker eventDatePicker;
    @FXML private TextField eventCapacityField;
    @FXML private TextField eventCostField;

    private YearMonth currentYearMonth;
    private Event selectedEvent = null;

    @FXML
    public void initialize() {
        currentYearMonth = YearMonth.now();
        updateCalendar();
    }

    private void updateCalendar() {
        calendarGrid.getChildren().clear();

        LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
        int daysInMonth = currentYearMonth.lengthOfMonth();
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1 = Monday

        monthYearLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentYearMonth.getYear());

        int dayCounter = 1;
        int colCount = 5;
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < colCount; col++) {
                if (dayCounter > daysInMonth) return;

                LocalDate date = currentYearMonth.atDay(dayCounter);
                Date javaDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

                StackPane dayCell = new StackPane();
                dayCell.setPrefSize(80, 80);
                dayCell.setStyle("-fx-border-color: lightgray; -fx-background-color: white;");
                Text dayText = new Text(String.valueOf(dayCounter));
                dayCell.getChildren().add(dayText);

                for (Event event : Event.getEventList()) {
                    if (isSameDay(event.getDateTime(), javaDate)) {
                        dayCell.setStyle("-fx-background-color: #d4edda; -fx-border-color: gray;");
                        Tooltip tooltip = new Tooltip(event.getEventName() + " @ " + event.getLocation());
                        Tooltip.install(dayCell, tooltip);

                        // Add click action to load form with event details
                        dayCell.setOnMouseClicked(e -> loadEventToForm(event));
                        break;
                    }
                }

                calendarGrid.add(dayCell, col, row);
                dayCounter++;
            }
        }
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

    @FXML
    private void addEvent() {
        String name = eventNameField.getText();
        String code = eventCodeField.getText();
        String location = eventLocationField.getText();
        LocalDate localDate = eventDatePicker.getValue();
        String capacityStr = eventCapacityField.getText();
        String costStr = eventCostField.getText();

        if (name.isEmpty() || code.isEmpty() || location.isEmpty() || localDate == null || capacityStr.isEmpty() || costStr.isEmpty()) {
            System.out.println("Please fill all fields before adding an event.");
            return;
        }

        try {
            Date dateTime = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            int capacity = Integer.parseInt(capacityStr);
            double cost = Double.parseDouble(costStr);

            Event newEvent = new Event(
                    name, code, "Auto-added via GUI", null,
                    location, dateTime, capacity, cost,
                    new ArrayList<>()
            );

            Event.addEvent(newEvent);
            ReadExcelFile.writeToExcel();
            System.out.println("Event added.");
            updateCalendar();
            clearForm();

        } catch (NumberFormatException e) {
            System.out.println("Invalid number input.");
        }
    }

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
        if (selectedEvent == null) {
            System.out.println("No event selected to edit.");
            return;
        }

        selectedEvent.setEventName(eventNameField.getText());
        selectedEvent.setEventCode(eventCodeField.getText());
        selectedEvent.setLocation(eventLocationField.getText());
        selectedEvent.setDateTime(Date.from(eventDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        selectedEvent.setCapacity(Integer.parseInt(eventCapacityField.getText()));
        selectedEvent.setCost(Double.parseDouble(eventCostField.getText()));

        System.out.println("Event updated.");
        updateCalendar();
        clearForm();
    }

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
        if (selectedEvent == null) {
            System.out.println("No event selected to delete.");
            return;
        }

        Event.removeEvent(selectedEvent.getEventCode());
        selectedEvent = null;
        System.out.println("Event deleted.");
        updateCalendar();
        clearForm();
    }

    @FXML
    private void viewEvents() {
        for (Event e : Event.getEventList()) {
            e.viewEventDetails();
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
    @FXML
    private void openStudentEnrollment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/AEventEnrollment.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Manage Event Enrollment");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Prevents interacting with the main window until closed
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Navigation stubs
    @FXML private void loadDashboard() { Router.navigate("ADashboard.fxml", "Admin Dashboard"); }
    @FXML private void loadSubjectManagement() { Router.navigate("ASubjectManagement.fxml", "Admin Subject Management"); }
    @FXML private void loadCourseManagement() { Router.navigate("ACourseManagement.fxml", "Admin Course Management"); }
    @FXML private void loadStudentManagement() { Router.navigate("AStudentManagement.fxml", "Admin Student Management"); }
    @FXML private void loadFacultyManagement() { Router.navigate("AFacultyManagement.fxml", "Admin Faculty Management"); }
    @FXML private void loadEventManagement() { Router.navigate("AEventManagement.fxml", "Admin Event Management"); }
}
