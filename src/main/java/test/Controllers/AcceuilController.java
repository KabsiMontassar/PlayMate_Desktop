package test.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.User;
import services.GestionUser.UserService;
import test.MainFx;

import java.io.IOException;
import java.sql.SQLException;

public class AcceuilController {

    public AnchorPane Container;
    public Button sername;
    public AnchorPane notificationicon;
    @FXML
    private Button btnlogout;

    @FXML
    private Text Username;

    @FXML
    private Button btnseeProfile;

    private User CurrentUser ;
    public void setData(User u) {
        this.CurrentUser = u ;
        Username.setText(u.getName());


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
