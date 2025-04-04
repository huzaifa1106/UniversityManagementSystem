package GUI.CompanyLogin;

import Backend.Event;
import Backend.Student;
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

public class UEventManagementController {

    @FXML private Label monthYearLabel;
    @FXML private GridPane calendarGrid;
    @FXML private TextField eventCodeField;

    private YearMonth currentYearMonth;
    private Student loggedInStudent;

    // This will be set from the login or previous screen
    public void setStudent(Student student) {
        this.loggedInStudent = student;
        System.out.println("✅ Student Loaded: " + student.getFullName());
    }

    @FXML
    private void initialize() {
        currentYearMonth = YearMonth.now();
        updateCalendar();
    }

    private void updateCalendar() {
        calendarGrid.getChildren().clear();
        LocalDate firstDay = currentYearMonth.atDay(1);
        int daysInMonth = currentYearMonth.lengthOfMonth();
        int startDay = firstDay.getDayOfWeek().getValue(); // 1 = Monday

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
                        dayCell.setStyle("-fx-background-color: #d1ecf1; -fx-border-color: gray;");
                        Tooltip tooltip = new Tooltip(event.getEventName() + " @ " + event.getLocation());
                        Tooltip.install(dayCell, tooltip);
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
    private void onRegisterButtonClicked() {
        String eventCode = eventCodeField.getText().trim();
        if (eventCode.isEmpty()) {
            showAlert("Missing Input", "Please enter an event code to register.", Alert.AlertType.WARNING);
            return;
        }

        List<Event> events = Event.getEventList();
        Event event = events.stream()
                .filter(e -> e.getEventCode().equalsIgnoreCase(eventCode))
                .findFirst()
                .orElse(null);

        if (event == null) {
            showAlert("Not Found", "No event found with that code.", Alert.AlertType.ERROR);
            return;
        }

        String studentID = String.valueOf(loggedInStudent.getStudentID());
        if (event.getRegisteredStudents().contains(studentID)) {
            showAlert("Already Registered", "You are already registered for this event.", Alert.AlertType.INFORMATION);
            return;
        }

        event.registerStudent(studentID);
        showAlert("Success", "You have been registered for: " + event.getEventName(), Alert.AlertType.INFORMATION);
        eventCodeField.clear();
        updateCalendar();
    }

    private void navigateTo(String fxmlFile, String title, String controllerKey) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            switch (controllerKey) {
                case "subject":
                    USubjectManagementController subjectController = loader.getController();
                    subjectController.setStudent(loggedInStudent);
                    break;
                case "course":
                    UCourseManagementController courseController = loader.getController();
                    courseController.setStudent(loggedInStudent);
                    break;
                case "student":
                    UStudentManagementController studentController = loader.getController();
                    studentController.setStudent(loggedInStudent);
                    break;
                case "faculty":
                    UFacultyManagementController facultyController = loader.getController();
                    facultyController.setStudent(loggedInStudent);
                    break;
            }

            Stage stage = (Stage) eventCodeField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Failed to load: " + fxmlFile, Alert.AlertType.ERROR);
        }
    }

    @FXML private void loadSubjectManagement() {
        navigateTo("USubjectManagement.fxml", "Subject Management", "subject");
    }

    @FXML private void loadCourseManagement() {
        navigateTo("UCourseManagement.fxml", "Course Management", "course");
    }

    @FXML private void loadStudentManagement() {
        navigateTo("UStudentManagement.fxml", "Student Management", "student");
    }

    @FXML private void loadFacultyManagement() {
        navigateTo("UFacultyManagement.fxml", "Faculty Management", "faculty");
    }

    @FXML private void loadEventManagement() {
        updateCalendar(); // Refresh
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
