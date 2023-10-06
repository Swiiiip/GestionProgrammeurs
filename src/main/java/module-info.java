module moduleInfo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens app to javafx.fxml;
    exports app;
    exports personnes;
    exports dao;
    exports utils;

    opens exec to javafx.graphics;
    exports exec;
}