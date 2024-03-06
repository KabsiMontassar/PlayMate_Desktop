package test.Controllers.ReservationController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.BlackList;
import models.User;
import services.GestionReservation.*;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BlackListController implements Initializable {


    @FXML
    private AnchorPane anchroPaneafficherBlackliste;
    @FXML
    private AnchorPane anchroPaneAjouterBlackliste;

    @FXML
    private Button buttonAccueil;

    @FXML
    private Button buttonActualites;

    @FXML
    private Button buttonReservation;

    @FXML
    private AnchorPane supprimerBlackliste;

    @FXML
    private VBox vboxSupprimerBlackListe;
    @FXML
    private VBox vboxAfficherrBlackListe1;
    private int IdUser;

    public void SetIdUser(int idUser) {

        this.IdUser = idUser;
    }
    public int GetIdUser() {
        return this.IdUser;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        supprimerBlackliste.setVisible(false);
         anchroPaneafficherBlackliste.setVisible(false);
    }


    public void supprimerblackliste() throws SQLException {
        supprimerBlackliste.setVisible(true);
        vboxSupprimerBlackListe.getChildren().clear();
        BlacklistService blacklistService = new BlacklistService();

        List<BlackList> blackLists = blacklistService.getAllBlackLists();
        for (BlackList blackList :blackLists){
            AnchorPane anchorPane = new AnchorPane();
            HBox hBox = new HBox();
            Label nomMembre = new Label("yemen");
            Label duree = new Label(""+blackList.getDuree());
            Label Cause = new Label(blackList.getCause());

            nomMembre.getStyleClass().add("label-style");
            duree.getStyleClass().add("label-style");
            Cause.getStyleClass().add("label-style");

            Button btnReserver = new Button("Supprimer");
            btnReserver.getStyleClass().add("reserver-button");

            btnReserver.setOnAction(event -> {

                try {

                    blacklistService.supprimerBlackList(blackList.getIdBlackList());
                    supprimerblackliste();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            hBox.getChildren().addAll(nomMembre, duree, Cause, btnReserver);
            anchorPane.getChildren().addAll(hBox);
            vboxSupprimerBlackListe.getChildren().add(anchorPane);


        }

    }

    public void afficherBlacklist(ActionEvent actionEvent) throws SQLException, NoSuchAlgorithmException {

        anchroPaneafficherBlackliste.setVisible(true);
        vboxAfficherrBlackListe1.getChildren().clear();
        BlacklistService blacklistService = new BlacklistService();
        ReservationService reservationService = new ReservationService();
        List<BlackList> blackLists = blacklistService.getAllBlackLists();
        for (BlackList blackList :blackLists){
            AnchorPane anchorPane = new AnchorPane();
            HBox hBox = new HBox();
            User user = ReservationService.getUserWithIdReservation(blackList.getIdReservation());
            Label name = new Label("nom: "+user.getName());
            Label email= new Label("email: "+user.getEmail());
            Label duree = new Label(""+blackList.getDuree());
            Label Cause = new Label(blackList.getCause());

            duree.getStyleClass().add("label-style");
            Cause.getStyleClass().add("label-style");


            hBox.getChildren().addAll(name ,email , duree, Cause);
            anchorPane.getChildren().addAll(hBox);
            vboxAfficherrBlackListe1.getChildren().add(anchorPane);


        }
    }
}
