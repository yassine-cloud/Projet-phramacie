package fxjava.projet_pharmacie.Controller;

import fxjava.projet_pharmacie.DAO.DaoMedicament;
import fxjava.projet_pharmacie.Model.Medicament;
import fxjava.projet_pharmacie.Model.TypeMed;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditMedicament implements Initializable {

    @FXML
    private Button cancel;

    @FXML
    private TextField codeField;

    @FXML
    private Button deleteMedicamentButton;

    @FXML
    private Button editMedicamentButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField stockField;

    @FXML
    private ChoiceBox<TypeMed> typeField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        typeField.getItems().add( TypeMed.Normal );
        typeField.getItems().add( TypeMed.Special );


        editMedicamentButton.setOnAction(event -> handleEdit());
        cancel.setOnAction(event -> handleCancel());
        deleteMedicamentButton.setOnAction(event -> handleDelete());
    }

    private void handleEdit() {
        if(codeField.getText() == null || nameField.getText() == null || prixField.getText() == null || stockField.getText() == null || typeField.getValue() == null) {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Modification");
            dia.setHeaderText("Erreur");
            dia.setContentText("Veuillez remplir tous les champs");
            dia.show();
            return;
        }
        try {
            Float.parseFloat(prixField.getText());
            Integer.parseInt(stockField.getText());
            // The text is a number
        } catch (NumberFormatException e) {
            // The text is not a number
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Modification");
            dia.setHeaderText("Erreur");
            dia.setContentText("Le prix et le stock doivent être des nombres");
            dia.show();
            return;
        }
        if( Integer.parseInt(stockField.getText()) < 0 || Double.parseDouble(prixField.getText()) < 0){
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Ajout");
            dia.setHeaderText("Erreur");
            dia.setContentText("Le stock et le prix doivent être positifs");
            dia.show();
            return;
        }
        Medicament medicament = new Medicament( Integer.parseInt(codeField.getText()) ,nameField.getText(),  Float.parseFloat(prixField.getText()),Integer.parseInt(stockField.getText()), TypeMed.valueOf(typeField.getValue().toString()));

        DaoMedicament.updateMedicament(medicament);
        Stage stage = (Stage) editMedicamentButton.getScene().getWindow();
        stage.close();
    }

    private void handleCancel() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    private void handleDelete() {
        if(codeField.getText() == null) {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Suppression");
            dia.setHeaderText("Erreur");
            dia.setContentText("Veuillez selectionner un medicament");
            dia.show();
            return;
        }
        Alert dia = new Alert(Alert.AlertType.CONFIRMATION);
        dia.setTitle("Confirmation");
        dia.setHeaderText("Confirmation requise");
        dia.setContentText("Êtes-vous sûr de vouloir supprimer ce medicament ?\n Cette action est irréversible");

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

        DaoMedicament.deleteMedicament(Integer.parseInt(codeField.getText()));
        Stage stage = (Stage) deleteMedicamentButton.getScene().getWindow();
        stage.close();
    }

    public void uploadMedicament(Medicament medicament){
        codeField.setText(String.valueOf(medicament.getCode_med()));
        nameField.setText(medicament.getNom_med());
        prixField.setText(String.valueOf(medicament.getPrix_med()));
        stockField.setText(String.valueOf(medicament.getStock_med()));
        typeField.setValue(medicament.getType_med());
    }
}
