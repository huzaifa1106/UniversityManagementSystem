/**
 *  File: UFacultyManagementController.java
 *  Description: Controls the User Faculty Management Window. Displays a list of Facultors .
 *  Author: Huzaifa A. & Group
 *  Date: March 2nd, 2025
 */
package GUI.CompanyLogin;

import Backend.Course;
import Backend.Faculty;
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
import java.util.*;

public class UFacultyManagementController {

    @FXML private TableView<Faculty> facultyListTable;
    @FXML private TableColumn<Faculty, String> colProfilePhoto;
    @FXML private TableColumn<Faculty, String> colName;
    @FXML private TableColumn<Faculty, String> colCoursesOffered;
    @FXML private TableColumn<Faculty, String> colEmail;

    @FXML private TableView<Faculty> facultyDetailsTable;
    @FXML private TableColumn<Faculty, String> colDegree;
    @FXML private TableColumn<Faculty, String> colResearchInterest;
    @FXML private TableColumn<Faculty, String> colOfficeLocation;

    private Student loggedInStudent;

    public void setStudent(Student student) {
        this.loggedInStudent = student;
        System.out.println("Student Loaded in Faculty Management: " + student.getFullName());
        loadFacultyTables();
    }

    private void loadFacultyTables() {
        // Get instructors from student's courses
        Set<String> teacherNames = new HashSet<>();
        for (Course c : loggedInStudent.getEnrolledCourses()) {
            teacherNames.add(c.getTeacherName());
        }

        // Get faculty objects based on teacher names
        List<Faculty> matchedFaculty = new ArrayList<>();
        for (String name : teacherNames) {
            Faculty faculty = Faculty.findByName(name);
            if (faculty != null) matchedFaculty.add(faculty);
        }

        // Populate Faculty List Table
        colProfilePhoto.setCellValueFactory(data -> new SimpleStringProperty("[📸 Profile]"));
        colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colCoursesOffered.setCellValueFactory(data -> new SimpleStringProperty(String.join(", ", data.getValue().getCoursesOffered())));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        facultyListTable.setItems(FXCollections.observableArrayList(matchedFaculty));

        // Populate Faculty Details Table
        colDegree.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDegree()));
        colResearchInterest.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResearchInterest()));
        colOfficeLocation.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOfficeLocation()));
        facultyDetailsTable.setItems(FXCollections.observableArrayList(matchedFaculty));
    }

    // 🔁 Reusable Navigation Helper
    private void navigateTo(String fxmlFile, String title, String controllerKey) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            switch (controllerKey) {
                case "subject":
                    USubjectManagementController subjectController = loader.getController();
                    subjectController.setStudent(loggedInStudent);
                    break;
                case "course":
                    UCourseManagementController courseController = loader.getController();
                    courseController.setStudent(loggedInStudent);
                    break;
                case "student":
                    UStudentManagementController studentController = loader.getController();
                    studentController.setStudent(loggedInStudent);
                    break;
                case "event":
                    UEventManagementController eventController = loader.getController();
                    eventController.setStudent(loggedInStudent);
                    break;
            }

            Stage stage = (Stage) facultyListTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Failed to load: " + fxmlFile, Alert.AlertType.ERROR);
        }
    }

    // Navigation Methods
    @FXML private void loadSubjectManagement() { navigateTo("USubjectManagement.fxml", "Subject Management", "subject"); }
    @FXML private void loadCourseManagement()   { navigateTo("UCourseManagement.fxml", "Course Management", "course"); }
    @FXML private void loadStudentManagement()  { navigateTo("UStudentManagement.fxml", "Student Management", "student"); }
    @FXML private void loadFacultyManagement()  { loadFacultyTables(); }
    @FXML private void loadEventManagement()    { navigateTo("UEventManagement.fxml", "Event Management", "event"); }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
