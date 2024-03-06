package test.Controllers.ReservationController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.BlackList;
import models.Reservation;
import models.Terrain;
import services.GestionReservation.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import services.GestionTerrain.TerrainService;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.scene.control.Button;
import test.MainFx;


import java.awt.*;
import java.awt.MenuItem;
import java.awt.TextField;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static models.TypeReservation.ReserverTerrainPourEquipe;

public class ReservationController implements Initializable {

    @FXML
    private Label adresseterrain;

    @FXML
    private AnchorPane anchorPanePagination;

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
    private Label duree;

    @FXML
    private Label gradin;

    @FXML
    private ImageView imagepagination;

    @FXML
    private SplitMenuButton nom_equipe;

    @FXML
    private Label nomterrain;

    @FXML
    private Pagination pagination;

    @FXML
    private Label prix;

    @FXML
    private Label vestiaire;


    @FXML
    private Button buttonPrecedent;

    @FXML
    private Button buttonSuivant;
    @FXML
    private AnchorPane anchroPane2;

    //*******************************************
    //hover

    @FXML
    private VBox vboxSupprimer;

    @FXML
    private VBox vbox1;

    @FXML
    private VBox vbox2;

    @FXML
    private VBox vbox3;



    private int IdUser;

    public void SetIdUser(int idUser) {

        this.IdUser = idUser;
    }
    public int GetIdUser() {
        return this.IdUser;
    }



