/**
 * ACourseManagementController.java
 *
 * This controller handles the adding, deleting and editing of courses.
 *
 * Author: Group 10
 * Date: April 2025
 */

package GUI.CompanyLogin;

import Backend.Course;
import Backend.Faculty;
import Backend.ReadExcelFile;
import Backend.Subject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ACourseManagementController extends ASubjectManagementController {

    // UI components (linked to FXML)
    @FXML private TableView<Course> courseDetailsTable;
    @FXML private TableColumn<Course, Integer> colCourseID, colSectionNumber;
    @FXML private TableColumn<Course, String> colCourseName, colTeacherName;
    @FXML private TableColumn<Course, String> colSubjectName, colLocation, colLectureDay, colLectureTime, colFinalExamDate;

    @FXML private TextField courseNameField, courseIDField, sectionField, locationField;
    @FXML private TextField startTimeField, endTimeField;
    @FXML private ComboBox<String> instructorComboBox, lectureDayComboBox, subjectComboBox;
    @FXML private DatePicker finalExamDatePicker;

    private Course selectedCourse = null;

    @FXML
    private void initialize() {
        setupTableColumns();
        populateInstructorAndSubjectLists();
        loadCoursesIntoTable();

        // Load course data when selected
        courseDetailsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedCourse = newVal;
            if (newVal != null) fillFormWithCourseData(newVal);
        });
    }

    private void setupTableColumns() {
        colCourseID.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getCourseID()).asObject());
        colCourseName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseName()));
        colSectionNumber.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getSectionNumber()).asObject());
        colTeacherName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTeacherName()));
        colSubjectName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSubjectName()));
        colLocation.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLocation()));
        colLectureDay.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLectureDay()));
        colLectureTime.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getLectureStartTime() + " - " + c.getValue().getLectureEndTime()));
        colFinalExamDate.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getFinalExamDate() != null ?
                        c.getValue().getFinalExamDate().toLocalDate().toString() : "Not Assigned"));
    }

    private void populateInstructorAndSubjectLists() {
        for (Faculty f : Faculty.getFacultyList()) {
            instructorComboBox.getItems().add(f.getName());
        }

        lectureDayComboBox.getItems().addAll(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
                "Mon/Wed", "Tue/Thu", "Mon/Wed/Fri", "Tue/Thu/Fri"
        );

        for (Subject s : Subject.getSubjectList()) {
            subjectComboBox.getItems().add(s.getSubjectName() + " (" + s.getSubjectCode() + ")");
        }
    }

    private void loadCoursesIntoTable() {
        courseDetailsTable.getItems().setAll(Course.getCourseList());
    }

    private void fillFormWithCourseData(Course course) {
        courseNameField.setText(course.getCourseName());
        courseIDField.setText(String.valueOf(course.getCourseID()));
        instructorComboBox.setValue(course.getTeacherName());
        sectionField.setText(String.valueOf(course.getSectionNumber()));
        subjectComboBox.setValue(course.getSubjectName() + " (" + course.getSubjectName() + ")");
        locationField.setText(course.getLocation());
        lectureDayComboBox.setValue(course.getLectureDay());
        finalExamDatePicker.setValue(course.getFinalExamDate() != null ? course.getFinalExamDate().toLocalDate() : null);
        startTimeField.setText(String.valueOf(course.getLectureStartTime()));
        endTimeField.setText(String.valueOf(course.getLectureEndTime()));
    }

    @FXML
    private void addCourse() {
        try {
            if (isFormInvalid()) {
                showAlert("Missing Fields", "Please fill in all required fields.", Alert.AlertType.WARNING);
                return;
            }

            int section = Integer.parseInt(sectionField.getText());
            int start = Integer.parseInt(startTimeField.getText().trim());
            int end = Integer.parseInt(endTimeField.getText().trim());

            if (start >= end) {
                showAlert("Invalid Time", "Start time must be before end time.", Alert.AlertType.ERROR);
                return;
            }

            String subjectCode = extractSubjectCode(subjectComboBox.getValue());
            LocalDateTime examDate = finalExamDatePicker.getValue() != null ? finalExamDatePicker.getValue().atStartOfDay() : null;

            Course course;
            if (courseIDField.getText().isEmpty()) {
                course = new Course(courseNameField.getText(), subjectCode, section,
                        instructorComboBox.getValue(), 50, locationField.getText(), lectureDayComboBox.getValue(),
                        start, end, examDate);
            } else {
                int id = Integer.parseInt(courseIDField.getText());
                if (Course.findCourseByID(id) != null) {
                    showAlert("Duplicate ID", "Course with this ID already exists.", Alert.AlertType.ERROR);
                    return;
                }
                course = new Course(id, courseNameField.getText(), subjectCode, section,
                        instructorComboBox.getValue(), 50, locationField.getText(), lectureDayComboBox.getValue(),
                        start, end, examDate);
            }

            Course.addCourse(course);
            ReadExcelFile.writeToExcel();
            loadCoursesIntoTable();
            clearFields();
            showAlert("Success", "Course added successfully!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "ID, section, and time fields must be numeric.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editCourse() {
        if (selectedCourse == null) {
            showAlert("No Selection", "Please select a course to edit.", Alert.AlertType.WARNING);
            return;
        }

        try {
            selectedCourse.changeCourseName(courseNameField.getText());
            selectedCourse.changeTeacherName(instructorComboBox.getValue());
            selectedCourse.changeLocation(locationField.getText());
            selectedCourse.changeCourseCapacity(Integer.parseInt(sectionField.getText()));
            selectedCourse.setLectureDay(lectureDayComboBox.getValue());
            selectedCourse.setLectureStartTime(Integer.parseInt(startTimeField.getText().trim()));
            selectedCourse.setLectureEndTime(Integer.parseInt(endTimeField.getText().trim()));
            selectedCourse.setFinalExamDate(finalExamDatePicker.getValue() != null ?
                    finalExamDatePicker.getValue().atStartOfDay() : null);

            ReadExcelFile.writeToExcel();
            loadCoursesIntoTable();
            clearFields();
            showAlert("Success", "Course updated successfully.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Start/end times and section must be numeric.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deleteCourse() {
        if (selectedCourse == null) {
            showAlert("No Selection", "Please select a course to delete.", Alert.AlertType.WARNING);
            return;
        }

        Course.removeCourse(selectedCourse.getCourseID());
        ReadExcelFile.writeToExcel();
        loadCoursesIntoTable();
        clearFields();
        selectedCourse = null;

        showAlert("Success", "Course deleted successfully.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void manageEnrollments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/ACourseManageEnrollment.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Course Enrollments");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            loadCoursesIntoTable(); // Refresh in case of changes
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load enrollment screen.", Alert.AlertType.ERROR);
        }
    }

    private boolean isFormInvalid() {
        return courseNameField.getText().isBlank()
                || instructorComboBox.getValue() == null
                || sectionField.getText().isBlank()
                || subjectComboBox.getValue() == null
                || locationField.getText().isBlank()
                || lectureDayComboBox.getValue() == null
                || startTimeField.getText().isBlank()
                || endTimeField.getText().isBlank();
    }

    private void clearFields() {
        courseNameField.clear();
        courseIDField.clear();
        instructorComboBox.setValue(null);
        lectureDayComboBox.setValue(null);
        sectionField.clear();
        subjectComboBox.setValue(null);
        locationField.clear();
        finalExamDatePicker.setValue(null);
        startTimeField.clear();
        endTimeField.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String extractSubjectCode(String display) {
        if (display == null || !display.contains("(")) return "";
        return display.substring(display.indexOf('(') + 1, display.indexOf(')')).trim();
    }
}
