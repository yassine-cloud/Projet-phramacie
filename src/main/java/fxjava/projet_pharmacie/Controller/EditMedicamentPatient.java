package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.Model.Medicament;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMedicamentPatient implements Initializable {

    @FXML
    private Label codeField;

    @FXML
    private Label StockField;

    @FXML
    private Label aPayerField;

    @FXML
    private Button addMedButton;

    @FXML
    private Button cancel;

    @FXML
    private ChoiceBox<Medicament> choiceField;

    @FXML
    private Spinner<Integer> spinnerField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
