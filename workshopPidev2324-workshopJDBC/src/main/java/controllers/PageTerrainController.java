package controllers;

import entity.Terrain;
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
import services.TerrainService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
//*******************************************************************
public class PageTerrainController  {
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
    private Text nom1;
    @FXML
    private Text nom2;
    @FXML
    private Text nom3;
    int i= 0;
    TerrainService Ts = new TerrainService();
    //*******************************************************************
    public void initialize() {actualise(Ts.getAllTerrains());}
    //*******************************************************************
    void actualise(List<Terrain> terrains){
        if(terrains.size()-1-i*3>0){btnsuivant.setVisible(true);}
        if(terrains.size()-1-i*3 <= 0){btnsuivant.setVisible(false);}
        if(i > 0){btnretour.setVisible(true);}
        if(i == 0){btnretour.setVisible(false);}
        if(!terrains.isEmpty()){
            if(terrains.size()-1-i*3>=0){
                BOX1.setVisible(true);
                nom1.setText(terrains.get(i*3).getNomTerrain());
                address1.setText(terrains.get(i*3).getAddress());
                Img1.setImage(new Image(terrains.get(i*3).getImage()));
            }else{BOX1.setVisible(false);}
            if(terrains.size()-2-i*3>=0){BOX2.setVisible(true);
                nom2.setText(terrains.get(1+i*3).getNomTerrain());
                address2.setText(terrains.get(1+i*3).getAddress());
                Img2.setImage(new Image(terrains.get(1+i*3).getImage()));
            }else{BOX2.setVisible(false);}
            if(terrains.size()-3-i*3>=0){
                BOX3.setVisible(true);
                nom3.setText(terrains.get(2+i*3).getNomTerrain());
                address3.setText(terrains.get(2+i*3).getAddress());
                Img3.setImage(new Image(terrains.get(2+i*3).getImage()));
            }else{BOX3.setVisible(false);}}}
    //*******************************************************************************************
    @FXML
    void retour(ActionEvent event){
        i -=1;
        actualise(Ts.getAllTerrains());}
    //*******************************************************************************************
    @FXML
    void suivant(ActionEvent event){i +=1;
        actualise(Ts.getAllTerrains());}
    //*******************************************************************************************
    @FXML
    void Ajout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Terrain.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Gestion_Terrain");
        stage.setScene(new Scene(root));
        stage.show();}
    //*******************************************************************************************
    @FXML
    void detail1(ActionEvent event) throws IOException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1;
        Terrain terrain = Ts.getAllTerrains().get(index);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/DetailTerrain.fxml"));
        Parent root = loader.load();
        DetailTerrainController controller = loader.getController();
        controller.initData(terrain);
        Stage stage = new Stage();
        stage.setTitle("Détails Terrain");
        stage.setScene(new Scene(root));
        stage.show();}
    //*******************************************************************************************
    @FXML
    void detail2(ActionEvent event) throws IOException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Terrain selectedTerrain = Ts.getAllTerrains().get(index);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/DetailTerrain.fxml"));
        Parent root = loader.load();
        DetailTerrainController controller = loader.getController();
        controller.initData(selectedTerrain);
        Stage stage = new Stage();
        stage.setTitle("Détails du Terrain");
        stage.setScene(new Scene(root));
        stage.show();}
    //*******************************************************************************************
    @FXML
    void detail3(ActionEvent event) throws IOException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Terrain selectedTerrain = Ts.getAllTerrains().get(index);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/DetailTerrain.fxml"));
        Parent root = loader.load();
        DetailTerrainController controller = loader.getController();
        controller.initData(selectedTerrain);
        Stage stage = new Stage();
        stage.setTitle("Détails du Terrain");
        stage.setScene(new Scene(root));
        stage.show();}
    //*******************************************************************************************
    @FXML
    void supp1(ActionEvent event) throws SQLException {
        Terrain terrainToDelete = Ts.getAllTerrains().get(i * 3);
        Ts.delete(terrainToDelete.getId());
        actualise(Ts.getAllTerrains());}
    //*******************************************************************************************
    @FXML
    void supp2(ActionEvent event) throws SQLException { Terrain terrainToDelete = Ts.getAllTerrains().get(1 + i * 3);
        Ts.delete(terrainToDelete.getId());
        actualise(Ts.getAllTerrains());}
    //*******************************************************************************************
    @FXML
    void supp3(ActionEvent event) throws SQLException {Terrain terrainToDelete = Ts.getAllTerrains().get(2 + i * 3);
        Ts.delete(terrainToDelete.getId());
        actualise(Ts.getAllTerrains());}}