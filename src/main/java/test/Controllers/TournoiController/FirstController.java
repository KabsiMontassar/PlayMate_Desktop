package test.Controllers.TournoiController;

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
import models.Tournoi;
import services.GestionUser.UserService;
import test.Controllers.UserController.AcceuilController;
import test.MainFx;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import services.GestionTournoi.ServiceTournoi;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class FirstController {
    public AnchorPane mainroot;

    public AnchorPane main;
    public Button Btnback;
    @FXML
    private AnchorPane BOX1;
    @FXML
    private AnchorPane BOX2;
    @FXML
    private AnchorPane BOX3;
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;

    @FXML
    private Button btnStat1;

    @FXML
    private Button btnStat2;

    @FXML
    private Button btnStat3;

    @FXML
    private AnchorPane MainPane;

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

    private int IdUser;

    public void SetIdUser(int idUser) {

        this.IdUser = idUser;
    }
    public int GetIdUser() {
        return this.IdUser;
    }
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
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/DetailTournoi.fxml"));
        Parent root = loader.load();
        DetailTournoiController controller = loader.getController();
        controller.SetIdUser(GetIdUser());
        controller.initData(t);
        Stage stage = new Stage();
        stage.setTitle("Détails Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
    //*******************************************************************************************
    @FXML
    void detail2(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1+3*i;
        Tournoi selectedTournoi = Ts.allTournoi().get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/DetailTournoi.fxml"));
        Parent root = loader.load();
        DetailTournoiController controller = loader.getController();
        controller.initData(selectedTournoi);
        controller.SetIdUser(GetIdUser());
        Stage stage = new Stage();
        stage.setTitle("Détails du Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //test git
    //*******************************************************************************************
    @FXML
    void detail3(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(9)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Tournoi selectedTournoi = Ts.allTournoi().get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/DetailTournoi.fxml"));
        Parent root = loader.load();
        DetailTournoiController controller = loader.getController();
        controller.initData(selectedTournoi);
        controller.SetIdUser(GetIdUser());
        Stage stage = new Stage();
        stage.setTitle("Détails du Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}
    //*******************************************************************************************
    @FXML
    void supp1(ActionEvent event) throws SQLException {
        int indexToDelete = i * 3;
        if (indexToDelete >= 0 && indexToDelete < Ts.allTournoi().size()) {
            Tournoi tournoiToDelete = Ts.allTournoi().get(indexToDelete);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce tournoi ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Ts.supprimer(tournoiToDelete.getId());
                actualise(Ts.allTournoi());}
        } else {showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "L'index de tournoi à supprimer n'est pas valide.");}}
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
        if (indexToDelete >= 0 && indexToDelete < Ts.allTournoi().size()) {
            Tournoi tournoiToDelete = Ts.allTournoi().get(indexToDelete);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce tournoi ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Ts.supprimer(tournoiToDelete.getId());
                actualise(Ts.allTournoi());}
        } else {showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "L'index de terrain à supprimer n'est pas valide.");}}
    //*******************************************************************************************
    @FXML
    void supp3(ActionEvent event) throws SQLException {
        int indexToDelete = i * 3 + 2;
        if (indexToDelete >= 0 && indexToDelete < Ts.allTournoi().size()) {
            Tournoi tournoiToDelete = Ts.allTournoi().get(indexToDelete);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce tournoi ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Ts.supprimer(tournoiToDelete.getId());
                actualise(Ts.allTournoi());}
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "L'index de terrain à supprimer n'est pas valide.");}}


    @FXML
    void stat1(ActionEvent event) throws SQLException, IOException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(7)) - 1+3*i;
        Tournoi t = Ts.allTournoi().get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/Stats.fxml"));
        Parent root = loader.load();
        StatistiquesController controller = loader.getController();
        controller.initData(t);
        Stage stage = new Stage();
        stage.setTitle("Statistiques du Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();

    }

    @FXML
    void stat2(ActionEvent event) throws SQLException, IOException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(7)) - 1+3*i;
        Tournoi selectedTournoi = Ts.allTournoi().get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/Stats.fxml"));
        Parent root = loader.load();
        StatistiquesController controller = loader.getController();
        controller.initData(selectedTournoi);
        Stage stage = new Stage();
        stage.setTitle("Détails du Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();

    }

    @FXML
    void stat3(ActionEvent event) throws SQLException, IOException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(7)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Tournoi selectedTournoi = Ts.allTournoi().get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/Stats.fxml"));
        Parent root = loader.load();
        StatistiquesController controller = loader.getController();
        controller.initData(selectedTournoi);
        Stage stage = new Stage();
        stage.setTitle("Détails du Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();

    }

    public void AjouterTournoi(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/FormulaireTournoi.fxml"));
        Parent root = loader.load();
        AjoutTournoiController controller = loader.getController();
        controller.SetIdUser(GetIdUser());

        Stage stage = new Stage();
        stage.setTitle("Gestion_Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();


    }

UserService us = new UserService();
    public void goToTournoiClient(ActionEvent actionEvent) throws IOException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/Acceuil.fxml"));
        Parent root = loader.load();
        AcceuilController controller = loader.getController();
        controller.setData(us.getByid(GetIdUser()));
        Stage stage = new Stage();
        stage.setTitle("Gestion_Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
