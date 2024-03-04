package test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import models.Equipe;
import models.Reservation;
import models.Terrain;
import services.EquipeService;
import services.ReservationService;
import services.TerrainService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static models.TypeReservation.ReserverTerrainPourEquipe;
import static models.TypeReservation.PostulerCommeAdversaire;

public class ChercherAdversaireController implements Initializable {





    @FXML
    private Button VoirReservation;

    @FXML
    private Label nomEquipeInvalide;
    @FXML
    private VBox vbox2;


    @FXML
    private Button buttonAccueil;

    @FXML
    private Button buttonActualites;

    @FXML
    private Button buttonEvenement;

    @FXML
    private Button buttonReservation;

    @FXML
    private ChoiceBox<String> nom_equipe;
    /*
                                    // montaaaaaaaaaaaasar a3tini id user

     */
    private int idUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nomEquipeInvalide.setVisible(false);
        String[] nom = nomEquipes();
        nom_equipe.getItems().addAll(nom);


    }

    public String[] nomEquipes() {
        EquipeService equipeService = new EquipeService();
        // *********************************************************************************************
        //                                                 monta heeeet numro hatit 7
        // *****************************************************************************************
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

    public void VoirReservation(ActionEvent actionEvent) throws SQLException {

        if (nom_equipe.getValue() == null) {
            nomEquipeInvalide.setVisible(true);
        } else {

            VoirReservation.setVisible(false);
            showReservation();
        }
    }

    public void showReservation() throws SQLException {

        vbox2.getChildren().clear();
        vbox2.getStyleClass().add("vbox-spacing");
        vbox2.setMaxSize(1280, 400);

        ReservationService reservationService = new ReservationService();

        List<Reservation> reservationList =  reservationService.getAllFutureUniqueReservations();  //reservationService.getAllReservation();


        for (Reservation reservation : reservationList) {
            int idres = reservation.getIdReservation();
            int idter = reservation.getIdTerrain();
            System.out.println(reservation);
            System.out.println(reservation.toString());
            System.out.println("idres "+idres+" id ter"+idter);
            AnchorPane anchorPane3 = new AnchorPane();
            HBox hBox = new HBox();
            anchorPane3.getStyleClass().add("anchor-pane-style");
            anchorPane3.setPadding(new Insets(10, 10, 10, 10));
            Label idReservationLabel = new Label("Id: " + reservation.getIdReservation());
            //Label nomEquipeLabel = new Label("Nom: " + reservation.getNomEquipe1());
            Label DateReservationLabel = new Label("Address: " + reservation.getDateReservation());
            Label heureReservationLabel = new Label("Gradin: " + reservation.getHeureReservation());

            DateReservationLabel.getStyleClass().add("label-style2");
            heureReservationLabel.getStyleClass().add("label-style2");


            TerrainService terrainService = new TerrainService();
            Terrain terrain = terrainService.getTerrainById(reservation.getIdTerrain());



            if (terrain.getStatus()) {
                System.out.println("4.1");
                Label idLabel = new Label("Id: " + terrain.getId());
                Label nomLabel = new Label("Nom: " + terrain.getNomTerrain());
                Label addressLabel = new Label("Address: " + terrain.getAddress());
                Label gradinLabel = new Label("Gradin: " + terrain.getGradin());
                Label vestiaireLabel = new Label("Vestiaire: " + terrain.getVestiaire());
                Label statusLabel = new Label("Status: " + terrain.getStatus());
                Label prixLabel = new Label("Prix: " + terrain.getPrix());
                Label dureeLabel = new Label("Durée: " + terrain.getDuree());

                nomLabel.getStyleClass().add("label-style2");
                addressLabel.getStyleClass().add("label-style2");
                vestiaireLabel.getStyleClass().add("label-style2");
                prixLabel.getStyleClass().add("label-style2");
                dureeLabel.getStyleClass().add("label-style2");
                gradinLabel.getStyleClass().add("label-style2");

                Button btnReserver = new Button("Réserver");
                btnReserver.getStyleClass().add("reserver-button2");
                System.out.println("5");
                btnReserver.setOnAction(event -> {

                    try {

                        ajouterReservationCommeAdverdaire(terrain.getId(),reservation.getDateReservation() ,reservation.getHeureReservation());


                        int dernieridReservationAjouter = reservationService.getLastIdReservationAddRecently();
                        PaimentController paimentController = new PaimentController();
                        paimentController.SetIdReservation(dernieridReservationAjouter);
                        paimentController.PaymentAPI();


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Réserver terrain avec l'ID: " + terrain.getId());
                });

                hBox.setSpacing(10);
                hBox.getStyleClass().add("hbox-with-padding");

                hBox.getChildren().addAll(nomLabel, addressLabel, gradinLabel, vestiaireLabel, dureeLabel, prixLabel,DateReservationLabel,heureReservationLabel, btnReserver );

                hBox.setSpacing(10);

                anchorPane3.getChildren().addAll(hBox);
                vbox2.getChildren().add(anchorPane3);
            }
        }
    }

    public void ajouterReservationCommeAdverdaire(int idTerrain , String dateReser ,String heure  ) throws SQLException {
        Reservation r1 = new Reservation(false, dateReser, heure, PostulerCommeAdversaire, idTerrain);
        ReservationService reservationService = new ReservationService();
        reservationService.ajouterReservation(r1);

    }


    /********************************/
    public void passerPaiement(int idReservation, int idUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Paiment.fxml"));
            Parent root = (Parent) loader.load();
            PaimentController paimentController = loader.getController();
            paimentController.SetIdReservation(idReservation);
            paimentController.SetIdUser(idUser);
            ;

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}