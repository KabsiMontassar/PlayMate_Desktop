package test.Controllers.TournoiController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.Tournoi;
import test.MainFx;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatistiquesController {

    @FXML
    private AnchorPane StatsTournoi;

    @FXML
    private BarChart<String, Number> chart;



    @FXML
    private Button btnRetour;

    @FXML
    private Text nbrparticipations;

    @FXML
    private Text nbrvisite;

    @FXML
    private CategoryAxis nom1;
    private Tournoi tournoiActuel;



    public void initData(Tournoi tournoi) throws SQLException {
        tournoiActuel = tournoi;
        System.out.println(tournoiActuel);
        nom1.setLabel(tournoi.getNom());
        loadChartData();
    }

    private void loadChartData() throws SQLException {
// Assurez-vous que la connexion à la base de données est configurée correctement
        String query = "SELECT COUNT(*) AS count FROM participation WHERE idTournoi = ?";

             PreparedStatement stmt = MyDatabase.getInstance().getConnection().prepareStatement(query); {

            // Définissez le paramètre idTournoi dans la requête
            stmt.setInt(1, tournoiActuel.getId()); // Remplacez getId() par la méthode appropriée pour obtenir l'ID du tournoi

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int participationCount = rs.getInt("count");

                chart.getData().clear();
                XYChart.Series<String, Number> seriesVisites = new XYChart.Series<>();
                seriesVisites.setName("Nombre de visites");

                XYChart.Series<String, Number> seriesParticipations = new XYChart.Series<>();
                seriesParticipations.setName("Nombre de participations");

                // Utilisez les résultats de la requête pour définir le nombre de visites et de participations
                seriesVisites.getData().add(new XYChart.Data<>("Statistiques", tournoiActuel.getVisite()));
                seriesParticipations.getData().add(new XYChart.Data<>("Statistiques", participationCount));

                chart.getData().addAll(seriesVisites, seriesParticipations);
            }

        }
    }


    @FXML
    void retour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/tournoi.fxml"));
        AnchorPane root = loader.load();
        StatsTournoi.getChildren().setAll(root);

    }
    }


