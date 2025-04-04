package GUI.CompanyLogin;

import Backend.Course;
import Backend.ReadExcelFile;
import Backend.Faculty;
import Backend.Subject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ACourseManagementController extends ASubjectManagementController {

    @FXML private TableColumn<Course, Integer> colCourseID, colSectionNumber;
    @FXML private TableColumn<Course, String> colCourseName, colTeacherName;
    @FXML private TableView<Course> courseDetailsTable;
    @FXML private TableColumn<Course, String> colSubjectName, colLocation, colLectureDay, colLectureTime, colFinalExamDate;

    @FXML private TextField courseNameField, courseIDField, sectionField, locationField;
    @FXML private ComboBox<String> instructorComboBox;
    @FXML private ComboBox<String> lectureDayComboBox;
    @FXML private ComboBox<String> subjectComboBox;
    @FXML private DatePicker finalExamDatePicker;

    private Course selectedCourse = null;

    @FXML
    private void initialize() {
        colCourseID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCourseID()).asObject());
        colCourseName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseName()));
        colSectionNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSectionNumber()).asObject());
        colTeacherName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeacherName()));
        colSubjectName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubjectName()));
        colLocation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        colLectureDay.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLectureDay()));
        colLectureTime.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getLectureStartTime() + " - " + cellData.getValue().getLectureEndTime()));
        colFinalExamDate.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFinalExamDate() != null ? cellData.getValue().getFinalExamDate().toLocalDate().toString() : "Not Assigned"));

        // Populate instructors
        List<String> instructorList = new ArrayList<>();
        for (Faculty f : Faculty.getFacultyList()) {
            instructorList.add(f.getName());
        }
        instructorComboBox.getItems().addAll(instructorList);

        // Populate lecture days
        lectureDayComboBox.getItems().addAll(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
                "Mon/Wed", "Tue/Thu", "Mon/Wed/Fri", "Tue/Thu/Fri"
        );

        // Populate subjects
        List<String> subjectDisplayList = new ArrayList<>();
        for (Subject s : Subject.getSubjectList()) {
            String display = s.getSubjectName() + " (" + s.getSubjectCode() + ")";
            subjectDisplayList.add(display);
        }
        subjectComboBox.getItems().addAll(subjectDisplayList);

        loadCoursesIntoTable();

        courseDetailsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedCourse = newVal;
            if (newVal != null) fillFormWithCourseData(newVal);
        });
    }

    private void loadCoursesIntoTable() {
        courseDetailsTable.getItems().setAll(Course.getCourseList());
    }

    private void fillFormWithCourseData(Course course) {
        courseNameField.setText(course.getCourseName());
        courseIDField.setText(String.valueOf(course.getCourseID()));
        instructorComboBox.setValue(course.getTeacherName());
        sectionField.setText(String.valueOf(course.getSectionNumber()));
        subjectComboBox.setValue(course.getSubjectName() + " (" + course.getSubjectName() + ")");
        locationField.setText(course.getLocation());
        lectureDayComboBox.setValue(course.getLectureDay());
        finalExamDatePicker.setValue(course.getFinalExamDate() != null ? course.getFinalExamDate().toLocalDate() : null);
    }

    @FXML
    private void addCourse() {
        try {
            if (courseNameField.getText().isEmpty() || instructorComboBox.getValue() == null ||
                    sectionField.getText().isEmpty() || subjectComboBox.getValue() == null ||
                    locationField.getText().isEmpty() || lectureDayComboBox.getValue() == null) {
                showAlert("Missing Fields", "Please fill in all required fields.", Alert.AlertType.WARNING);
                return;
            }

            int sectionNumber = Integer.parseInt(sectionField.getText());
            String instructor = instructorComboBox.getValue();
            String lectureDay = lectureDayComboBox.getValue();
            String subjectCode = extractSubjectCode(subjectComboBox.getValue());
            LocalDateTime finalExamDate = finalExamDatePicker.getValue() != null ?
                    finalExamDatePicker.getValue().atStartOfDay() : null;

            Course newCourse;
            if (courseIDField.getText().isEmpty()) {
                newCourse = new Course(courseNameField.getText(), subjectCode, sectionNumber,
                        instructor, 50, locationField.getText(), lectureDay,
                        900, 1100, finalExamDate);
            } else {
                int courseID = Integer.parseInt(courseIDField.getText());
                if (Course.findCourseByID(courseID) != null) {
                    showAlert("Duplicate ID", "Course with this ID already exists.", Alert.AlertType.ERROR);
                    return;
                }
                newCourse = new Course(courseID, courseNameField.getText(), subjectCode, sectionNumber,
                        instructor, 50, locationField.getText(), lectureDay,
                        900, 1100, finalExamDate);
            }

            Course.addCourse(newCourse);
            ReadExcelFile.writeToExcel();
            loadCoursesIntoTable();
            clearFields();
            showAlert("Success", "Course added successfully!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Section and Course ID (if provided) must be numeric.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editCourse() {
        if (selectedCourse != null) {
            try {
                selectedCourse.changeCourseName(courseNameField.getText());
                selectedCourse.changeTeacherName(instructorComboBox.getValue());
                selectedCourse.changeLocation(locationField.getText());
                selectedCourse.changeCourseCapacity(Integer.parseInt(sectionField.getText()));
                ReadExcelFile.writeToExcel();
                loadCoursesIntoTable();
                clearFields();
                showAlert("Success", "Course updated successfully.", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Section must be numeric.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("No Selection", "Please select a course to edit.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void deleteCourse() {
        if (selectedCourse != null) {
            Course.removeCourse(selectedCourse.getCourseID());
            ReadExcelFile.writeToExcel();
            loadCoursesIntoTable();
            clearFields();
            selectedCourse = null;
            showAlert("Success", "Course deleted successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("No Selection", "Please select a course to delete.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void viewCourses() {
        if (selectedCourse != null) {
            showAlert("Viewing Course", "Course: " + selectedCourse.getCourseName(), Alert.AlertType.INFORMATION);
        } else {
            showAlert("No Selection", "Please select a course to view.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void manageEnrollments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CompanyLogin/ACourseManageEnrollment.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Course Enrollments");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            loadCoursesIntoTable();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load enrollment screen.", Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        courseNameField.clear();
        courseIDField.clear();
        instructorComboBox.setValue(null);
        lectureDayComboBox.setValue(null);
        sectionField.clear();
        subjectComboBox.setValue(null);
        locationField.clear();
        finalExamDatePicker.setValue(null);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String extractSubjectCode(String displayString) {
        if (displayString == null || !displayString.contains("(")) return "";
        return displayString.substring(displayString.indexOf('(') + 1, displayString.indexOf(')')).trim();
    }
}
