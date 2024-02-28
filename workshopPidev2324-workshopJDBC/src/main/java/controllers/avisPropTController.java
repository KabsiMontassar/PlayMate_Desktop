package controllers;

import entity.AvisTerrain;
import entity.Terrain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.util.List;
//*******************************************************************************************
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
    //*******************************************************************************************
    List<AvisTerrain> lt;
    Terrain t = new Terrain();
    int i =0;
    //*******************************************************************
    void actualise(List<AvisTerrain> avisTerrains){
        if(avisTerrains.size()-1-i*3>0){btnsuivant.setVisible(true);}
        if(i > 0){btnretour.setVisible(true);}
        if(!avisTerrains.isEmpty()){
            if(avisTerrains.size()-1-i*3>=0){
                BOX1.setVisible(true);
                comment1.setText(avisTerrains.get(i*3).getCommentaire());
                note1.setText(String.valueOf(avisTerrains.get(i*3).getNote()));
            }else{BOX1.setVisible(false);}
            if(avisTerrains.size()-2-i*3>=0){BOX2.setVisible(true);
                comment2.setText(avisTerrains.get(1+i*3).getCommentaire());
                note2.setText(String.valueOf(avisTerrains.get(1+i*3).getNote()));
            }else{BOX2.setVisible(false);}
            if(avisTerrains.size()-3-i*3>=0){
                BOX3.setVisible(true);
                comment3.setText(avisTerrains.get(2+i*3).getCommentaire());
                note3.setText(String.valueOf(avisTerrains.get(2+i*3).getNote()));
            }else{BOX3.setVisible(false);}}
        else{
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);
        }
        //btnsuivant.setVisible(avisTerrains.size()-3*i > 3);
        }
    //*******************************************************************************************
    @FXML
    void retour(ActionEvent event){
        i -=1;
        actualise(lt);}
    //*******************************************************************************************
    @FXML
    void suivant(ActionEvent event){i +=1;
        actualise(lt);}
    //*******************************************************************************************
    public void initData(List<AvisTerrain> avisTerrains) {
        this.lt = avisTerrains;
        actualise(avisTerrains);}
}