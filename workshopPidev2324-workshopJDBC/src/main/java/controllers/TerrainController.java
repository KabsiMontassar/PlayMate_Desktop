package controllers;
import entity.Terrain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.TerrainService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
//*******************************************************************************************
public class TerrainController {

    public AnchorPane anchorpane;
    @FXML
    private Button btannuler;
    @FXML
    private Button btinserer;
    @FXML
    private Button btninserervid;
    @FXML
    private Button btnsave;
    @FXML
    private Button btupdate;
    @FXML
    private Button btvoir;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfaddress;
    @FXML
    private CheckBox cbGradin;
    @FXML
    private CheckBox cbVestiaire;
    @FXML
    private CheckBox cbStatus;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tfduree;
    @FXML
    private TextField tfemplacement;
    @FXML
    private ImageView img;
    @FXML
    private MediaView vid;
    @FXML
    private VBox terrainContainer;
    //*******************************************************************************************
    private String imagePath;
    private String videoPath;
    private TerrainService ts = new TerrainService();
    private List<Terrain> pageTerrain;
    private Terrain terrainActuel;
    //*******************************************************************************************
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTerrains();
        cbGradin.setSelected(false);
        cbVestiaire.setSelected(false);
        cbStatus.setSelected(false);
        pageTerrain = new ArrayList<>();}
    //*******************************************************************************************
    public void setModifierButtonVisibility(boolean visible) {
        btupdate.setVisible(visible); // Modifier 'btnsave' pour correspondre à l'ID de votre bouton "Modifier"
    }
    //*******************************************************************************************
    public void setajouterButtonVisibility(boolean visible) {
        btnsave.setVisible(visible); // Modifier 'btnsave' pour correspondre à l'ID de votre bouton "Modifier"
    }
    //*******************************************************************************************
    private void showTerrains() {
        List<Terrain> terrains = ts.getAllTerrains();
    }
    //*******************************************************************************************
    @FXML
    void clearField() {
        tfnom.setText("");
        tfaddress.setText("");
        cbGradin.setSelected(false);
        cbVestiaire.setSelected(false);
        cbStatus.setSelected(false);
        tfprix.setText("");
        tfduree.setText("");
        tfemplacement.setText("");
        img.setImage(null);
        vid.setMediaPlayer(null);
        imagePath = null;
        videoPath = null;}
    //*******************************************************************************************
    @FXML
    void createTerrain(ActionEvent event) throws SQLException, IOException {
        if (videoPath == null) {
            videoPath = "";}
        if (isValidTerrain()) {
            if (imagePath == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez sélectionner une image.");
                return; // Arrêter l'exécution si l'image n'est pas sélectionnée
            }
            // Vérifier si le nom du terrain existe déjà dans la liste
            String nomTerrain = tfnom.getText().trim();
            boolean nomExiste = ts.getAllTerrains().stream().anyMatch(terrain -> terrain.getNomTerrain().equalsIgnoreCase(nomTerrain));
            if (nomExiste) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nom du terrain existe déjà.");
            } else {
                float prixValue = Float.parseFloat(tfprix.getText());
                Terrain terrain = new Terrain(tfaddress.getText(), cbGradin.isSelected(), cbVestiaire.isSelected(), cbStatus.isSelected(), tfnom.getText(), prixValue, Integer.parseInt(tfduree.getText()), tfemplacement.getText(), imagePath, videoPath);
                ts.add(terrain);
                showTerrains(); // Mettre à jour l'affichage après avoir ajouté un nouveau terrain
                clearField(); // Efface les champs après l'ajout
                ((Button) event.getSource()).getScene().getWindow().hide();
                voirlist();}}}
    //*******************************************************************************************
    private boolean isValidTerrain() {
        if (tfnom.getText().isEmpty() || tfaddress.getText().isEmpty() || tfprix.getText().isEmpty() || tfduree.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs obligatoires.");
            return false;}
        try {
            float prix = Float.parseFloat(tfprix.getText());
            int duree = Integer.parseInt(tfduree.getText());
            if (prix <= 0 || duree <= 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le prix et la durée doivent être supérieurs à zéro.");
                return false;}
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le prix doit être un nombre décimal et la durée doit être un nombre entier.");
            return false;}
        if (imagePath == null || imagePath.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez sélectionner une image.");
            return false;}
        return true;}
    //*******************************************************************************************
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();}
    //*******************************************************************************************
    @FXML
    void updateTerrain(ActionEvent event) throws IOException {
        if (isValidTerrain()) {
            float prixValue = Float.parseFloat(tfprix.getText());
            terrainActuel.setNomTerrain(tfnom.getText());
            terrainActuel.setAddress(tfaddress.getText());
            terrainActuel.setGradin(cbGradin.isSelected());
            terrainActuel.setVestiaire(cbVestiaire.isSelected());
            terrainActuel.setStatus(cbStatus.isSelected());
            terrainActuel.setPrix(prixValue);
            terrainActuel.setDuree(Integer.parseInt(tfduree.getText()));
            terrainActuel.setGouvernorat(tfemplacement.getText());
            terrainActuel.setImage(imagePath);
            terrainActuel.setVideo(videoPath);
            TerrainService terrainService = new TerrainService();
            terrainService.update(terrainActuel);
            // Mise à jour de la liste des terrains affichés
            showTerrains();
            clearField();
            ((Button) event.getSource()).getScene().getWindow().hide();
            voirlist();}}
    //*******************************************************************************************
    @FXML
    void getData(MouseEvent event) {
        Node source = (Node) event.getSource();
        HBox terrainBox = (HBox) source.getParent();
        Terrain terrain = (Terrain) terrainBox.getUserData();
        if (terrain != null) {
            tfnom.setText(terrain.getNomTerrain());
            tfaddress.setText(terrain.getAddress());
            cbGradin.setSelected(terrain.getGradin());
            cbVestiaire.setSelected(terrain.getVestiaire());
            cbStatus.setSelected(terrain.getStatus());
            tfprix.setText(String.valueOf(terrain.getPrix()));
            tfduree.setText(String.valueOf(terrain.getDuree()));
            tfemplacement.setText(terrain.getGouvernorat());
            String imagePath = terrain.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image(imagePath);
                img.setImage(image);
            } else {
                img.setImage(null);}
            String videoPath = terrain.getVideo();
            if (videoPath != null && !videoPath.isEmpty()) {
                Media media = new Media(videoPath);
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                vid.setMediaPlayer(mediaPlayer);
                mediaPlayer.play();
            } else {
                vid.setMediaPlayer(null);}}}
    //*******************************************************************************************
    @FXML
    void addTerrain_imageview(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            img.setImage(image);}}
    //*******************************************************************************************
    @FXML
    void addvid(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une vidéo");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichiers vidéo", "*.mp4", "*.flv", "*.avi"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            videoPath = selectedFile.toURI().toString();
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            vid.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();}}
    //*******************************************************************************************
    @FXML
    void voirlist() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/PageTerrain.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Liste des terrains");
        stage.setScene(new Scene(root));
        stage.show();
        // Récupérer la fenêtre actuelle et la cacher
        ((Stage) btvoir.getScene().getWindow()).hide();}
    //*******************************************************************************************
    @FXML
    void showTerrainDetails(Terrain terrain) {
        initData(terrain);}
    public void initData(Terrain terrain) {
        this.terrainActuel = terrain;
        tfnom.setText(terrain.getNomTerrain());
        tfaddress.setText(terrain.getAddress());
        cbGradin.setSelected(terrain.getGradin());
        cbVestiaire.setSelected(terrain.getVestiaire());
        cbStatus.setSelected(terrain.getStatus());
        tfprix.setText(String.valueOf(terrain.getPrix()));
        tfduree.setText(String.valueOf(terrain.getDuree()));
        tfemplacement.setText(terrain.getGouvernorat());
        imagePath = terrain.getImage();
        videoPath = terrain.getVideo();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(imagePath);
            img.setImage(image);}
        if (videoPath != null && !videoPath.isEmpty()) {
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            vid.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();}}}