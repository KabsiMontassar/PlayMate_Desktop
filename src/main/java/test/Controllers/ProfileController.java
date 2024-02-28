package test.Controllers;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Fournisseur;
import models.Organisateur;
import models.Roles;
import models.User;
import services.GestionUser.UserService;
import test.MainFx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class ProfileController {

    public AnchorPane profileRoot;
    public TextField Roleinput;
    public Text RoleLabel;
    public AnchorPane imganchodid;
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
    public void setData(User u) throws SQLException {
        imganchodid.setVisible(false);
        Roleinput.setVisible(false);
        RoleLabel.setVisible(false);
        UserService us = new UserService();
        CurrentUser = us.getByEmail(u.getEmail());

        tfRoleAffichage.setText(CurrentUser.getRole().toString());
        tfDatedecreation.setText(CurrentUser.getDate_de_Creation());
        tdNomaffichage.setText(CurrentUser.getName());
        inputPassword.setText(CurrentUser.getPassword());
        inputCPassword.setText(CurrentUser.getPassword());
        if(CurrentUser.getRole() == Roles.Organisateur) {
            RoleLabel.setVisible(true);
            Roleinput.setVisible(true);
            Organisateur org = us.getOrganisateurbyid(CurrentUser.getId());
            RoleLabel.setText("Organisation");
            Roleinput.setText(org.getNom_Organisation());
        }
            if(CurrentUser.getRole() == Roles.Fournisseur){
                RoleLabel.setVisible(true);
                Roleinput.setVisible(true);
                Fournisseur org = us.getFournisseurbyid(CurrentUser.getId());
                RoleLabel.setText("Societe");
                Roleinput.setText(org.getNom_Societe());
            }


        InputAddress.setText(
                CurrentUser.getEmail().isEmpty() ? "" : CurrentUser.getAddress()
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

        if( CurrentUser.getImage() != null){

            Path targetPath = Paths.get("src/main/resources/ProfilePictures", CurrentUser.getImage());
            Image image = new Image(targetPath.toUri().toString());
            imgview.setImage(image);

        }

    }

    public void initialize() throws SQLException {



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
        if (showConfirmationDialog("Etes-vous sûr de vouloir désactiver votre compte?")) {
            try {
                UserService us = new UserService();

                us.InvertStatus(CurrentUser.getEmail());

            } catch (SQLException e) {
                e.printStackTrace();

            }
            try {
                UserService us = new UserService();

                FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("LoginRegistrationPage.fxml"));
                AnchorPane root = loader.load();

                LoginRegistrationPageController controller = loader.getController();
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
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(btnuploadimage.getScene().getWindow());
        if (selectedFile != null) {
            UserService us = new UserService();
            us.updatePhoto(CurrentUser.getId() +".png",CurrentUser.getEmail());

            try {
                String filename = CurrentUser.getId() +".png";
                Path targetPath = Paths.get("src/main/resources/ProfilePictures", filename);
                Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                Image image = new Image(targetPath.toUri().toString());
                imgview.setImage(image);
            } catch ( IOException e) {
                e.printStackTrace();
            }
        }


    }

    @FXML
    void updateProfile(ActionEvent event) throws SQLException {
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
                InputPhone.getText().isEmpty() ? 0 : Integer.parseInt(InputPhone.getText())
        );
        UpdateUser.setPassword(inputPassword.getText());
        UpdateUser.setName(InputNom.getText());
        UpdateUser.setAge(
                InputAge.getText().isEmpty() ? 0 : Integer.parseInt(InputAge.getText())

        );

        if(CurrentUser.getRole() == Roles.Fournisseur){
            us.UpdateNom_Societe(CurrentUser.getId(),Roleinput.getText());
        }
        if(CurrentUser.getRole() == Roles.Organisateur){
            us.UpdateNom_Organisation(CurrentUser.getId(),Roleinput.getText());
        }




        us.update(UpdateUser);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profil mis à jour avec succès\n");
        alert.setHeaderText("votre compte a été mis à jour");
        alert.showAndWait();
    }

    @FXML
    void goToAcceuil(ActionEvent event) {
        try {
            UserService us = new UserService();


            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("Acceuil.fxml"));
            Parent root = loader.load();


            AcceuilController acceuilController = loader.getController();


            acceuilController.setData(us.getByEmail(CurrentUser.getEmail()));



            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) Btnback.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
