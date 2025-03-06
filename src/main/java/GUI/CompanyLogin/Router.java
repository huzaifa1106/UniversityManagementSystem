/**
 *  File: Router.java
 *  Description: This class is used for the controlling the flow of the
 *  windows for the application, Helping Route through windows for a seemingless experience
 *  Date: March 2nd, 2025
 *  */

package GUI.CompanyLogin;

//Import statements
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Router {

    //Static so it can be accessed anywhere in the window management
    private static Stage primaryStage; // Keep track of the main stage


    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
    //Navigates to a specified FXML file by updating the primary stage's scene.
    public static void navigate(String fxmlFile, String title) {
        try {
            //Loading file from directory
            FXMLLoader loader = new FXMLLoader(Router.class.getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            // Update the primary stage with the new scene and title
            primaryStage.setTitle(title);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML: " + fxmlFile);
        }
    }
}