    //********************************************
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }





    public boolean verfierHeure(String horaire) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalTime heure = LocalTime.parse(horaire, formatter);
            return (heure.isAfter(LocalTime.of(7, 59)) && heure.isBefore(LocalTime.of(23, 0)));
        } catch (Exception e) {
            return false;
        }
    }
    public boolean verfierDate(DatePicker datepicker){
        LocalDate dateActuelle = LocalDate.now();
        LocalDate dateSelectionnee = datepicker.getValue();
        return dateSelectionnee != null && dateSelectionnee.isAfter(dateActuelle);
    }

    public String convertirDateEnString(DatePicker datepicker){
        LocalDate dateSelectionnee = datepicker.getValue(); ;
        if (dateSelectionnee != null) {
            return dateSelectionnee.toString();
        } else {
            return null;
        }
    }


    private void showTerrains() {
        TerrainService ts = new TerrainService();

        List<Terrain> terrains = ts.getAllTerrains();
        for (Terrain terrain : terrains) {
            HBox terrainBox = new HBox();
            terrainBox.setSpacing(10);
            Label idLabel = new Label("Id: " + terrain.getId());
            Label nomLabel = new Label("Nom: " + terrain.getNomTerrain());
            Label addressLabel = new Label("Address: " + terrain.getAddress());
            Label gradinLabel = new Label("Gradin: " + terrain.getGradin());
            Label vestiaireLabel = new Label("Vestiaire: " + terrain.getVestiaire());
            Label statusLabel = new Label("Status: " + terrain.getStatus());
            Label prixLabel = new Label("Prix: " + terrain.getPrix());
            Label dureeLabel = new Label("Durée: " + terrain.getDuree());
            Label gouvernoratLabel = new Label("Gouvernorat: " + terrain.getGouvernorat());
            ImageView imageView = new ImageView(new Image(terrain.getImage()));
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            Media media = new Media(terrain.getVideo());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setFitWidth(50);
            mediaView.setFitHeight(50);

            terrainBox.getChildren().addAll(idLabel,nomLabel, addressLabel, gradinLabel, vestiaireLabel, statusLabel, prixLabel, dureeLabel, gouvernoratLabel, imageView, mediaView);
            anchroPane2.getChildren().add(terrainBox);
        }


    }

    public void ReserverTerrain(ActionEvent actionEvent) {
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

    public void chercherAdversaire(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/chercherAdversaire.fxml"));
            Parent root = (Parent) loader.load();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void LancerDefi(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/LancerVous.fxml"));
            Parent root = (Parent) loader.load();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void AfficherVbox1Seulement(MouseEvent mouseEvent) {

        vbox1.setVisible(true);
        vbox2.setVisible(false);
        vbox3.setVisible(false);
    }

    public void AfficherVboxtout(MouseEvent mouseEvent) {
        vbox1.setVisible(true);
        vbox2.setVisible(true);
        vbox3.setVisible(true);
    }

    public void AfficherVbox2Seulement(MouseEvent mouseEvent) {

        vbox1.setVisible(false);
        vbox2.setVisible(true);
        vbox3.setVisible(false);
    }

    public void AfficherVbox3Seulement(MouseEvent mouseEvent) {
        vbox1.setVisible(false);
        vbox2.setVisible(false);
        vbox3.setVisible(true);
    }



    public void supprimerUneReservation() throws SQLException {
        vboxSupprimer.setVisible(true);
        vboxSupprimer.getChildren().clear();
        ReservationService reservationService = new ReservationService();

         //  ****************************      id user 8       et en futrue
        List<Reservation> reservations =      reservationService.getAllFutureReservationsByIdMembre(8)  ; //  reservationService.getAllReservationByIdMembre(8);
        for (Reservation reservation : reservations){
            AnchorPane anchorPane = new AnchorPane();
            HBox hBox = new HBox();
            Label dateRes = new Label(reservation.getDateReservation());
            Label HeureRes = new Label(reservation.getHeureReservation());



            dateRes.getStyleClass().add("label-style");
            HeureRes.getStyleClass().add("label-style");



            Button btnReserver = new Button("Supprimer");
            btnReserver.getStyleClass().add("reserver-button");

            btnReserver.setOnAction(event -> {

                try {

                    reservationService.supprimerReservation(reservation.getIdReservation());
                    supprimerUneReservation();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            hBox.getChildren().addAll(dateRes, HeureRes, btnReserver);
            anchorPane.getChildren().addAll(hBox);
            vboxSupprimer.getChildren().add(anchorPane);


        }



    }

    @FXML
    void chargerInterfaceSuppression(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/SupprimerReservation.fxml"));
            Parent root = (Parent) loader.load();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void chargerInterfaceHistorique(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/historique.fxml"));
            Parent root = (Parent) loader.load();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }



/*
    public void add(ActionEvent event) throws SQLException {
        if(verfierHeure(heure.getText()) && verfierDate(datepicker)){
            String date = convertirDateEnString(datepicker);
            //                                                                    a ajouter id terrain
            int idt = 1 ;
            Reservation r1 = new Reservation(false,date,heure.getText(),ReserverTerrainPourEquipe ,idt);
            ReservationService reservationService = new ReservationService();
            reservationService.ajouterReservation(r1);
        }
    }
*/








    /*

    ObservableList<Terrain> data = FXCollections.observableArrayList();
    public void viewTerrain() throws SQLException {
        TerrainService terrainService = new TerrainService();
        List<Terrain> lt = terrainService.getAll();
        for(Terrain t :lt){
            data.add(t);
        }
        nomTerrain.setCellValueFactory(new PropertyValueFactory<Terrain, String>("nomTerrain"));
        address.setCellValueFactory(new PropertyValueFactory<Terrain,String>("address"));
        duree.setCellValueFactory(new PropertyValueFactory<Terrain,Integer>("duree"));
        prix.setCellValueFactory(new PropertyValueFactory<Terrain,Integer>("prix"));
        table.setItems(data);

    }
    private void addReserverCheckBoxToTable() {
        reserver.setCellFactory(param -> new TableCell<>() {
            private final CheckBox reserverCheckBox = new CheckBox();

            {
                // Définissez l'action en fonction du changement d'état de la CheckBox
                reserverCheckBox.setOnAction(event -> {
                    boolean selected = reserverCheckBox.isSelected();
                    Terrain terrain = getTableView().getItems().get(getIndex());
                    // Ajoutez ici le code associé au changement d'état de la CheckBox
                });

                // Ajoutez la classe CSS à votre CheckBox si nécessaire
                reserverCheckBox.getStyleClass().add("votreClasseCSS");
            }

            @Override
            protected void updateItem(Terrain item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(reserverCheckBox);
                }
            }
        });
    }*/



}


