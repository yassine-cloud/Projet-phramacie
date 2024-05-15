package fxjava.projet_pharmacie.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMedicament implements Initializable {

    @FXML
    private Button addPatientButton;

    @FXML
    private Button cancel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField stockField;

    @FXML
    private ChoiceBox<?> typeField;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addPatientButton.setOnAction(event -> handleAdd());
        cancel.setOnAction(event -> handleCancel());
    }
}
