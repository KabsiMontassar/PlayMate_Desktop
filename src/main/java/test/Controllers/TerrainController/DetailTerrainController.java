package test.Controllers.TerrainController;

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
import services.GestionUser.UserService;
import test.MainFx;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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

    public  void setData(User u){
        this.CurrentUser = u;
    }
    //**



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
    public void initData(Terrain terrain) {
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
            Image image = new Image(terrain.getImage());
            imgd.setImage(image);}
        if (terrain.getVideo() != null && !terrain.getVideo().isEmpty()) {
            Media media = new Media(terrain.getVideo());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            detvid.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();}}
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