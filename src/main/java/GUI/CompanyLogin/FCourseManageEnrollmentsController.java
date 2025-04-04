package GUI.CompanyLogin;

import Backend.Course;
import Backend.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class FCourseManageEnrollmentsController {

    @FXML private Label courseTitleLabel;
    @FXML private ListView<String> studentListView;

    private Course selectedCourse;

    public void setCourse(Course course) {
        this.selectedCourse = course;
        courseTitleLabel.setText("Students Enrolled in: " + course.getCourseName());

        studentListView.setItems(FXCollections.observableArrayList(
                course.getEnrolledStudents().stream().map(Student::getFullName).toList()
        ));
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) courseTitleLabel.getScene().getWindow();
        stage.close();
    }
}
