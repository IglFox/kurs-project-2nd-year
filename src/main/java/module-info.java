module edu.university.kursproject2ndyear {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens edu.university.kursproject2ndyear to javafx.fxml;
    exports edu.university.kursproject2ndyear.apartmentdb.controller;
    opens edu.university.kursproject2ndyear.apartmentdb.controller to javafx.fxml;
}