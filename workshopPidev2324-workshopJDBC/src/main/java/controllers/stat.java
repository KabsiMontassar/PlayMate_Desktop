package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Categorie;
import models.Product;
import services.CategorieService;
import services.ProductService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//*******************************************************************************************
public class stat {
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private Button retour;
    @FXML
    private NumberAxis yAxis;
    private CategorieService cs;
    @FXML
    private Button btretour;
    //*******************************************************************************************
    public stat() {
        this.cs = new CategorieService();
    }
    //*******************************************************************************************
    @FXML
    public void initialize() throws IOException {
        List<Categorie> categories;

        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            ProductService ps=new ProductService();
            List<Product> products=ps.getAll();
            categories = cs.getAll();
            for(Categorie c : categories){
                int i=0;
                for(Product p : products){
                    if(p.getCategorie()==c.getId()){
                        i++;
                    }
               }
                series.getData().add(new XYChart.Data<>(c.getNom(),i));
            }

            barChart.getData().add(series);
            // Formatage des valeurs de l'axe des ordonnées en entiers sans virgule
            yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
                @Override
                public String toString(Number object) {
                    return String.format("%d", object.intValue());
                }});
            // Définir la valeur de départ de l'axe des ordonnées à 0
            yAxis.setTickUnit(1);
            yAxis.setLowerBound(0);

            // Désactiver la génération automatique des étiquettes
            yAxis.setAutoRanging(false);
        } catch (SQLException e) {e.printStackTrace();}

    }
    @FXML
    void retour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Products.fxml"));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    @FXML
    private Button Excel;
    @FXML
    void Excel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(barChart.getScene().getWindow());
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Categorie,                   nbProduits\n");
                for (XYChart.Data<String, Number> data : barChart.getData().get(0).getData()) {
                    writer.write(data.getXValue() + "                       ," + data.getYValue() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }


}