package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoMedicament;
import fxjava.projet_pharmacie.Model.TypeMed;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Medicament implements Initializable {


    @FXML
    public TableView<fxjava.projet_pharmacie.Model.Medicament> tableMedicament;
    public TableColumn<fxjava.projet_pharmacie.Model.Medicament , String> colNom;
    @FXML
    public TableColumn<fxjava.projet_pharmacie.Model.Medicament , String> colQuantite;

    @FXML
    public TableColumn<fxjava.projet_pharmacie.Model.Medicament , String> colType;
    @FXML
    public Button btnAdd;
    @FXML
    public TableColumn<fxjava.projet_pharmacie.Model.Medicament , String> colPrix;

    @FXML
    private Button buttonDashboard;

    @FXML
    private Button buttonLogout;

    @FXML
    private Button buttonMedicament;

    @FXML
    private Button buttonPatient;

    @FXML
    private Button buttonUser;


    ArrayList<fxjava.projet_pharmacie.Model.Medicament> medicaments;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        medicaments = DaoMedicament.getAllMedicaments();
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom_med()));
        colQuantite.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStock_med())));
        colType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType_med().toString()));
        colPrix.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrix_med())));

        tableMedicament.getItems().setAll(medicaments);

        buttonDashboard.setOnAction(event -> handleDashboard());
        buttonLogout.setOnAction(event -> handleLogout());
//        buttonMedicament.setOnAction(event -> handleMedicament());
        buttonPatient.setOnAction(event -> handlePatient());
        buttonUser.setOnAction(event -> handleUser());

        btnAdd.setOnAction(event -> handleAdd());

        tableMedicament.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tableMedicament.getSelectionModel().isEmpty()) {
                fxjava.projet_pharmacie.Model.Medicament medicament = tableMedicament.getSelectionModel().getSelectedItem();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/EditMedicament.fxml"));
                try {
                    // Set the new scene
                    Scene scene = new Scene((fxmlLoader.load()));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    stage.setTitle("Modifier un medicament");
                    EditMedicament controller = fxmlLoader.getController();
                    controller.uploadMedicament(medicament);
                    stage.showAndWait();
                    medicaments = DaoMedicament.getAllMedicaments();
                    tableMedicament.getItems().setAll(medicaments);
                    tableMedicament.refresh();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private void handleAdd() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/addMedicament.fxml"));
        try {
            // Set the new scene
            Scene scene = new Scene((fxmlLoader.load()));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Ajouter un medicament");
            stage.showAndWait();
            medicaments = DaoMedicament.getAllMedicaments();
            tableMedicament.getItems().setAll(medicaments);
            tableMedicament.refresh();

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

    private void handleUser() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/User.fxml"));
        try {
            // Set the new scene
            Stage stage = (Stage) buttonUser.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
