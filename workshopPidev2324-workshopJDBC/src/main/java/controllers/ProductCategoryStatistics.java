package controllers;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class ProductCategoryStatistics extends Application {
    @Override
    public void start(Stage stage) {
        // Créer les données pour le diagramme circulaire
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Catégorie 1", 30),
                        new PieChart.Data("Catégorie 2", 20),
                        new PieChart.Data("Catégorie 3", 10),
                        new PieChart.Data("Catégorie 4", 40)
                );

        // Créer le diagramme circulaire
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Produits par catégorie");

        // Créer un groupe pour afficher le diagramme circulaire
        Group root = new Group(chart);
        Scene scene = new Scene(root, 800, 600);

        // Configurer la scène et afficher la fenêtre
        stage.setScene(scene);
        stage.setTitle("Statistiques par catégorie de produit");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}