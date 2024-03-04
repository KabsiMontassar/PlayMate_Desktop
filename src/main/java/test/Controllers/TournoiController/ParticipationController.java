package test.Controllers.TournoiController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Participation;
import models.Tournoi;
import services.GestionTournoi.ServiceParticipation;
import services.GestionTournoi.ServiceTournoi;
import test.MainFx;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ParticipationController implements Initializable {
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel3.setVisible(false);
        errorLabel4.setVisible(false);


    }


    @FXML
    private Button Btnback;

    @FXML
    private Button btparticiper;

    @FXML
    private AnchorPane detailroot;

    @FXML
    private Label nomd;

    @FXML
    private Label errorLabel3;

    @FXML
    private Label errorLabel4;


    @FXML
    private ComboBox<String> nomequipe;

    private Tournoi tournoiActuel;

  private List<String> nomEquipes = new ArrayList<>();
   private ObservableList<String> options ;

   ServiceTournoi st = new ServiceTournoi();
    @FXML
    void goToDetailsClient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("TournoiClient.fxml"));
        AnchorPane root = loader.load();
        detailroot.getChildren().setAll(root);
    }

    public void initData(Tournoi tournoi) throws SQLException {
        this.tournoiActuel = tournoi;
        ServiceParticipation ps = new ServiceParticipation();

        nomEquipes = ps.getNomsEquipesPourMembre(3);
        System.out.println(nomEquipes);
        options = FXCollections.observableArrayList(nomEquipes);
        nomequipe.setItems(options);
        tournoiActuel.setParticipationList(st.getparticipationbytournoiid(tournoiActuel.getId()));
        }

    private boolean validateNbrParticipation() throws SQLException {
        if (tournoiActuel.getParticipationList().size() < tournoiActuel.getNbrquipeMax()) {
            errorLabel3.setVisible(false);
            return true;
        } else {
            errorLabel3.setVisible(true);

        }
        return false;
    }

    private boolean validateEquipeParticipation(String nomEquipeSelectionne) {
        for (Participation participation : tournoiActuel.getParticipationList()) {
            if (participation.getNomEquipe().equals(nomEquipeSelectionne)) {
                errorLabel4.setVisible(true);
                return false;
            }
        }
        errorLabel4.setVisible(false);
        return true;
    }




    @FXML
    void participerd(ActionEvent event) throws SQLException, IOException {
if(validateNbrParticipation() && validateEquipeParticipation( nomequipe.getValue())){
        ServiceParticipation ps = new ServiceParticipation();
        String nomEquipeSelectionne = nomequipe.getValue();
        int idTournoi = tournoiActuel.getId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de participation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir participez dans ce tournoi ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Participation nouvelleParticipation = new Participation(3, nomEquipeSelectionne, idTournoi);
            ps.ajouter(nouvelleParticipation);
            tournoiActuel.ajouterParticipation(nouvelleParticipation);
            System.out.println(nouvelleParticipation);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/GestionTournoi/tournoiClient.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion_Tournoi");
            stage.setScene(new Scene(root));
            stage.show();
            ((Button) event.getSource()).getScene().getWindow().hide();}
     else {showAlert(Alert.AlertType.ERROR, "Erreur de participation", "L'index de tournoi à participer n'est pas valide.");}
}
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();}



}
