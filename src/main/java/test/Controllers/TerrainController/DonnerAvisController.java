package test.Controllers.TerrainController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import models.AvisTerrain;
import models.Terrain;
import services.GestionTerrain.AvisService;

import java.sql.SQLException;


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
            // Envoyer un SMS au propriétaire du terrain avec Twilio
            smsAPi.sendSms();

            // Afficher éventuellement un message de succès à l'utilisateur
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du commentaire : " + e.getMessage());
        }
    }

    public void initData(Terrain terrain) {this.terrainId = terrain.getId();}}