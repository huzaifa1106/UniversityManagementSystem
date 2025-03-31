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
        // Lets admin select more than one course
        courseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Tell each column what data from the Course object it should show
        colCourseName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourseName()));

        colCourseID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCourseID())));

        colSection.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getSectionNumber())));

        colSubject.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSubjectName()));

        colInstructor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTeacherName()));

        // Loads courses into table
        courseTable.setItems(FXCollections.observableArrayList(Course.getCourseList()));
    }

    // lets us know which faculty we are selecting
    public void setFaculty(Faculty faculty) {
        this.selectedFaculty = faculty;
    }

    @FXML
    private void assignSelectedCourse() {
        // Grab all the courses the admin selected in the table
        List<Course> selectedCourses = courseTable.getSelectionModel().getSelectedItems();

        // stop if nothing selected or faculty isnt set somehow
        if (selectedCourses.isEmpty() || selectedFaculty == null) {
            showAlert("Please select one or more courses to assign.");
            return;
        }

        // copies the faculty course list so we can change it
        List<String> currentCourses = new ArrayList<>(selectedFaculty.getCoursesOffered());

        for (Course selectedCourse : selectedCourses) {
            // Update the course so this faculty member is now teaching it
            selectedCourse.changeTeacherName(selectedFaculty.getName());

            // If the faculty doesn't already have this course in their list, add it
            if (!currentCourses.contains(selectedCourse.getCourseName())) {
                currentCourses.add(selectedCourse.getCourseName());
            }
        }

        // saves the updated course list back to the faculty
        selectedFaculty.setCoursesOffered(currentCourses);

        courseTable.refresh();

        showAlert("Selected courses assigned successfully.");

        Stage stage = (Stage) courseTable.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
