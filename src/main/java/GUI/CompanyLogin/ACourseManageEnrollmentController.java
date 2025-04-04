package GUI.CompanyLogin;

import Backend.Course;
import Backend.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ACourseManageEnrollmentController {

    @FXML private ListView<String> courseListView;
    @FXML private Label courseNameLabel;
    @FXML private TextArea studentsTextArea;
    @FXML private ComboBox<String> addStudentComboBox;
    @FXML private ComboBox<String> removeStudentComboBox;

    private Course selectedCourse;

    @FXML
    private void initialize() {
        for (Course course : Course.getCourseList()) {
            courseListView.getItems().add(course.getCourseName());
        }

        courseListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= 0) {
                selectedCourse = Course.getCourseList().get(newVal.intValue());
                updateCourseInfo();
            }
        });

        for (Student student : Student.getStudentList()) {
            addStudentComboBox.getItems().add(student.getFullName());
        }
    }

    private void updateCourseInfo() {
        courseNameLabel.setText("Course: " + selectedCourse.getCourseName());

        StringBuilder sb = new StringBuilder();
        removeStudentComboBox.getItems().clear();

        for (Student s : selectedCourse.getEnrolledStudents()) {
            sb.append("- ").append(s.getFullName()).append("\n");
            removeStudentComboBox.getItems().add(s.getFullName());
        }

        studentsTextArea.setText(sb.toString());
    }

    @FXML
    private void addStudent() {
        if (selectedCourse == null) return;

        String selectedName = addStudentComboBox.getValue();
        if (selectedName == null) return;

        Student student = Student.getStudentList().stream()
                .filter(s -> s.getFullName().equalsIgnoreCase(selectedName))
                .findFirst().orElse(null);

        if (student != null && !selectedCourse.getEnrolledStudents().contains(student)) {
            selectedCourse.getEnrolledStudents().add(student);
            student.getEnrolledCourses().add(selectedCourse);
            updateCourseInfo();
        }
    }

    @FXML
    private void removeStudent() {
        if (selectedCourse == null) return;

        String selectedName = removeStudentComboBox.getValue();
        if (selectedName == null) return;

        Student student = Student.getStudentList().stream()
                .filter(s -> s.getFullName().equalsIgnoreCase(selectedName))
                .findFirst().orElse(null);

        if (student != null) {
            selectedCourse.getEnrolledStudents().remove(student);
            student.getEnrolledCourses().remove(selectedCourse);
            updateCourseInfo();
        }
    }

    @FXML
    private void closeWindow() {
        ((Stage) courseListView.getScene().getWindow()).close();
    }
}