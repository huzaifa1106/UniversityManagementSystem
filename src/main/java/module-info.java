module GuelphLoginPage {
    requires javafx.controls;
    requires javafx.fxml;

    opens CompanyLogin to javafx.fxml;
    exports CompanyLogin;
}
