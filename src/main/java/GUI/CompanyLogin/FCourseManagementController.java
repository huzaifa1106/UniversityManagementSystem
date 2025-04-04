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

    private String loggedInFacultyName;

    public void setFacultyName(String name) {
        this.loggedInFacultyName = name;
        loadFacultyCourses();
    }

    private void loadFacultyCourses() {
        List<Course> myCourses = Course.getCourseList().stream()
                .filter(c -> c.getTeacherName().equalsIgnoreCase(loggedInFacultyName))
                .collect(Collectors.toList());

        colCourseID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCourseID())));
        colCourseName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourseName()));
        colSectionNumber.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSectionNumber())));
        colSubjectName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSubjectName()));
        colLocation.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));

        facultyCoursesTable.setItems(FXCollections.observableArrayList(myCourses));
    }

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

    @FXML
    private void loadCourseManagement() {
        // Already on this screen
    }

    @FXML
    private void loadUserManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FUserManagement.fxml"));
            Parent root = loader.load();

            FUserManagementController controller = loader.getController();
            controller.setFaculty(Faculty.findByName(loggedInFacultyName));

            Stage stage = (Stage) facultyCoursesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Faculty Profile");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
