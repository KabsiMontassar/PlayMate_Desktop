package test.Controllers.ReservationController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Historique;
import models.Reservation;
import models.Terrain;
import models.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.GestionReservation.*;
import services.GestionTerrain.TerrainService;
import services.GestionUser.UserService;
import test.Controllers.UserController.AcceuilController;
import test.MainFx;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class HistoriqueController implements Initializable {
    @FXML
    private VBox vboxHistoriqueAdmin;

    @FXML
    private VBox vboxHistorique;


    //    ***************************************************************************    REQUIRES : ****************

    private int IdUser;
    public void SetIdUser(int idUser) {
        this.IdUser = idUser;
        try {
            //afficherHistoriquePourAdmin();
            afficherHistorique(idUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int GetIdUser() {
        return  this.IdUser ;

    }
    // *************************************************************************** ****************************


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        try {
//            //afficherHistoriquePourAdmin();
//            afficherHistorique(this.GetIdUser());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void  afficherHistorique(int idJoueur) throws SQLException {

        HistoriqueService historiqueService = new HistoriqueService();


        ReservationService reservationService = new ReservationService();
        TerrainService terrainService = new TerrainService();

        List<Historique> historiqueList = historiqueService.consulterHistoriqueParMembre(this.GetIdUser());
        for (Historique historique : historiqueList){
            AnchorPane anchorPane3 = new AnchorPane();
            HBox hBox = new HBox();
            anchorPane3.getStyleClass().add("anchor-pane-style");
            anchorPane3.setPadding(new Insets(10, 10, 10, 10));
            Label idHistoriqueLabel = new Label("Id: " + historique.getIdHistorique());

            Reservation reservation = reservationService.getReservationByIdReservation(historique.getIdReservation());
             Terrain terrain= terrainService.getTerrainById(reservation.getIdTerrain());


            Label idReservationLabel = new Label("IdReservation: " + reservation.getIdReservation());
            Label DateReservationLabel = new Label("Date Reservation : " + reservation.getDateReservation());
            Label HeureReservationLabel = new Label("Horaire Reservation : " + reservation.getHeureReservation());

            Label idLabel = new Label("Id: " + terrain.getId());
            Label nomLabel = new Label("Nom:  " + terrain.getNomTerrain());
            Label prixLabel = new Label("Prix: " + terrain.getPrix()+" dt");
            Label dureeLabel = new Label("Durée: " + terrain.getDuree()+" min");


            DateReservationLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #02365C;  -fx-font-weight: bold; -fx-margin: 5px 5px; -fx-padding: 10 5");
            HeureReservationLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #02365C;  -fx-font-weight: bold; -fx-margin: 5px 5px; -fx-padding: 10 10");
            nomLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #02365C;  -fx-margin: 5px 5px; -fx-padding: 10 5");
            prixLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #02365C; -fx-font-weight: bold; -fx-margin: 5px 5px; -fx-padding: 10 5");
            dureeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #02365C; -fx-font-weight: bold; -fx-margin: 5px 5px ; -fx-padding: 10 5");


            Button btnImpression = new Button("imprimer facture");
            btnImpression.getStyleClass().add("imprimer-button");

            btnImpression.setStyle("-fx-background-color: #4CAF50; -fx-border-color: #52B66C; -fx-text-fill: #02365c; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 10;-fx-border-radius: 3px;");
            btnImpression.setOnAction(event -> {
                UserService userService = new UserService();
                try {
                    User user = userService.getByid(this.GetIdUser());


                    ImprimerPdf(user.getName(),user.getEmail(),reservation.getHeureReservation(), reservation.getDateReservation(),terrain.getNomTerrain(),terrain.getPrix());

                } catch (SQLException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException |
                         IllegalBlockSizeException | NoSuchPaddingException e) {
                    throw new RuntimeException(e);
                }


            });







            hBox.setSpacing(10);


            hBox.getChildren().addAll(nomLabel,prixLabel,DateReservationLabel,dureeLabel, HeureReservationLabel,btnImpression);
            hBox.getStyleClass().add("hbox-with-padding");
            hBox.setSpacing(10);
            hBox.getStyleClass().add("hbox-style");
            hBox.setPadding(new Insets(15, 15, 15, 15));
            anchorPane3.getChildren().addAll(hBox);
            vboxHistorique.setVgrow(anchorPane3, Priority.ALWAYS);
            vboxHistorique.setMargin(anchorPane3, new Insets(10));
            vboxHistorique.getChildren().add(anchorPane3);
        }
    }

// user.getName(),user.getEmail(),reservation.getHeureReservation(), reservation.getDateReservation(),terrain.getNomTerrain(),terrain.getPrix()
    public void ImprimerPdf(String nom ,String email , String heureReservation , String dateRes , String nomTerrain , int prix ) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            float y = 700; // Starting Y-coordinate

            // Reservation Details (Red and Centered)
            contentStream.setNonStrokingColor(255, 125, 0);
            String text = "Reservation Details:";
            float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000f * 12;
            float startX = (page.getMediaBox().getWidth() - textWidth) / 2; // Centered
            contentStream.newLineAtOffset(startX, y);
            contentStream.showText(text);
            contentStream.newLineAtOffset(-startX, 0); // Adjust for centering
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // Reset color to default (black)
            contentStream.setNonStrokingColor(0, 0, 0);

            // Name
            contentStream.showText("Name: " + nom);
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // Email
            contentStream.showText("Email: " + email);
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // heureReservation
            contentStream.showText("horaire de reservation: " + heureReservation);
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // date res
            contentStream.showText("date de reservation : " + dateRes);
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // nomTerrain
            contentStream.showText("nom Terrain: " + nomTerrain);
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);
            // Montant
            contentStream.showText("Montant: " + prix);
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);



            contentStream.endText();
            contentStream.close();

            document.save("Reservation_Details.pdf");
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("PDF file generated successfully!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while generating PDF file!");
            alert.showAndWait();
        }
    }

    public void  afficherHistoriquePourAdmin() throws SQLException {

        HistoriqueService historiqueService = new HistoriqueService();


        ReservationService reservationService = new ReservationService();
        TerrainService terrainService = new TerrainService();

        historiqueService.actualiserTableHistorique();
        List<Historique> historiqueList = historiqueService.getAllHistorique();
        for (Historique historique : historiqueList){
            AnchorPane anchorPane3 = new AnchorPane();
            HBox hBox = new HBox();
            anchorPane3.getStyleClass().add("anchor-pane-style");
            anchorPane3.setPadding(new Insets(10, 10, 10, 10));
            Label idHistoriqueLabel = new Label("Id: " + historique.getIdHistorique());

            Reservation reservation = reservationService.getReservationByIdReservation(historique.getIdReservation());
            Terrain terrain= terrainService.getTerrainById(reservation.getIdTerrain());


            Label idReservationLabel = new Label("IdReservation: " + reservation.getIdReservation());
            Label DateReservationLabel = new Label("Date Reservation : " + reservation.getDateReservation());
            Label HeureReservationLabel = new Label("Horaire Reservation : " + reservation.getHeureReservation());

            Label idLabel = new Label("Id: " + terrain.getId());
            Label nomLabel = new Label("Nom:  " + terrain.getNomTerrain());
            Label prixLabel = new Label("Prix: " + terrain.getPrix()+" dt");
            Label dureeLabel = new Label("Durée: " + terrain.getDuree()+" min");


            DateReservationLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #02365C;  -fx-font-weight: bold; -fx-margin: 5px 5px; -fx-padding: 10 5");
            HeureReservationLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #02365C;  -fx-font-weight: bold; -fx-margin: 5px 5px; -fx-padding: 10 10");
            nomLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #02365C;  -fx-margin: 5px 5px; -fx-padding: 10 5");
            prixLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #02365C;-fx-font-weight: bold; -fx-margin: 5px 5px; -fx-padding: 10 5");
            dureeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #02365C; -fx-font-weight: bold; -fx-margin: 5px 5px ; -fx-padding: 10 5");


            Button btnImpression = new Button("imprimer facture");
            btnImpression.getStyleClass().add("imprimer-button");

            btnImpression.setStyle("-fx-background-color: #4CAF50; -fx-border-color: #52B66C; -fx-text-fill: #02365c; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 10;-fx-border-radius: 3px;");
            btnImpression.setOnAction(event -> {
                UserService userService = new UserService();
                try {
                    User user = userService.getByid(this.GetIdUser());


                    ImprimerPdf(user.getName(),user.getEmail(),reservation.getHeureReservation(), reservation.getDateReservation(),terrain.getNomTerrain(),terrain.getPrix());

                } catch (SQLException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException |
                         IllegalBlockSizeException | NoSuchPaddingException e) {
                    throw new RuntimeException(e);
                }


            });
            hBox.setSpacing(10);


            hBox.getChildren().addAll(nomLabel,prixLabel,DateReservationLabel,dureeLabel, HeureReservationLabel,btnImpression);
            hBox.getStyleClass().add("hbox-with-padding");
            hBox.setSpacing(10);
            hBox.getStyleClass().add("hbox-style");
            hBox.setPadding(new Insets(15, 15, 15, 15));
            anchorPane3.getChildren().addAll(hBox);
            vboxHistoriqueAdmin.setVgrow(anchorPane3, Priority.ALWAYS);
            vboxHistoriqueAdmin.setMargin(anchorPane3, new Insets(10));
            vboxHistoriqueAdmin.getChildren().add(anchorPane3);
        }
    }

    public void evenementPart(ActionEvent actionEvent) {
    }

    public void voirProduit(ActionEvent actionEvent) {
    }

    public void Toreservation(ActionEvent actionEvent) {

        try {

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionReservation/choix2.fxml"));
            Parent root = loader.load();
            ReservationController controller = loader.getController();
            controller.SetIdUser(this.GetIdUser());
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.show();
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnseeProfile(ActionEvent actionEvent) {
    }

    public void logoutaction(ActionEvent actionEvent) {
    }

    public void VoirTerrain(ActionEvent actionEvent) {
    }

    public void VoirOrganisateur(ActionEvent actionEvent) {
    }

    public void openjeu(ActionEvent actionEvent) {
    }
}
