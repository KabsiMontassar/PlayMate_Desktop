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
    public Button retourbtn;
    public Button nextbtn;
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

        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("reservezMaintenant.fxml"));
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

        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource(elements.get(currentIndex)));
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
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource(elements.get(currentIndex)));
        AnchorPane root = loader.load();
        System.out.println(elements.get(((currentIndex-1)+3)%3));
        root.setStyle("-fx-background-color: white;");
        AccueilStuff.getChildren().setAll(root);
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
