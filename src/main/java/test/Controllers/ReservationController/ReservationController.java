package test.Controllers.ReservationController;

import com.mailjet.client.errors.MailjetException;
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
import models.User;
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
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;


import javafx.scene.control.Button;
import services.GestionUser.UserService;
import test.Controllers.TournoiController.DetailTournoiController;
import test.Controllers.UserController.AcceuilController;
import test.MainFx;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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

import static models.TypeReservation.Creer_Partie;

public class ReservationController  {

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

    @FXML
    private  Button buttonAccueil1 ;


    private User CurrentUser ;
    private int IdUser;

    public void SetIdUser(int idUser) {

        System.out.println(idUser);
        this.IdUser = idUser;
    }
    public int GetIdUser() {
        return this.IdUser;
    }


    public AnchorPane Container;
    //********************************************


    @FXML
    void goToAcceuil(ActionEvent event) {
        try {
            UserService us = new UserService();


            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/Acceuil.fxml"));
            Parent root = loader.load();


            AcceuilController acceuilController = loader.getController();


            acceuilController.setData(us.getByid(this.GetIdUser()));



            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) buttonAccueil1.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

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

    @FXML
    private AnchorPane anchroChoix2;


    public void ReserverTerrain(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/reserverTerrainVersion2.fxml"));
            AnchorPane root = loader.load();
            ReserverTerrainController controller = loader.getController();
            controller.SetIdUser(GetIdUser());


            anchroChoix2.getChildren().setAll(root);





        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void chercherAdversaire(ActionEvent actionEvent) {
        try {


            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/chercherAdversaire.fxml"));
            AnchorPane root = loader.load();
            ChercherAdversaireController controller = loader.getController();
            controller.SetIdUser(GetIdUser());

            anchroChoix2.getChildren().setAll(root);
/*
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.show();
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();
            */
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public void LancerDefi(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/LancerVous.fxml"));
            Parent root = loader.load();
            LancezVousController controller = loader.getController();
            controller.SetIdUser(GetIdUser());

            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.show();
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Reservation> reservations =      reservationService.getAllFutureReservationsByIdMembre(this.GetIdUser())  ; //  reservationService.getAllReservationByIdMembre(8);
        for (Reservation reservation : reservations){
            AnchorPane anchorPane = new AnchorPane();
            HBox hBox = new HBox();
            Label dateRes = new Label(reservation.getDateReservation());
            Label HeureRes = new Label(reservation.getHeureReservation());



            dateRes.getStyleClass().add("label-style");
            HeureRes.getStyleClass().add("label-style");



            Button btnSupprimer= new Button("Supprimer");
            btnSupprimer.getStyleClass().add("reserver-button");

            btnSupprimer.setOnAction(event -> {

                try {

                    LocalDate dateReservation = LocalDate.parse(reservation.getDateReservation(), formatter);
                    LocalDate today = LocalDate.now();


                    long daysBetween = ChronoUnit.DAYS.between(dateReservation, today);

                    if (daysBetween <= -1) { // dateReservation est au moins 1 jour avant aujourd'hui

                        BlacklistService blacklistService = new BlacklistService();
                        BlackList blackList = new BlackList();
                        blackList.setIdReservation(reservation.getIdReservation());
                        blackList.setCause("Annulation de la réservation");
                        blackList.setDuree(30);
                        blacklistService.ajouterBlackList(blackList);
                        UserService userService = new UserService();
                        userService.desactiverCompte(this.GetIdUser());
                                                                            //                        reservationService.supprimerReservation(reservation.getIdReservation());
                                                                                        //                        supprimerUneReservation();
                    }else {

                        reservationService.annulerReservation(reservation.getIdReservation());

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchPaddingException e) {
                    throw new RuntimeException(e);
                } catch (IllegalBlockSizeException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (BadPaddingException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (MailjetException e) {
                    throw new RuntimeException(e);
                }
            });

            hBox.getChildren().addAll(dateRes, HeureRes, btnSupprimer);
            anchorPane.getChildren().addAll(hBox);
            vboxSupprimer.getChildren().add(anchorPane);


        }



    }
    @FXML
    public AnchorPane ContainerSupp;

    @FXML
    void chargerInterfaceSuppression(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/SupprimerReservation.fxml"));
            Parent root = (Parent) loader.load();
            ReservationController c = loader.getController();
            c.SetIdUser(GetIdUser());
            anchroChoix2.getChildren().setAll(root);
/*
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();*/
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void chargerInterfaceHistorique(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/historique.fxml"));
            AnchorPane root = loader.load();
            HistoriqueController c = loader.getController();
            c.SetIdUser(GetIdUser());

            anchroChoix2.getChildren().setAll(root);
/*
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();*/
        }catch (Exception e){
            System.out.println(e);
        }
    }
UserService us = new UserService();

    @FXML
    public void gotoaccueil(ActionEvent actionEvent) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/Acceuil.fxml"));
        Parent root = loader.load();
        AcceuilController controller = loader.getController();
        controller.setData(us.getByid(GetIdUser()));
        Stage stage = new Stage();

        stage.setScene(new Scene(root));
        stage.show();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();

    }

    public void jouerPlaymate(ActionEvent actionEvent) {
    }

    public void evenementPart(ActionEvent actionEvent) {
    }

    public void voirProduit(ActionEvent actionEvent) {
    }

    public void Toreservation(ActionEvent actionEvent) {

        try {

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/choix2.fxml"));
            Parent root = loader.load();
            ReservationController controller = loader.getController();
            controller.SetIdUser(this.GetIdUser());
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.show();
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terrains2(ActionEvent actionEvent) {
    }

    public void btnseeProfile(ActionEvent actionEvent) {
    }

    public void logoutaction(ActionEvent actionEvent) {
    }

    public void VoirTerrain(ActionEvent actionEvent) {
    }

    public void VoirOrganisateur(ActionEvent actionEvent) {
    }

    public void openjeu(ActionEvent actionEvent) {
    }











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


