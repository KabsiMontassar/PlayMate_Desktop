package test.Controllers.TournoiController;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Tournoi;
import netscape.javascript.JSObject;
import services.GestionTournoi.ServiceTournoi;
import test.MainFx;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Pattern;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModifierTournoiController implements Initializable {

    private int IdUser;

    public void SetIdUser(int idUser) {

        this.IdUser = idUser;
    }
    public int GetIdUser() {
        return this.IdUser;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setVisible(false);
        errorLabel2.setVisible(false);
        errorLabel3.setVisible(false);
        first = new ArrayList<>();
        WebEngine webEngine = mapView.getEngine();
        webEngine.load(MainFx.class.getResource("GestionTournoi/googlemaps.html").toExternalForm());

        // Enable JavaScript communication
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("java", this);
            }
        });
    }

    public void handleSelectedLocation(double latitude, double longitude) {
        Platform.runLater(() -> {
            System.out.println("Selected Location: " + latitude + ", " + longitude);
            String placeName = getPlaceName(latitude, longitude);
            String locationInfo = "";
            if (placeName != null && !placeName.isEmpty()) {
                locationInfo += placeName + " ";
            }
            InputAddress.setText(locationInfo);
            // You can perform any necessary actions with the received latitude, longitude, and placeName here
        });
    }

    private String getPlaceName(double latitude, double longitude) {
        String apiKey = "AIzaSyBKbAQafF9CzI3D1HJkRgwxWywnFK8oSgM";
        String fullAddress = null;

        try {
            String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?" +
                    "latlng=" + latitude + "," + longitude +
                    "&key=" + apiKey;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            if (jsonResponse.has("results") && jsonResponse.getJSONArray("results").length() > 0) {
                JSONObject result = jsonResponse.getJSONArray("results").getJSONObject(0);
                fullAddress = result.getString("formatted_address");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fullAddress;
    }


    private List<Tournoi> first;

    private List<String> imagePaths = new ArrayList<>();


    @FXML
    private Button Btnback;

    @FXML
    private AnchorPane FormulaireRoot;

    @FXML
    private TextField InputAddress;

    @FXML
    private TextField InputNom;

    @FXML
    private TextField InputNombreéquipes;

    @FXML
    private Button btnAjouterTournoi;

    @FXML
    private Button btnSeeinformationsTournoi;

    @FXML
    private Button btnuploadimage;

    @FXML
    private ImageView imgview;

    @FXML
    private TextField InputDateDébut;

    @FXML
    private TextField InputDateFin;


    @FXML
    private Label errorLabel;

    @FXML
    private Label errorLabel2;

    @FXML
    private Label errorLabel3;

    private Tournoi tournoiActuel;

    @FXML
    private WebView mapView;


    public void goToTournoi(ActionEvent actionEvent) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/tournoi.fxml"));
        AnchorPane root = loader.load();
        FirstController controller = loader.getController();
        controller.SetIdUser(GetIdUser());
        Stage stage = new Stage();
        stage.setTitle("Détails du Tournoi");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void initData(Tournoi tournoi) {
        String imagePathsString = String.join(", ", imagePaths);

        this.tournoiActuel = tournoi;
        InputNombreéquipes.setText(String.valueOf(tournoi.getNbrquipeMax()));
        InputNom.setText(tournoi.getNom());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        InputDateDébut.setText(sdf.format(tournoi.getDatedebut()));
        InputDateFin.setText(sdf.format(tournoi.getDatefin()));
        InputAddress.setText(tournoi.getAddress());
        imagePathsString = tournoi.getAffiche();

        if (imagePathsString != null && !imagePathsString.isEmpty()) {
            try {

                String basePath = "C:\\Users\\lenovo\\Documents\\GitHub\\SpartansPIWeb\\public\\uploads\\images";
                String firstImagePath = basePath + File.separator + imagePathsString;
                Image image = new Image(firstImagePath);
                imgview.setImage(image);
            } catch (IllegalArgumentException e) {
                // Handle the error when the URL is invalid or resource not found
                imgview.setImage(null); // Set the image view to display nothing
            }}}

    private boolean validateDate() {
        String dateDebutText = InputDateDébut.getText();
        String dateFinText = InputDateFin.getText();

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateDebut = LocalDate.parse(dateDebutText, formatter);
            LocalDate dateFin = LocalDate.parse(dateFinText, formatter);

            if (dateDebut.isAfter(dateFin)) {
                errorLabel.setVisible(true);
                return  false ;
            } else {
                errorLabel.setVisible(false);
                return  true ;
            }
        } catch (DateTimeParseException e) {
            errorLabel.setVisible(true);
            errorLabel.setText("Format sous la forme yyyy-MM-dd svp .");
            return false ;
        }
    }

    private boolean validateNombreEquipes() {
        String nombreEquipesText = InputNombreéquipes.getText();
        if (Pattern.matches("^(8|16|24|48)$", nombreEquipesText)) {
            errorLabel2.setVisible(false);
            return  true;
        } else {
            errorLabel2.setVisible(true);
        }
        return false;
    }
    private boolean validateNom() {
        String nomText = InputNom.getText();

        if (Pattern.matches("^[a-zA-Z0-9]+$", nomText)) {
            errorLabel3.setVisible(false);
            return true;
        } else {
            errorLabel3.setVisible(true);

        }
        return false;
    }

    @FXML
    void addimage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            try {

                for (File file : selectedFiles) {
                    // Generate a unique filename for each image
                    String fileName = generateUniqueFileName(file);
                    // Construct the destination path
                    Path destination = Paths.get("C:\\Users\\lenovo\\Documents\\GitHub\\SpartansPIWeb\\public\\uploads\\images", fileName);
                    // Copy the selected image to the destination
                    Files.copy(file.toPath(), destination);
                    // Store the image filename
                    imagePaths.add(fileName);

                }
                String basePath = "C:\\Users\\lenovo\\Documents\\GitHub\\SpartansPIWeb\\public\\uploads\\images";
                String imagePathsString = String.join(", ", imagePaths);
                String firstImagePath = basePath + File.separator + imagePathsString;
                System.out.println("aaa" + firstImagePath);
                Image image = new Image(firstImagePath);
                imgview.setImage(image);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle file copy errors
            }
        }
    }
    // Generate a unique filename (you can use UUID or any other method)
    private String generateUniqueFileName(File file) {
        String originalName = file.getName();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }

    public void ModifierTournoi(ActionEvent actionEvent) throws SQLException, IOException {
        String imagePathsString = String.join(", ", imagePaths);

        if ( validateNombreEquipes() && validateNom() && validateDate()) {
            ServiceTournoi ts = new ServiceTournoi();
            Tournoi tournoi = new Tournoi(tournoiActuel.getId(), Integer.parseInt(InputNombreéquipes.getText()), InputNom.getText(), imagePathsString, InputAddress.getText(), InputDateDébut.getText(), InputDateFin.getText());
            ts.modifier(tournoi);
            System.out.println(tournoi);
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/tournoi.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion_Tournoi");
            stage.setScene(new Scene(root));
            stage.show();
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();
        }
    }


}
