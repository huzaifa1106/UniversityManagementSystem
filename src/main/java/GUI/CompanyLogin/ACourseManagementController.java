/**
 *  File: ACourseManagementController.java
 *  Description: This controller manages the admin interface for course management,
 *  including adding, editing, deleting, and viewing course information.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

import Backend.Course;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ACourseManagementController extends ASubjectManagementController {

    @FXML private TableColumn<Course, Integer> colCourseID, colSectionNumber;
    @FXML private TableColumn<Course, String> colCourseName, colTeacherName;

    @FXML private TableView<Course> courseDetailsTable;
    @FXML private TableColumn<Course, String> colSubjectName, colLocation, colLectureDay, colLectureTime, colFinalExamDate;

    @FXML private TextField courseNameField, courseIDField, instructorField, sectionField, subjectField,
            locationField, lectureDayField, finalExamDateField;

    @FXML private Button addCourseButton, editCourseButton, deleteCourseButton, viewCoursesButton, manageEnrollmentsButton;

    private Course selectedCourse = null;

    // Initializes the table view and sets up bindings for each column
    @FXML
    private void initialize() {
        colCourseID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCourseID()).asObject());
        colCourseName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseName()));
        colSectionNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSectionNumber()).asObject());
        colTeacherName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeacherName()));

        colSubjectName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectName()));
        colLocation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        colLectureDay.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLectureDay()));
        colLectureTime.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getLectureStartTime() + " - " + cellData.getValue().getLectureEndTime()));
        colFinalExamDate.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFinalExamDate() != null ? cellData.getValue().getFinalExamDate().toString() : "Not Assigned"));

        loadCoursesIntoTable();

        courseDetailsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedCourse = newVal;
            if (newVal != null) fillFormWithCourseData(newVal);
        });
    }

    // Loads all courses into the table view
    private void loadCoursesIntoTable() {
        courseDetailsTable.getItems().setAll(Course.getCourseList());
    }

    // Fills form fields with selected course data
    private void fillFormWithCourseData(Course course) {
        courseNameField.setText(course.getCourseName());
        courseIDField.setText(String.valueOf(course.getCourseID()));
        instructorField.setText(course.getTeacherName());
        sectionField.setText(String.valueOf(course.getSectionNumber()));
        subjectField.setText(course.getSubjectName());
        locationField.setText(course.getLocation());
        lectureDayField.setText(course.getLectureDay());
        finalExamDateField.setText(course.getFinalExamDate() != null ? course.getFinalExamDate().toString() : "");
    }

    // Handles adding a new course
    @FXML
    private void addCourse() {
        try {
            if (courseNameField.getText().isEmpty() || instructorField.getText().isEmpty() ||
                    sectionField.getText().isEmpty() || subjectField.getText().isEmpty() ||
                    locationField.getText().isEmpty() || lectureDayField.getText().isEmpty()) {
                showAlert("Missing Fields", "Please fill in all required fields.", Alert.AlertType.WARNING);
                return;
            }

            int sectionNumber = Integer.parseInt(sectionField.getText());
            Course newCourse;

            if (courseIDField.getText().isEmpty()) {
                newCourse = new Course(courseNameField.getText(), subjectField.getText(), sectionNumber,
                        instructorField.getText(), 50, locationField.getText(), lectureDayField.getText(),
                        900, 1100, null);
            } else {
                int courseID = Integer.parseInt(courseIDField.getText());

                if (Course.findCourseByID(courseID) != null) {
                    showAlert("Duplicate ID", "Course with this ID already exists.", Alert.AlertType.ERROR);
                    return;
                }

                newCourse = new Course(courseID, courseNameField.getText(), subjectField.getText(), sectionNumber,
                        instructorField.getText(), 50, locationField.getText(), lectureDayField.getText(),
                        900, 1100, null);
            }

            Course.addCourse(newCourse);
            loadCoursesIntoTable();
            clearFields();
            showAlert("Success", "Course added successfully!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Section and Course ID (if provided) must be numeric.", Alert.AlertType.ERROR);
        }
    }

    // Handles editing the selected course
    @FXML
    private void editCourse() {
        if (selectedCourse != null) {
            try {
                selectedCourse.changeCourseName(courseNameField.getText());
                selectedCourse.changeTeacherName(instructorField.getText());
                selectedCourse.changeLocation(locationField.getText());
                selectedCourse.changeCourseCapacity(Integer.parseInt(sectionField.getText()));
                loadCoursesIntoTable();
                clearFields();
                showAlert("Success", "Course updated successfully.", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Section must be numeric.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("No Selection", "Please select a course to edit.", Alert.AlertType.WARNING);
        }
    }

    // Handles deleting the selected course
    @FXML
    private void deleteCourse() {
        if (selectedCourse != null) {
            Course.removeCourse(selectedCourse.getCourseID());
            loadCoursesIntoTable();
            clearFields();
            selectedCourse = null;
            showAlert("Success", "Course deleted successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("No Selection", "Please select a course to delete.", Alert.AlertType.WARNING);
        }
    }

    // Displays information about the selected course
    @FXML
    private void viewCourses() {
        if (selectedCourse != null) {
            showAlert("Viewing Course", "Course: " + selectedCourse.getCourseName(), Alert.AlertType.INFORMATION);
        } else {
            showAlert("No Selection", "Please select a course to view.", Alert.AlertType.WARNING);
        }
    }

    // Placeholder for managing enrollments
    @FXML
    private void manageEnrollments() {
        showAlert("Info", "Manage enrollments clicked.", Alert.AlertType.INFORMATION);
    }

    // Clears all input fields
    private void clearFields() {
        courseNameField.clear();
        courseIDField.clear();
        instructorField.clear();
        sectionField.clear();
        subjectField.clear();
        locationField.clear();
        lectureDayField.clear();
        finalExamDateField.clear();
    }

    // Utility method to display alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}