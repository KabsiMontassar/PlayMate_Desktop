package controllers;

import entity.AvisTerrain;
import entity.Terrain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import services.TerrainService;

import java.util.List;

public class avisPropTController {

    @FXML
    private AnchorPane BOX1;
    @FXML
    private AnchorPane BOX2;
    @FXML
    private AnchorPane BOX3;
    @FXML
    private TextArea comment1;

    @FXML
    private TextArea comment2;

    @FXML
    private TextArea comment3;

    @FXML
    private Text note1;

    @FXML
    private Text note2;

    @FXML
    private Text note3;

    @FXML
    private Button btnretour;
    @FXML
    private Button btnsuivant;

int i =0;
    TerrainService Ts = new TerrainService();
    //*******************************************************************
    public void initialize() {actualise(Ts.getAllTerrains());}
    //*******************************************************************
    void actualise(List<Terrain> terrains){
        if(terrains.size()-1-i*3>0){btnsuivant.setVisible(true);}
        if(i > 0){btnretour.setVisible(true);}
        if(!terrains.isEmpty()){
            if(terrains.size()-1-i*3>=0){
                BOX1.setVisible(true);
                comment1.setText(terrains.get(i*3).getNomTerrain());
                note1.setText(terrains.get(i*3).getAddress());
            }else{BOX1.setVisible(false);}
            if(terrains.size()-2-i*3>=0){BOX2.setVisible(true);
                comment2.setText(terrains.get(1+i*3).getNomTerrain());
                note2.setText(terrains.get(1+i*3).getAddress());
            }else{BOX2.setVisible(false);}
            if(terrains.size()-3-i*3>=0){
                BOX3.setVisible(true);
                comment3.setText(terrains.get(2+i*3).getNomTerrain());
                note3.setText(terrains.get(2+i*3).getAddress());
            }else{BOX3.setVisible(false);}}
        else{
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);
        }
        btnsuivant.setVisible(terrains.size()-3*i > 3);
    }
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
    public void initData(AvisTerrain avisTerrain) {
        // Utilisez les données du terrain pour initialiser votre interface utilisateur
        // Par exemple, vous pouvez afficher le nom du terrain et l'adresse dans les zones de texte correspondantes
        // Assurez-vous que les éléments de l'interface utilisateur (comme comment1, note1, etc.) sont définis dans votre fichier FXML
        comment1.setText(avisTerrain.getCommentaire());
        note1.setText(String.valueOf(avisTerrain.getNote()));
    }

}