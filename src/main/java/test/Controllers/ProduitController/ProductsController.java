package test.Controllers.ProduitController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Categorie;
import models.Product;
import services.GestionProduit.CategorieService;
import services.GestionProduit.ProductService;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    @FXML
    private Button ajouter;

    @FXML
    private Text categorie;

    @FXML
    private Text description;
    @FXML
    private Button choisir;

    @FXML
    private TextField descriptionform;

    @FXML
    private ImageView imagef;

    @FXML
    private Text nom;

    @FXML
    private TextField nomform;

    @FXML
    private Text prix;

    @FXML
    private TextField prixform;
    @FXML
    private Label erreurdescription;

    @FXML
    private Label erreurnom;

    @FXML
    private Label erreurprix;

    @FXML
    private Button retour;

    @FXML
    private Button validerf;
    private String imagePath;
    private Product prod;
    @FXML
    private ChoiceBox<Integer> categorieform;
    ProductService cs = new ProductService();

    @FXML
    void RetourVersProducts(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Products.fxml"));
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    public void initialiseData(Product product) {
        this.prod = product;
        nomform.setText(String.valueOf(prod.getNom()));
        descriptionform.setText(String.valueOf(product.getDescription()));
        prixform.setText(String.valueOf(product.getPrix()));
        categorieform.setValue(product.getCategorie());
        imagePath = product.getImage();
        Image image = new Image(imagePath);
        imagef.setImage(image);

    }
    @FXML
    void ValiderModification(ActionEvent event) throws SQLException {
        List<Product> products=cs.getAll();
        Alert alert;
        String xnom=nomform.getText();
        String xdescription=descriptionform.getText();
        int xprix1=Integer.parseInt(prixform.getText());

        if (nomform.getText().isEmpty()
                || descriptionform.getText().isEmpty()
                || prixform.getText().isEmpty()
                || categorieform.getValue()==null
                || imagef.getImage()==null)
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else  if (!xnom.matches("[a-zA-Z]+"))
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Nom ne peut contenir que des caracteres");
            alert.showAndWait();}
        else if((!xdescription.matches("[a-zA-Z]+")))
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Description ne peut contenir que des caracteres");
            alert.showAndWait();
        }else if (xprix1<0){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Prix doit etre > 0");
            alert.showAndWait();
        }
        else{
        if (!products.isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation Message");
            alert1.setHeaderText(null);
            alert1.setContentText("Voulez-vous modifier ce produit " + "?");

            Optional<ButtonType> option = alert1.showAndWait();

            if (option.get().equals(ButtonType.OK)) {


                prod.setNom(nomform.getText());
                prod.setDescription(descriptionform.getText());

                prod.setPrix(Integer.parseInt(prixform.getText()));
                prod.setCategorie((categorieform.getValue()));
                prod.setImage(imagef.getImage().getUrl());
                ProductService cc = new ProductService();
                cc.update(prod, prod.getId());
                // Mise à jour de la liste des terrains affichés
                JOptionPane.showMessageDialog(null, "Modification Success!");
                effacer();

            }}
        }}

    @FXML
    void ajouter(ActionEvent event) throws SQLException {
        Alert alert;
        String xnom=nomform.getText();
        String xdescription=descriptionform.getText();
        String xprix = (prixform.getText());
        int xprix1=Integer.parseInt(prixform.getText());

        if (nomform.getText().isEmpty()
                    || descriptionform.getText().isEmpty()
                || prixform.getText().isEmpty()
                || categorieform.getValue()==null
                || imagef.getImage()==null
        )
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else  if (!xnom.matches("[a-zA-Z]+"))
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Nom ne peut contenir que des caracteres");
            alert.showAndWait();}
        else if((!xdescription.matches("[a-zA-Z]+")))
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Description ne peut contenir que des caracteres");
            alert.showAndWait();
        }else if (xprix1<0){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Prix doit etre > 0");
            alert.showAndWait();
        }

        else {
            cs.add(new Product(nomform.getText(),descriptionform.getText(),Integer.parseInt(prixform.getText()),imagePath,categorieform.getValue()));
            JOptionPane.showMessageDialog(null, "Ajout Success!");

            effacer();

        }
    }
    public void effacer() {
        nomform.setText("");

        descriptionform.setText("");
        prixform.setText("");
        imagef.setImage(null);
        if (categorieform.getValue() != null) {
            categorieform.setValue(null);

            categorie.setText("Categorie");
        }



    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         nomform.setText("");
            descriptionform.setText("");
            prixform.setText("");
            categorieform.setValue(null);
        CategorieService cs=new CategorieService();
        List<Categorie> test= null;
        try {
            test = cs.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(Categorie cat : test)
        {
            categorieform.getItems().add(cat.getId());
        }
        categorieform.setOnAction(event -> affichernom());


    }
    @FXML
    public void affichernom() {
        if (categorieform.getValue() != null) {
        int i=0;
        i=categorieform.getValue();
        CategorieService cs=new CategorieService();
        try {
            categorie.setText(cs.getById(i).getNom());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}}

    public void insererimage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            imagef.setImage(image);}
    }
}
