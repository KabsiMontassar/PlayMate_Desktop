package test.Controllers.ProduitController;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Categorie;
import models.Gmailer;
import models.Product;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import services.GestionProduit.ProductService;
import test.MainFx;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Products implements Initializable {



    @FXML
    private Button qr1;

    @FXML
    private Button qr2;

    @FXML
    private Button qr3;
    @FXML
    private AnchorPane BOX1;

    @FXML
    private AnchorPane BOX2;

    @FXML
    private AnchorPane BOX3;

    @FXML
    private ImageView Img1;

    @FXML
    private ImageView Img2;

    @FXML
    private ImageView Img3;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Text address1;

    @FXML
    private Text address2;

    @FXML
    private Text address3;

    @FXML
    private Button btnDetail1;

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
    private Button btnajout;
    @FXML
    private Button mail;

    @FXML
    private Button btndetail2;

    @FXML
    private Button btndetail3;

    @FXML
    private Button btnretour;

    @FXML
    private Button btnsuivant;

    @FXML
    private Button btnsupp3;

    @FXML
    private Text nom1;

    @FXML
    private Text nom2;

    @FXML
    private Text nom3;
    @FXML
    private Button stat;
    /*

    BOX1 :
    nom1
    address1
    btnDetail1
btnSupp1
btnModif1
Img1

    * */
    int i= 0;

    private int IdUser;

    public void SetIdUser(int idUser) {

        this.IdUser = idUser;
        try {
            actualise(ps.getAllbyidFor(idUser));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int GetIdUser() {
        return this.IdUser;
    }



    ProductService ps = new ProductService();



    void actualise(List<Product> products){
        if(products.size()-1-i*3>0){btnsuivant.setVisible(true);
        }

        if(products.size()-1-i*3 <= 0){btnsuivant.setVisible(false);
        }
        if(i > 0){btnretour.setVisible(true);
        }
        if(i == 0){btnretour.setVisible(false);
        }
        if(!products.isEmpty()){
            if(products.size()-1-i*3>=0){
                BOX1.setVisible(true);
                nom1.setText(products.get(i*3).getNom());
                address1.setText(products.get(i*3).getDescription());
                Img1.setImage(new Image(products.get(i*3).getImage()));

            }
            else{BOX1.setVisible(false);
            }
            if(products.size()-2-i*3>=0){
                BOX2.setVisible(true);
                nom2.setText(products.get(1+i*3).getNom());
                address2.setText(products.get(1+i*3).getDescription());
                Img2.setImage(new Image(products.get(1+i*3).getImage()));

            }
            else{
                BOX2.setVisible(false);
            }
            if(products.size()-3-i*3>=0){
                BOX3.setVisible(true);
                nom3.setText(products.get(2+i*3).getNom());
                address3.setText(products.get(2+i*3).getDescription());
                Img3.setImage(new Image(products.get(2+i*3).getImage()));

            }else{BOX3.setVisible(false);}}else{
            BOX1.setVisible(false);
            BOX2.setVisible(false);
            BOX3.setVisible(false);
        }
        btnsuivant.setVisible(products.size()-3*i > 3);
    }
    @FXML
    void retour(ActionEvent event) {
        i -=1;
        try {
            actualise(ps.getAllbyidFor(GetIdUser()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void suivant(ActionEvent event) {
        i +=1;
        try {

            actualise(ps.getAllbyidFor(GetIdUser()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void Ajout(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/GestionProd.fxml"));
        Parent root = loader.load();
        ProductsController controller = loader.getController();
        controller.SetIdUser(GetIdUser());
        Stage stage = new Stage();

        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();
    }



    @FXML
    private Button liencategorie;

    @FXML
    void Modif1(ActionEvent event) throws SQLException, IOException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(8)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Product selectedProduct = ps.getAllbyidFor(GetIdUser()).get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/GestionProd.fxml"));
        Parent root = loader.load();
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ProductsController controller = loader.getController();
        controller.initialiseData(selectedProduct);
    }

    @FXML
    void modif2(ActionEvent event) throws SQLException, IOException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(8)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Product selectedProduct = ps.getAllbyidFor(GetIdUser()).get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/GestionProd.fxml"));
        Parent root = loader.load();
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ProductsController controller = loader.getController();
        controller.initialiseData(selectedProduct);
    }

    @FXML
    void modif3(ActionEvent event) throws IOException, SQLException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(8)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Product selectedProduct = ps.getAllbyidFor(GetIdUser()).get(index);
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/GestionProd.fxml"));
        Parent root = loader.load();
        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ProductsController controller = loader.getController();
        controller.initialiseData(selectedProduct);
    }



    @FXML
    void supp1(ActionEvent event) throws SQLException {
        Alert alert;

        List<Product> products;
        try {
            products = ps.getAllbyidFor(GetIdUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!products.isEmpty()) {alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous supprimez ce produit " + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                if (products.size() - 1 - i * 3 >= 0) {
                ps.delete(products.get((i*3)).getId());
                JOptionPane.showMessageDialog(null, "Delete Success!");

            }
        }

    }}
    @FXML
    void supp2(ActionEvent event) throws SQLException {
        Alert alert;

        List<Product> products;
        try {
            products = ps.getAllbyidFor(GetIdUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!products.isEmpty()) {alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous supprimez ce produit " + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
            if (products.size() - 1 - i * 3 >= 0) {
                ps.delete(products.get((i*3)+1).getId());
                JOptionPane.showMessageDialog(null, "Delete Success!");

            }
        }


    }}

    @FXML
    void supp3(ActionEvent event) throws SQLException {
        Alert alert;
        List<Product> products;
        try {
            products = ps.getAllbyidFor(GetIdUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!products.isEmpty()) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous supprimez ce produit " + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                if (products.size() - 1 - i * 3 >= 0) {
                    ps.delete(products.get((i * 3) + 2).getId());
                    JOptionPane.showMessageDialog(null, "Delete Success!");

                }
            }
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void categorie(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/Categorie.fxml"));
        Parent root = loader.load();
        Categories controller = loader.getController();
        controller.SetIdUser(GetIdUser());
        Stage stage = new Stage();

        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    void EnvoyerMail(ActionEvent event) throws Exception {
      /*  ProductService ps=new ProductService();
        String aa=ps.getAllbyidFor(GetIdUser()).toString();
        //System.out.println(aa);
        new Gmailer().sendMail("A new message", aa);
        JOptionPane.showMessageDialog(null, "Envoie avec succes");*/
    }
    @FXML
    void generateqrcode1(ActionEvent event) throws SQLException, IOException, WriterException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(2)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Product selectedProduct = ps.getAllbyidFor(GetIdUser()).get(index);
        String data=selectedProduct.toString();
        String path="/Users/seifl/Desktop/QRcodes/"+selectedProduct.getNom()+".jpg";

        BitMatrix matrix=new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,500,500);
        MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));
        System.out.println("jawek behy "+ ((3*i)+1));
    }

    @FXML
    void generateqrcode2(ActionEvent event) throws SQLException, WriterException, IOException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(2)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Product selectedProduct = ps.getAllbyidFor(GetIdUser()).get(index);
        String data=selectedProduct.toString();
        String path="/Users/seifl/Desktop/QRcodes/"+selectedProduct.getNom()+".jpg";

        BitMatrix matrix=new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,500,500);
        MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));
        System.out.println("jawek behy "+((3*i)+2));
    }

    @FXML
    void generateqrcode3(ActionEvent event) throws SQLException, WriterException, IOException {
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getId().substring(2)) - 1+3*i; // Assuming the button IDs are like "btnDetail1", "btnDetail2", etc.
        Product selectedProduct = ps.getAllbyidFor(GetIdUser()).get(index);
        String data=selectedProduct.toString();
        String path="/Users/seifl/Desktop/QRcodes/"+selectedProduct.getNom()+".jpg";

        BitMatrix matrix=new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,500,500);
        MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));
        System.out.println("jawek behy "+ ((i*3)+3));

    }
    @FXML
    void allerversstat(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionProduit/stat.fxml"));
        Parent root = loader.load();
        stat controller = loader.getController();
        controller.SetIdUser(GetIdUser());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
    private static final String API_KEY = "28f3f5fdf3644946806ce4f2671b5ab2";
    private static final String location = "southafricanorth";
    public void Traduire(ActionEvent actionEvent) {

        Stage popupStage = new Stage();
        popupStage.setTitle("Translator");

        TextField inputTextArea = new TextField();
        inputTextArea.setPromptText("Enter text to translate");

        ChoiceBox<String> sourceLanguageChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
                "en", "fr", "es", "ar"));
        sourceLanguageChoiceBox.getSelectionModel().selectFirst();

        ChoiceBox<String> targetLanguageChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
                "en", "fr", "es", "ar"));
        targetLanguageChoiceBox.getSelectionModel().selectFirst();

        Button translateButton = new Button("Translate");

        Label translatedTextLabel = new Label("Translated Text:");
        Label translatedText = new Label();

        translateButton.setOnAction(event -> {
            String sourceLanguage = sourceLanguageChoiceBox.getValue();
            String targetLanguage = targetLanguageChoiceBox.getValue();
            String inputText = inputTextArea.getText();

            System.out.println("Source Language: " + sourceLanguage); // Print the selected source language

            try {
                String url = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=" + sourceLanguage + "&to=" + targetLanguage;

                System.out.println("API URL: " + url); // Print the API request URL

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Ocp-Apim-Subscription-Key", API_KEY)
                        .addHeader("Ocp-Apim-Subscription-Region", location)
                        .addHeader("Content-Type", "application/json")
                        .post(RequestBody.create(okhttp3.MediaType.parse("application/json"), "[{\"Text\": \"" + inputText + "\"}]"))
                        .build();

                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();

                JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                JsonArray translations = jsonObject.getAsJsonArray("translations");
                String translatedTextValue = translations.get(0).getAsJsonObject().get("text").getAsString();

                // Update translated text label
                translatedText.setText(translatedTextValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Create layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.add(new Label("Source Language:"), 0, 0);
        inputGrid.add(sourceLanguageChoiceBox, 1, 0);
        inputGrid.add(new Label("Target Language:"), 0, 1);
        inputGrid.add(targetLanguageChoiceBox, 1, 1);
        inputGrid.add(inputTextArea, 0, 2, 2, 1);
        inputGrid.add(translateButton, 0, 3, 2, 1);
        inputGrid.add(translatedTextLabel, 0, 4);
        inputGrid.add(translatedText, 1, 4);

        root.getChildren().add(inputGrid);

        Scene scene = new Scene(root, 400, 250);

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(scene);
        popupStage.show();
    }
    }
