package test.Controllers.TerrainController;
import models.Terrain;
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
import models.User;
import services.GestionTerrain.TerrainService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import test.MainFx;

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
    private ComboBox<String> tfgouvernorat;
    @FXML
    private ComboBox<String> tfcountry;
    @FXML
    private ImageView img;
    @FXML
    private MediaView vid;
    @FXML
    private VBox terrainContainer;
    //*******************************************************************************************
    private String imagePath;
    private String videoPath;
    private TerrainService ts = new TerrainService(); //instance classe service
    private List<Terrain> pageTerrain;
    private Terrain terrainActuel;
    @FXML
    private ComboBox<String> scrollableComboBox;
    private int selectedIndex = 0;

    private  User  CurrentUser  ;

    public  void setData(User e) {
        this.CurrentUser = e ;

    }

    //*******************************************************************************************
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbGradin.setSelected(false); // Décoche la case à cocher Gradin
        cbVestiaire.setSelected(false); // Décoche la case à cocher Vestiaire
        cbStatus.setSelected(false); // Décoche la case à cocher Status
        pageTerrain = new ArrayList<>(); // Initialise la liste des terrains affichés

    }
    @FXML
    void getStatesByCountry(ActionEvent event) {
        String country = tfcountry.getValue(); // Récupère le pays sélectionné
        List<String> states = StatesApi.getbyCountry(country); // Appelle l'API pour obtenir les gouvernorats

        tfgouvernorat.getItems().clear(); // Efface les anciens gouvernorats
        tfgouvernorat.getItems().addAll(states); // Ajoute les nouveaux gouvernorats
    }

    //*******************************************************************************************
    public void setModifierButtonVisibility(boolean visible) {
        btupdate.setVisible(visible); // Rend visible ou invisible le bouton de modification
    }
    //*******************************************************************************************
    public void setajouterButtonVisibility(boolean visible) {
        btnsave.setVisible(visible); // Rend visible ou invisible le bouton d'ajout
    }
    //*******************************************************************************************

    //*******************************************************************************************
    @FXML
    void clearField() {
        tfnom.setText(""); // Efface le contenu du champ nom
        tfaddress.setText(""); // Efface le contenu du champ address
        cbGradin.setSelected(false); // Décoche la case à cocher Gradin
        cbVestiaire.setSelected(false); // Décoche la case à cocher Vestiaire
        cbStatus.setSelected(false); // Décoche la case à cocher Status
        tfprix.setText(""); // Efface le contenu du champ prix
        tfduree.setText(""); // Efface le contenu du champ duree
        tfgouvernorat.setValue(null); // Efface le contenu du champ emplacement
        img.setImage(null); // Efface l'image affichée
        vid.setMediaPlayer(null); // Arrête la lecture de la vidéo
        imagePath = null; // Réinitialise le chemin de l'image
        videoPath = null;}
    //*******************************************************************************************
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean isValidAddress(String address) {
        return address.matches("[a-zA-Z0-9 ]+");
    }
    @FXML
    void createTerrain(ActionEvent event) throws SQLException, IOException {
        if (videoPath == null) {
            videoPath = "";}
        if (isValidTerrain()) //Vérifier si les données du terrain sont valides
            {
            if (imagePath == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez sélectionner une image.");
                return; // Arrêter l'exécution si l'image n'est pas sélectionnée
            }
                String nomTerrain = tfnom.getText();
                if (!isValidName(nomTerrain)) {
                    showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nom du terrain ne doit contenir que des lettres.");
                    return;
                }
                String address = tfaddress.getText();
                if (!isValidAddress(address)) {
                    showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "L'adresse du terrain ne doit contenir que des lettres, des chiffres et des espaces.");
                    return;
                }
            // Vérifier si le nom du terrain existe déjà dans la liste
                boolean nomExiste = ts.getTerrainbyPropid(CurrentUser.getId()).stream().anyMatch(terrain -> terrain.getNomTerrain().equalsIgnoreCase(nomTerrain));
            if (nomExiste) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nom du terrain existe déjà.");
            } else {
                int prixValue = Integer.parseInt(tfprix.getText()); //Convertir le prix en float
                // Créer un nouveau terrain avec les données saisies
                Terrain terrain = new Terrain(tfaddress.getText(), cbGradin.isSelected(), cbVestiaire.isSelected(), cbStatus.isSelected(), tfnom.getText(), prixValue, Integer.parseInt(tfduree.getText()), tfgouvernorat.getValue(), imagePath, videoPath);
                ts.add(CurrentUser,terrain);
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
    // Affiche une boîte de dialogue avec un message d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType); // Crée une nouvelle boîte de dialogue de type alerte
        alert.setTitle(title); // Définit le titre de la boîte de dialogue
        alert.setHeaderText(null); // Définit le texte d'en-tête de la boîte de dialogue à null
        alert.setContentText(message); // Définit le message de la boîte de dialogue
        alert.showAndWait(); // Affiche la boîte de dialogue et attend la réponse de l'utilisateur
    }
    //*******************************************************************************************
    @FXML
    void updateTerrain(ActionEvent event) throws IOException {
        if (isValidTerrain()) {
            int prixValue = Integer.parseInt(tfprix.getText());
            terrainActuel.setNomTerrain(tfnom.getText());
            terrainActuel.setAddress(tfaddress.getText());
            terrainActuel.setGradin(cbGradin.isSelected());
            terrainActuel.setVestiaire(cbVestiaire.isSelected());
            terrainActuel.setStatus(cbStatus.isSelected());
            terrainActuel.setPrix(prixValue);
            terrainActuel.setDuree(Integer.parseInt(tfduree.getText()));
            terrainActuel.setGouvernorat(tfgouvernorat.getValue());
            terrainActuel.setImage(imagePath);
            terrainActuel.setVideo(videoPath);
            TerrainService terrainService = new TerrainService();
            terrainService.update(terrainActuel);
            // Mise à jour de la liste des terrains affichés
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
            tfgouvernorat.setValue(terrain.getGouvernorat());
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
            img.setImage(image);}
    }
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
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/PageTerrain.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        PageTerrainController controller= loader.getController();
        controller.setData(CurrentUser);
        stage.setTitle("Liste des terrains");
        stage.setScene(new Scene(root));
        stage.show();
        // Récupérer la fenêtre actuelle et la cacher
        ((Stage) btvoir.getScene().getWindow()).hide();}
    //*******************************************************************************************
    @FXML
    void showTerrainDetails(Terrain terrain) {initData(terrain);}
    //*******************************************************************************************
    public void initData(Terrain terrain) {
        this.terrainActuel = terrain;
        tfnom.setText(terrain.getNomTerrain());
        tfaddress.setText(terrain.getAddress());
        cbGradin.setSelected(terrain.getGradin());
        cbVestiaire.setSelected(terrain.getVestiaire());
        cbStatus.setSelected(terrain.getStatus());
        tfprix.setText(String.valueOf(terrain.getPrix()));
        tfduree.setText(String.valueOf(terrain.getDuree()));
        tfgouvernorat.setValue(terrain.getGouvernorat());
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