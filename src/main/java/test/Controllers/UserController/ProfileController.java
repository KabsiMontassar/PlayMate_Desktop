package test.Controllers.UserController;

import com.mailjet.client.errors.MailjetException;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Roles;
import models.User;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import services.GestionUser.UserService;
import test.MainFx;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class ProfileController {

    public AnchorPane profileRoot;



    public Text tdds ;
    private String imagePath;
    public Button btnlocation;
    public WebView mavView;
    public Button confirmer;
    public TextField Inputaddress;
    public Button retourner;
    public AnchorPane mappane;
    @FXML
    private Button Btnback;

    @FXML
    private TextField InputAddress;

    @FXML
    private TextField InputAge;

    @FXML
    private TextField InputNom;

    @FXML
    private TextField InputPhone;

    @FXML
    private Button btnDesactiver;

    @FXML
    private Button btnSeeInformationuser;

    @FXML
    private Button btnUpdateProfile;

    @FXML
    private Button btnseeRoleTab;

    @FXML
    private Button btnuploadimage;


    @FXML
    private ImageView imgview;


    @FXML
    private PasswordField inputCPassword;



    @FXML
    private PasswordField inputPassword;

    @FXML
    private Text tdNomaffichage;

    @FXML
    private Text tfDatedecreation;

    @FXML
    private Text tfRoleAffichage;

    private User CurrentUser ;

private String FromMapAddress;

   UserService us = new UserService();
    public void setAddress(String lastString) throws SQLException {
        InputAddress.setText(lastString);

    }




    public void setData(User u) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {


        UserService us = new UserService();
        CurrentUser = us.getByEmail(u.getEmail());

        System.out.println(CurrentUser);
        tfRoleAffichage.setText(CurrentUser.getRole().toString());
        tfDatedecreation.setText(CurrentUser.getDate_de_Creation());
        tdNomaffichage.setText(CurrentUser.getName());
        inputPassword.setText(CurrentUser.getPassword());
        inputCPassword.setText(CurrentUser.getPassword());

        if(!CurrentUser.getAddress().isEmpty()){
            InputAddress.setText(CurrentUser.getAddress());
        }else{
            InputAddress.setText("");
        }




        InputAddress.setText(
                CurrentUser.getEmail() == null ? "" : CurrentUser.getAddress()
        );
        InputAge.setText(
                CurrentUser.getAge() == 0 ? "" : String.valueOf(CurrentUser.getAge())
        );
        InputNom.setText(
                CurrentUser.getName().isEmpty() ? "" : CurrentUser.getName()
        );
        InputPhone.setText(
                CurrentUser.getPhone() == 0 ? "" : String.valueOf(CurrentUser.getPhone())
        );

        String imagePath = CurrentUser.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(imagePath);
            if (image.isError()) {

                imgview.setImage(null);
            } else {
                imgview.setImage(image);
            }
        } else {
            imgview.setImage(null);
        }

    }


    public void initialize() throws SQLException {

        WebEngine webEngine = mavView.getEngine();
        webEngine.load(Objects.requireNonNull(getClass().getResource("/test/GestionUser/googlemaps.html")).toExternalForm());

        // Enable JavaScript communication
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("java", this);
            }
        });


        UnaryOperator<TextFormatter.Change> filterPhone = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*") && newText.length()<=8 ) {
                return change;
            }
            return null;
        };
        UnaryOperator<TextFormatter.Change> filterAge = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*") && newText.length()<=2) {
                return change;
            }
            return null;
        };


        TextFormatter<String> textFormatter = new TextFormatter<>(filterPhone);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(filterAge);


        InputPhone.setTextFormatter(textFormatter);
        InputAge.setTextFormatter(textFormatter2);





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
            Inputaddress.setText(address);
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
            if (jsonResponse.has("results") && !jsonResponse.getJSONArray("results").isEmpty()) {
                JSONObject result = jsonResponse.getJSONArray("results").getJSONObject(0);
                fullAddress = result.getString("formatted_address");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fullAddress;
    }
    public boolean showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);


        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    @FXML
    void DesactiverProfile(ActionEvent event) {
        if (showConfirmationDialog("Etes-vous sûr de vouloir desactiver votre compte?")) {
            try {
                UserService us = new UserService();

                us.InvertStatus(CurrentUser.getEmail());

            } catch (SQLException e) {
                e.printStackTrace();

            } catch (NoSuchPaddingException | MailjetException | IOException | InvalidKeyException |
                     BadPaddingException | NoSuchAlgorithmException | InterruptedException | IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            }
            try {

                FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/LoginRegistrationPage.fxml"));
                AnchorPane root = loader.load();

                profileRoot.getChildren().setAll(root);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    @FXML
    void SeeInformationElements(ActionEvent event) {



    }


    @FXML
    void changerphoto(ActionEvent event) throws SQLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg" ));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            us.updatePhoto(imagePath,CurrentUser.getEmail());
            imgview.setImage(image);

        }


    }

    @FXML
    void updateProfile(ActionEvent event) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if( !inputPassword.getText().equals(inputCPassword.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("mots de passe ne correspondent pas");
            alert.setContentText("mot de passe et confirmer le mot de mot de passe devraient avoir le même contenu");
            alert.setHeaderText("CAlert Alert");
            alert.showAndWait();
            return ;
        }
        if( inputPassword.getText().isEmpty()  ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("mots de passe invalid");
            alert.setContentText("le mot de passe ne peut pas être vide");
            alert.setHeaderText("CAlert Alert");
            alert.showAndWait();
            return ;
        }

        UserService us = new UserService();
        User UpdateUser = new User();


        UpdateUser.setAddress(InputAddress.getText());
        UpdateUser.setPhone(
                InputPhone.getText().isEmpty() ? "0" : InputPhone.getText()
        );
        UpdateUser.setPassword(inputPassword.getText());
        UpdateUser.setName(InputNom.getText());
        UpdateUser.setAge(
                InputAge.getText().isEmpty() ? 0 : Integer.parseInt(InputAge.getText())

        );


UpdateUser.setEmail(CurrentUser.getEmail());


        us.update(UpdateUser);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profil mis à jour avec succès\n");
        alert.setHeaderText("votre compte a ete mis à jour");
        alert.showAndWait();
    }

    @FXML
    void goToAcceuil(ActionEvent event) {
        try {
            UserService us = new UserService();


            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/Acceuil.fxml"));
            Parent root = loader.load();


            AcceuilController acceuilController = loader.getController();


            acceuilController.setData(us.getByEmail(CurrentUser.getEmail()));



            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) Btnback.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }


    public void locationsetter(ActionEvent mouseEvent) throws IOException {
       mappane.setVisible(true);
    }


    public void retourner(ActionEvent actionEvent) {
        mappane.setVisible(false);
        Inputaddress.setText("");
    }
    CAlert ca = new CAlert();
    public void confirmer(ActionEvent actionEvent) {
        if (!Inputaddress.getText().contains("Tunisia")) {
            ca.generateAlert("WARNING", "Address invalid");
            return;
        }

        InputAddress.setText(Inputaddress.getText());
        mappane.setVisible(false);

    }
}
