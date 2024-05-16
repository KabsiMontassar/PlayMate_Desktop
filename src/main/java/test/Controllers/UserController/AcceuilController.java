package test.Controllers.UserController;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Roles;
import models.User;
import services.GestionUser.UserService;
import services.UserActivityLogger;
import test.Controllers.EquipeController.EquipeController;
import test.Controllers.ProduitController.Products;
import test.Controllers.ReservationController.HistoriqueController;
import test.Controllers.ReservationController.ReservationController;
import test.Controllers.TerrainController.AvisController;
import test.Controllers.TerrainController.PageTerrainController;
import test.Controllers.TournoiController.AfficherListeTournoisClientController;
import test.Controllers.TournoiController.FirstController;
import test.MainFx;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class AcceuilController {
    public Button retourbtn;
    public Button nextbtn;
    public Button btnReservation;
    public Button btnOrganisateur;
    public Button btnevenementPart;
    public Button voirProduit;
    public Button produitbtn;
    private User CurrentUser ;
    public AnchorPane Container;
    public Button sername;
    public AnchorPane notificationicon;


    @FXML
    private ChoiceBox<String> choicebox;

    @FXML
    private Button btnlogout;

    public AnchorPane AccueilStuff;

    ArrayList<String> elements = new ArrayList<>(Arrays.asList("reservezMaintenant.fxml", "Apropos.fxml", "emailus.fxml"));

    @FXML
    private Button btnseeProfile;


    private int currentIndex = 0;

    @FXML
    private Button VoirTerrain;

    public void setData(User u) {
        if(u.getRole() == Roles.Organisateur){
            choicebox.getItems().add("Voir Tournois");
        }
        if(u.getRole() == Roles.Proprietaire_de_Terrain){
            choicebox.getItems().add("Voir Terrains");
        }
        if(u.getRole() == Roles.Fournisseur){
            choicebox.getItems().add("Voir Produits");
        }
        if(u.getRole() == Roles.Membre){
            choicebox.getItems().add("Voir Equipe");
            choicebox.getItems().add("Historique");
            choicebox.getItems().add("Vos Reservation");
        }
        choicebox.getItems().add("Voir Profile");
        choicebox.getItems().add("Logout");

        this.CurrentUser = u ;




    }


    public void initialize() throws IOException {
        choicebox.setOnAction(event -> {
            String selectedItem = choicebox.getSelectionModel().getSelectedItem();
            switch (selectedItem) {
                case "Voir Tournois":
                    VoirOrganisateur();
                    break;
                case "Voir Produits":
                    voirProduits();
                    break;
                case "Voir Terrains":
                    VoirTerrain();
                    break;
                case "Voir Profile":
                    btnseeProfile();
                    break;
                case "Logout":
                    logoutaction();
                    break;
                case "Voir Equipe":
                    toEquipe();
                    break;
                case "Historique":
                    toHistorique();
                    break;
                case "Vos Reservation":
                    toFutureReservation();
                    break;
                default:
                    break;
            }
        });
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/reservezMaintenant.fxml"));
        AnchorPane root = loader.load();
        root.setStyle("-fx-background-color: white;");
        AccueilStuff.getChildren().setAll(root);


    }

    private void voirProduits() {

        try {
            UserService us = new UserService();

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/Products.fxml"));

            AnchorPane root = loader.load();
            Products ptg = loader.getController();

            ptg.SetIdUser(us.getByEmail(CurrentUser.getEmail()).getId());
            Container.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void toFutureReservation() {

        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/SupprimerReservation.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            ReservationController c = loader.getController();
            UserService us = new UserService();
            c.SetIdUser(us.getByEmail(CurrentUser.getEmail()).getId());

            Container.getChildren().setAll(root);


        }catch (Exception e){
            System.out.println(e);
        }
    }


    public void toHistorique() {

        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/historique.fxml"));
            AnchorPane root = loader.load();
            HistoriqueController c = loader.getController();
            UserService us = new UserService();
            c.SetIdUser(us.getByEmail(CurrentUser.getEmail()).getId());

            Container.getChildren().setAll(root);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void toEquipe() {
        try {

            UserService us = new UserService();

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionEquipe/Equipe.fxml"));
            AnchorPane root = loader.load();

            EquipeController ptg = loader.getController();


            ptg.SetIdUser(us.getByEmail(CurrentUser.getEmail()).getId());
            Container.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void loadnextPage(ActionEvent actionEvent) throws IOException {
        if(currentIndex < 2){
            currentIndex +=1 ;
        }else{
            currentIndex = 0;
        }

        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/"+elements.get(currentIndex)));
        AnchorPane root = loader.load();
        System.out.println(elements.get(((currentIndex+1)+3)%3));
        root.setStyle("-fx-background-color: white;");
        AccueilStuff.getChildren().setAll(root);
        // Animation de transition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), root);
        transition.setFromX(600);
        transition.setToX(0);
        transition.play();

    }
    @FXML
    void loadPreviousPage(ActionEvent actionEvent) throws IOException {
        if(currentIndex > 0){
            currentIndex -=1 ;
        }else{
            currentIndex = 2;
        }
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/"+elements.get(currentIndex)));
        AnchorPane root = loader.load();
        System.out.println(elements.get(((currentIndex-1)+3)%3));
        root.setStyle("-fx-background-color: white;");
        AccueilStuff.getChildren().setAll(root);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), root);
        transition.setFromX(600);
        transition.setToX(0);
        transition.play();
    }




    @FXML
    void btnseeProfile() {
        try {

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/Profile.fxml"));
            AnchorPane root = loader.load();


            ProfileController profilecontroler = loader.getController();

            System.out.println(CurrentUser);
            profilecontroler.setData(CurrentUser);

            Container.getChildren().setAll(root);



        } catch (IOException | SQLException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void logoutaction() {
        try {

            UserService us = new UserService();
            UserActivityLogger UAL = new UserActivityLogger();
        //    UAL.logAction(CurrentUser.getEmail() ,  " deconnecter");
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/LoginRegistrationPage.fxml"));
            AnchorPane root = loader.load();

            LoginRegistrationPageController Registrationcontroller = loader.getController();


            Registrationcontroller.setData(us.getByEmail(CurrentUser.getEmail()));
            Container.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VoirTerrain() {
        try {

            UserService us = new UserService();

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/PageTerrain.fxml"));
            AnchorPane root = loader.load();

            PageTerrainController ptg = loader.getController();


            ptg.setData(us.getByEmail(CurrentUser.getEmail()));
            Container.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public void Toreservation(ActionEvent actionEvent) {

        try {

            UserService us = new UserService();

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/choix2.fxml"));
            AnchorPane root = loader.load();

            ReservationController ptg = loader.getController();


            ptg.SetIdUser(us.getByEmail(CurrentUser.getEmail()).getId());
            Container.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public void VoirOrganisateur() {

        try {

            UserService us = new UserService();

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/tournoi.fxml"));
            AnchorPane root = loader.load();

            FirstController ptg = loader.getController();


            ptg.SetIdUser(us.getByEmail(CurrentUser.getEmail()).getId());
            Container.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public void evenementPart(ActionEvent actionEvent) {
        try {

            UserService us = new UserService();

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/tournoiClient.fxml"));
            AnchorPane root = loader.load();

            AfficherListeTournoisClientController ptg = loader.getController();


            ptg.SetIdUser(us.getByEmail(CurrentUser.getEmail()).getId());
            Container.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }



    public void voirProduit(ActionEvent actionEvent) {
        try {

            UserService us = new UserService();

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/Products.fxml"));
            AnchorPane root = loader.load();

            Products ptg = loader.getController();


            ptg.SetIdUser(us.getByEmail(CurrentUser.getEmail()).getId());
            Container.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }



    public void openjeu(ActionEvent actionEvent) {
        try {



            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/jeuPlayMate.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gestion_Tournoi");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terrains2(ActionEvent event) {
        try {

            UserService us = new UserService();

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/AvisTerrain.fxml"));
            AnchorPane root = loader.load();

            AvisController ptg = loader.getController();


            ptg.setData(us.getByEmail(CurrentUser.getEmail()));
            Stage stage = new Stage();
            stage.setTitle("Gestion_Terrain");
            stage.setScene(new Scene(root));
            stage.show();
            ((Button) event.getSource()).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }
}



