/**
 *  File: UCourseManagementController.java
 *  Description: Controls the User Course Management Window. Displays enrolled and offered courses.
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
import java.time.format.DateTimeFormatter;

public class UCourseManagementController {

    // FXML bindings for enrolled courses table
    @FXML private TableView<Course> enrolledCoursesTable;
    @FXML private TableColumn<Course, String> colCourseID;
    @FXML private TableColumn<Course, String> colCourseName;
    @FXML private TableColumn<Course, String> colSectionNumber;
    @FXML private TableColumn<Course, String> colTeacherName;

    // FXML bindings for all course details table
    @FXML private TableView<Course> courseDetailsTable;
    @FXML private TableColumn<Course, String> colSubjectName;
    @FXML private TableColumn<Course, String> colLocation;
    @FXML private TableColumn<Course, String> colLectureDay;
    @FXML private TableColumn<Course, String> colLectureTime;
    @FXML private TableColumn<Course, String> colFinalExamDate;

    // Holds the logged-in student
    private Student loggedInStudent;

    // Called by previous screen to pass in logged-in student
    public void setStudent(Student student) {
        this.loggedInStudent = student;
        System.out.println("Student Loaded in Course Management: " + student.getFullName());
        loadCourseTables();
    }

    // Loads both enrolled and all course data into tables
    private void loadCourseTables() {
        if (loggedInStudent == null) return;

        // Populate enrolled courses table
        colCourseID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCourseID())));
        colCourseName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourseName()));
        colSectionNumber.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSectionNumber())));
        colTeacherName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTeacherName()));
        enrolledCoursesTable.setItems(FXCollections.observableArrayList(loggedInStudent.getEnrolledCourses()));

        // Populate detailed course offerings table
        colSubjectName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSubjectName()));
        colLocation.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));
        colLectureDay.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLectureDay()));
        colLectureTime.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getLectureStartTime() + " - " + data.getValue().getLectureEndTime())
        );

        // Format exam date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");
        colFinalExamDate.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getFinalExamDate().format(formatter))
        );

        courseDetailsTable.setItems(FXCollections.observableArrayList(loggedInStudent.getEnrolledCourses()));
    }

    // Utility method to switch between screens and preserve student data
    private void navigateTo(String fxmlFile, String title, String controllerKey) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            // Set student in target controller
            switch (controllerKey) {
                case "student":
                    UStudentManagementController studentController = loader.getController();
                    studentController.setStudent(loggedInStudent);
                    break;
                case "event":
                    UEventManagementController eventController = loader.getController();
                    eventController.setStudent(loggedInStudent);
                    break;
            }

            // Show new scene
            Stage stage = (Stage) enrolledCoursesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Failed to load: " + fxmlFile, Alert.AlertType.ERROR);
        }
    }

    @FXML private void loadStudentManagement() {
        navigateTo("UStudentManagement.fxml", "Student Management", "student");
    }

    @FXML private void loadEventManagement() {
        navigateTo("UEventManagement.fxml", "Event Management", "event");
    }

    @FXML private void loadCourseManagement() {
        loadCourseTables(); // Refresh current page
    }

    // Simple alert helper
    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
