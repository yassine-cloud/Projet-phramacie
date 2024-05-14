package fxjava.projet_pharmacie.Controller;


import fxjava.projet_pharmacie.DAO.DaoAuth;
import fxjava.projet_pharmacie.Model.Users;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public TextField email;
    @FXML
    public PasswordField password;
    @FXML
    public Button but;

    @FXML
    protected void seConnecter() {

        String email = this.email.getText();
        String password = this.password.getText();
        if(email.isEmpty() || password.isEmpty()){
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Authentification");
            dia.setHeaderText("Erreur");
            dia.setContentText("Veuillez remplir tous les champs");
            dia.show();
            return;
        }

        Users user = DaoAuth.authentifier(email, password);
        if (user != null) {

            Alert dia = new Alert(Alert.AlertType.INFORMATION);
            dia.setTitle("Authentification");
            dia.setHeaderText("Bienvenue");
            dia.setContentText("Bienvenue " + user.getNom_user() );
            dia.show();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxjava/projet_pharmacie/Controller/Patient.fxml"));
            try {
                // Set the new scene
                Stage stage = (Stage) but.getScene().getWindow();
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Authentification");
            dia.setHeaderText("Erreur");
            dia.setContentText("Login ou mot de passe incorrect");
            this.email.clear();
            this.password.clear();
            dia.show();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
