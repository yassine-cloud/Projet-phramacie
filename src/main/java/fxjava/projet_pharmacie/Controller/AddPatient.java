package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoPatient;
import fxjava.projet_pharmacie.Model.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
