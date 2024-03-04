package test.Controllers.TournoiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Tournoi;
import test.MainFx;

import java.io.IOException;




public class DetailTournoiController {

    private AnchorPane FormulaireRoot;
    @FXML
    private Label adresse;

    @FXML
    private Button btmodifd;

    @FXML
    private ImageView imgd;

    @FXML
    private Label nombre;

    @FXML
    private Label nomd;

    @FXML
    private AnchorPane detailroot;

    private Tournoi tournoiActuel;
   public void initData(Tournoi tournoi) {
       tournoiActuel = tournoi;
        nombre.setText(String.valueOf(tournoi.getNbrquipeMax()));
        nomd.setText(tournoi.getNom());
        adresse.setText(tournoi.getAddress());
        /*inputDateFin.setText(tournoi.getDatefin());
        InputAddress.setText(tournoi.getAddress());*/
       if (tournoi.getAffiche() != null && !tournoi.getAffiche().isEmpty()) {
           Image image = new Image(tournoi.getAffiche());
           imgd.setImage(image);}

    }
    @FXML
    void modifd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/GestionTournoi/FormulaireModif.fxml"));
        Parent root = loader.load();
        ModifierTournoiController controller = loader.getController();
        controller.initData(tournoiActuel);
        Stage stage = new Stage();
        stage.setTitle("Modifier");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();}

    public void goToTournoi(ActionEvent actionEvent) throws IOException {


            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("tournoi.fxml"));
            AnchorPane root = loader.load();
        detailroot.getChildren().setAll(root);
        }
    }

