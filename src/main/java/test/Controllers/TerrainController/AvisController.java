package test.Controllers.TerrainController;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
//*******************************************************************
public class AvisController  {

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
    private Button btndetail2;
    @FXML
    private Button btndetail3;
    @FXML
    private Button btavis1;
    @FXML
    private Button btavis2;
    @FXML
    private Button btavis3;
    @FXML
    private Button btnretour;
    @FXML
    private Button btnsuivant;
    @FXML
    private Text nom1;
    @FXML
    private Text nom2;
    @FXML
    private Text nom3;
    @FXML
    private TextField search;

    @FXML
    private Button btnlogout;

    @FXML
    private ComboBox<String> sort;
    TerrainService Ts = new TerrainService();


    UserService us = new UserService();

    private User CurrentUser;
    int i= 0;
    List<Terrain> sortedTerrains ;

    public void setData(User u ){
        this.CurrentUser = u;
         sortedTerrains = Ts.getAllTerrains();
        sort.getItems().addAll("Prix Croissant", "Prix Décroissant");
        actualise(sortedTerrains);
    }
    //*******************************************************************

    //*******************************************************************
    void actualise(List<Terrain> terrains){
        if(terrains.size()-1-i*3>0){
            btnsuivant.setVisible(true);}
        if(terrains.size()-1-i*3 <= 0){btnsuivant.setVisible(false);}
        if(i > 0){btnretour.setVisible(true);}
        if(i == 0){btnretour.setVisible(false);}
        if(!terrains.isEmpty()){
            if(terrains.size()-1-i*3>=0){
                BOX1.setVisible(true);
                nom1.setText(terrains.get(i*3).getNomTerrain());
                address1.setText(terrains.get(i*3).getAddress());
                try {
                    Image img = new Image(terrains.get(i * 3).getImage());
                    Img1.setImage(img);
                } catch (IllegalArgumentException e) {
                    // Handle the error when the URL is invalid or resource not found
                    Img1.setImage(null); // Set the image view to display nothing
                }
                        }else{BOX1.setVisible(false);}
            if(terrains.size()-2-i*3>=0){BOX2.setVisible(true);
                nom2.setText(terrains.get(1+i*3).getNomTerrain());
                address2.setText(terrains.get(1+i*3).getAddress());
                try {
                    Image img = new Image(terrains.get(1+ i * 3).getImage());
                    Img2.setImage(img);
                } catch (IllegalArgumentException e) {
                    // Handle the error when the URL is invalid or resource not found
                    Img2.setImage(null); // Set the image view to display nothing
                }
            }else{BOX2.setVisible(false);}
            if(terrains.size()-3-i*3>=0){
                BOX3.setVisible(true);
                nom3.setText(terrains.get(2+i*3).getNomTerrain());
                address3.setText(terrains.get(2+i*3).getAddress());
                try {
                    Image img = new Image(terrains.get(2 + i * 3).getImage());
                    Img3.setImage(img);
                } catch (IllegalArgumentException e) {
                    // Handle the error when the URL is invalid or resource not found
                    Img3.setImage(null); // Set the image view to display nothing
                }
            }else{BOX3.setVisible(false);}}
        else{
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);}
        btnsuivant.setVisible(terrains.size()-3*i > 3);}
    //*******************************************************************************************
    @FXML
    void retour(ActionEvent event){
        i -=1;
        actualise(sortedTerrains);}
    //*******************************************************************************************
    @FXML
    void suivant(ActionEvent event){
        i +=1;
        actualise(sortedTerrains);}
    //*******************************************************************************************
    @FXML
    void detail1(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        Terrain terrain = sortedTerrains.get(i*3);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/detailuser.fxml"));
        Parent root = loader.load();

        DetailTerrainController controller = loader.getController();
        controller.initData(terrain, us.getByEmail(CurrentUser.getEmail()));
         Stage stage = new Stage();
        stage.setTitle("Détails Terrain");
        stage.setScene(new Scene(root));
        stage.show();}
    //*******************************************************************************************
    @FXML
    void detail2(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        Terrain terrain = sortedTerrains.get(1+i*3);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/detailuser.fxml"));
        Parent root = loader.load();

        DetailTerrainController controller = loader.getController();
        controller.initData(terrain, us.getByEmail(CurrentUser.getEmail()));
         Stage stage = new Stage();
        stage.setTitle("Détails du Terrain");
        stage.setScene(new Scene(root));
        stage.show();}
    //*******************************************************************************************
    @FXML
    void detail3(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        Terrain terrain = sortedTerrains.get(2+i*3);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/detailuser.fxml"));
        Parent root = loader.load();

        DetailTerrainController controller = loader.getController();
        controller.initData(terrain, us.getByEmail(CurrentUser.getEmail()));
         Stage stage = new Stage();
        stage.setTitle("Détails du Terrain");
        stage.setScene(new Scene(root));
        stage.show();}
    //*******************************************************************************************
    @FXML
    public void add_avis1(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
            Button btn = (Button) event.getSource();
        Terrain terrain = sortedTerrains.get(i*3);
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/DonnerAvis.fxml"));
            Parent root = loader.load();

            DonnerAvisController controller = loader.getController();
        controller.setData(us.getByEmail(CurrentUser.getEmail()));
            controller.initData(terrain);
            Stage stage = new Stage();
            stage.setTitle("Donner un avis");
            stage.setScene(new Scene(root));
            stage.show();}
        //*******************************************************************************************
    @FXML
    void add_avis2(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        Terrain terrain = sortedTerrains.get(1+i*3);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/DonnerAvis.fxml"));
        Parent root = loader.load();

        DonnerAvisController controller = loader.getController();
        controller.setData(us.getByEmail(CurrentUser.getEmail()));
        controller.initData(terrain);
        Stage stage = new Stage();
        stage.setTitle("Donner un avis");
        stage.setScene(new Scene(root));
        stage.show();}
    //*******************************************************************************************
    @FXML
    void add_avis3(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Button btn = (Button) event.getSource();
        Terrain terrain = sortedTerrains.get(2+i*3);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTerrain/DonnerAvis.fxml"));
        Parent root = loader.load();

        DonnerAvisController controller = loader.getController();
        controller.setData(us.getByEmail(CurrentUser.getEmail()));
        controller.initData(terrain);
        Stage stage = new Stage();
        stage.setTitle("Donner un avis");
        stage.setScene(new Scene(root));
        stage.show();}
    //*******************************************************************************************
    @FXML
    void search(ActionEvent event) {
        String address = search.getText().toLowerCase();
        List<Terrain> terrains = new ArrayList<>(Ts.getAllTerrains()); // Copie de la liste d'origine
        FilteredList<Terrain> filteredList = new FilteredList<>(FXCollections.observableList(terrains));
        filteredList.setPredicate(terrain -> terrain.getAddress().toLowerCase().contains(address));
        sortedTerrains = filteredList.stream().collect(Collectors.toList()); // Mettre à jour la liste sortedTerrains
        actualise(sortedTerrains);
        i = 0;} // Réinitialisation de l'index
    //*******************************************************************************************
    @FXML
    void sort(ActionEvent event) {
        String selectedSort = (String) sort.getValue();
        switch (selectedSort) {
            case "Prix Croissant":
                sortedTerrains.sort(Comparator.comparingDouble(Terrain::getPrix));
                System.out.println(sortedTerrains);
                break;
            case "Prix Décroissant":
                sortedTerrains.sort(Comparator.comparingDouble(Terrain::getPrix).reversed());
                System.out.println(sortedTerrains);
                break;
            default:
                break;}
        actualise(sortedTerrains);
        i = 0;}

    @FXML
    void retour1(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/Acceuil.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        AcceuilController controller = loader.getController();
        controller.setData(us.getByEmail(CurrentUser.getEmail()));
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();

    }

}