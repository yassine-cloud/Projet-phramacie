package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoAuth;
import fxjava.projet_pharmacie.Model.Users;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUser implements Initializable {
    private String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    @FXML
    private Button cancel;

    @FXML
    private Button addUserButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField rePassField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addUserButton.setOnAction(event -> handleAdd());
        cancel.setOnAction(event -> handleCancel());
    }

    private void handleAdd() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String pass = passField.getText();
        String rePass = rePassField.getText();

        if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty() || rePass.isEmpty()){
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Veuillez remplir tous les champs");
            dia.show();
            return;
        }
        if(!email.matches(emailRegex)) {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Error");
            dia.setHeaderText("Email invalide");
            dia.setContentText("Veuillez saisir un email valide");
            dia.show();
            return;
        }
        try {
            Integer.parseInt(phone);
        } catch (NumberFormatException e) {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Le numéro de téléphone doit être un nombre");
            dia.show();
            return;
        }
        if(phone.length() != 8){
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Le numéro de téléphone doit contenir 8 chiffres");
            dia.show();
            return;
        }

        if(!pass.equals(rePass)) {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Les mots de passe ne correspondent pas");
            dia.show();
            return;
        }
        if(pass.length() < 4) {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Le mot de passe doit contenir au moins 4 caractères");
            dia.show();
            return;
        }

        Users user = new Users(email, pass , name, phone);
        if(DaoAuth.addUser(user)){
            Alert dia = new Alert(Alert.AlertType.INFORMATION);
            dia.setTitle("Ajout");
            dia.setHeaderText("Succès");
            dia.setContentText("Utilisateur ajouté avec succès");
            dia.show();
            Stage stage = (Stage) addUserButton.getScene().getWindow();
            stage.close();
        } else {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Erreur lors de l'ajout de l'utilisateur");
            dia.show();
        }
    }

    private void handleCancel() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
