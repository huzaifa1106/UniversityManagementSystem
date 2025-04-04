/**
 * File: FUserManagementController.java
 * Description: This controller handles the faculty profile view in the University Management System.
 *              It shows faculty details, the courses they teach, and allows navigation to other modules
 *              such as Course and Event Management.
 * Author: Group 10
 * Date: April 2025
 */

package GUI.CompanyLogin;

import Backend.Course;
import Backend.Faculty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FUserManagementController {

    // UI elements for displaying faculty profile
    @FXML private Label facultyNameLabel;
    @FXML private Label facultyEmailLabel;
    @FXML private Label facultyDegreeLabel;
    @FXML private Label facultyOfficeLabel;
    @FXML private Label facultyResearchLabel;
    @FXML private Label facultyCoursesLabel;

    // Table to display the courses this faculty teaches
    @FXML private TableView<Course> facultyCourseTable;
    @FXML private TableColumn<Course, String> colCourseID;
    @FXML private TableColumn<Course, String> colCourseName;
    @FXML private TableColumn<Course, String> colSectionID;
    @FXML private TableColumn<Course, String> colLocation;

    private Faculty loggedInFaculty;

    /**
     * Called by the login or previous screen to inject the logged-in faculty.
     * Also populates the profile information and course table.
     */
    public void setFaculty(Faculty faculty) {
        this.loggedInFaculty = faculty;
        loadFacultyInfo();
        loadCourseTable();
    }

    /**
     * Loads faculty's personal details into labels.
     */
    private void loadFacultyInfo() {
        facultyNameLabel.setText(loggedInFaculty.getName());
        facultyEmailLabel.setText(loggedInFaculty.getEmail());
        facultyDegreeLabel.setText(loggedInFaculty.getDegree());
        facultyOfficeLabel.setText(loggedInFaculty.getOfficeLocation());
        facultyResearchLabel.setText(loggedInFaculty.getResearchInterest());
        facultyCoursesLabel.setText(loggedInFaculty.getCoursesAsString());
    }

    /**
     * Displays all the courses that the faculty is teaching in a table.
     */
    private void loadCourseTable() {
        List<Course> facultyCourses = Course.getCourseList().stream()
                .filter(course -> course.getTeacherName().equalsIgnoreCase(loggedInFaculty.getName()))
                .collect(Collectors.toList());

        colCourseID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCourseID())));
        colCourseName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourseName()));
        colSectionID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSectionNumber())));
        colLocation.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));

        facultyCourseTable.setItems(FXCollections.observableArrayList(facultyCourses));
    }

    // Navigation buttons (from sidebar)

    /**
     * Navigates to the faculty's course management screen.
     */
    @FXML
    private void loadCourseManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FCourseManagement.fxml"));
            Parent root = loader.load();

            FCourseManagementController controller = loader.getController();
            controller.setFaculty(loggedInFaculty);

            Stage stage = (Stage) facultyCourseTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Faculty Course Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not load Course Management.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Refreshes and reloads the faculty profile screen.
     */
    @FXML
    private void loadUserManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FUserManagement.fxml"));
            Parent root = loader.load();

            FUserManagementController controller = loader.getController();
            controller.setFaculty(loggedInFaculty);

            Stage stage = (Stage) facultyCourseTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Faculty Profile");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not reload Faculty Profile.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Navigates to the event management calendar for faculty.
     */
    @FXML
    private void loadEventManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FEventManagement.fxml"));
            Parent root = loader.load();

            FEventManagementController controller = loader.getController();
            controller.setFaculty(loggedInFaculty);

            Stage stage = (Stage) facultyCourseTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Event Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not load Event Management.", Alert.AlertType.ERROR);
        }
    }

    // Utility methods

    /**
     * Shows an alert pop-up with the specified title, message, and type.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a quick error message (helper method).
     */
    private void showError(String message) {
        showAlert("Error", message, Alert.AlertType.ERROR);
    }
}
