/**
 * AAssignCoursesController.java
 *
 * This controller handles assigning courses to a selected faculty member.
 * Allows the user to select one or more courses from a table and link them
 * to the faculty. The course's instructor is updated accordingly, and the
 * faculty's course list is updated to reflect the changes.
 *
 * Author: Group 10
 * Date: April 2025
 */

package GUI.CompanyLogin;

import Backend.Course;
import Backend.Faculty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AAssignCoursesController {

    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> colCourseName;
    @FXML private TableColumn<Course, String> colCourseID;
    @FXML private TableColumn<Course, String> colSection;
    @FXML private TableColumn<Course, String> colSubject;
    @FXML private TableColumn<Course, String> colInstructor;

    private Faculty selectedFaculty;

    @FXML
    private void initialize() {
        // Allow multiple selection in the course table
        courseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Bind course fields to table columns
        colCourseName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourseName()));
        colCourseID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCourseID())));
        colSection.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSectionNumber())));
        colSubject.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSubjectName()));
        colInstructor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTeacherName()));

        // Load all courses into the table
        courseTable.setItems(FXCollections.observableArrayList(Course.getCourseList()));
    }

    /**
     * This method is called externally to define which faculty is being assigned courses.
     */
    public void setFaculty(Faculty faculty) {
        this.selectedFaculty = faculty;
    }

    /**
     * Assigns the selected courses from the table to the selected faculty.
     */
    @FXML
    private void assignSelectedCourse() {
        List<Course> selectedCourses = courseTable.getSelectionModel().getSelectedItems();

        if (selectedFaculty == null || selectedCourses.isEmpty()) {
            showAlert("Please select one or more courses to assign.");
            return;
        }

        // Start with the faculty's existing course list
        List<String> updatedCourses = new ArrayList<>(selectedFaculty.getCoursesOffered());

        for (Course course : selectedCourses) {
            // Set the faculty name as the instructor
            course.changeTeacherName(selectedFaculty.getName());

            // Add course name to faculty list if not already present
            if (!updatedCourses.contains(course.getCourseName())) {
                updatedCourses.add(course.getCourseName());
            }
        }

        // Save the updated course list back to the faculty object
        selectedFaculty.setCoursesOffered(updatedCourses);

        // Refresh table in case the instructor column was updated
        courseTable.refresh();

        showAlert("Selected courses assigned successfully.");

        // Close the current window
        Stage stage = (Stage) courseTable.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an information alert with the provided message.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
