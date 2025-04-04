/**
 * ACourseManageEnrollmentController.java
 *
 * This controller handles the enrollment of students into specific courses.
 * Admins can view which students are currently enrolled in a selected course,
 * and can add or remove students accordingly. All changes are persisted to Excel.
 *
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

public class ACourseManageEnrollmentController {

    @FXML private ListView<String> courseListView;
    @FXML private Label courseNameLabel;
    @FXML private TextArea studentsTextArea;
    @FXML private ComboBox<String> addStudentComboBox;
    @FXML private ComboBox<String> removeStudentComboBox;

    private Course selectedCourse;

    @FXML
    private void initialize() {
        // Load all course names into the list view
        for (Course course : Course.getCourseList()) {
            courseListView.getItems().add(course.getCourseName());
        }

        // Update UI when a course is selected
        courseListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= 0) {
                selectedCourse = Course.getCourseList().get(newVal.intValue());
                updateCourseInfo();
                populateAddableStudents();
            }
        });

        // Initialize student combo box on load (for default selection)
        populateAddableStudents();
    }

    /**
     * Fills the 'Add Student' dropdown with students not already enrolled in the selected course.
     */
    private void populateAddableStudents() {
        addStudentComboBox.getItems().clear();

        if (selectedCourse == null) return;

        for (Student student : Student.getStudentList()) {
            if (!selectedCourse.getEnrolledStudents().contains(student)) {
                addStudentComboBox.getItems().add(student.getFullName());
            }
        }
    }

    /**
     * Updates the course label and list of currently enrolled students.
     */
    private void updateCourseInfo() {
        if (selectedCourse == null) return;

        courseNameLabel.setText("Course: " + selectedCourse.getCourseName());
        StringBuilder sb = new StringBuilder();
        removeStudentComboBox.getItems().clear();

        for (Student student : selectedCourse.getEnrolledStudents()) {
            sb.append("- ").append(student.getFullName()).append("\n");
            removeStudentComboBox.getItems().add(student.getFullName());
        }

        studentsTextArea.setText(sb.toString());
    }

    /**
     * Adds a selected student to the selected course, after checking for conflicts.
     */
    @FXML
    private void addStudent() {
        if (selectedCourse == null) return;

        String selectedName = addStudentComboBox.getValue();
        if (selectedName == null) return;

        Student student = Student.getStudentList().stream()
                .filter(s -> s.getFullName().equalsIgnoreCase(selectedName))
                .findFirst()
                .orElse(null);

        if (student == null || student.getEnrolledCourses().contains(selectedCourse)) return;

        // Prevent overlapping schedules
        if (student.hasTimeConflictWith(selectedCourse)) {
            showAlert("Time Conflict", "Student is already enrolled in a conflicting course.", Alert.AlertType.WARNING);
            return;
        }

        // Enroll student
        selectedCourse.getEnrolledStudents().add(student);
        student.getEnrolledCourses().add(selectedCourse);

        // Link subject (if needed)
        Subject subject = Subject.findSubjectByCode(selectedCourse.getSubjectName());
        if (subject != null && !student.getEnrolledSubjects().contains(subject)) {
            student.getEnrolledSubjects().add(subject);
        }

        ReadExcelFile.writeToExcel(); // Persist data
        updateCourseInfo();
        populateAddableStudents();

        showAlert("Success", student.getFullName() + " has been added to the course.", Alert.AlertType.INFORMATION);
    }

    /**
     * Removes a selected student from the current course.
     */
    @FXML
    private void removeStudent() {
        if (selectedCourse == null) return;

        String selectedName = removeStudentComboBox.getValue();
        if (selectedName == null) return;

        Student student = Student.getStudentList().stream()
                .filter(s -> s.getFullName().equalsIgnoreCase(selectedName))
                .findFirst()
                .orElse(null);

        if (student == null) return;

        selectedCourse.getEnrolledStudents().remove(student);
        student.getEnrolledCourses().remove(selectedCourse);

        ReadExcelFile.writeToExcel(); // Save changes
        updateCourseInfo();
        populateAddableStudents();
    }

    /**
     * Closes the enrollment management window.
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) courseListView.getScene().getWindow();
        stage.close();
    }

    /**
     * Shows an alert dialog with the given title and message.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
