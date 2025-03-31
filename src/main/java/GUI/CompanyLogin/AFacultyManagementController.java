/**
 *  File: AFacultyManagementController.java
 *  Description: This controller manages faculty data within the admin panel,
 *  including adding, editing, and deleting faculty members as well as viewing details.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

import Backend.Faculty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AFacultyManagementController {

    @FXML private TextField facultyNameField, facultyEmailField, facultyDegreeField, facultyOfficeField,
            facultyResearchField, facultyCoursesField;

    @FXML private TableView<Faculty> facultyTable;
    @FXML private TableColumn<Faculty, String> colFacultyID, colFacultyName, colFacultyEmail, colFacultyDegree,
            colFacultyResearch, colFacultyOffice;

    private Faculty selectedFaculty;

    // Static list of sample faculty members
    private static final List<Faculty> facultyList = new ArrayList<>();

    // Initialize sample data
    static {
        facultyList.add(new Faculty("F0001", "Dr. Alan Turing", null, "Ph.D.",
                "Computational Theory", List.of("CS101"), "turing@university.edu", "Room 201"));
        facultyList.add(new Faculty("F0002", "Prof. Emily Brontë",null, "Master's",
                "English Literature", List.of("ENG101"), "bronte@university.edu", "Room 202"));
        facultyList.add(new Faculty("F0003", "Dr. Grace Hopper", null, "Ph.D.",
                "Computer Programming", List.of("CS201"), "hopper@university.edu", "Lab 203"));
        facultyList.add(new Faculty("F0004", "Dr. Lakyn Copeland", null, "Master's",
                "English Literature", List.of("ENG102"), "copeland@university.edu", "Room 201"));
        facultyList.add(new Faculty("F0005", "Albozr Gharabaghi", null, "Ph.D.",
                "Water and Soil", List.of("ENGG402"), "gharabaghi@university.edu", "Lab 202"));
    }

    // Initializes the table and selection listeners
    @FXML
    private void initialize() {
        colFacultyID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFacultyID()));
        colFacultyName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colFacultyEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        colFacultyDegree.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDegree()));
        colFacultyResearch.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResearchInterest()));
        colFacultyOffice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOfficeLocation()));

        facultyTable.getItems().setAll(facultyList);

        facultyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedFaculty = newVal;
            if (newVal != null) fillFormWithFaculty(newVal);
        });
    }

    // Populates form fields with selected faculty data
    private void fillFormWithFaculty(Faculty faculty) {
        facultyNameField.setText(faculty.getName());
        facultyEmailField.setText(faculty.getEmail());
        facultyDegreeField.setText(faculty.getDegree());
        facultyOfficeField.setText(faculty.getOfficeLocation());
        facultyResearchField.setText(faculty.getResearchInterest());
        facultyCoursesField.setText(String.join(", ", faculty.getCoursesOffered()));
    }

    // Adds a new faculty to the list
    @FXML private void addFaculty() {
        String id = "F" + String.format("%04d", facultyList.size() + 1);
        Faculty newFaculty = new Faculty(
                id,
                facultyNameField.getText(),
                null,
                facultyDegreeField.getText(),
                facultyResearchField.getText(),
                List.of(facultyCoursesField.getText().split(",\\s*")),
                facultyEmailField.getText(),
                facultyOfficeField.getText()
        );
        Faculty.addFaculty(newFaculty);
        refreshFacultyTable();
        clearForm();
    }

    // Edits selected faculty member's details
    @FXML private void editFaculty() {
        if (selectedFaculty != null) {
            selectedFaculty.setName(facultyNameField.getText());
            selectedFaculty.setEmail(facultyEmailField.getText());
            selectedFaculty.setDegree(facultyDegreeField.getText());
            selectedFaculty.setOfficeLocation(facultyOfficeField.getText());
            selectedFaculty.setResearchInterest(facultyResearchField.getText());
            selectedFaculty.setCoursesOffered(List.of(facultyCoursesField.getText().split(",\\s*")));
            refreshFacultyTable();
            clearForm();
        }
    }

    // Deletes selected faculty member
    @FXML private void deleteFaculty() {
        if (selectedFaculty != null) {
            Faculty.removeFaculty(selectedFaculty.getFacultyID());
            refreshFacultyTable();
            clearForm();
        }
    }

    // Refreshes the table view with the latest faculty list
    private void refreshFacultyTable() {
        facultyTable.getItems().setAll(Faculty.getFacultyList());
    }

    // Clears input form fields
    private void clearForm() {
        facultyNameField.clear();
        facultyEmailField.clear();
        facultyDegreeField.clear();
        facultyOfficeField.clear();
        facultyResearchField.clear();
        facultyCoursesField.clear();
        selectedFaculty = null;
    }

    //takes you to new fxml file
    @FXML
    private void assignCourses() {
        if (selectedFaculty == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a faculty member first.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AAssignCourses.fxml"));
            Parent root = loader.load();

            AAssignCoursesController controller = loader.getController();
            controller.setFaculty(selectedFaculty);

            Stage stage = new Stage();
            stage.setTitle("Assign Courses");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load Assign Courses screen.");
            alert.showAndWait();
        }
    }



    // Navigation methods
    @FXML private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    @FXML private void loadSubjectManagement() {
        Router.navigate("ASubjectManagement.fxml", "Subject Management");
    }

    @FXML private void loadCourseManagement() {
        Router.navigate("ACourseManagement.fxml", "Course Management");
    }

    @FXML private void loadStudentManagement() {
        Router.navigate("AStudentManagement.fxml", "Student Management");
    }

    @FXML private void loadFacultyManagement() {
        Router.navigate("AFacultyManagement.fxml", "Faculty Management");
    }

    @FXML private void loadEventManagement() {
        Router.navigate("AEventManagement.fxml", "Event Management");
    }
}