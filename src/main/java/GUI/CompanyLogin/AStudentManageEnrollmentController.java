/*
 * File: AStudentManageEnrollmentController.java
 * Description: Controller to assign or remove course enrollments for a student.
 * UI handles selecting a student, enrolling them in courses, and saving changes.
 * Author: Group 10
 * Date: April 2025
 */

package GUI.CompanyLogin;

import Backend.Course;
import Backend.ReadExcelFile;
import Backend.Student;
import Backend.Subject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AStudentManageEnrollmentController {

    @FXML private ListView<String> studentListView;
    @FXML private Label studentNameLabel;
    @FXML private Label studentIDLabel;
    @FXML private TextArea coursesTextArea;
    @FXML private ComboBox<String> addCourseComboBox;
    @FXML private ComboBox<String> removeCourseComboBox;

    private Student selectedStudent;

    @FXML
    private void initialize() {
        // Fill student list view with student names
        for (Student student : Student.getStudentList()) {
            studentListView.getItems().add(student.getFullName());
        }

        // When a student is selected, load their data
        studentListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= 0) {
                selectedStudent = Student.getStudentList().get(newVal.intValue());
                updateStudentInfo();
                populateAddableCourses();
            }
        });
    }

    private void updateStudentInfo() {
        if (selectedStudent == null) return;

        studentNameLabel.setText("Name: " + selectedStudent.getFullName());
        studentIDLabel.setText("ID: " + selectedStudent.getStudentID());

        StringBuilder courseText = new StringBuilder();
        removeCourseComboBox.getItems().clear();

        for (Course c : selectedStudent.getEnrolledCourses()) {
            courseText.append("- ").append(c.getCourseName()).append(" (ID: ").append(c.getCourseID()).append(")\n");
            removeCourseComboBox.getItems().add(c.getCourseName());
        }

        coursesTextArea.setText(courseText.toString());
    }

    private void populateAddableCourses() {
        addCourseComboBox.getItems().clear();
        if (selectedStudent == null) return;

        for (Course course : Course.getCourseList()) {
            if (!selectedStudent.getEnrolledCourses().contains(course)) {
                addCourseComboBox.getItems().add(course.getCourseName());
            }
        }
    }

    @FXML
    private void addCourse() {
        if (selectedStudent == null) return;

        String selectedCourseName = addCourseComboBox.getValue();
        if (selectedCourseName == null) return;

        Course course = Course.getCourseList().stream()
                .filter(c -> c.getCourseName().equalsIgnoreCase(selectedCourseName))
                .findFirst()
                .orElse(null);

        if (course != null && !selectedStudent.getEnrolledCourses().contains(course)) {
            if (selectedStudent.hasTimeConflictWith(course)) {
                showAlert("Time Conflict", "Student is already enrolled in a conflicting course.", Alert.AlertType.WARNING);
                return;
            }

            // Add course to student and vice versa
            selectedStudent.getEnrolledCourses().add(course);
            course.getEnrolledStudents().add(selectedStudent);

            // Add subject if needed
            Subject subject = Subject.findSubjectByCode(course.getSubjectName());
            if (subject != null && !selectedStudent.getEnrolledSubjects().contains(subject)) {
                selectedStudent.getEnrolledSubjects().add(subject);
            }

            ReadExcelFile.writeToExcel(); // Save changes

            showAlert("Success", selectedCourseName + " added for " + selectedStudent.getFullName(), Alert.AlertType.INFORMATION);
            updateStudentInfo();
            populateAddableCourses();
        }
    }

    @FXML
    private void removeCourse() {
        if (selectedStudent == null) return;

        String selectedCourseName = removeCourseComboBox.getValue();
        if (selectedCourseName == null) return;

        Course course = Course.getCourseList().stream()
                .filter(c -> c.getCourseName().equalsIgnoreCase(selectedCourseName))
                .findFirst()
                .orElse(null);

        if (course != null) {
            selectedStudent.getEnrolledCourses().remove(course);
            course.getEnrolledStudents().remove(selectedStudent);

            ReadExcelFile.writeToExcel(); // Save changes

            updateStudentInfo();
            populateAddableCourses();
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) studentListView.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
