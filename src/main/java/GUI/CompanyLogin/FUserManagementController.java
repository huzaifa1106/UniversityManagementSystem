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

    @FXML private Label facultyNameLabel;
    @FXML private Label facultyEmailLabel;
    @FXML private Label facultyDegreeLabel;
    @FXML private Label facultyOfficeLabel;
    @FXML private Label facultyResearchLabel;
    @FXML private Label facultyCoursesLabel;

    @FXML private TableView<Course> facultyCourseTable;
    @FXML private TableColumn<Course, String> colCourseID;
    @FXML private TableColumn<Course, String> colCourseName;
    @FXML private TableColumn<Course, String> colSectionID;
    @FXML private TableColumn<Course, String> colLocation;

    private Faculty loggedInFaculty;

    public void setFaculty(Faculty faculty) {
        this.loggedInFaculty = faculty;
        loadFacultyInfo();
        loadCourseTable();
    }

    private void loadFacultyInfo() {
        facultyNameLabel.setText(loggedInFaculty.getName());
        facultyEmailLabel.setText(loggedInFaculty.getEmail());
        facultyDegreeLabel.setText(loggedInFaculty.getDegree());
        facultyOfficeLabel.setText(loggedInFaculty.getOfficeLocation());
        facultyResearchLabel.setText(loggedInFaculty.getResearchInterest());
        facultyCoursesLabel.setText(loggedInFaculty.getCoursesAsString());
    }

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

    @FXML
    private void loadUserManagement() {
        setFaculty(loggedInFaculty); // Refresh view
    }

    @FXML
    private void loadCourseManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/FCourseManagement.fxml"));
            Parent root = loader.load();

            FCourseManagementController controller = loader.getController();
            controller.setFacultyName(loggedInFaculty.getName());

            Stage stage = (Stage) facultyNameLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Faculty Course Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
