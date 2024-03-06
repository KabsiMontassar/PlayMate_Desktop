package test.Controllers.ReservationController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Equipe;
import models.Reservation;
import models.Terrain;
import services.GestionEquipe.EquipeService;
import services.GestionReservation.*;
import services.GestionTerrain.TerrainService;
import test.MainFx;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static models.TypeReservation.ReserverTerrainPourEquipe;

public class LancezVousController implements Initializable {


    @FXML
    private Button buttonAccueil;

    @FXML
    private Button buttonActualites;

    @FXML
    private Button buttonEvenement;

    @FXML
    private Button buttonReservation;

    @FXML
    private Button critereChoixBotton;

    @FXML
    private Label dateInvalide;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TextField heure;

    @FXML
    private Label horaireInvalides;

    @FXML
    private Label nomEquipeInvalide;

    @FXML
    private Label nomEquipeInvalide1;

    @FXML
    private ChoiceBox<String> nom_equipe = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> nom_equipe2 = new ChoiceBox<>();
    @FXML
    private VBox Vbox1;


    // ***********   id user
    //*********************************
//*****************************************************************

    //*****************************************************************

    //*****************************************************************
    private int IdUser;

    public void SetIdUser(int idUser) {
        String[] nom = nomEquipes(idUser);

        nom_equipe.getItems().addAll(nom);
        nom_equipe2.getItems().addAll(nom);
        this.IdUser = idUser;
    }
    public int GetIdUser() {
        return this.IdUser;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomEquipeInvalide1.setVisible(false);
        nomEquipeInvalide.setVisible(false);
        dateInvalide.setVisible(false);
        horaireInvalides.setVisible(false);


    }


    public void AfficherTerrain(ActionEvent actionEvent) throws SQLException {
        showTerrains();
    }

    //*********************************************************************

    //*********************************************************************

    //******************** verfier equipe****************

    //*********************************************************************

    //*********************************************************************

    public String[] nomEquipes(int idUser){
        EquipeService equipeService = new EquipeService();
        // *********************************************************************************************
        //                                                 monta heeeet numro hatit 7
        try {
            List<Equipe> equipeList = equipeService.getEquipesParMembre(idUser);     /*this.GetIdUser()*/
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

    public void  showTerrains() throws SQLException {

        TerrainService ts = new TerrainService();
        List<Terrain> terrains = ts.getAllTerrains();

        Vbox1.getChildren().clear();
        Vbox1.getStyleClass().add("vbox-spacing");


        ReservationService reservationService = new ReservationService();

        if(verfierDate(datepicker) && verfierHeure(heure.getText()) && SelecterEquipe()) {
            for (Terrain terrain : terrains) {
                // terrain dispo wela lee
                if (terrain.getStatus()) {
                    // njib ken el disponible fel wa9t w nhar
                    if (reservationService.VerfierDisponibleTerrain(terrain.getId(), terrain.getDuree(), heure.getText(), convertirDateEnString(datepicker))) {
                        AnchorPane anchorPane3 = new AnchorPane();
                        HBox hBox = new HBox();
                        anchorPane3.getStyleClass().add("anchor-pane-style");
                        Label idLabel = new Label("Id: " + terrain.getId());
                        Label nomLabel = new Label("Nom: " + terrain.getNomTerrain());
                        Label addressLabel = new Label("Address: " + terrain.getAddress());
                        Label gradinLabel = new Label("Gradin: " + terrain.getGradin());
                        Label vestiaireLabel = new Label("Vestiaire: " + terrain.getVestiaire());
                        Label statusLabel = new Label("Status: " + terrain.getStatus());
                        int price = (2*terrain.getPrix());
                        Label prixLabel = new Label("Prix: " + price);
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

                                // loutaa ajouterReservation
                                ajouterReservationTerrain(terrain.getId());

                                ReservationService reservationService1 = new ReservationService();
                                int dernieridReservationAjouter = reservationService1.getLastIdReservationAddRecently();

                                PaimentController paimentController = new PaimentController();
                                paimentController.SetIdReservation(dernieridReservationAjouter);
                                paimentController.appelPaymentAPI(price);

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
                reservationService.ajouterReservationPourLancerUnePartie(r1);


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

        if (nom_equipe.getValue() == null  ) {
            nomEquipeInvalide.setVisible(true);
            return false;
        } else if (nom_equipe2.getValue()==null) {
            nomEquipeInvalide1.setVisible(true);
            return false ;
        } else if (nom_equipe.getValue().equals(nom_equipe2.getValue())) {
            nomEquipeInvalide1.setText("changer l'equipe !");
            nomEquipeInvalide1.setVisible(true);
            return false ;

        } else {
            nomEquipeInvalide.setVisible(false);
            nomEquipeInvalide1.setVisible(false);
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

    //**************************
    //**************************

    //**************************        changer view

    //**************************

    //**************************



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
            LancezVousController c = loader.load();
            c.SetIdUser(GetIdUser());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }


}
