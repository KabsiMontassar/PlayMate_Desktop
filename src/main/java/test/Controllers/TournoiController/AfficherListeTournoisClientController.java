package test.Controllers.TournoiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Tournoi;
import services.GestionTournoi.ServiceTournoi;
import test.MainFx;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private TextField recherche;


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

    @FXML
    private Text visite1;

    @FXML
    private Text visite2;

    @FXML
    private Text visite3;

    int i= 0;
    ServiceTournoi Ts = new ServiceTournoi();

    List<Tournoi> Tournois = new ArrayList<>();
    //*******************************************************************


    private int IdUser;
    public int GetIdUser() {
        return this.IdUser;
    }

    public void SetIdUser(int idUser) throws SQLException {

        this.IdUser = idUser;
        Tournois = Ts.allTournoi();
        actualise(Tournois);
    }


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
                visite1.setText(String.valueOf(tournois.get(i*3).getVisite()));
                System.out.println(tournois.get(i*3));
                img1.setImage(new Image(tournois.get(i*3).getAffiche()));}
            else{BOX1.setVisible(false);}
            if(tournois.size()-2-i*3>=0){
                BOX2.setVisible(true);
                nom2.setText(tournois.get(1+i*3).getNom());
                img2.setImage(new Image(tournois.get(1+i*3).getAffiche()));
                visite2.setText(String.valueOf(tournois.get(1+i*3).getVisite()));}
            else{
                BOX2.setVisible(false);}
            if(tournois.size()-3-i*3>=0){
                BOX3.setVisible(true);
                nom3.setText(tournois.get(2+i*3).getNom());
                img3.setImage(new Image(tournois.get(2+i*3).getAffiche()));
                visite3.setText(String.valueOf(tournois.get(2+i*3).getVisite()));
            }else{BOX3.setVisible(false);}}else{
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);}
        btnsuivant.setVisible(tournois.size()-3*i > 3);}
    //*******************************************************************************************
    @FXML
    void retour(ActionEvent event) throws SQLException {
        i -=1;
        actualise(Tournois);}
    //*******************************************************************************************
    @FXML
    void suivant(ActionEvent event) throws SQLException {
        i +=1;
        actualise(Tournois);}
    //*******************************************************************************************

    //*******************************************************************************************
    @FXML
    void detail1(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1+3*i;
        Tournoi t = Tournois.get(index);
        incrementNbClick(t.getId());
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/DetailClient.fxml"));
        Parent root;
        root = loader.load();
        DetailClientController controller = loader.getController();
        controller.initData(t);
        controller.SetIdUser(GetIdUser());
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
        Tournoi selectedTournoi = Tournois.get(index);
        incrementNbClick(selectedTournoi.getId());
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/DetailClient.fxml"));
        Parent root = loader.load();
        DetailClientController controller = loader.getController();
        controller.SetIdUser(GetIdUser());
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
        Tournoi selectedTournoi = Tournois.get(index);
        incrementNbClick(selectedTournoi.getId());
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/DetailClient.fxml"));

        Parent root = loader.load();
        DetailClientController controller = loader.getController();
        controller.SetIdUser(GetIdUser());
        controller.initData(selectedTournoi);
        Stage stage = new Stage();
        stage.setTitle("Détails du Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}

//    @FXML
//    void calendar(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/Calender.fxml"));
//        Parent root = loader.load();
//        CalendarController controller = loader.getController();
//        Stage stage = new Stage();
//        stage.setTitle("Gestion_Tournoi");
//        stage.setScene(new Scene(root));
//        stage.show();
//        ((Button) event.getSource()).getScene().getWindow().hide();
//
//    }

    @FXML
    void recherche(KeyEvent event) {
        String searchTerm = recherche.getText().trim();
        if (searchTerm.isEmpty()) {
            // If search term is empty, display all tournaments
            actualise(Tournois);
        } else {
            // If search term is not empty, display searched tournaments
            List<Tournoi> filteredList = searchTournoi(searchTerm);
            actualise(filteredList);
        }
    }

    // Method to search tournaments based on a search term
    private List<Tournoi> searchTournoi(String searchTerm) {
        List<Tournoi> filteredList = new ArrayList<>();
        for (Tournoi tournoi : Tournois) {
            if (tournoi.getNom().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredList.add(tournoi);
            }
        }
        return filteredList;
    }

    private void incrementNbClick(int espaceId) {
        try {
            String updateQuery = "UPDATE tournoi SET visite = visite + 1 WHERE id = ?";
            PreparedStatement pst = MyDatabase.getInstance().getConnection().prepareStatement(updateQuery);
            pst.setInt(1, espaceId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

