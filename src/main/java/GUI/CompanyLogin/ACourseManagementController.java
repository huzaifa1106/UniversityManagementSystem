/**
 *  File: ACourseManagementController.java
 *  Description: This class is used for the controlling the flow of the
 *  Admin CourseManagement Window for the application, we use "@FXML" because these are event handles that
 *  were defined in the FXML file. Helping Route through windows for a seemingless experience
 *  Date: March 2nd, 2025
 *  */

package GUI.CompanyLogin;

//Importing Class
import javafx.fxml.FXML;

//Inherites all methods from ASubjectManagementController for rerouting after event occours
public class ACourseManagementController extends ASubjectManagementController {


    //Routing Window to Dashboard.
    @FXML
    private void loadDashboard() {
        Router.navigate("ADashboard.fxml", "Admin Dashboard");
    }

    //Method to prompt an action for adding course.
    @FXML
    private void addCourse() {
        System.out.println("Add Course clicked");
    }

    //Method to prompt an action for editing course.
    @FXML
    private void editCourse() {
        System.out.println("Edit Course clicked");
    }

    //Method to prompt an action for deleting course.
    @FXML
    private void deleteCourse() {
        System.out.println("Delete Course clicked");
    }

    //Method to prompt an action for viewing course.
    @FXML
    private void viewCourses() {
        System.out.println("View Courses clicked");
    }

    //Method to prompt an action for assigning a course a faculty course.
    @FXML
    private void assignFaculty() {
        System.out.println("Assign Faculty clicked");
    }
    //Method to prompt an action for managing enrollment.
    @FXML
    private void manageEnrollments() {
        System.out.println("Manage Enrollments clicked");
    }
}
