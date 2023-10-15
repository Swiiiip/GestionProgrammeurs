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
    requires weka.stable;
    requires javafx.web;
    requires annotations;
    requires org.fusesource.jansi;
    requires org.slf4j;

    opens app to javafx.fxml;
    exports app;
    exports personnes;
    exports dao;
    exports utils;

    opens exec to javafx.graphics;
    exports exec;
    exports loadingscreen;
    exports personnes.utils;
}