module com.fatecorehelper.fatecorehelper {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.fatecorehelper to javafx.fxml;
    exports com.fatecorehelper;
}