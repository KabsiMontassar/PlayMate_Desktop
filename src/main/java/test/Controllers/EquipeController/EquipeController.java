package test.Controllers.EquipeController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Equipe;
import services.GestionEquipe.EquipeService;
import services.GestionUser.UserService;
import test.Controllers.UserController.AcceuilController;
import test.MainFx;
;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EquipeController implements Initializable {


    private int idUser ;
    @FXML
    private VBox vbox1;
    @FXML
    private ChoiceBox<String> nomEquipeChoice = new ChoiceBox<>();

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

    private int IdUser;
    public int GetIdUser() {
        return this.IdUser;
    }

    public void SetIdUser(int idUser) throws SQLException {



        String[] nom = nomEquipes(idUser);
        nomEquipeChoice.getItems().addAll(nom);
        this.IdUser = idUser;

    }

    public String[] nomEquipes(int idUser){
        EquipeService equipeService = new EquipeService();
        // *********************************************************************************************
        //                                                 supoosant 3
        try {
            List<Equipe> equipeList = equipeService.getEquipesParMembre(idUser);
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
        List<Equipe> equipeList = equipeService.getEquipesParMembre(GetIdUser());
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
        // supposant 7 comme id
        int idEquipe = equipeService.getLastIdEquipeAddRecently();
        equipeService.ajouterMembreParEquipe(idEquipe,GetIdUser());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inavlideNomEquipe.setVisible(false);
        inavlideNbreEquipe.setVisible(false);

    }
  UserService us = new UserService();
    public void Retour(ActionEvent actionEvent) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/Acceuil.fxml"));
        Parent root = loader.load();
        AcceuilController controller = loader.getController();
        controller.setData(us.getByid(GetIdUser()));
        Stage stage = new Stage();

        stage.setScene(new Scene(root));
        stage.show();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();

    }

    public void toaccueil(ActionEvent actionEvent) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserService us = new UserService();
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/Acceuil.fxml"));
        Parent root = loader.load();
        AcceuilController controller = loader.getController();
        controller.setData(us.getByid(IdUser));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
