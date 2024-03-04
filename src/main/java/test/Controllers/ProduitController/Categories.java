package test.Controllers.ProduitController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Categorie;
import services.GestionProduit.CategorieService;
import test.MainFx;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Categories implements Initializable {
    @FXML
    private Text descriptionf;

    @FXML
    private TextField descriptionform;

    @FXML
    private Text nomf;

    @FXML
    private TextField nomform;

    @FXML
    private Button retour;

    @FXML
    private Button validerf;


    @FXML
    private AnchorPane BOX1;

    @FXML
    private AnchorPane BOX2;

    @FXML
    private AnchorPane BOX3;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Button btnModif1;

    @FXML
    private Button btnModif2;

    @FXML
    private Button btnModif3;

    @FXML
    private Button btnSupp1;

    @FXML
    private Button btnSupp2;

    @FXML
    private Button btnSupp3;

    @FXML
    private Button btnajout;

    @FXML
    private Button btnretour;

    @FXML
    private Button btnsuivant;

    @FXML
    private Text description1;

    @FXML
    private Text description2;

    @FXML
    private Text description3;

    @FXML
    private Text nom1;

    @FXML
    private Text nom2;

    @FXML
    private Text nom3;
    int i= 0;

    @FXML
    private Button lienproducts;



    CategorieService cs = new CategorieService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            actualise(cs.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void actualise(List<Categorie> categories){
        if(categories.size()-1-i*3>0){btnsuivant.setVisible(true);
        }

        if(categories.size()-1-i*3 <= 0){btnsuivant.setVisible(false);
        }
        if(i > 0){btnretour.setVisible(true);
        }
        if(i == 0){btnretour.setVisible(false);
        }
        if(!categories.isEmpty()){
            if(categories.size()-1-i*3>=0){
                BOX1.setVisible(true);
                nom1.setText(categories.get(i*3).getNom());
                description1.setText(categories.get(i*3).getDescription());

            }
            else{BOX1.setVisible(false);
            }
            if(categories.size()-2-i*3>=0){
                BOX2.setVisible(true);
                nom2.setText(categories.get(1+i*3).getNom());
                description2.setText(categories.get(1+i*3).getDescription());

            }
            else{
                BOX2.setVisible(false);
            }
            if(categories.size()-3-i*3>=0){
                BOX3.setVisible(true);
                nom3.setText(categories.get(2+i*3).getNom());
                description3.setText(categories.get(2+i*3).getDescription());

            }else{BOX3.setVisible(false);}}else{
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);
        }
        btnsuivant.setVisible(categories.size()-3*i > 3);
    }

    @FXML
    void Ajout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/modif.fxml"));

        Parent root = loader.load();
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void Modif1(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(8)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Categorie selectedCategorie = cs.getAll().get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/modif.fxml"));
        Parent root = loader.load();
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        modif controller = loader.getController();
        controller.initData(selectedCategorie);

    }
    @FXML
    void Modif2(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(8)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Categorie selectedCategorie = cs.getAll().get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/modif.fxml"));
        Parent root = loader.load();
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        modif controller = loader.getController();
        controller.initData(selectedCategorie);

    }
    @FXML
    void Modif3(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(8)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Categorie selectedCategorie = cs.getAll().get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/modif.fxml"));
        Parent root = loader.load();
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        modif controller = loader.getController();
        controller.initData(selectedCategorie);
    }

    @FXML
    void retour(ActionEvent event) {
        i -=1;
        try {
            actualise(cs.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void suivant(ActionEvent event) {
        i +=1;
        try {

            actualise(cs.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supp1(ActionEvent event) throws SQLException {
        Alert alert;

        List<Categorie>  categories;
        try {
            categories = cs.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!categories.isEmpty()) {alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous supprimez ce produit " + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                if (categories.size() - 1 - i * 3 >= 0) {
                    cs.delete(categories.get((i*3)).getId());
                    JOptionPane.showMessageDialog(null, "Delete Success!");

                }
            }
        }
        actualise(categories);

    }

    @FXML
    void supp2(ActionEvent event) throws SQLException {
        Alert alert;

        List<Categorie>  categories;
        try {
            categories = cs.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!categories.isEmpty()) {alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous supprimez ce produit " + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                if (categories.size() - 1 - i * 3 >= 0) {
                    cs.delete(categories.get((i*3)+1).getId());
                    JOptionPane.showMessageDialog(null, "Delete Success!");

                }
            }
        }
        actualise(categories);

    }

    @FXML
    void supp3(ActionEvent event) throws SQLException {
        Alert alert;

        List<Categorie>  categories;
        try {
            categories = cs.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!categories.isEmpty()) {alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous supprimez ce produit " + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                if (categories.size() - 1 - i * 3 >= 0) {
                    cs.delete(categories.get((i*3)+2).getId());
                    JOptionPane.showMessageDialog(null, "Delete Success!");

                }
            }
        }
        actualise(categories);
    }


    @FXML
    void produits(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/modif.fxml"));

        Parent root = loader.load();
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
