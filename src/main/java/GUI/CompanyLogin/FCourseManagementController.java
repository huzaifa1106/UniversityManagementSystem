/**
 * File: FCourseManagementController.java
 * Description: Controls the Faculty-side course management interface.
 *              Displays a list of faculty-assigned courses, and allows navigation
 *              to manage enrollments and access related dashboards.
 * Author: Group 10
 * Last Updated: April 2025
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

public class FCourseManagementController {

    @FXML private TableView<Course> facultyCoursesTable;
    @FXML private TableColumn<Course, String> colCourseID;
    @FXML private TableColumn<Course, String> colCourseName;
    @FXML private TableColumn<Course, String> colSectionNumber;
    @FXML private TableColumn<Course, String> colSubjectName;
    @FXML private TableColumn<Course, String> colLocation;

    private Faculty loggedInFaculty;

    /**
     * Called by parent to provide the logged-in faculty member context.
     * @param faculty the logged-in Faculty object
     */
    public void setFaculty(Faculty faculty) {
        this.loggedInFaculty = faculty;
        loadFacultyCourses();
    }

    /**
     * Filters and displays the list of courses assigned to the current faculty member.
     */
    private void loadFacultyCourses() {
        List<Course> myCourses = Course.getCourseList().stream()
                .filter(c -> c.getTeacherName().equalsIgnoreCase(loggedInFaculty.getName()))
                .collect(Collectors.toList());

        colCourseID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCourseID())));
        colCourseName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourseName()));
        colSectionNumber.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSectionNumber())));
        colSubjectName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSubjectName()));
        colLocation.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));

        facultyCoursesTable.setItems(FXCollections.observableArrayList(myCourses));
    }

    /**
     * Opens a window to view and manage enrollments for the selected course.
     */
    @FXML
    private void manageEnrollments() {
        Course selected = facultyCoursesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a course to manage enrollments.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FCourseManageEnrollments.fxml"));
            Parent root = loader.load();

            FCourseManageEnrollmentsController controller = loader.getController();
            controller.setCourse(selected);

            Stage stage = new Stage();
            stage.setTitle("Manage Course Enrollments");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not open enrollment window.", Alert.AlertType.ERROR);
        }
    }

    // ---------- Navigation Buttons (Sidebar) ----------

    @FXML
    private void loadUserManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FUserManagement.fxml"));
            Parent root = loader.load();

            FUserManagementController controller = loader.getController();
            controller.setFaculty(loggedInFaculty);

            Stage stage = (Stage) facultyCoursesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Faculty Profile");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not load User Management screen.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void loadEventManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FEventManagement.fxml"));
            Parent root = loader.load();

            FEventManagementController controller = loader.getController();
            controller.setFaculty(loggedInFaculty);

            Stage stage = (Stage) facultyCoursesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Event Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not load Event Management screen.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void loadCourseManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FCourseManagement.fxml"));
            Parent root = loader.load();

            FCourseManagementController controller = loader.getController();
            controller.setFaculty(loggedInFaculty); // Refresh context

            Stage stage = (Stage) facultyCoursesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Course Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not reload Course Management screen.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Displays a simple alert dialog.
     */
    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
