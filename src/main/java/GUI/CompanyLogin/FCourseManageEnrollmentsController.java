/**
 * File: FCourseManageEnrollmentsController.java
 * Purpose: Handles the UI logic for viewing which students are enrolled in a selected course.
 * Author: Group 10
 * Last Updated: April 2025
 *
 * Description:
 * This controller is launched as a popup window when a faculty member chooses
 * to view enrollments for a specific course. It simply displays a list of
 * students who are currently enrolled in that course.
 */

package GUI.CompanyLogin;

import Backend.Course;
import Backend.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class FCourseManageEnrollmentsController {

    @FXML private Label courseTitleLabel;
    @FXML private ListView<String> studentListView;

    private Course selectedCourse;

    /**
     * This method is called externally to provide the selected course
     * when opening this view.
     *
     * @param course The course whose student list will be shown
     */
    public void setCourse(Course course) {
        this.selectedCourse = course;

        // Update the label with the course name
        courseTitleLabel.setText("Students Enrolled in: " + course.getCourseName());

        // Populate the list with full names of enrolled students
        studentListView.setItems(FXCollections.observableArrayList(
                course.getEnrolledStudents()
                        .stream()
                        .map(Student::getFullName)
                        .toList()
        ));
    }

    /**
     * Closes the current popup window when the "Close" button is clicked.
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) courseTitleLabel.getScene().getWindow();
        stage.close();
    }
}
