package test.Controllers.TerrainController;

import models.Roles;
import models.Terrain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.User;
import services.GestionTerrain.TerrainService;
import services.GestionUser.UserService;
import test.MainFx;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//*******************************************************************************************
public class DetailTerrainController {
    @FXML
    private Label addressd;
    @FXML
    private Button btmodifd;
    @FXML
    private Button btretour;
    @FXML
    private MediaView detvid;
    @FXML
    private Label dureed;
    @FXML
    private Label gouvd;
    @FXML
    private Label gradind;
    @FXML
    private ImageView imgd;
    @FXML
    private Label nomd;
    @FXML
    private Label prixd;
    @FXML
    private Label statd;
    @FXML
    private Label vestd;
    //*******************************************************************************************
    private Terrain terrainActuel;

    UserService us = new UserService();
    private  User CurrentUser  ;



    //*******************************************************************************************
    private String getAvailability(boolean value) {
        return value ? "disponible" : "indisponible";
    }
    private String getStatus(boolean value) {
        return value ? "actif" : "en travaux";
    }
    private String getDuration(int minutes) {return minutes + " minutes";}
    private String getPrice(float price) {
        return price + " DT";
    }
    //*******************************************************************************************
    public void initData(Terrain terrain, User u) {
        this.CurrentUser = u;
        terrainActuel = terrain;
        nomd.setText(terrain.getNomTerrain());
        gouvd.setText(terrain.getGouvernorat());
        dureed.setText(getDuration(terrain.getDuree()));
        prixd.setText(getPrice(terrain.getPrix()));
        addressd.setText(terrain.getAddress());
        gradind.setText(getAvailability(terrain.getGradin()));
        vestd.setText(getAvailability(terrain.getVestiaire()));
        statd.setText(getStatus(terrain.getStatus()));
        if (terrain.getImage() != null && !terrain.getImage().isEmpty()) {
           try {
               String imagee = terrain.getImage();
               System.out.println(imagee);
               String basePath = "C:\\Users\\lenovo\\Documents\\GitHub\\SpartansPIWeb\\public\\uploads\\images";
               String firstImagePath = basePath + File.separator + imagee;
               Image image = new Image(firstImagePath);

                 imgd.setImage(image);
            } catch (IllegalArgumentException e) {
                // Handle the error when the URL is invalid or resource not found
                imgd.setImage(null); // Set the image view to display nothing
            }
        }
        if (terrain.getVideo() != null && !terrain.getVideo().isEmpty()) {
            try {
                Media media = new Media(terrain.getVideo());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                detvid.setMediaPlayer(mediaPlayer);
                mediaPlayer.play();
            } catch (IllegalArgumentException e) {
                // Handle the error when the URL is invalid or resource not found
                detvid.setMediaPlayer(null); // Set the image view to display nothing
            }}

    }
    //*******************************************************************************************
    @FXML
    void modifd(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/Terrain.fxml"));
        Parent root = loader.load();
        TerrainController controller = loader.getController();
        controller.setData(us.getByEmail(CurrentUser.getEmail()));
        controller.initData(terrainActuel);
        controller.setajouterButtonVisibility(false); // Masquer le bouton "Modifier"
        Stage stage = new Stage();
        stage.setTitle("Modifier");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void retourd() throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/PageTerrain.fxml"));
            Parent root = loader.load();
            PageTerrainController controller = loader.getController();
            controller.setData(us.getByEmail(CurrentUser.getEmail()));
            Stage stage = new Stage();
            stage.setTitle("Liste des terrains");
            stage.setScene(new Scene(root));
            stage.show();

        // Récupérer la fenêtre actuelle et la cacher
        ((Stage) btretour.getScene().getWindow()).hide();}
}