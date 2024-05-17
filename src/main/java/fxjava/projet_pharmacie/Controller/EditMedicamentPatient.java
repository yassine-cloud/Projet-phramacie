package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoMedicament;
import fxjava.projet_pharmacie.DAO.DaoPatientMed;
import fxjava.projet_pharmacie.Model.Medicament;
import fxjava.projet_pharmacie.Model.PatientMed;
import fxjava.projet_pharmacie.Model.TypeMed;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditMedicamentPatient implements Initializable {

    @FXML
    private Label StockField;

    @FXML
    private Label aPayerField;

    @FXML
    private Button editMedButton;

    @FXML
    private Button cancel;

    @FXML
    private ChoiceBox<Medicament> choiceField;

    @FXML
    private Label codeField;

    @FXML
    private Button restoreButton;

    @FXML
    private Spinner<Integer> spinnerField;

    @FXML
    private Button supprimerButton;

    private PatientMed oldPatientMed;
    private PatientMed newPatientMed;
    private Medicament oldMedicament;
    private Medicament newMedicament;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aPayerField.setStyle("-fx-text-fill: green");
        spinnerField.setDisable(true);
        spinnerField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)); // Set a default ValueFactory

        editMedButton.setOnAction(event -> handleEdit());
        restoreButton.setOnAction(event -> handleRestore());
        supprimerButton.setOnAction(event -> handleDelete());

        ArrayList<Medicament> medicaments = DaoMedicament.getAllMedicaments();
        choiceField.getItems().addAll(medicaments);

        choiceField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // This code will be executed when the selected item changes
            if(newValue == null) return;
            if(newValue.getStock_med() == 0) {
                Alert dia = new Alert(Alert.AlertType.ERROR);
                dia.setTitle("Ajout");
                dia.setHeaderText("Erreur");
                dia.setContentText("Stock épuisé");
                dia.show();
                return;
            }
            newMedicament = newValue;
            if(newMedicament.equals(oldMedicament)) {
                spinnerField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, ( oldMedicament.getStock_med() + oldPatientMed.getQte() ), oldPatientMed.getQte()));
                StockField.setText(String.valueOf(oldMedicament.getStock_med() + oldPatientMed.getQte()));
                aPayerField.setText(String.valueOf(oldMedicament.getPrix_med() * oldPatientMed.getQte()) + " Dinars");
                spinnerField.setDisable(false);
                return;
            }
            else {
                StockField.setText(String.valueOf(newMedicament.getStock_med()));
                aPayerField.setText(String.valueOf(newMedicament.getPrix_med()) + " Dinars");
                spinnerField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, newMedicament.getStock_med(), 1));
                spinnerField.setDisable(false);
            }
        });

        spinnerField.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Spinner value changed to: " + newValue); // Print the new value to verify the listener is triggered


            float newTotal = newMedicament.getPrix_med() * newValue;
            aPayerField.setText(String.valueOf(newTotal) + " Dinars");
        });


        cancel.setOnAction(event -> handleCancel());

    }

    private void handleEdit(){
        if(newMedicament.equals(oldMedicament)){
            if(oldPatientMed.getQte() == spinnerField.getValue()){
                handleCancel();
                return ;
            }
            else if(oldPatientMed.getQte() < spinnerField.getValue()){
                oldMedicament.setStock_med(oldMedicament.getStock_med() - (spinnerField.getValue() - oldPatientMed.getQte()));
                DaoMedicament.updateMedicament(oldMedicament);
                newPatientMed.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
                newPatientMed.setPayer(spinnerField.getValue() * oldMedicament.getPrix_med());
                newPatientMed.setQte(spinnerField.getValue());
                DaoPatientMed.updatePatientMed(newPatientMed);
                Stage stage = (Stage) editMedButton.getScene().getWindow();
                stage.close();
            }
            else {
                oldMedicament.setStock_med(oldMedicament.getStock_med() + (oldPatientMed.getQte() - spinnerField.getValue()));
                DaoMedicament.updateMedicament(oldMedicament);
                newPatientMed.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
                newPatientMed.setQte(spinnerField.getValue());
                newPatientMed.setPayer(spinnerField.getValue() * oldMedicament.getPrix_med());
                DaoPatientMed.updatePatientMed(newPatientMed);
                Stage stage = (Stage) editMedButton.getScene().getWindow();
                stage.close();
            }
        }
        else {

            if(choiceField.getValue() == null) {
                Alert dia = new Alert(Alert.AlertType.ERROR);
                dia.setTitle("Ajout");
                dia.setHeaderText("Erreur");
                dia.setContentText("Veuillez choisir un médicament");
                dia.show();
                return;
            }
            if(spinnerField.getValue() == 0) {
                Alert dia = new Alert(Alert.AlertType.ERROR);
                dia.setTitle("Ajout");
                dia.setHeaderText("Erreur");
                dia.setContentText("Veuillez choisir une quantité supérieure à 0");
                dia.show();
                return;
            }
            AtomicBoolean canAdd = new AtomicBoolean(true);
            if(newMedicament.getType_med() == TypeMed.Special){
                boolean found = false;
                ArrayList<PatientMed> patientMeds = DaoPatientMed.getPatientMedByCodePatient(newPatientMed.getCodePatient());
                for (PatientMed pm : patientMeds) {
                    if(pm.getCode_med() == newMedicament.getCode_med()){
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    Alert dia = new Alert(Alert.AlertType.CONFIRMATION);
                    dia.setTitle("Confirmation");
                    dia.setHeaderText("Confirmation requise");
                    dia.setContentText("Êtes-vous sûr de vouloir ajouter un médicament spécial ?");

                    ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                    ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
                    dia.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                    dia.showAndWait().ifPresent(response -> {
                        if (response == buttonTypeNo) {
                            canAdd.set(false);
                        }
                    });
                }
            }
            if(!canAdd.get()) return;
            oldMedicament.setStock_med(oldMedicament.getStock_med() + oldPatientMed.getQte());
            DaoMedicament.updateMedicament(oldMedicament);
            newMedicament.setStock_med(newMedicament.getStock_med() - spinnerField.getValue());
            DaoMedicament.updateMedicament(newMedicament);
            newPatientMed.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
            newPatientMed.setPayer(spinnerField.getValue() * newMedicament.getPrix_med());
            newPatientMed.setQte(spinnerField.getValue());
            newPatientMed.setCode_med(newMedicament.getCode_med());
            DaoPatientMed.updatePatientMed(newPatientMed);
            Stage stage = (Stage) editMedButton.getScene().getWindow();
            stage.close();

        }

    }

    private void handleRestore() {
        Alert dia = new Alert(Alert.AlertType.CONFIRMATION);
        dia.setTitle("Confirmation");
        dia.setHeaderText("Confirmation requise");
        dia.setContentText("Êtes-vous sûr de vouloir restaurer les données ?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
        dia.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        AtomicBoolean conf = new AtomicBoolean(true);
        dia.showAndWait().ifPresent(response -> {
            if (response == buttonTypeNo){
                conf.set(false);
            }
        });
        if(conf.get()) {
            oldMedicament.setStock_med(oldPatientMed.getQte()+oldMedicament.getStock_med());
            DaoMedicament.updateMedicament(oldMedicament);
            DaoPatientMed.deletePatientMed(oldPatientMed.getId());
            Stage stage = (Stage) restoreButton.getScene().getWindow();
            stage.close();
        }

    }

    private void handleDelete(){
        Alert dia = new Alert(Alert.AlertType.CONFIRMATION);
        dia.setTitle("Confirmation");
        dia.setHeaderText("Confirmation requise");
        dia.setContentText("Êtes-vous sûr de vouloir supprimer ce médicament ? (Rq : Cette action est irréversible)");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
        dia.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        AtomicBoolean conf = new AtomicBoolean(true);
        dia.showAndWait().ifPresent(response -> {
            if (response == buttonTypeNo) {
                conf.set(false);
            }
        });
        if(conf.get()) {
            DaoPatientMed.deletePatientMed(oldPatientMed.getId());
            Stage stage = (Stage) supprimerButton.getScene().getWindow();
            stage.close();
        }

    }



    private void handleCancel() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void setPatientMed(PatientMed patientMed) {
        this.oldPatientMed = patientMed;
        this.newPatientMed = patientMed;
        this.oldMedicament = DaoMedicament.getMedicamentByCode(this.oldPatientMed.getCode_med());
        this.newMedicament = this.oldMedicament;
        codeField.setText(String.valueOf(oldPatientMed.getId()));
        choiceField.setValue(newMedicament);
        StockField.setText(String.valueOf(oldMedicament.getStock_med() + oldPatientMed.getQte()));
        spinnerField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, ( oldMedicament.getStock_med() + oldPatientMed.getQte() ), patientMed.getQte()));
        spinnerField.setDisable(false);
        aPayerField.setText(String.valueOf(oldMedicament.getPrix_med() * patientMed.getQte()) + " Dinars");
    }
}
