package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoAuth;
import fxjava.projet_pharmacie.DAO.DaoPatient;
import fxjava.projet_pharmacie.Model.Patient;
import fxjava.projet_pharmacie.Model.Users;
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

public class UserController implements Initializable {

    @FXML
    private Button addUserButton;

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

    @FXML
    private TableColumn<Users, String> emailColumn;

    @FXML
    private TableColumn<Users, String> nomColumn;

    @FXML
    private TableView<Users> userTable;

    @FXML
    private TableColumn<Users, String> telColumn;

    ArrayList<Users> users;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        users = DaoAuth.getAllUsers();
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom_user()));
        telColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTel()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        userTable.getItems().addAll(users);

        userTable.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && userTable.getSelectionModel().getSelectedItem() != null){
                Users user = userTable.getSelectionModel().getSelectedItem();
                if(user != null) {

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/editUser.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(scene);
                        stage.setTitle("Edit User");
                       editUser controller = fxmlLoader.getController();
                        controller.setUser(user);
                        stage.showAndWait();
                        users = DaoAuth.getAllUsers();
                        userTable.getItems().setAll(users);
                        userTable.refresh();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    System.out.println("No patient selected");
                }
            }
        });

        addUserButton.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/AddUser.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Add User");
                stage.showAndWait();
                users = DaoAuth.getAllUsers();
                userTable.getItems().setAll(users);
                userTable.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        buttonDashboard.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Dashboard.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) buttonDashboard.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonLogout.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Login.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) buttonLogout.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonMedicament.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Medicament.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) buttonMedicament.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonPatient.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Patient.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) buttonPatient.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        buttonUser.setOnAction(event -> {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/User.fxml"));
//                Scene scene = new Scene(fxmlLoader.load());
//                Stage stage = (Stage) buttonUser.getScene().getWindow();
//                stage.setScene(scene);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }


}
