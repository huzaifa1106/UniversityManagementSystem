/**
 *  File: UStudentManagementController.java
 *  Description: Controls the User Student Management Window. Displays profile, progress, and tuition info.
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */

package GUI.CompanyLogin;

import Backend.Course;
import Backend.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UStudentManagementController {

    @FXML private Label studentNameLabel;
    @FXML private Label studentIDLabel;
    @FXML private Label studentPhoneLabel;
    @FXML private Label studentAddressLabel;
    @FXML private Label studentProgressLabel;
    @FXML private Label studentThesisLabel;
    @FXML private Label studentEmailLabel;
    @FXML private Label studentLevelLabel;

    @FXML private TableView<Course> progressTable;
    @FXML private TableColumn<Course, String> colCourse;
    @FXML private TableColumn<Course, String> colGrade;
    @FXML private TableColumn<Course, String> colCredits;

    @FXML private TableView<String[]> tuitionTable;
    @FXML private TableColumn<String[], String> colSemester;
    @FXML private TableColumn<String[], String> colAmount;
    @FXML private TableColumn<String[], String> colStatus;

    private Student loggedInStudent;

    public void setStudent(Student student) {
        this.loggedInStudent = student;
        System.out.println("✅ Student Loaded in Student Management: " + student.getFullName());
        loadStudentInfo();
        loadProgressTable();
        loadTuitionTable();
    }

    private void loadStudentInfo() {
        studentNameLabel.setText(loggedInStudent.getFullName());
        studentIDLabel.setText(String.valueOf(loggedInStudent.getStudentID()));
        studentPhoneLabel.setText(String.valueOf(loggedInStudent.getTelephone()));
        studentAddressLabel.setText(loggedInStudent.getAddress());
        studentProgressLabel.setText(loggedInStudent.getProgress() + "%");
        studentThesisLabel.setText(loggedInStudent.getThesisTitle());
        studentEmailLabel.setText(loggedInStudent.getEmailAddress());
        studentLevelLabel.setText(loggedInStudent.getAcademicLevel());
    }

    private void loadProgressTable() {
        colCourse.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourseName()));
        colGrade.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getGrade() == -1 ? "N/A" : String.valueOf(data.getValue().getGrade()))
        );
        colCredits.setCellValueFactory(data -> new SimpleStringProperty("3")); // Static credits

        progressTable.setItems(FXCollections.observableArrayList(loggedInStudent.getEnrolledCourses()));
    }

    private void loadTuitionTable() {
        String[] row = new String[]{
                loggedInStudent.getSemester(),
                "$" + loggedInStudent.getTuitionBalance(),
                (loggedInStudent.getTuitionBalance() == 0 ? "Paid" : "Pending")
        };

        List<String[]> data = new ArrayList<>();
        data.add(row);

        colSemester.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        colAmount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));

        tuitionTable.setItems(FXCollections.observableArrayList(data));
    }

    // 🔁 Navigation Helper
    private void navigateTo(String fxmlFile, String title, String controllerKey) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            switch (controllerKey) {

                case "course":
                    UCourseManagementController courseController = loader.getController();
                    courseController.setStudent(loggedInStudent);
                    break;

                case "event":
                    UEventManagementController eventController = loader.getController();
                    eventController.setStudent(loggedInStudent);
                    break;
            }

            Stage stage = (Stage) studentNameLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Failed to load: " + fxmlFile, Alert.AlertType.ERROR);
        }
    }

    // Navigation Buttons

    @FXML private void loadCourseManagement() {
        navigateTo("UCourseManagement.fxml", "Course Management", "course");
    }

    @FXML private void loadStudentManagement() {
        loadStudentInfo();
        loadProgressTable();
        loadTuitionTable();
    }

    @FXML private void loadEventManagement() {
        navigateTo("UEventManagement.fxml", "Event Management", "event");
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
