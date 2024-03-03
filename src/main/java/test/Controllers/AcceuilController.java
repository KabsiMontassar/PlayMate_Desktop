package test.Controllers;
import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.User;
import services.GestionUser.UserService;
import test.MainFx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AcceuilController {
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

    public void initialize() throws IOException {
        // Initialisation de la première page
        loadPage(elements.get(currentIndex));

        // Changement de page toutes les 5 secondes
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(Duration.seconds(5), event -> {
                    currentIndex = (currentIndex + 1) % elements.size();
                    try {
                        loadPage(elements.get(currentIndex));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
        );
        timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
        timeline.play();
    }

    private void loadPage(String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource(fxmlFileName));
        AnchorPane root = loader.load();
        root.setStyle("-fx-background-color: white;");


        AccueilStuff.getChildren().setAll(root);

        // Animation de transition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), root);
        transition.setFromX(600);
        transition.setToX(0);
        transition.play();
    }

    public void setData(User u) {

        this.CurrentUser = u ;
        System.out.println(CurrentUser);



    }


    @FXML
    void btnseeProfile(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("Profile.fxml"));
            AnchorPane root = loader.load();


            ProfileController profilecontroler = loader.getController();


            profilecontroler.setData(CurrentUser);

            Container.getChildren().setAll(root);



        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void logoutaction(ActionEvent event) {
        try {
            UserService us = new UserService();

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("LoginRegistrationPage.fxml"));
            AnchorPane root = loader.load();

            LoginRegistrationPageController Registrationcontroller = loader.getController();


            Registrationcontroller.setData(us.getByEmail(CurrentUser.getEmail()));
            Container.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
