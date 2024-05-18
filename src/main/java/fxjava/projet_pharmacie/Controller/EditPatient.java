package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoPatient;
import fxjava.projet_pharmacie.Model.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditPatient implements Initializable {
    @FXML
    public TextField codeField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField phoneField;
    @FXML
    public TextField emailField;
    @FXML
    public DatePicker dobPicker;
    @FXML
    public Button editPatientButton;
    @FXML
    public Button cancel;
    @FXML
    public Button deletePatientButton;


    Patient patient;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editPatientButton.setOnAction(event -> handleEdit());
        cancel.setOnAction(event -> handleCancel());
        deletePatientButton.setOnAction(event -> handleDelete());
    }

    private void handleEdit() {
        if(codeField.getText() == null || nameField.getText() == null || phoneField.getText() == null || emailField.getText() == null || dobPicker.getValue() == null) {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Modification");
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
            dia.setTitle("Modification");
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
        patient.setCodePatient(Integer.parseInt(codeField.getText()));
        patient.setNomPatient(nameField.getText());
        patient.setTelPatient(phoneField.getText());
        patient.setEmailPatient(emailField.getText());
        patient.setDate_nais(java.sql.Date.valueOf(dobPicker.getValue()));
        if(DaoPatient.updatePatient(patient)) {
            Alert dia = new Alert(Alert.AlertType.INFORMATION);
            dia.setTitle("Modification");
            dia.setHeaderText("Modification effectuée");
            dia.setContentText("Le patient a été modifié avec succès");
            dia.show();
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        } else {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Modification");
            dia.setHeaderText("Erreur");
            dia.setContentText("Une erreur s'est produite lors de la modification du patient");
            dia.show();
        }

    }

    private void handleDelete() {
        if(patient == null) {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Suppression");
            dia.setHeaderText("Erreur");
            dia.setContentText("Le patient n'existe pas");
            dia.show();
            return;
        }
        Alert dia = new Alert(Alert.AlertType.CONFIRMATION);
        dia.setTitle("Confirmation");
        dia.setHeaderText("Confirmation requise");
        dia.setContentText("Êtes-vous sûr de vouloir supprimer ce patient ?\n Cette action est irréversible");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
        dia.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        AtomicBoolean canDelete = new AtomicBoolean(true);
        dia.showAndWait().ifPresent(response -> {
            if (response == buttonTypeNo) {
                canDelete.set(false);
            }
        });
        if(!canDelete.get()) return;
        if(DaoPatient.deletePatient(patient.getCodePatient())) {
            dia = new Alert(Alert.AlertType.INFORMATION);
            dia.setTitle("Suppression");
            dia.setHeaderText("Suppression effectuée");
            dia.setContentText("Le patient a été supprimé avec succès");
            dia.show();
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        } else {
            dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Suppression");
            dia.setHeaderText("Erreur");
            dia.setContentText("Une erreur s'est produite lors de la suppression du patient");
            dia.show();
        }
    }

    private void handleCancel() {
        // Close the dialog or navigate back to the previous scene
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void uploadPatient(Patient patient) {
        this.patient = patient;
        codeField.setText(String.valueOf(patient.getCodePatient()));
        nameField.setText(patient.getNomPatient());
        phoneField.setText(patient.getTelPatient());
        emailField.setText(patient.getEmailPatient());
        dobPicker.setValue(LocalDate.parse(patient.getDate_nais().toString()));
    }


}
