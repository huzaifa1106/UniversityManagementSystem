module GuelphLoginPage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens GUI.CompanyLogin to javafx.fxml;
    exports GUI.CompanyLogin;
}
