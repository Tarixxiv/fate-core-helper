module com.fatecorehelper.fatecorehelper {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.fatecorehelper to javafx.fxml;
    exports com.fatecorehelper;
    exports com.fatecorehelper.generator.business;
    exports com.fatecorehelper.model;

    opens com.fatecorehelper.controller to javafx.fxml;
    exports com.fatecorehelper.controller;
    exports com.fatecorehelper.controller.util;
    opens com.fatecorehelper.controller.util to javafx.fxml;

}