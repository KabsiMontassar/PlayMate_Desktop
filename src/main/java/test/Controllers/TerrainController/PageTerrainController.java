package test.Controllers.TerrainController;

import models.AvisTerrain;
import models.Terrain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import services.GestionTerrain.AvisService;
import services.GestionTerrain.TerrainService;
import services.GestionUser.UserService;
import test.Controllers.UserController.AcceuilController;
import test.MainFx;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//*******************************************************************
public class PageTerrainController  {
    public AnchorPane main;
    @FXML
    private AnchorPane BOX1;
    @FXML
    private AnchorPane BOX2;
    @FXML
    private AnchorPane BOX3;
    @FXML
    private ImageView Img1;
    @FXML
    private ImageView Img2;
    @FXML
    private ImageView Img3;
    @FXML
    private AnchorPane MainPane;
    @FXML
    private Text address1;
    @FXML
    private Text address2;
    @FXML
    private Text address3;
    @FXML
    private Button btnDetail1;
    @FXML
    private Button btnSupp1;
    @FXML
    private Button btnSupp2;
    @FXML
    private Button btnajout;
    @FXML
    private Button btndetail2;
    @FXML
    private Button btndetail3;
    @FXML
    private Button btnretour;
    @FXML
    private Button btnsuivant;
    @FXML
    private Button btnsupp3;
    @FXML
    private Button btnavis1;
    @FXML
    private Button btnavis2;
    @FXML
    private Button btnavis3;
    @FXML
    private Button btnlogout;
    @FXML
    private Text nom1;
    @FXML
    private Text nom2;
    @FXML
    private Text nom3;
    @FXML
    private Button btstat;

    int i = 0; // Initialisation d'un compteur pour la pagination
     TerrainService Ts = new TerrainService(); //Instanciation du service TerrainService
    private  User CurrentUser;

     List<Terrain> terrains = new ArrayList<>() ;

    UserService us = new UserService();

