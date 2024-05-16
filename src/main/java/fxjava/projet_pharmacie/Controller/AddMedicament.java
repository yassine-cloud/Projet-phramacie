package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoMedicament;
import fxjava.projet_pharmacie.Model.Medicament;
import fxjava.projet_pharmacie.Model.TypeMed;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private ChoiceBox<TypeMed> typeField;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        typeField.getItems().add( TypeMed.Normal );
        typeField.getItems().add( TypeMed.Special );


        addPatientButton.setOnAction(event -> handleAdd());
        cancel.setOnAction(event -> handleCancel());
    }

    private void handleAdd() {
        if(nameField.getText() == null || prixField.getText() == null || stockField.getText() == null || typeField.getValue() == null) {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Veuillez remplir tous les champs");
            dia.show();

            return;
        }
        try {
            Integer.parseInt(stockField.getText());
            Float.parseFloat(prixField.getText());
            // The text is a number
        } catch (NumberFormatException e) {
            // The text is not a number
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Le stock et le prix doivent être des nombres");
            dia.show();
            return;
        }
        if( Integer.parseInt(stockField.getText()) < 0 || Double.parseDouble(prixField.getText()) < 0){
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Le stock et le prix doivent être positifs");
            dia.show();
            return;
        }
        Medicament medicament = new Medicament( nameField.getText(),  Float.parseFloat(prixField.getText()),Integer.parseInt(stockField.getText()), TypeMed.valueOf(typeField.getValue().toString()));
        DaoMedicament.addMedicament(medicament);
        Stage stage = (Stage) addPatientButton.getScene().getWindow();
        stage.close();
    }

    private void handleCancel() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
