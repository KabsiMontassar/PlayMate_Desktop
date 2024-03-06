package test.Controllers.UserController;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.Roles;
import models.User;
import services.GestionUser.UserService;
import services.UserActivityLogger;
import test.Controllers.ReservationController.ReservationController;
import test.Controllers.TerrainController.PageTerrainController;
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
    private User CurrentUser ;
    public AnchorPane Container;
    public Button sername;
    public AnchorPane notificationicon;

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

        this.CurrentUser = u ;
        System.out.println(CurrentUser);
        if(CurrentUser.getRole() != Roles.Proprietaire_de_Terrain){
            System.out.println(CurrentUser.getRole());
            VoirTerrain.setVisible(false);
        }


    }
    public void initialize() throws IOException {

        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/reservezMaintenant.fxml"));
        AnchorPane root = loader.load();
        root.setStyle("-fx-background-color: white;");
        AccueilStuff.getChildren().setAll(root);


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
    void btnseeProfile(ActionEvent event) {
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
    void logoutaction(ActionEvent event) {
        try {

            UserService us = new UserService();
            UserActivityLogger UAL = new UserActivityLogger();
            UAL.logAction(CurrentUser.getEmail() ,  " deconnecter");
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
    void VoirTerrain(ActionEvent event) {
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
}



