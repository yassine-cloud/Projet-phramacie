package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoPatient;
import fxjava.projet_pharmacie.Model.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPatient implements Initializable {
    @FXML
    public TextField nameField;
    @FXML
    public TextField phoneField;
    @FXML
    public TextField emailField;
    @FXML
    public DatePicker dobPicker;
    @FXML
    public Button addPatientButton;
    @FXML
    public Button cancel;

    Patient patient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPatientButton.setOnAction(event -> handleAdd());
        cancel.setOnAction(event -> handleCancel());
    }

    private void handleAdd() {
        if(nameField.getText() == null || phoneField.getText() == null || emailField.getText() == null || dobPicker.getValue() == null) {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Veuillez remplir tous les champs");
            dia.show();

            return;
        }
        try {
            Integer.parseInt(phoneField.getText());
            // The text is a number
        } catch (NumberFormatException e) {
            // The text is not a number
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Le numéro de téléphone doit être un nombre");
            dia.show();
            return;
        }
        if(phoneField.getText().length() != 8){
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Modification");
            dia.setHeaderText("Erreur");
            dia.setContentText("Le numéro de téléphone doit contenir 8 chiffres");
            dia.show();
            return;
        }
        patient = new Patient();
        patient.setNomPatient(nameField.getText());
        patient.setTelPatient(phoneField.getText());
        patient.setEmailPatient(emailField.getText());
        patient.setDate_nais(java.sql.Date.valueOf(dobPicker.getValue()));
        DaoPatient.addPatient(patient);
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    private void handleCancel() {
        // Close the dialog or navigate back to the previous scene
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
