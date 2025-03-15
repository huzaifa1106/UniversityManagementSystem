/**
 *  File: Main.java
 *  Description: This class is used for the launching of the application
 *  Date: March 2nd, 2025
 *  */

package GUI.CompanyLogin;

//Import Statements
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Router.setPrimaryStage(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("/GUI/CompanyLogin/LoginPage.fxml"));
        primaryStage.setTitle("Login - University Management System");
        primaryStage.setScene(new Scene(root));//Open login screen
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

