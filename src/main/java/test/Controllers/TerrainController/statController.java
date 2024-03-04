package test.Controllers.TerrainController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import services.GestionTerrain.AvisService;
import models.Terrain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Button;
import test.MainFx;

public class statController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private AvisService avisService;

    @FXML
    private Button btretour;


    public statController() {
        this.avisService = new AvisService();
    }

    @FXML
    public void initialize() throws IOException {
        List<Terrain> terrains;
        try {
            terrains = avisService.getTerrainsOrderByCommentaires();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (Terrain terrain : terrains) {
                series.getData().add(new XYChart.Data<>(terrain.getNomTerrain(), terrain.getNbCommentaires()));
            }
            barChart.getData().add(series);

            // Formatage des valeurs de l'axe des ordonnées en entiers sans virgule
            yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
                @Override
                public String toString(Number object) {
                    return String.format("%d", object.intValue());
                }
            });
            // Définir l'incrémentation de l'axe des ordonnées à 2
            yAxis.setTickUnit(5);
            // Définir la valeur de départ de l'axe des ordonnées à 0
            yAxis.setLowerBound(0);
            // Désactiver la génération automatique des étiquettes
            yAxis.setAutoRanging(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    @FXML
    void retour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("PageTerrain.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Liste des terrains");
        stage.setScene(new Scene(root));
        stage.show();
        // Récupérer la fenêtre actuelle et la cacher
        ((Stage) btretour.getScene().getWindow()).hide();}
    }
