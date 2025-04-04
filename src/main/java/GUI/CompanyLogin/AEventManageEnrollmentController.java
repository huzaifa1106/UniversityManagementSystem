package GUI.CompanyLogin;

import Backend.Event;
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
        // Populate event names into list view
        for (Event event : Event.getEventList()) {
            eventListView.getItems().add(event.getEventName());
            System.out.println("HERE");
        }

        // Populate student combo box for adding
        for (Student student : Student.getStudentList()) {
            String display = student.getFullName() + " (" + student.getStudentID() + ")";
            addStudentComboBox.getItems().add(display);
        }

        // Event selection logic
        eventListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= 0) {
                selectedEvent = Event.getEventList().get(newVal.intValue());
                updateEventInfo();
            }
        });
    }

    private void updateEventInfo() {
        if (selectedEvent == null) return;

        eventNameLabel.setText("Event: " + selectedEvent.getEventName());
        studentsTextArea.clear();
        removeStudentComboBox.getItems().clear();

        for (String studentId : selectedEvent.getRegisteredStudents()) {
            Student student = findStudentById(studentId);
            if (student != null) {
                String display = student.getFullName() + " (" + student.getStudentID() + ")";
                studentsTextArea.appendText("- " + display + "\n");
                removeStudentComboBox.getItems().add(display);
            }
        }
    }

    private Student findStudentById(String id) {
        try {
            int sid = Integer.parseInt(id);
            for (Student s : Student.getStudentList()) {
                if (s.getStudentID() == sid) {
                    return s;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid student ID: " + id);
        }
        return null;
    }

    @FXML
    private void addStudent() {
        if (selectedEvent == null) return;

        String selection = addStudentComboBox.getValue();
        if (selection == null || !selection.contains("(")) return;

        String studentId = selection.substring(selection.indexOf("(") + 1, selection.indexOf(")"));

        if (!selectedEvent.getRegisteredStudents().contains(studentId)) {
            selectedEvent.registerStudent(studentId);
            updateEventInfo();
        }
    }

    @FXML
    private void removeStudent() {
        if (selectedEvent == null) return;

        String selection = removeStudentComboBox.getValue();
        if (selection == null || !selection.contains("(")) return;

        String studentId = selection.substring(selection.indexOf("(") + 1, selection.indexOf(")"));

        selectedEvent.getRegisteredStudents().remove(studentId);
        updateEventInfo();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) eventListView.getScene().getWindow();
        stage.close();
    }
}
