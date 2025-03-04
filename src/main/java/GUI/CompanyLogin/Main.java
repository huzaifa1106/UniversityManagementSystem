package GUI.CompanyLogin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load FXML from classpath
        URL fxmlLocation = getClass().getResource("/GUI/CompanyLogin/LoginPage.fxml");


        if (fxmlLocation == null) {
            System.err.println("Error: LoginPage.fxml not found. Check the file path!");
            return;
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        primaryStage.setTitle("Company Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
