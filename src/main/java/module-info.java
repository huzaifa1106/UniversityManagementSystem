module GuelphLoginPage {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.desktop;

    opens GUI.CompanyLogin to javafx.fxml;
    exports GUI.CompanyLogin;

    //lets javafx have access to backend.faculty
    opens Backend to javafx.base, javafx.fxml;

}
