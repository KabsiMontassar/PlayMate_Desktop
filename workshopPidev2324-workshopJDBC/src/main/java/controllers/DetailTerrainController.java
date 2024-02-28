package controllers;
import entity.Terrain;
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
import java.io.IOException;
//*******************************************************************************************
public class DetailTerrainController {
    @FXML
    private Label addressd;
    @FXML
    private Button btmodifd;
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
    void modifd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Terrain.fxml"));
        Parent root = loader.load();
        TerrainController controller = loader.getController();
        controller.initData(terrainActuel);
        controller.setajouterButtonVisibility(false); // Masquer le bouton "Modifier"
        Stage stage = new Stage();
        stage.setTitle("Modifier");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}}