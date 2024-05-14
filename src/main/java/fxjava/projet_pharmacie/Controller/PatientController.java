package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoPatient;
import fxjava.projet_pharmacie.Model.Patient;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PatientController {

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, String> nomColumn;

    @FXML
    private TableColumn<Patient, String> telColumn;

    @FXML
    private TableColumn<Patient, String> emailColumn;

    @FXML
    private TableColumn<Patient, String> dateNaisColumn;


    @FXML
    private Button addPatientButton;

    // Initialize the TableView and set up columns (similar to your existing code)
    ArrayList<Patient> patients;
    @FXML
    private void initialize() {
        patients = DaoPatient.getAllPatients();
        // Add patients to the TableView
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomPatient()));
        telColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelPatient()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmailPatient()));
        dateNaisColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_nais().toString()));

        patientTable.getItems().addAll(patients);



        // Set up your TableView columns (codeColumn, nomColumn, etc.)

        patientTable.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && patientTable.getSelectionModel().getSelectedItem() != null){
                Patient patient = patientTable.getSelectionModel().getSelectedItem();
                if(patient != null) {

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/editPatient.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(scene);
                        EditPatient editPatient = fxmlLoader.getController();
                        editPatient.uploadPatient(patient);
                        stage.showAndWait();
                        patients = DaoPatient.getAllPatients(); // Fetch the updated list of patients
                        patientTable.getItems().setAll(patients); // Update the table items
                        patientTable.refresh(); // Refresh the table

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    System.out.println("No patient selected");
                }
            }
        });

        // Handle the button click
        addPatientButton.setOnAction(event -> {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/AddPatient.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            patients = DaoPatient.getAllPatients(); // Fetch the updated list of patients
            patientTable.getItems().setAll(patients); // Update the table items
            patientTable.refresh(); // Refresh the table
        } catch (IOException e) {
            e.printStackTrace();
        }
        });
    }

    // Add a method to handle the button click event

    }