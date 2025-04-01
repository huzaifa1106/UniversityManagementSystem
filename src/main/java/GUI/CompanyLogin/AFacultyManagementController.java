package GUI.CompanyLogin;

import Backend.Faculty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AFacultyManagementController {

    @FXML private TextField facultyNameField, facultyEmailField, facultyDegreeField, facultyOfficeField,
            facultyResearchField, facultyCoursesField;

    @FXML private TableView<Faculty> facultyTable;
    @FXML private TableColumn<Faculty, String> colFacultyID, colFacultyName, colFacultyEmail, colFacultyDegree,
            colFacultyResearch, colFacultyOffice, colFacultyCourses;

    private Faculty selectedFaculty;

    @FXML
    private void initialize() {
        System.out.println("colFacultyCourses: " + colFacultyCourses);

        if (colFacultyCourses == null) {
            System.out.println("ERROR: colFacultyCourses is NULL! Check your FXML.");
            return;
        }

        colFacultyID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFacultyID()));
        colFacultyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colFacultyEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colFacultyDegree.setCellValueFactory(new PropertyValueFactory<>("degree"));
        colFacultyResearch.setCellValueFactory(new PropertyValueFactory<>("researchInterest"));
        colFacultyOffice.setCellValueFactory(new PropertyValueFactory<>("officeLocation"));
        colFacultyCourses.setCellValueFactory(new PropertyValueFactory<>("coursesAsString"));

        facultyTable.getItems().setAll(Faculty.getFacultyList());

        facultyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedFaculty = newVal;
            if (newVal != null) fillFormWithFaculty(newVal);
        });
    }

    private void fillFormWithFaculty(Faculty faculty) {
        facultyNameField.setText(faculty.getName());
        facultyEmailField.setText(faculty.getEmail());
        facultyDegreeField.setText(faculty.getDegree());
        facultyOfficeField.setText(faculty.getOfficeLocation());
        facultyResearchField.setText(faculty.getResearchInterest());
        facultyCoursesField.setText(String.join(", ", faculty.getCoursesOffered()));
    }

    @FXML private void addFaculty() {
        String id = "F" + String.format("%04d", Faculty.getFacultyList().size() + 1);
        Faculty newFaculty = new Faculty(
                id,
                facultyNameField.getText(),
                null,
                facultyDegreeField.getText(),
                facultyResearchField.getText(),
                Arrays.asList(facultyCoursesField.getText().split(",\\s*")),
                facultyEmailField.getText(),
                facultyOfficeField.getText()
        );
        Faculty.addFaculty(newFaculty);
        refreshFacultyTable();
        clearForm();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Faculty added successfully!");
        alert.showAndWait();
    }

    @FXML private void editFaculty() {
        if (selectedFaculty != null) {
            selectedFaculty.setName(facultyNameField.getText());
            selectedFaculty.setEmail(facultyEmailField.getText());
            selectedFaculty.setDegree(facultyDegreeField.getText());
            selectedFaculty.setOfficeLocation(facultyOfficeField.getText());
            selectedFaculty.setResearchInterest(facultyResearchField.getText());
            selectedFaculty.setCoursesOffered(Arrays.asList(facultyCoursesField.getText().split(",\\s*")));
            refreshFacultyTable();
            clearForm();
        }
    }

    @FXML private void deleteFaculty() {
        if (selectedFaculty != null) {
            Faculty.removeFaculty(selectedFaculty.getFacultyID());
            refreshFacultyTable();
            clearForm();
        }
    }

    private void refreshFacultyTable() {
        facultyTable.getItems().setAll(Faculty.getFacultyList());
    }

    private void clearForm() {
        facultyNameField.clear();
        facultyEmailField.clear();
        facultyDegreeField.clear();
        facultyOfficeField.clear();
        facultyResearchField.clear();
        facultyCoursesField.clear();
        selectedFaculty = null;
    }

    @FXML private void viewFacultyProfile() {
        System.out.println("View Faculty Profile clicked");
    }

    @FXML private void assignCourses() {
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
