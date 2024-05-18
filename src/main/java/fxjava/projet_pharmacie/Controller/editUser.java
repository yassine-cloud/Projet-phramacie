package fxjava.projet_pharmacie.Controller;


import fxjava.projet_pharmacie.DAO.DaoAuth;
import fxjava.projet_pharmacie.Model.Users;
import fxjava.projet_pharmacie.Utilities.Password;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;


public class editUser implements Initializable {
    private String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";


    @FXML
    private Button cancel;

    @FXML
    private TextField codeField;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button editUserButton;

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

    Users user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editUserButton.setOnAction(event -> handleEdit());
        deleteUserButton.setOnAction(event -> handleDelete());
        cancel.setOnAction(event -> handleCancel());
    }

    private void handleEdit() {
        String code = codeField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String pass = passField.getText();
        String rePass = rePassField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Les champs ne sont pas remplis");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
            return;
        }
        if (!email.matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Email invalide");
            alert.setContentText("Veuillez saisir un email valide");
            alert.show();
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
        if(!pass.isEmpty()) {
            if (!pass.equals(rePass)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Les mots de passe ne correspondent pas");
                alert.setContentText("Veuillez saisir le même mot de passe");
                alert.show();
                return;
            }
            if (pass.length() < 4) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Mot de passe court");
                alert.setContentText("Le mot de passe doit contenir au moins 4 caractères");
                alert.show();
                return;
            }
            if(Password.checkPassword(pass, user.getPassword())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Le mot de passe est le même");
                alert.setContentText("Veuillez saisir un nouveau mot de passe ( ou laissez le champ vide ) ");
                alert.show();
                return;
            }
        }

        Users user = new Users(Integer.parseInt(code), email,pass , name, phone);
        if(DaoAuth.updateUser(user)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification");
            alert.setHeaderText("Succès");
            alert.setContentText("L'utilisateur a été modifié avec succès");
            alert.show();
            handleCancel();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modification");
            alert.setHeaderText("Erreur");
            alert.setContentText("Erreur lors de la modification de l'utilisateur");
            alert.show();
        }


    }

    private void handleDelete() {
        if(user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Suppression");
            alert.setHeaderText("Erreur");
            alert.setContentText("L'utilisateur n'existe pas");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Voulez-vous vraiment supprimer l'utilisateur ?");
        ButtonType ButtonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType ButtonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(ButtonTypeYes, ButtonTypeNo);
        AtomicBoolean canDelete = new AtomicBoolean(true);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonTypeNo) {
                canDelete.set(false);
            }
        });
        if(!canDelete.get()) return;


        if(DaoAuth.deleteUser(user.getId())) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("Succès");
            alert.setContentText("L'utilisateur a été supprimé avec succès");
            alert.show();
            handleCancel();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Suppression");
            alert.setHeaderText("Erreur");
            alert.setContentText("Erreur lors de la suppression de l'utilisateur");
            alert.show();
        }
    }


    private void handleCancel() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void setUser(Users user) {
        this.user = user;
        codeField.setText(String.valueOf(user.getId()));
        nameField.setText(user.getNom_user());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getTel());

    }
}
