package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Categorie;
import services.CategorieService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class modif implements Initializable {
    @FXML
    private Button ajouter;
    @FXML
    private Text descriptionf;

    @FXML
    private TextField descriptionform = new TextField();

    @FXML
    private Text nomf;

    @FXML
    private TextField nomform = new TextField();

    @FXML
    private Button retour;

    @FXML
    private Button validerf;

    int i= 0;

    private Categorie cat;
    int id;

    CategorieService cs = new CategorieService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {        nomform.setText("");
            descriptionform.setText("");
            actualise(cs.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void actualise(List<Categorie> categorieList)
    {   /*Categories mm=new Categories();
        int x=mm.i;

        if(!categorieList.isEmpty()){
            if(categorieList.size()-1-x*3>=0){

                nomf.setText(categorieList.get(x*3).getNom());
                descriptionf.setText(categorieList.get(x*3).getDescription());


            }
            if(categorieList.size()-2-x*3>=0){
                nomf.setText(categorieList.get(1+x*3).getNom());
                descriptionf.setText(categorieList.get(1+x*3).getDescription());
            }
            if(categorieList.size()-3-x*3>=0){
                nomf.setText(categorieList.get(2+x*3).getNom());
                descriptionf.setText(categorieList.get(2+x*3).getDescription());
            }

        }*/
    }
    @FXML
    void RetourVersCategorie(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Categorie.fxml"));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);


    }


    @FXML
    void ValiderModification(ActionEvent event) throws SQLException {
        List<Categorie> categories=cs.getAll();
        if (!categories.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous modifier ce produit " + "?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {


            cat.setNom(nomform.getText());
            cat.setDescription(descriptionform.getText());
            CategorieService cc = new CategorieService();
            cc.update(cat, cat.getId());
            // Mise à jour de la liste des terrains affichés
            JOptionPane.showMessageDialog(null, "Modification Success!");
            effacer();

    }}}


    public void initData(Categorie categorie) {
        this.cat = categorie;
        nomform.setText(categorie.getNom());
        descriptionform.setText(categorie.getDescription());
    }

    @FXML
    void ajouter(ActionEvent event) throws SQLException {
        Alert alert;
        if (nomform.getText().isEmpty()
                || descriptionform.getText().isEmpty())
                 {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            cs.add(new Categorie(nomform.getText(),descriptionform.getText()));
            JOptionPane.showMessageDialog(null, "Ajout Success!");

        effacer();

        }
    }
    public void effacer() {
        nomform.setText("");

        descriptionform.setText("");



    }

}
