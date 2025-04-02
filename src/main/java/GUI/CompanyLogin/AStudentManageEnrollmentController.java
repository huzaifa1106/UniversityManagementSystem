package GUI.CompanyLogin;

import Backend.Course;
import Backend.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class AStudentManageEnrollmentController {

    @FXML private ListView<String> studentListView;
    @FXML private Label studentNameLabel;
    @FXML private Label studentIDLabel;
    @FXML private TextArea coursesTextArea;
    @FXML private ComboBox<String> addCourseComboBox;
    @FXML private ComboBox<String> removeCourseComboBox;

    private List<Student> students = Student.getStudentList();
    private Student selectedStudent;

    @FXML
    private void initialize() {
        for (Student s : students) {
            studentListView.getItems().add(s.getFullName());
        }

        studentListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= 0) {
                selectedStudent = students.get(newVal.intValue());
                updateStudentInfo();
            }
        });

        // Load all courses into the Add Course dropdown
        for (Course c : Course.getCourseList()) {
            addCourseComboBox.getItems().add(c.getCourseName());
        }
    }

    private void updateStudentInfo() {
        studentNameLabel.setText("Name: " + selectedStudent.getFullName());
        studentIDLabel.setText("ID: " + selectedStudent.getStudentID());
        coursesTextArea.setText(selectedStudent.getCourseNamesAsString());

        // Refresh removeCourseComboBox with current enrolled courses
        removeCourseComboBox.getItems().clear();
        for (Course c : selectedStudent.getEnrolledCourses()) {
            removeCourseComboBox.getItems().add(c.getCourseName());
        }
    }

    @FXML
    private void addCourse() {
        if (selectedStudent == null) return;

        String courseName = addCourseComboBox.getValue();
        if (courseName == null) {
            showAlert("Please select a course to add.");
            return;
        }

        Course course = Course.findCourse(courseName);
        if (course != null && !selectedStudent.checkDuplicateCourse(course)) {
            selectedStudent.updateEnrolledCourses(course);
            Student.saveAllStudents();
            updateStudentInfo();
        } else {
            showAlert("Course not found or already enrolled.");
        }
    }

    @FXML
    private void removeCourse() {
        if (selectedStudent == null) return;

        String courseName = removeCourseComboBox.getValue();
        if (courseName == null) {
            showAlert("Please select a course to remove.");
            return;
        }

        selectedStudent.getEnrolledCourses().removeIf(c -> c.getCourseName().equalsIgnoreCase(courseName));
        Student.saveAllStudents();
        updateStudentInfo();
    }


    @FXML
    private void closeWindow() {
        Stage stage = (Stage) studentListView.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }
}
