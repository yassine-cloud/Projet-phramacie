module fxjava.projet_pharmacie {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;

    opens fxjava.projet_pharmacie to javafx.fxml;
    opens fxjava.projet_pharmacie.Controller to javafx.fxml;

    exports fxjava.projet_pharmacie;
//    exports fxjava.projet_pharmacie;
}