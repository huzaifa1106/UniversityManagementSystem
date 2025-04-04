/*
 * File: ADashboardController.java
 * Purpose: Admin-side dashboard to view relevant/recent activity
 * Features: # of Students, Course, Faculities and Events
 * Author: Group 10
 * Date: April 2025
 */


package GUI.CompanyLogin;

//Imports
import Backend.Course;
import Backend.Event;
import Backend.Faculty;
import Backend.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ADashboardController implements Initializable {

    @FXML private Label totalStudentsLabel;
    @FXML private Label totalCoursesLabel;
    @FXML private Label totalFacultiesLabel;
    @FXML private Label totalEventsLabel;

    @FXML private ListView<String> notificationsList;

    @FXML private TableView<EventTableItem> upcomingEventsTable;
    @FXML private TableColumn<EventTableItem, String> colEventName;
    @FXML private TableColumn<EventTableItem, String> colEventDate;

    @FXML private TableView<RegistrationTableItem> recentRegistrationsTable;
    @FXML private TableColumn<RegistrationTableItem, String> colRegStudentName;
    @FXML private TableColumn<RegistrationTableItem, String> colRegCourse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDashboardData();
    }

    //Loading data into dashboard
    private void loadDashboardData() {
        List<Student> students = Student.getStudentList();
        List<Course> courses = Course.getCourseList();
        List<Faculty> faculties = Faculty.getFacultyList();
        List<Event> events = Event.getEventList();

        //Setting labels for total # of objects
        totalStudentsLabel.setText(String.valueOf(students.size()));
        totalCoursesLabel.setText(String.valueOf(courses.size()));
        totalFacultiesLabel.setText(String.valueOf(faculties.size()));
        totalEventsLabel.setText(String.valueOf(events.size()));

        //Static Array for Notifications
        notificationsList.setItems(FXCollections.observableArrayList(
                "Welcome to the Admin Panel",
                "Upcoming event: Orientation",
                "Faculty updates available",
                "New students registered"
        ));

        // Event Formatting/Outputting
        ObservableList<EventTableItem> eventItems = FXCollections.observableArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Event e : events) {
            eventItems.add(new EventTableItem(e.getEventName(), sdf.format(e.getDateTime())));
        }
        colEventName.setCellValueFactory(data -> data.getValue().eventNameProperty());
        colEventDate.setCellValueFactory(data -> data.getValue().eventDateProperty());
        upcomingEventsTable.setItems(eventItems);

        // Course Formatting/Outputting
        ObservableList<RegistrationTableItem> registrationItems = FXCollections.observableArrayList();
        for (Student s : students) {
            for (Course c : s.getEnrolledCourses()) {
                registrationItems.add(new RegistrationTableItem(s.getFullName(), c.getCourseName()));
            }
        }
        colRegStudentName.setCellValueFactory(data -> data.getValue().studentNameProperty());
        colRegCourse.setCellValueFactory(data -> data.getValue().courseNameProperty());
        recentRegistrationsTable.setItems(registrationItems);
    }

    public static class EventTableItem {
        // These are JavaFX string properties. Required for TableView data binding.
        private final javafx.beans.property.SimpleStringProperty eventName;
        private final javafx.beans.property.SimpleStringProperty eventDate;

        // Constructor accepts regular strings and wraps them in JavaFX properties
        public EventTableItem(String name, String date) {
            this.eventName = new javafx.beans.property.SimpleStringProperty(name); // Set the event name
            this.eventDate = new javafx.beans.property.SimpleStringProperty(date); // Set the event date (formatted as String)
        }

        // Return JavaFX properties for TableView to attach to the correct column
        public javafx.beans.property.StringProperty eventNameProperty() {
            return eventName;
        }

        public javafx.beans.property.StringProperty eventDateProperty() {
            return eventDate;
        }
    }
    public static class RegistrationTableItem {
        // These properties store a student's name and the course they registered for
        private final javafx.beans.property.SimpleStringProperty studentName;
        private final javafx.beans.property.SimpleStringProperty courseName;

        // Constructor wraps given values in observable properties
        public RegistrationTableItem(String student, String course) {
            this.studentName = new javafx.beans.property.SimpleStringProperty(student); // Student full name
            this.courseName = new javafx.beans.property.SimpleStringProperty(course);   // Course name
        }

        // These properties are exposed so TableColumns can bind to them
        public javafx.beans.property.StringProperty studentNameProperty() {
            return studentName;
        }

        public javafx.beans.property.StringProperty courseNameProperty() {
            return courseName;
        }
    }


    //Navigation Functions
    @FXML
    private void loadDashboard() {
        navigateTo("ADashboard.fxml");
    }

    @FXML
    private void loadSubjectManagement() {
        navigateTo("ASubjectManagement.fxml");
    }

    @FXML
    private void loadCourseManagement() {
        navigateTo("ACourseManagement.fxml");
    }

    @FXML
    private void loadStudentManagement() {
        navigateTo("AStudentManagement.fxml");
    }

    @FXML
    private void loadFacultyManagement() {
        navigateTo("AFacultyManagement.fxml");
    }

    @FXML
    private void loadEventManagement() {
        navigateTo("AEventManagement.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            Window currentWindow = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            if (currentWindow instanceof Stage) {
                Stage stage = (Stage) currentWindow;
                stage.setScene(new Scene(root));
                stage.setTitle(fxmlFile.replace(".fxml", " - Admin Panel"));
                stage.show();
            } else {
                System.out.println("Error: No active window found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}