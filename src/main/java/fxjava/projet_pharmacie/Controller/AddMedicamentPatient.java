package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoMedicament;
import fxjava.projet_pharmacie.DAO.DaoPatientMed;
import fxjava.projet_pharmacie.Model.Medicament;
import fxjava.projet_pharmacie.Model.Patient;
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

public class AddMedicamentPatient implements Initializable {


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

    Patient patient;
    Medicament medicament;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aPayerField.setStyle("-fx-text-fill: green");
        spinnerField.setDisable(true);
        spinnerField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)); // Set a default ValueFactory

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
            medicament = newValue;
            StockField.setText(String.valueOf(medicament.getStock_med()));
            aPayerField.setText(String.valueOf(medicament.getPrix_med()) + " Dinars");
            spinnerField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, medicament.getStock_med(), 1));
            spinnerField.setDisable(false);
        });

        spinnerField.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Spinner value changed to: " + newValue); // Print the new value to verify the listener is triggered


            float newTotal = medicament.getPrix_med() * newValue;
            aPayerField.setText(String.valueOf(newTotal) + " Dinars");
        });


        addMedButton.setOnAction(event -> handleAdd());
        cancel.setOnAction(event -> handleCancel());

    }

    private void handleAdd() {
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
        if(medicament.getType_med() == TypeMed.Special){
            boolean found = false;
            ArrayList<PatientMed> patientMeds = DaoPatientMed.getPatientMedByCodePatient(patient.getCodePatient());
            for (PatientMed pm : patientMeds) {
                if(pm.getCode_med() == medicament.getCode_med()){
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
        PatientMed patientMed = new PatientMed();
        patientMed.setCode_med(choiceField.getValue().getCode_med());
        patientMed.setCodePatient(patient.getCodePatient());
        patientMed.setPayer(medicament.getPrix_med() * spinnerField.getValue());
        patientMed.setQte(spinnerField.getValue());
        patientMed.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        DaoPatientMed.addPatientMed(patientMed);
        medicament.setStock_med(medicament.getStock_med() - spinnerField.getValue());
        DaoMedicament.updateMedicament(medicament);
        Stage stage = (Stage) addMedButton.getScene().getWindow();
        stage.close();
    }
    private void handleCancel() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }


    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
