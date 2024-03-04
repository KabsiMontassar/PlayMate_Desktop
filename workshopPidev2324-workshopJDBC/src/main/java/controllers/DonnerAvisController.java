package controllers;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Email;
import entity.AvisTerrain;
import entity.Terrain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.json.JSONArray;
import org.json.JSONObject;
import services.AvisService;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.resource.Emailv31;
import java.io.IOException;
import java.sql.SQLException;
import com.mailjet.client.errors.MailjetException;


//*******************************************************************************************
public class DonnerAvisController {
    @FXML
    private Circle star1;
    @FXML
    private Circle star2;
    @FXML
    private Circle star3;
    @FXML
    private Circle star4;
    @FXML
    private Circle star5;
    @FXML
    private TextArea commentaireArea;
    @FXML
    private Button submitbt;
    private int terrainId;
    //*******************************************************************************************
    @FXML
    public void initialize() {
        star1.setOnMouseClicked(event -> {
            star1.setFill(star1.getFill() == Color.TRANSPARENT ? Color.YELLOW : Color.TRANSPARENT);
            star2.setFill(Color.TRANSPARENT);
            star3.setFill(Color.TRANSPARENT);
            star4.setFill(Color.TRANSPARENT);
            star5.setFill(Color.TRANSPARENT);});
        star2.setOnMouseClicked(event -> {
            star1.setFill(Color.YELLOW);
            star2.setFill(star2.getFill() == Color.TRANSPARENT ? Color.YELLOW : Color.TRANSPARENT);
            star3.setFill(Color.TRANSPARENT);
            star4.setFill(Color.TRANSPARENT);
            star5.setFill(Color.TRANSPARENT);});
        star3.setOnMouseClicked(event -> {
            star1.setFill(Color.YELLOW);
            star2.setFill(Color.YELLOW);
            star3.setFill(star3.getFill() == Color.TRANSPARENT ? Color.YELLOW : Color.TRANSPARENT);
            star4.setFill(Color.TRANSPARENT);
            star5.setFill(Color.TRANSPARENT);});
        star4.setOnMouseClicked(event -> {
            star1.setFill(Color.YELLOW);
            star2.setFill(Color.YELLOW);
            star3.setFill(Color.YELLOW);
            star4.setFill(star4.getFill() == Color.TRANSPARENT ? Color.YELLOW : Color.TRANSPARENT);
            star5.setFill(Color.TRANSPARENT);});
        star5.setOnMouseClicked(event -> {
            star1.setFill(Color.YELLOW);
            star2.setFill(Color.YELLOW);
            star3.setFill(Color.YELLOW);
            star4.setFill(Color.YELLOW);
            star5.setFill(star5.getFill() == Color.TRANSPARENT ? Color.YELLOW : Color.TRANSPARENT);});}
    //*******************************************************************************************
    public String getCommentaire() {
        return commentaireArea.getText();
    }
    //*******************************************************************************************
    public int getNote() {
        int note = 0;
        if (star1.getFill() == Color.YELLOW) note++;
        if (star2.getFill() == Color.YELLOW) note++;
        if (star3.getFill() == Color.YELLOW) note++;
        if (star4.getFill() == Color.YELLOW) note++;
        if (star5.getFill() == Color.YELLOW) note++;
        return note;}
    //*******************************************************************************************
    @FXML
    void submit(ActionEvent event) {
        String commentaire = getCommentaire();
        int note = getNote();
        Terrain terrain = new Terrain();
        terrain.setId(terrainId); // Utiliser l'ID du terrain initialisé dans initData
        AvisTerrain avisTerrain = new AvisTerrain(terrain.getId(), commentaire, note);
        AvisService avisService = new AvisService();
        try {
            avisService.add(avisTerrain);
            System.out.println("Commentaire ajouté avec succès !");

            // Adresse email du propriétaire du terrain
            String ownerEmail = "bendjemia.malek30@gmail.com";
            try {
                // Envoyer un email au propriétaire du terrain avec Mailjet
                MailjetClient client = new MailjetClient("0e79dd8ff2dbec34fff4a1b6580e0dcd", "01afe818ef5573541ce665d96aabe6fe");
                MailjetRequest request = new MailjetRequest(Email.resource)
                        .property(Email.FROMEMAIL, "benjangel2001@gmail.com")
                        .property(Email.FROMNAME, "playmate")
                        .property(Email.SUBJECT, "Nouvel avis sur votre terrain")
                        .property(Email.TEXTPART, "Un nouvel avis a été ajouté à votre terrain.")
                        .property(Email.RECIPIENTS, new JSONArray()
                                .put(new JSONObject()
                                        .put("Email", ownerEmail)
                                        .put("Name", "Owner Name")));
                client.post(request);

                // Afficher éventuellement un message de succès à l'utilisateur
                System.out.println("Email envoyé avec succès !");
            } catch (MailjetException e) {
                System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
            }


            // Afficher éventuellement un message de succès à l'utilisateur
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du commentaire : " + e.getMessage());
        }
    }



    public void initData(Terrain terrain) {this.terrainId = terrain.getId();}}