package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoMedicament;
import fxjava.projet_pharmacie.DAO.DaoPatientMed;
import fxjava.projet_pharmacie.Model.Medicament;
import fxjava.projet_pharmacie.Model.Patient;
import fxjava.projet_pharmacie.Model.PatientMed;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MedicamentPatient implements Initializable {

    @FXML
    private TableView<PatientMed> MedicamentTable;

    @FXML
    private Button addMedButton;

    @FXML
    private Button buttonDashboard;

    @FXML
    private Button buttonLogout;

    @FXML
    private Button buttonMedicament;

    @FXML
    private Button buttonPatient;

    @FXML
    private TableColumn<PatientMed, String> dateColumn;

    @FXML
    private TableColumn<PatientMed, String> medColumn;

    @FXML
    private Label naisPatient;

    @FXML
    private Label nomPatient;

    @FXML
    private TableColumn<PatientMed, String> payerColumn;

    @FXML
    private TableColumn<PatientMed, String> qteColumn;

    @FXML
    private TableColumn<PatientMed, String> typeColumn;

    Patient patient = null;
    ArrayList<PatientMed> patientMeds;
    ArrayList<Medicament> medicaments;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addMedButton.setOnAction(event -> handleAddMed());
        buttonDashboard.setOnAction(event -> handleDashboard());
        buttonLogout.setOnAction(event -> handleLogout());
        buttonMedicament.setOnAction(event -> handleMedicament());
        buttonPatient.setOnAction(event -> handlePatient());

        MedicamentTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !MedicamentTable.getSelectionModel().isEmpty()) {
                PatientMed medicament = MedicamentTable.getSelectionModel().getSelectedItem();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/editMedicamentPatient.fxml"));
                try {
                    // Set the new scene
                    Scene scene = new Scene((fxmlLoader.load()));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    stage.setTitle("Modifier un medicament");
                    EditMedicamentPatient controller = fxmlLoader.getController();
                    controller.setPatientMed(medicament);
                    stage.showAndWait();
                    patientMeds = DaoPatientMed.getPatientMedByCodePatient(patient.getCodePatient());
                    medicaments = DaoMedicament.getAllMedicaments();
                    MedicamentTable.getItems().setAll(patientMeds);
                    MedicamentTable.refresh();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });





    }

    public void setPatient(Patient p){
        this.patient = p;
        nomPatient.setText(p.getNomPatient());
        naisPatient.setText(p.getDate_nais().toString());

        patientMeds = DaoPatientMed.getPatientMedByCodePatient(patient.getCodePatient());
        medicaments = DaoMedicament.getAllMedicaments();


        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        medColumn.setCellValueFactory(cellData -> {
            for (Medicament m : medicaments) {
                if (m.getCode_med() == cellData.getValue().getCode_med()) {
                    return new SimpleStringProperty(m.getNom_med());
                }
            }
            return new SimpleStringProperty("");
        });
        payerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPayer())));
        qteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQte())));
        typeColumn.setCellValueFactory(cellData -> {
            for (Medicament m : medicaments) {
                if (m.getCode_med() == cellData.getValue().getCode_med()) {
                    return new SimpleStringProperty(String.valueOf(m.getType_med()));
                }
            }
            return new SimpleStringProperty("");

        });
        MedicamentTable.getItems().addAll(patientMeds);

    }

    private void handleAddMed() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/addMedicamentPatient.fxml"));
        try {
            // Set the new scene
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            AddMedicamentPatient addMedicament = fxmlLoader.getController();
            addMedicament.setPatient(patient);
            stage.showAndWait();
            patientMeds = DaoPatientMed.getPatientMedByCodePatient(patient.getCodePatient());
            MedicamentTable.getItems().setAll(patientMeds);
            MedicamentTable.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDashboard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Dashboard.fxml"));
        try {
            // Set the new scene
            Stage stage = (Stage) buttonDashboard.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogout() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Login.fxml"));
        try {
            // Set the new scene
            Stage stage = (Stage) buttonLogout.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMedicament() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Medicament.fxml"));
        try {
            // Set the new scene
            Stage stage = (Stage) buttonMedicament.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePatient() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Patient.fxml"));
        try {
            // Set the new scene
            Stage stage = (Stage) buttonPatient.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
