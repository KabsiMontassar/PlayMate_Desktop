package test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Equipe;
import services.EquipeService;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherController implements Initializable {

    @FXML
    private AnchorPane anchorPaneContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane BOX1;

    @FXML
    private AnchorPane BOX2;

    @FXML
    private AnchorPane BOX3;

    @FXML
    private Button btnAjouterEquipe;

    @FXML
    private Button btnDetail1;

    @FXML
    private Button btnDetail2;

    @FXML
    private Button btnDetail3;

    @FXML
    private Button btnModif1;

    @FXML
    private Button btnModif2;

    @FXML
    private Button btnModif3;

    @FXML
    private Button btnretour;

    @FXML
    private Button btnsuivant;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private GridPane mainroot;

    @FXML
    private Text nom1;

    @FXML
    private Text nom2;

    @FXML
    private Text nom3;

    @FXML
    private Text nomCapitaine1;

    @FXML
    private Text nomCapitaine2;

    @FXML
    private Text nomCapitaine3;

    @FXML
    void Ajouter(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("formEquipe.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 360);

        Stage stage = new Stage();
        stage.setScene(scene);
        //  ((AfficherController) fxmlLoader.getController()).initialize();
        stage.show();

    }

    @FXML
    void detail1(ActionEvent event) {

    }

    @FXML
    void detail2(ActionEvent event) {

    }

    @FXML
    void detail3(ActionEvent event) {

    }

    @FXML
    void modif1(ActionEvent event) {

    }

    @FXML
    void modif2(ActionEvent event) {

    }

    @FXML
    void modif3(ActionEvent event) {

    }

    @FXML
    void retour(ActionEvent event) {

    }

    @FXML
    void suivant(ActionEvent event) {

    }

    public AfficherController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EquipeService equipeService = new EquipeService();
        // idjoueur a changer !!!!
        List<Equipe> equipeList = null;
        try {
            equipeList = equipeService.getEquipesParMembre(7);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //  anchorPaneContainer.getChildren().clear();

        HBox hbox = new HBox();
        for (Equipe equipe : equipeList) {
            hbox.getChildren().add(createAnchropaneTeam(equipe.getNomEquipe()));
        }
        scrollPane.setContent(hbox);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private AnchorPane createAnchropaneTeam(String teamName){
        AnchorPane anchorPane = new AnchorPane();

        anchorPane.setLayoutX(14.0);
        anchorPane.setLayoutY(0);
        anchorPane.setPrefHeight(200.0);
        anchorPane.setMaxHeight(200.0);
        anchorPane.setPrefWidth(234.0);
        ImageView img1 = new ImageView();
        img1.setFitHeight(50.0);
        img1.setFitWidth(200.0);
        img1.setLayoutX(17.0);
        img1.setLayoutY(0);
        // Set your image here
        // img1.setImage(...);

        // Creating Text elements
        Text textNomEquipe = new Text("Nom de l'équipe : ");
        textNomEquipe.setLayoutX(28.0);
        textNomEquipe.setLayoutY(300.0);

        Text textNom = new Text(teamName);
        textNom.setLayoutX(126.0);
        textNom.setLayoutY(300.0);

        Text textNomCapitaine = new Text("Nom de capitaine : ");
        textNomCapitaine.setLayoutX(28.0);
        textNomCapitaine.setLayoutY(328.0);

        Text textNomCapitaineValue = new Text("Nom: ");
        textNomCapitaineValue.setLayoutX(131.0);
        textNomCapitaineValue.setLayoutY(328.0);

        // Creating Buttons
        Button btnModif1 = new Button("Modifier");
        btnModif1.setLayoutX(14.0);
        btnModif1.setLayoutY(383.0);
        //btnModif1.setOnAction(event -> modif1());

        Button btnDetail1 = new Button("Détails");
        btnDetail1.setLayoutX(126.0);
        btnDetail1.setLayoutY(383.0);
        // btnDetail1.setOnAction(event -> detail1());
        anchorPane.setStyle("-fx-background-color: white;"+"-fx-border-color: black; " + "-fx-border-width: 2px;");


        // Adding all elements to AnchorPane
        anchorPane.getChildren().addAll(img1, textNomEquipe, textNom, textNomCapitaine, textNomCapitaineValue, btnModif1, btnDetail1);
        return  anchorPane;
    }


}
