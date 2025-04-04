/*
 * File: AEventManageEnrollmentController.java
 * Description: Controller class for managing student enrollments in events (Admin-side).
 *              Allows the admin to view, add, or remove students from event participation.
 * Author: Group 10
 * Date: April 2025
 */

package GUI.CompanyLogin;

import Backend.Event;
import Backend.ReadExcelFile;
import Backend.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AEventManageEnrollmentController {

    @FXML private ListView<String> eventListView;
    @FXML private Label eventNameLabel;
    @FXML private TextArea studentsTextArea;
    @FXML private ComboBox<String> addStudentComboBox;
    @FXML private ComboBox<String> removeStudentComboBox;

    private Event selectedEvent;

    @FXML
    private void initialize() {
        // Fill the event list with existing event names
        for (Event event : Event.getEventList()) {
            eventListView.getItems().add(event.getEventName());
        }

        // Prepare the add combo box with all students
        for (Student student : Student.getStudentList()) {
            addStudentComboBox.getItems().add(formatStudentDisplay(student));
        }

        // Listen for event selection
        eventListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= 0) {
                selectedEvent = Event.getEventList().get(newVal.intValue());
                updateEventInfo();
            }
        });
    }

    // Refresh the UI with selected event's info
    private void updateEventInfo() {
        if (selectedEvent == null) return;

        eventNameLabel.setText("Event: " + selectedEvent.getEventName());
        studentsTextArea.clear();
        removeStudentComboBox.getItems().clear();

        for (String studentId : selectedEvent.getRegisteredStudents()) {
            Student student = findStudentById(studentId);
            if (student != null) {
                String display = formatStudentDisplay(student);
                studentsTextArea.appendText("- " + display + "\n");
                removeStudentComboBox.getItems().add(display);
            }
        }
    }

    // Attempts to find a student from ID string
    private Student findStudentById(String id) {
        try {
            int studentId = Integer.parseInt(id);
            for (Student s : Student.getStudentList()) {
                if (s.getStudentID() == studentId) return s;
            }
        } catch (NumberFormatException ignored) {}
        return null;
    }

    @FXML
    private void addStudent() {
        if (selectedEvent == null) return;

        String selected = addStudentComboBox.getValue();
        if (selected == null || !selected.contains("(")) return;

        String id = selected.substring(selected.indexOf("(") + 1, selected.indexOf(")"));

        if (!selectedEvent.getRegisteredStudents().contains(id)) {
            selectedEvent.registerStudent(id);
            ReadExcelFile.writeToExcel();
            updateEventInfo();
        }
    }

    @FXML
    private void removeStudent() {
        if (selectedEvent == null) return;

        String selected = removeStudentComboBox.getValue();
        if (selected == null || !selected.contains("(")) return;

        String id = selected.substring(selected.indexOf("(") + 1, selected.indexOf(")"));
        selectedEvent.getRegisteredStudents().remove(id);
        ReadExcelFile.writeToExcel();
        updateEventInfo();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) eventListView.getScene().getWindow();
        stage.close();
    }

    // Builds a readable string like Alice Smith (100001)
    private String formatStudentDisplay(Student s) {
        return s.getFullName() + " (" + s.getStudentID() + ")";
    }
}