    public void setData(User u){
        CurrentUser = u;
        terrains.addAll(Ts.getTerrainbyPropid(CurrentUser.getId()));
        actualise(terrains);
    }
//****************************************************************************
    void actualise(List<Terrain> terrains) {
        // Affichage ou masquage du bouton suivant en fonction du nombre de terrains restants à afficher
        if (terrains.size() - 1 - i * 3 > 0) {
            btnsuivant.setVisible(true);
        }
        if (terrains.size() - 1 - i * 3 <= 0) {
            btnsuivant.setVisible(false);
        }
        // Affichage ou masquage du bouton retour en fonction de la position dans la pagination
        if (i > 0) {
            btnretour.setVisible(true);
        }
        if (i == 0) {
            btnretour.setVisible(false);
        }
        if (!terrains.isEmpty()) {
            // Affichage des informations du premier terrain dans BOX1
            if (terrains.size() - 1 - i * 3 >= 0) {
                BOX1.setVisible(true);
                nom1.setText(terrains.get(i * 3).getNomTerrain());
                address1.setText(terrains.get(i * 3).getAddress());
                 try {
                    Image img = new Image(terrains.get(i * 3).getImage());
                    Img1.setImage(img);
                } catch (IllegalArgumentException e) {
                    // Handle the error when the URL is invalid or resource not found
                    Img1.setImage(null); // Set the image view to display nothing
                }
            } else {
                BOX1.setVisible(false);
            }
            // Affichage des informations du deuxième terrain dans BOX2
            if (terrains.size() - 2 - i * 3 >= 0) {
                BOX2.setVisible(true);
                nom2.setText(terrains.get(1 + i * 3).getNomTerrain());
                address2.setText(terrains.get(1 + i * 3).getAddress());
                try {
                    Image img = new Image(terrains.get(1+ i * 3).getImage());
                    Img2.setImage(img);
                } catch (IllegalArgumentException e) {
                    // Handle the error when the URL is invalid or resource not found
                    Img2.setImage(null); // Set the image view to display nothing
                }            } else {
                BOX2.setVisible(false);
            }
            // Affichage des informations du troisième terrain dans BOX3
            if (terrains.size() - 3 - i * 3 >= 0) {
                BOX3.setVisible(true);
                nom3.setText(terrains.get(2 + i * 3).getNomTerrain());
                address3.setText(terrains.get(2 + i * 3).getAddress());
                try {
                    Image img = new Image(terrains.get(2 + i * 3).getImage());
                    Img3.setImage(img);
                } catch (IllegalArgumentException e) {
                    // Handle the error when the URL is invalid or resource not found
                    Img3.setImage(null); // Set the image view to display nothing
                }            } else {
                BOX3.setVisible(false);
            }
        } else {
            // Si la liste de terrains est vide, masquer tous les BOX
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);
        }
        // Affichage ou masquage du bouton suivant en fonction du nombre de terrains restants à afficher
        btnsuivant.setVisible(terrains.size() - 3 * i > 3);
    }
    //*******************************************************************************************
    @FXML
    void retour(ActionEvent event) {
        i -= 1; // Décrémentation du compteur pour afficher les terrains précédents
        actualise(Ts.getTerrainbyPropid(CurrentUser.getId())); // Appel de la méthode actualise avec la liste de tous les terrains
    }
    //*******************************************************************************************
    @FXML
    void suivant(ActionEvent event) {
        i += 1; // Incrémentation du compteur pour afficher les terrains suivants
        actualise(Ts.getTerrainbyPropid(CurrentUser.getId())); // Appel de la méthode actualise avec la liste de tous les terrains
    }
    //*******************************************************************************************
    @FXML
    void Ajout(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/Terrain.fxml"));
        Parent root = loader.load();
        TerrainController controller = loader.getController();
        controller.setModifierButtonVisibility(false); // Masquer le bouton "Modifier"
        controller.setData(us.getByEmail(CurrentUser.getEmail()));
        Stage stage = new Stage();
        stage.setTitle("Gestion_Terrain");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void detail1(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1+3*i;
        Terrain terrain = Ts.getTerrainbyPropid(CurrentUser.getId()).get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/DetailTerrain.fxml"));
        Parent root = loader.load();
        DetailTerrainController controller = loader.getController();
        controller.initData(terrain, us.getByEmail(CurrentUser.getEmail()));
         Stage stage = new Stage();
        stage.setTitle("Détails Terrain");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void detail2(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Terrain selectedTerrain = Ts.getTerrainbyPropid(CurrentUser.getId()).get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/DetailTerrain.fxml"));
        Parent root = loader.load();
        DetailTerrainController controller = loader.getController();
        controller.initData(selectedTerrain, us.getByEmail(CurrentUser.getEmail()));
         Stage stage = new Stage();
        stage.setTitle("Détails du Terrain");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void detail3(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Terrain selectedTerrain = Ts.getTerrainbyPropid(CurrentUser.getId()).get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/DetailTerrain.fxml"));
        Parent root = loader.load();
        DetailTerrainController controller = loader.getController();
        controller.initData(selectedTerrain, us.getByEmail(CurrentUser.getEmail()));
         Stage stage = new Stage();
        stage.setTitle("Détails du Terrain");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void supp1(ActionEvent event) throws SQLException {
        int indexToDelete = i * 3;
        if (indexToDelete >= 0 && indexToDelete < Ts.getTerrainbyPropid(CurrentUser.getId()).size()) {
            Terrain terrainToDelete = Ts.getTerrainbyPropid(CurrentUser.getId()).get(indexToDelete);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce terrain ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Ts.delete(terrainToDelete.getId());
                actualise(Ts.getTerrainbyPropid(CurrentUser.getId()));}
        } else {showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "L'index de terrain à supprimer n'est pas valide.");}}
    //*******************************************************************************************
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();}
    //*******************************************************************************************
    @FXML
    void supp2(ActionEvent event) throws SQLException {
        int indexToDelete = i * 3 + 1;
        if (indexToDelete >= 0 && indexToDelete < Ts.getTerrainbyPropid(CurrentUser.getId()).size()) {
            Terrain terrainToDelete = Ts.getTerrainbyPropid(CurrentUser.getId()).get(indexToDelete);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce terrain ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Ts.delete(terrainToDelete.getId());
                actualise(Ts.getTerrainbyPropid(CurrentUser.getId()));}
        } else {showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "L'index de terrain à supprimer n'est pas valide.");}}
    //*******************************************************************************************
    @FXML
    void supp3(ActionEvent event) throws SQLException {
        int indexToDelete = i * 3 + 2;
        if (indexToDelete >= 0 && indexToDelete < Ts.getTerrainbyPropid(CurrentUser.getId()).size()) {
            Terrain terrainToDelete = Ts.getTerrainbyPropid(CurrentUser.getId()).get(indexToDelete);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce terrain ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Ts.delete(terrainToDelete.getId());
                actualise(Ts.getTerrainbyPropid(CurrentUser.getId()));}
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "L'index de terrain à supprimer n'est pas valide.");}}
    //*******************************************************************************************
    @FXML
    void avis1(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(7)) - 1 + 3 * i;
        Terrain selectedTerrain = Ts.getTerrainbyPropid(CurrentUser.getId()).get(index);
        AvisService as = new AvisService();
        List<AvisTerrain> avisTerrain = as.getAvisByTerrainId(selectedTerrain.getId());
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/avisPropT.fxml"));
        Parent root = loader.load();
        avisPropTController controller = loader.getController();
        controller.setData(us.getByEmail(CurrentUser.getEmail()));
        controller.initData(avisTerrain);
        Stage stage = new Stage();
        stage.setTitle("Les avis");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void avis2(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(7)) - 1 + 3 * i;
        Terrain selectedTerrain = Ts.getTerrainbyPropid(CurrentUser.getId()).get(index);
        AvisService as = new AvisService();
        List<AvisTerrain> avisTerrain = as.getAvisByTerrainId(selectedTerrain.getId());
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/avisPropT.fxml"));
        Parent root = loader.load();
        avisPropTController controller = loader.getController();
        controller.setData(us.getByEmail(CurrentUser.getEmail()));
        controller.initData(avisTerrain);
        Stage stage = new Stage();
        stage.setTitle("Les avis");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void avis3(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(7)) - 1 + 3 * i;
        Terrain selectedTerrain = Ts.getTerrainbyPropid(CurrentUser.getId()).get(index);
        AvisService as = new AvisService();
        List<AvisTerrain> avisTerrain = as.getAvisByTerrainId(selectedTerrain.getId());
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/avisPropT.fxml"));
        Parent root = loader.load();
        avisPropTController controller = loader.getController();
        controller.initData(avisTerrain);
        controller.setData(us.getByEmail(CurrentUser.getEmail()));

        Stage stage = new Stage();
        stage.setTitle("Les avis");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void logoutaction(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/Acceuil.fxml"));
        Parent root = loader.load();
        AcceuilController controller = loader.getController();
        controller.setData(us.getByEmail(CurrentUser.getEmail()));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();

    }


    @FXML
    void stat(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/stat.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        statController controller = loader.getController();
        controller.setData(us.getByEmail(CurrentUser.getEmail()));

        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();
    }





}