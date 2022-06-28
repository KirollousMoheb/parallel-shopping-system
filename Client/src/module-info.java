module Client {
    requires javafx.fxml;
    requires javafx.controls;
    requires json.simple;
    opens Shopping.Controllers to javafx.fxml;
    opens Shopping;
}