package test.Controllers.TournoiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.Participation;
import models.Tournoi;
import services.GestionTournoi.ServiceParticipation;
import services.GestionTournoi.ServiceTournoi;
import test.MainFx;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DetailClientController implements Initializable {

    private int IdUser;

    public void SetIdUser(int idUser) {

        this.IdUser = idUser;
    }
    public int GetIdUser() {
        return this.IdUser;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btparticiper.setVisible(false);
        btannuler.setVisible(false);
        WebEngine webEngine = weatherView.getEngine();
        webEngine.load(MainFx.class.getResource("GestionTournoi/weather.html").toExternalForm());
    }
    @FXML
    private Button Btnback;

    @FXML
    private Label adresse;

    @FXML
    private Button btparticiper;

    @FXML
    private AnchorPane detailroot;

    @FXML
    private ImageView imgd;

    @FXML
    private Label nombre;

    @FXML
    private Label nomd;
    @FXML
    private Label datedebut;

    @FXML
    private Label datefin;

    @FXML
    private Button btannuler;
    private Tournoi tournoiActuel;

    @FXML
    private TextArea forecastTextArea;

    @FXML
    private WebView weatherView;

    ServiceTournoi st = new ServiceTournoi();

    ServiceParticipation sp = new ServiceParticipation();




    @FXML
    public void goToTournoiClient(ActionEvent actionEvent) throws IOException, SQLException {


        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/tournoiClient.fxml"));
        AnchorPane root = loader.load();
        AfficherListeTournoisClientController controller = loader.getController();
        controller.SetIdUser(GetIdUser());



        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();

    }

    public void initData(Tournoi tournoi) throws SQLException {
        this.tournoiActuel = tournoi;
        System.out.println(tournoiActuel);
        nombre.setText(String.valueOf(tournoi.getNbrquipeMax()));
        nomd.setText(tournoi.getNom());
        adresse.setText(tournoi.getAddress());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        datedebut.setText(sdf.format(tournoi.getDatedebut()));
        datefin.setText(sdf.format(tournoi.getDatefin()));
        /*inputDateFin.setText(tournoi.getDatefin());
        InputAddress.setText(tournoi.getAddress());*/
        if (tournoi.getAffiche() != null && !tournoi.getAffiche().isEmpty()) {
            try {
                Image img = new Image(tournoi.getAffiche());
                imgd.setImage(img);
            } catch (IllegalArgumentException e) {
                // Handle the error when the URL is invalid or resource not found
                imgd.setImage(null); // Set the image view to display nothing
            }

        }
        tournoiActuel.setParticipationList(st.getparticipationbytournoiid(tournoiActuel.getId()));
        verifierParticipation(3);


    }

    private void verifierParticipation(int idMembre) {
        System.out.println(tournoiActuel.getParticipationList());
        boolean aParticipe = false;
        for (Participation participation : tournoiActuel.getParticipationList()) {
            if (participation.getIdMembre() == idMembre) {
                aParticipe = true;
                break;
            }
        }
        System.out.println(aParticipe);
        btparticiper.setVisible(!aParticipe);
        btannuler.setVisible(aParticipe);
    }



    @FXML
    void participerd(ActionEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/Participation.fxml"));
        AnchorPane root = loader.load();
        ParticipationController controller = loader.getController();

        controller.SetIdUser(GetIdUser());
        controller.initData(tournoiActuel);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();


    }

    @FXML
    void annuler(ActionEvent event) throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette participation ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for ( Participation participation: tournoiActuel.getParticipationList())
            {
                if (participation.getIdTournoi() == tournoiActuel.getId() && participation.getIdMembre() == 3)
                {
                    sp.supprimer( participation.getId());

                }
            }}
     else {
         showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "L'index de participation à supprimer n'est pas valide.");
     }
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/tournoiClient.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();}


    }




