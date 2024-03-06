package test.Controllers.TournoiController;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Tournoi;
import services.GestionTournoi.ServiceTournoi;
import test.Controllers.UserController.CAlert;
import test.MainFx;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AjoutTournoiController implements Initializable {

    private int IdUser;

    public void SetIdUser(int idUser) {

        this.IdUser = idUser;
        errorLabel.setVisible(false);
        errorLabel2.setVisible(false);
        errorLabel3.setVisible(false);
        first = new ArrayList<>();

    }
    public int GetIdUser() {
        return this.IdUser;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = mapView.getEngine();
        webEngine.load(Objects.requireNonNull(getClass().getResource("/test/GestionTournoi/googlemaps.html")).toExternalForm());

        // Enable JavaScript communication
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("java", this);
            }
        });
    }
CAlert c = new CAlert();
    public void handleSelectedLocation(double latitude, double longitude) {
        Platform.runLater(() -> {
            System.out.println("Selected Location: " + latitude + ", " + longitude);
            String placeName = getPlaceName(latitude, longitude);
            System.out.println("Selected Montasar: " + latitude + ", " + longitude);

            String locationInfo = "";
            if (placeName != null && !placeName.isEmpty()) {
                locationInfo += placeName + " ";
            }
            String address = locationInfo.substring(locationInfo.indexOf(' ')+1).trim();
            if(!address.contains("Tunisia")){
                c.generateAlert("Warning","Invalid Address");
            }
            InputAddress.setText(address);
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

    private String imagePath;

    @FXML
    private Label errorLabel;

    @FXML
    private Label errorLabel2;

    @FXML
    private Label errorLabel3;

    private Tournoi tournoiActuel;

    @FXML
    private WebView mapView;



    public void goToTournoi(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/tournoi.fxml"));
        AnchorPane root = loader.load();
        FirstController controller = loader.getController(); // Retrieve the controller
        controller.SetIdUser(GetIdUser());



        Stage stage = new Stage();
        stage.setTitle("Modifier");
        stage.setScene(new Scene(root));
        stage.show();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void initData(Tournoi tournoi) {
        this.tournoiActuel = tournoi;
        InputNombreéquipes.setText(String.valueOf(tournoi.getNbrquipeMax()));
        InputNom.setText(tournoi.getNom());
        InputDateDébut.setText(tournoi.getDatedebut());
        InputDateFin.setText(tournoi.getDatefin());
        InputAddress.setText(tournoi.getAddress());
        imagePath = tournoi.getAffiche();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(imagePath);
            imgview.setImage(image);
        }

    }

    private boolean validateDate() {
        String dateDebutText = InputDateDébut.getText();
        String dateFinText = InputDateFin.getText();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateDebut = LocalDate.parse(dateDebutText, formatter);
            LocalDate dateFin = LocalDate.parse(dateFinText, formatter);

            // Vérifiez si la date de début est après la date de fin
            if (dateDebut.isAfter(dateFin)) {
                errorLabel.setText("La date de début doit être avant la date de fin.");
                errorLabel.setVisible(true);
                return false;
            }

            // Calculez la différence en jours entre la date de début et la date de fin
            long daysBetween = ChronoUnit.DAYS.between(dateDebut, dateFin);

            // Vérifiez si la différence est supérieure à 30 jours
            if (daysBetween > 30) {
                errorLabel.setText("La différence entre les dates ne doit pas dépasser 30 jours.");
                errorLabel.setVisible(true);
                return false;
            }

            // Si toutes les validations sont réussies
            errorLabel.setVisible(false);
            return true;

        } catch (DateTimeParseException e) {
            errorLabel.setText("Format sous la forme dd/mm/aaaa svp.");
            errorLabel.setVisible(true);
            return false;
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
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            imgview.setImage(image);}
    }

    public void AjouterTournoi(ActionEvent actionEvent) throws SQLException, IOException {

        if (validateNombreEquipes() && validateNom() && validateDate()) {
            ServiceTournoi ts = new ServiceTournoi();
            Tournoi tournoi = new Tournoi(Integer.parseInt(InputNombreéquipes.getText()), InputNom.getText(), imagePath, InputAddress.getText(), InputDateDébut.getText(), InputDateFin.getText(), GetIdUser());
            ts.ajouter(tournoi);
            System.out.println(tournoi);
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionTournoi/tournoi.fxml"));

            AnchorPane root = loader.load();
            FirstController controller = loader.load();
            controller.SetIdUser(GetIdUser());
            Stage stage = new Stage();
            stage.setTitle("Gestion_Tournoi");
            stage.setScene(new Scene(root));
            stage.show();
            ((Button) actionEvent.getSource()).getScene().getWindow().hide();

        }
    }



}
