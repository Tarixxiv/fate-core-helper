module com.fatecorehelper.fatecorehelper {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    opens com.fatecorehelper.controller to javafx.fxml;
    exports com.fatecorehelper to javafx.graphics;
    exports com.fatecorehelper.controller to javafx.fxml;
}