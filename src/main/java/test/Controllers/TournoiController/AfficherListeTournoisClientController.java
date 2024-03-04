package test.Controllers.TournoiController;

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
import models.Tournoi;
import services.GestionTournoi.ServiceTournoi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherListeTournoisClientController {
    @FXML
    private AnchorPane BOX1;

    @FXML
    private AnchorPane BOX2;

    @FXML
    private AnchorPane BOX3;

    @FXML
    private Button btnDetail1;

    @FXML
    private Button btnDetail2;

    @FXML
    private Button btnDetail3;

    @FXML
    private Button btnretour;

    @FXML
    private Button btnsuivant;

    @FXML
    private Button btnCalendar;


    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private AnchorPane mainroot;

    @FXML
    private Text nom1;

    @FXML
    private Text nom2;

    @FXML
    private Text nom3;

    int i= 0;
    ServiceTournoi Ts = new ServiceTournoi();
    //*******************************************************************
    public void initialize() throws SQLException {actualise(Ts.allTournoi());}
    //*******************************************************************
    void actualise(List<Tournoi> tournois){
        if(tournois.size()-1-i*3>0){btnsuivant.setVisible(true);}
        if(tournois.size()-1-i*3 <= 0){btnsuivant.setVisible(false);}
        if(i > 0){btnretour.setVisible(true);}
        if(i == 0){btnretour.setVisible(false);}
        if(!tournois.isEmpty()){
            if(tournois.size()-1-i*3>=0){
                BOX1.setVisible(true);
                nom1.setText(tournois.get(i*3).getNom());
                img1.setImage(new Image(tournois.get(i*3).getAffiche()));}
            else{BOX1.setVisible(false);}
            if(tournois.size()-2-i*3>=0){
                BOX2.setVisible(true);
                nom2.setText(tournois.get(1+i*3).getNom());
                img2.setImage(new Image(tournois.get(1+i*3).getAffiche()));}
            else{
                BOX2.setVisible(false);}
            if(tournois.size()-3-i*3>=0){
                BOX3.setVisible(true);
                nom3.setText(tournois.get(2+i*3).getNom());
                img3.setImage(new Image(tournois.get(2+i*3).getAffiche()));
            }else{BOX3.setVisible(false);}}else{
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);}
        btnsuivant.setVisible(tournois.size()-3*i > 3);}
    //*******************************************************************************************
    @FXML
    void retour(ActionEvent event) throws SQLException {
        i -=1;
        actualise(Ts.allTournoi());}
    //*******************************************************************************************
    @FXML
    void suivant(ActionEvent event) throws SQLException {
        i +=1;
        actualise(Ts.allTournoi());}
    //*******************************************************************************************

    //*******************************************************************************************
    @FXML
    void detail1(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1+3*i;
        Tournoi t = Ts.allTournoi().get(index);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/GestionTournoi/DetailClient.fxml"));
        Parent root;
        root = loader.load();
        DetailClientController controller = loader.getController();
        controller.initData(t);
        Stage stage = new Stage();
        stage.setTitle("Détails Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void detail2(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Tournoi selectedTournoi = Ts.allTournoi().get(index);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/GestionTournoi/DetailClient.fxml"));
        Parent root = loader.load();
        DetailClientController controller = loader.getController();
        controller.initData(selectedTournoi);
        Stage stage = new Stage();
        stage.setTitle("Détails du Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void detail3(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Tournoi selectedTournoi = Ts.allTournoi().get(index);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/GestionTournoi/DetailClient.fxml"));
        Parent root = loader.load();
        DetailClientController controller = loader.getController();
        controller.initData(selectedTournoi);
        Stage stage = new Stage();
        stage.setTitle("Détails du Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}

    @FXML
    void calendar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/GestionTournoi/Calender.fxml"));
        Parent root = loader.load();
        CalendarController controller = loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Gestion_Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();

    }

}
