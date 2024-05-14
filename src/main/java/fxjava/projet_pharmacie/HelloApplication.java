package fxjava.projet_pharmacie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(Objects.requireNonNull(HelloApplication.class.getResource("login.fxml")));
        Parent root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("login.fxml")) );
        System.out.println("Hello");

        Scene scene = new Scene(root);

        stage.setTitle("Hello!");


        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}