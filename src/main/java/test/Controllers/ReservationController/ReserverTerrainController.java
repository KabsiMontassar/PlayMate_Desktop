package test.Controllers.ReservationController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.Equipe;
import models.Reservation;
import models.Terrain;
import services.GestionEquipe.EquipeService;
import services.GestionReservation.*;
import services.GestionTerrain.TerrainService;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import test.MainFx;

import static models.TypeReservation.ReserverTerrainPourEquipe;

public class ReserverTerrainController implements Initializable {



    @FXML
    private VBox Vbox1;

    @FXML
    private AnchorPane anchroPane2;

    @FXML
    private AnchorPane anchropane1;

    @FXML
    private Button buttonAccueil;

    @FXML
    private Button buttonActualites;

    @FXML
    private Button buttonEvenement;

    @FXML
    private Button buttonReservation;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label nomEquipeInvalide;

    @FXML
    private ChoiceBox<String> nom_equipe;



    @FXML
    private Label horaireInvalides;
    @FXML
    private Label dateInvalide;

    @FXML
    private TextField heure;

    @FXML
    private ComboBox<String> filterchoice;

// montaaaaaaaaaaaasar a3tini id user
    private int idUser ;

    public void initialize(URL location, ResourceBundle resources) {
        horaireInvalides.setVisible(false);
        dateInvalide.setVisible(false);
        nomEquipeInvalide.setVisible(false);

        //      ----------------------------------------------   ID USER  YAAAAAAAAAAAAAAAAA MONTASSAR
        String[] nom = nomEquipes();
        nom_equipe.getItems().addAll(nom);

        filterchoice.setItems(FXCollections.observableArrayList("prix","duree"));
    }
    //     ------------------------------------------------------------   idUser
    public String[] nomEquipes(){
        EquipeService equipeService = new EquipeService();
        // *********************************************************************************************
        //                                                 monta heeeet numro hatit 7
        try {
            List<Equipe> equipeList = equipeService.getEquipesParMembre(36);
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

// ajouter photo et video
    // comme quoi reservation 1 er equi


    public List<Terrain> trierTerrainsParPrix() {
        TerrainService ts = new TerrainService();
        List<Terrain> terrains = ts.getAllTerrains();
        terrains.sort(Comparator.comparingInt(Terrain::getPrix));
        return terrains;
    }

//    public List<Terrain> trierTerrainsParDuree() {
//        TerrainService ts = new TerrainService();
//        List<Terrain> terrains = ts.getAllTerrains();
//        terrains.sort(Comparator.comparingInt(Terrain::getDuree));
//        return terrains;
//    }

    public List<Terrain> trierTerrainsParDuree() {
        TerrainService ts = new TerrainService();
        List<Terrain> terrains = ts.getAllTerrains();

        // Tri des terrains par duree de la plus grande à la plus petite
        terrains.sort(Comparator.comparingInt(Terrain::getDuree).reversed());

        return terrains;
    }

    public void showTerrains() throws SQLException {
        Vbox1.getChildren().clear();
        Vbox1.getStyleClass().add("vbox-spacing");

        ReservationService reservationService = new ReservationService();

        if (verfierDate(datepicker) && verfierHeure(heure.getText()) && SelecterEquipe()) {
            if (filterchoice.getValue() != null) {
                if (filterchoice.getValue().equals("prix")) {
                    List<Terrain> terrainsParPrix = trierTerrainsParPrix();
                    afficherTerrains(terrainsParPrix, reservationService);
                } else {
                    List<Terrain> terrainsParDuree = trierTerrainsParDuree();
                    afficherTerrains(terrainsParDuree, reservationService);
                }
            } else {
                TerrainService terrainService = new TerrainService();
                List<Terrain> terrains = terrainService.getAllTerrains();
                afficherTerrains(terrains, reservationService);
            }
        }
    }

    private void afficherTerrains(List<Terrain> terrains, ReservationService reservationService) throws SQLException {
        for (Terrain terrain : terrains) {
            if (terrain.getStatus() && reservationService.VerfierDisponibleTerrain(terrain.getId(), terrain.getDuree(), heure.getText(), convertirDateEnString(datepicker))) {
                AnchorPane anchorPane3 = new AnchorPane();
                HBox hBox = new HBox();
                anchorPane3.getStyleClass().add("anchor-pane-style");
                Label idLabel = new Label("Id: " + terrain.getId());
                Label nomLabel = new Label("Nom: " + terrain.getNomTerrain());
                Label addressLabel = new Label("Address: " + terrain.getAddress());
                Label gradinLabel = new Label("Gradin: " + terrain.getGradin());
                Label vestiaireLabel = new Label("Vestiaire: " + terrain.getVestiaire());
                Label statusLabel = new Label("Status: " + terrain.getStatus());
                Label prixLabel = new Label("Prix: " + terrain.getPrix());
                Label dureeLabel = new Label("Durée: " + terrain.getDuree());

                nomLabel.getStyleClass().add("label-style");
                addressLabel.getStyleClass().add("label-style");
                vestiaireLabel.getStyleClass().add("label-style");
                prixLabel.getStyleClass().add("label-style");
                dureeLabel.getStyleClass().add("label-style");
                gradinLabel.getStyleClass().add("label-style");

                Button btnReserver = new Button("Réserver");
                btnReserver.getStyleClass().add("reserver-button");

                btnReserver.setOnAction(event -> {
                    try {
                        ajouterReservationTerrain(terrain.getId());
                        ReservationService reservationService1 = new ReservationService();
                        int dernieridReservationAjouter = reservationService1.getLastIdReservationAddRecently();
                        PaimentController paimentController = new PaimentController();
                        paimentController.SetIdReservation(dernieridReservationAjouter);
                        paimentController.PaymentAPI();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Réserver terrain avec l'ID: " + terrain.getId());
                });

                hBox.getChildren().addAll(nomLabel, addressLabel, gradinLabel, vestiaireLabel, dureeLabel, prixLabel, btnReserver);
                anchorPane3.getChildren().addAll(hBox);
                Vbox1.getChildren().add(anchorPane3);
            }
        }
    }


    public void ajouterReservationTerrain(int idTerrain ) throws SQLException {

        if(verfierHeure(heure.getText()) && verfierDate(datepicker)){
            ReservationService reservationService1 = new ReservationService();

            TerrainService terrainService = new TerrainService();
            Terrain terraine = terrainService.getTerrainById(idTerrain);

            if(reservationService1.VerfierDisponibleTerrain( idTerrain,terraine.getDuree(),heure.getText(),convertirDateEnString(datepicker))) {

                Reservation r1 = new Reservation(false, convertirDateEnString(datepicker), heure.getText(), ReserverTerrainPourEquipe, idTerrain);
                ReservationService reservationService = new ReservationService();
                reservationService.ajouterReservation(r1);


                //PaimentController paimentController = new PaimentController();
            }

        }
    }
    // **********************************************************************************************************************

    // **********************************************************************************************************************

    // **********************************************************************************************************************

    // **********************************************************************************************************************

    // **********************************************************************************************************************
    // **********************************************************************************************************************



    // *********************************************      controle saisie  *************************************************


    // **********************************************************************************************************************
    // **********************************************************************************************************************

    // **********************************************************************************************************************
    // **********************************************************************************************************************

    // **********************************************************************************************************************




    // verfier la selection d une equipe :

    public boolean SelecterEquipe(){
        if (nom_equipe.getValue() == null) {
            nomEquipeInvalide.setVisible(true);
            return false;
        } else {

            return true;
        }
    }

    //transformer la horaire a la forme hh:mm puis verfier que le match entre 8h et 23
    public boolean verfierHeure(String horaire) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalTime heure = LocalTime.parse(horaire, formatter);
             if(heure.isAfter(LocalTime.of(7, 59)) && heure.isBefore(LocalTime.of(23, 1))){
                 return true ;
             }else {
                    horaireInvalides.setVisible(true);
                    return false;
             }
        } catch (Exception e) {
            horaireInvalides.setVisible(true);
            return false;
        }
    }
    // verfier que la date est en future
    public boolean verfierDate(DatePicker datepicker){
        LocalDate dateActuelle = LocalDate.now();
        LocalDate dateSelectionnee = datepicker.getValue();
        if(dateSelectionnee != null && dateSelectionnee.isAfter(dateActuelle)){
            return true ;
        }else {
            dateInvalide.setVisible(true);
            return false;
        }
    }

    // convertir la date sous la forme yyyy-mm-dd
    public String convertirDateEnString(DatePicker datepicker){
        LocalDate dateSelectionnee = datepicker.getValue(); ;
        if (dateSelectionnee != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return dateSelectionnee.format(formatter);
        } else {
            return null;
        }
    }

    //       -------------------------------------------------------------------------------------------------

    //       ------------------------------------------------------------------------------------------------

    //       ------------------------------------------------------------------------------------------------

    //       ------------------------------------------------------------------------------------------------

    //       -------------------------------------Passage de fxml a autre-------------------------------------

    //       ------------------------------------------------------------------------------------------------

    //       ------------------------------------------------------------------------------------------------
    public void AfficherTerrain(ActionEvent actionEvent) throws SQLException {
        dateInvalide.setVisible(false);
        horaireInvalides.setVisible(false);
        nomEquipeInvalide.setVisible(false);
        showTerrains();
    }
    public void passerPaiement(int idReservation , int idUser){
        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/Paiment.fxml"));
            Parent root = (Parent) loader.load();
            PaimentController paimentController = loader.getController();
            paimentController.SetIdReservation(idReservation);
            paimentController.SetIdUser(idUser); ;

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private static Scene scene;
    public void ActualiserLaPageReservation(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/reserverTerrainVersion2.fxml"));
            Parent root = (Parent) loader.load();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}


//    public void  showTerrains() throws SQLException {
//
//
//
//
//
//
//        Vbox1.getChildren().clear();
//        Vbox1.getStyleClass().add("vbox-spacing");
//
//
//        ReservationService reservationService = new ReservationService();
//
//        if(verfierDate(datepicker) && verfierHeure(heure.getText()) && SelecterEquipe()) {
//
//            if(filterchoice.getValue() !=null) {
//                if (filterchoice.getValue().equals("prix")) {
//                    List<Terrain> terrainsParPrix = trierTerrainsParPrix();
//                    for (Terrain terrain : terrainsParPrix) {
//                        // terrain dispo wela lee
//                        if (terrain.getStatus()) {
//                            // njib ken el disponible fel wa9t w nhar
//                            if (reservationService.VerfierDisponibleTerrain(terrain.getId(), terrain.getDuree(), heure.getText(), convertirDateEnString(datepicker))) {
//                                AnchorPane anchorPane3 = new AnchorPane();
//                                HBox hBox = new HBox();
//                                anchorPane3.getStyleClass().add("anchor-pane-style");
//                                Label idLabel = new Label("Id: " + terrain.getId());
//                                Label nomLabel = new Label("Nom: " + terrain.getNomTerrain());
//                                Label addressLabel = new Label("Address: " + terrain.getAddress());
//                                Label gradinLabel = new Label("Gradin: " + terrain.getGradin());
//                                Label vestiaireLabel = new Label("Vestiaire: " + terrain.getVestiaire());
//                                Label statusLabel = new Label("Status: " + terrain.getStatus());
//                                Label prixLabel = new Label("Prix: " + terrain.getPrix());
//                                Label dureeLabel = new Label("Durée: " + terrain.getDuree());
//
//                                nomLabel.getStyleClass().add("label-style");
//                                addressLabel.getStyleClass().add("label-style");
//                                vestiaireLabel.getStyleClass().add("label-style");
//                                prixLabel.getStyleClass().add("label-style");
//                                dureeLabel.getStyleClass().add("label-style");
//                                gradinLabel.getStyleClass().add("label-style");
//
//                                Button btnReserver = new Button("Réserver");
//                                btnReserver.getStyleClass().add("reserver-button");
//
//                                btnReserver.setOnAction(event -> {
//
//                                    try {
//
//                                        // loutaa
//                                        ajouterReservationTerrain(terrain.getId());
//
//                                        ReservationService reservationService1 = new ReservationService();
//                                        int dernieridReservationAjouter = reservationService1.getLastIdReservationAddRecently();
//                                        PaimentController paimentController = new PaimentController();
//                                        paimentController.SetIdReservation(dernieridReservationAjouter);
//                                        paimentController.PaymentAPI();
//                                        //                                passerPaiement( dernieridReservationAjouter , idUser);
//                                        //id user a changer de montassar
//
//                                    } catch (SQLException e) {
//                                        throw new RuntimeException(e);
//                                    }
//                                    System.out.println("Réserver terrain avec l'ID: " + terrain.getId());
//                                });
//
//                                hBox.getChildren().addAll(nomLabel, addressLabel, gradinLabel, vestiaireLabel, dureeLabel, prixLabel, btnReserver);
//                                anchorPane3.getChildren().addAll(hBox);
//                                Vbox1.getChildren().add(anchorPane3);
//                            }
//
//                        }
//                    }
//                }
//                } else {
//                    List<Terrain> terrainsParDuree = trierTerrainsParDuree();
//                    for (Terrain terrain : terrainsParDuree) {
//                        // terrain dispo wela lee
//                        if (terrain.getStatus()) {
//                            // njib ken el disponible fel wa9t w nhar
//                            if (reservationService.VerfierDisponibleTerrain(terrain.getId(), terrain.getDuree(), heure.getText(), convertirDateEnString(datepicker))) {
//                                AnchorPane anchorPane3 = new AnchorPane();
//                                HBox hBox = new HBox();
//                                anchorPane3.getStyleClass().add("anchor-pane-style");
//                                Label idLabel = new Label("Id: " + terrain.getId());
//                                Label nomLabel = new Label("Nom: " + terrain.getNomTerrain());
//                                Label addressLabel = new Label("Address: " + terrain.getAddress());
//                                Label gradinLabel = new Label("Gradin: " + terrain.getGradin());
//                                Label vestiaireLabel = new Label("Vestiaire: " + terrain.getVestiaire());
//                                Label statusLabel = new Label("Status: " + terrain.getStatus());
//                                Label prixLabel = new Label("Prix: " + terrain.getPrix());
//                                Label dureeLabel = new Label("Durée: " + terrain.getDuree());
//
//                                nomLabel.getStyleClass().add("label-style");
//                                addressLabel.getStyleClass().add("label-style");
//                                vestiaireLabel.getStyleClass().add("label-style");
//                                prixLabel.getStyleClass().add("label-style");
//                                dureeLabel.getStyleClass().add("label-style");
//                                gradinLabel.getStyleClass().add("label-style");
//
//                                Button btnReserver = new Button("Réserver");
//                                btnReserver.getStyleClass().add("reserver-button");
//
//                                btnReserver.setOnAction(event -> {
//
//                                    try {
//
//                                        // loutaa
//                                        ajouterReservationTerrain(terrain.getId());
//
//                                        ReservationService reservationService1 = new ReservationService();
//                                        int dernieridReservationAjouter = reservationService1.getLastIdReservationAddRecently();
//                                        PaimentController paimentController = new PaimentController();
//                                        paimentController.SetIdReservation(dernieridReservationAjouter);
//                                        paimentController.PaymentAPI();
//                                        //                                passerPaiement( dernieridReservationAjouter , idUser);
//                                        //id user a changer de montassar
//
//                                    } catch (SQLException e) {
//                                        throw new RuntimeException(e);
//                                    }
//                                    System.out.println("Réserver terrain avec l'ID: " + terrain.getId());
//                                });
//
//                                hBox.getChildren().addAll(nomLabel, addressLabel, gradinLabel, vestiaireLabel, dureeLabel, prixLabel, btnReserver);
//                                anchorPane3.getChildren().addAll(hBox);
//                                Vbox1.getChildren().add(anchorPane3);
//                            }
//
//                        }
//
//                }
//
//
//            }
//            if(filterchoice.getValue() == null){
//                TerrainService terrainService = new TerrainService();
//                List<Terrain> terrains = terrainService.getAllTerrains();
//                for (Terrain terrain : terrains ) {
//                    // terrain dispo wela lee
//                    if (terrain.getStatus()) {
//                        // njib ken el disponible fel wa9t w nhar
//                        if (reservationService.VerfierDisponibleTerrain(terrain.getId(), terrain.getDuree(), heure.getText(), convertirDateEnString(datepicker))) {
//                            AnchorPane anchorPane3 = new AnchorPane();
//                            HBox hBox = new HBox();
//                            anchorPane3.getStyleClass().add("anchor-pane-style");
//                            Label idLabel = new Label("Id: " + terrain.getId());
//                            Label nomLabel = new Label("Nom: " + terrain.getNomTerrain());
//                            Label addressLabel = new Label("Address: " + terrain.getAddress());
//                            Label gradinLabel = new Label("Gradin: " + terrain.getGradin());
//                            Label vestiaireLabel = new Label("Vestiaire: " + terrain.getVestiaire());
//                            Label statusLabel = new Label("Status: " + terrain.getStatus());
//                            Label prixLabel = new Label("Prix: " + terrain.getPrix());
//                            Label dureeLabel = new Label("Durée: " + terrain.getDuree());
//
//                            nomLabel.getStyleClass().add("label-style");
//                            addressLabel.getStyleClass().add("label-style");
//                            vestiaireLabel.getStyleClass().add("label-style");
//                            prixLabel.getStyleClass().add("label-style");
//                            dureeLabel.getStyleClass().add("label-style");
//                            gradinLabel.getStyleClass().add("label-style");
//
//                            Button btnReserver = new Button("Réserver");
//                            btnReserver.getStyleClass().add("reserver-button");
//
//                            btnReserver.setOnAction(event -> {
//
//                                try {
//
//                                    // loutaa
//                                    ajouterReservationTerrain(terrain.getId());
//
//                                    ReservationService reservationService1 = new ReservationService();
//                                    int dernieridReservationAjouter = reservationService1.getLastIdReservationAddRecently();
//                                    PaimentController paimentController = new PaimentController();
//                                    paimentController.SetIdReservation(dernieridReservationAjouter);
//                                    paimentController.PaymentAPI();
//                                    //                                passerPaiement( dernieridReservationAjouter , idUser);
//                                    //id user a changer de montassar
//
//                                } catch (SQLException e) {
//                                    throw new RuntimeException(e);
//                                }
//                                System.out.println("Réserver terrain avec l'ID: " + terrain.getId());
//                            });
//
//                            hBox.getChildren().addAll(nomLabel, addressLabel, gradinLabel, vestiaireLabel, dureeLabel, prixLabel, btnReserver);
//                            anchorPane3.getChildren().addAll(hBox);
//                            Vbox1.getChildren().add(anchorPane3);
//                        }
//                    }
//
//                    }
//                }
//            }
//        }
//    }

