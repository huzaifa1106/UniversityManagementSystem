package GUI.CompanyLogin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Router {

    private static Stage primaryStage; // Keep track of the main stage

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void navigate(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource("/GUI/CompanyLogin/" + fxmlFile));
            Parent root = loader.load();

            primaryStage.setTitle(title);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML: " + fxmlFile);
        }
    }
}
