module GuelphLoginPage {
    requires javafx.controls;
    requires javafx.fxml;

    opens GUI.CompanyLogin to javafx.fxml;
    exports GUI.CompanyLogin;
}
