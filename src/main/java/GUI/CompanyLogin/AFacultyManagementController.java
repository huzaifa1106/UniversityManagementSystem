/**
 *  File: AFacultyManagementController.java
 *  Description: This class is used for the controlling the flow of the
 *  Admin FacultyManagement Window for the application, we use "@FXML" because these are event handles that
 *  were defined in the FXML file. Helping Route through windows for a seemingless experience
 *  Date: March 2nd, 2025
 *  */


package GUI.CompanyLogin;

//Important Statement
import javafx.fxml.FXML;
import Backend.Faculty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Arrays;
import java.util.List;


public class AFacultyManagementController extends ASubjectManagementController {

    //Method to switch window to dashboard
    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    //textbox
    @FXML private TextField facultyNameField;
    @FXML private TextField facultyEmailField;
    @FXML private TextField facultyDegreeField;
    @FXML private TextField facultyOfficeField;
    @FXML private TextField facultyResearchField;
    @FXML private TextField facultyCoursesField;
    @FXML private TableView<Faculty> facultyTable;
    @FXML private TableColumn<Faculty, String> colFacultyName;
    @FXML private TableColumn<Faculty, String> colFacultyEmail;
    @FXML private TableColumn<Faculty, String> colFacultyDegree;
    @FXML private TableColumn<Faculty, String> colFacultyCourses;


    @FXML
    public void initialize() {
        colFacultyName.setCellValueFactory(new PropertyValueFactory<>("name"));  // Looks for getName()
        colFacultyEmail.setCellValueFactory(new PropertyValueFactory<>("email"));  // Looks for getEmail()
        colFacultyDegree.setCellValueFactory(new PropertyValueFactory<>("degree"));  // Looks for getDegree()
        colFacultyCourses.setCellValueFactory(new PropertyValueFactory<>("coursesAsString"));  // Looks for getCoursesAsString()

        loadFacultyTable();
    }



    @FXML
    private void loadFacultyTable() {
        facultyTable.getItems().clear();
        facultyTable.getItems().addAll(Faculty.getAllFaculty());
        facultyTable.refresh();
    }



    //Add faculty button clicked
    @FXML
    private void addFaculty() {

        System.out.println("Add Faculty clicked");
        String facultyName = facultyNameField.getText();
        String facultyEmail = facultyEmailField.getText();
        String facultyDegree = facultyDegreeField.getText();
        String facultyOffice = facultyOfficeField.getText();
        String facultyResearchInterest = facultyResearchField.getText();
        String facultyCoursesAssigned = facultyCoursesField.getText();
        if (facultyName.isEmpty()) {
            System.out.println("Error: Faculty name is required!");
            return; // Stop execution if the field is empty
        }
        else if (facultyEmail.isEmpty()) {
            System.out.println("Error: Faculty email is required!");
            return; // Stop execution if the field is empty
        }
        else if (facultyDegree.isEmpty()) {
            System.out.println("Error: Faculty degree is required!");
            return; // Stop execution if the field is empty
        }
        else if (facultyOffice.isEmpty()) {
            System.out.println("Error: Faculty office is required!");
            return; // Stop execution if the field is empty
        }
        else if (facultyResearchInterest.isEmpty()) {
            System.out.println("Error: Faculty research interest is required!");
            return; // Stop execution if the field is empty
        }
        else if (facultyCoursesAssigned.isEmpty()) {
            System.out.println("Error: Faculty course assigned is required!");
            return; // Stop execution if the field is empty
        }

        List<String> facultyCourses = Arrays.asList(facultyCoursesAssigned.split("\\s*,\\s*"));  // Splits input by comma
        Faculty newFaculty = new Faculty(facultyName, null, facultyDegree, facultyResearchInterest, facultyCourses, facultyEmail, facultyOffice);
        Faculty.addFaculty(newFaculty);
        loadFacultyTable();

        // show that it worked, not sure if we want to do it a differet way?
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Faculty added successfully!");
        alert.showAndWait();

        //clears textbox after succesfully adds info
        facultyNameField.clear();
        facultyEmailField.clear();
        facultyDegreeField.clear();
        facultyOfficeField.clear();
        facultyResearchField.clear();
        facultyCoursesField.clear();
    }

    //Edit faculty button clicked
    @FXML
    private void editFaculty() {
        System.out.println("Edit Faculty clicked");
    }

    //Delete faculty button clicked
    @FXML
    private void deleteFaculty() {
        Faculty selectedFaculty = facultyTable.getSelectionModel().getSelectedItem();

        if (selectedFaculty != null) {
            facultyTable.getItems().remove(selectedFaculty);
            facultyTable.refresh();
            System.out.println("Deleted: " + selectedFaculty.getName());
        } else {
            System.out.println("No faculty selected!");
        }
    }


    //View faculty button clicked
    @FXML
    private void viewFacultyProfile() {
        System.out.println("View Faculty Profile clicked");
    }
    //Assign faculty a course button clicked
    @FXML
    private void assignCourses() {
        System.out.println("Assign Courses clicked");
    }
}