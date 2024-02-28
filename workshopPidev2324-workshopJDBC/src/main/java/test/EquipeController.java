package test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Equipe;
import services.EquipeService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EquipeController implements Initializable {
//    @FXML
//    private Label inavlideNbreEquipe;
//
//    @FXML
//    private Label inavlideNomEquipe;
//
//    @FXML
//    private TextField nomEquipe;
//
//    @FXML
//    private TextField nombreJoueur;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] nom = nomEquipes();
        nomEquipeChoice.getItems().addAll(nom);
        inavlideNbreEquipe.setVisible(false);
        inavlideNomEquipe.setVisible(false);
    }
    @FXML
    private ChoiceBox<String> nomEquipeChoice;

    @FXML
    private Label inavlideNbreEquipe;

    @FXML
    private Label inavlideNomEquipe;

    @FXML
    private TextField nomEquipe;

    @FXML
    private TextField nombreJoueur;
    /*
    *
    *           IDUSER  DEMANDER
    *
    * */
    private int idUser ;
    @FXML
    private VBox vbox1;


    public String[] nomEquipes(){
        EquipeService equipeService = new EquipeService();
        // *********************************************************************************************
        //                                                 monta heeeet numro hatit 8
        try {
            List<Equipe> equipeList = equipeService.getEquipesParMembre(8);
            String[] nomEquipe = new String[equipeList.size()];

            int index = 0;
            for (Equipe equipe : equipeList) {
                nomEquipe[index] = equipe.getNomEquipe();
                index++;
            }
            return nomEquipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void afficherEquipe(ActionEvent event) throws SQLException {

        EquipeService equipeService = new EquipeService();
        // idjoueur a changer !!!!
        List<Equipe> equipeList = equipeService.getEquipesParMembre(7);
        vbox1.getChildren().clear();

        for (Equipe equipe :equipeList){
            AnchorPane anchorPane = new AnchorPane();
            HBox hBox = new HBox();

            Label idequipe = new Label("Id: "+equipe.getIdEquipe());
            Label nomEquipe = new Label(equipe.getNomEquipe());
            Label espace = new Label("   ");
            Label nbjoueur = new Label(String.valueOf(equipe.getNbreJoueur()));
            hBox.getChildren().addAll(nomEquipe,espace,nbjoueur);
            anchorPane.getChildren().addAll(hBox);
            vbox1.getChildren().add(anchorPane);

        }
    }
    @FXML
    void ajouterEquipe(ActionEvent event) throws SQLException {

        if (validateNombreMembres() && validateNom()){

        Equipe equipe = new Equipe();
        equipe.setNomEquipe(nomEquipe.getText());
        int nbj = Integer.parseInt(nombreJoueur.getText());
        System.out.println(nbj);
        equipe.setNbreJoueur(nbj);
        EquipeService equipeService = new EquipeService();
        equipeService.ajouterEquipe(equipe);
        // j ai pris 7 comme id
        int idEquipe = equipeService.getLastIdEquipeAddRecently();
        equipeService.ajouterMembreParEquipe(idEquipe,7);
    }
    }


    public void SupprimerEquipe(ActionEvent actionEvent) throws SQLException {
        EquipeService equipeService = new EquipeService();
        equipeService.supprimerEquipe(nomEquipeChoice.getValue());
    }

    private boolean validateNombreMembres() {
        String nombreEquipesText = nombreJoueur.getText();
        if (Pattern.matches("^(1|2|3|4|5|6|7|8|9|10|11)$", nombreEquipesText)) {
            inavlideNbreEquipe.setVisible(false);
            return  true;
        } else {
            inavlideNbreEquipe.setVisible(true);
        }
        return false;
    }
    private boolean validateNom() {
        String nomText = nomEquipe.getText();

        if (Pattern.matches("^[a-zA-Z0-9]+$", nomText)) {
            inavlideNomEquipe.setVisible(false);
            return true;
        } else {
            inavlideNomEquipe.setVisible(true);

        }
        return false;
    }
}
