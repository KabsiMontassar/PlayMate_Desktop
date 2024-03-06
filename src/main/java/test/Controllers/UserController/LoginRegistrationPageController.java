package test.Controllers.UserController;


import com.mailjet.client.errors.MailjetException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import javafx.util.Duration;
import models.*;
import services.GestionUser.UserService;
import services.UserActivityLogger;
import test.MainFx;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.awt.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import java.util.Objects;
import java.util.function.UnaryOperator;



public class LoginRegistrationPageController {

    public Text Registeragelabel;
    public TextField Registerfield111numero;
    public TextField Registerfield21age;
    public Text RegisterLabel111numero;
    public Line fort;
    public Line moyen;
    public Line faible;
    public Line moyen2;
    public Line fort1;
    public Line fort2;
    public Text textcfort;
    @FXML
    private Button BtnSinscrire;

    @FXML
    private Button RegisterBtnSinscrire;

    @FXML
    private Text RegisterHeader;

    @FXML
    private Text RegisterLabel1;

    @FXML
    private Text RegisterLabel2;

    @FXML
    private Text RegisteraddressLabel;

    @FXML
    private TextField Registerfield1;

    @FXML
    private TextField Registerfield2;

    @FXML
    private Text Registerlabel12;

    @FXML
    private PasswordField Registerpass1;

    @FXML
    private Text Registertxt2;

    @FXML
    private Text Registrertxt1;

    @FXML
    private AnchorPane RightPane;

    @FXML
    private Text SeconnecterLabel1;

    @FXML
    private Text SeconnecterLabel2;

    @FXML
    private AnchorPane MainPane;
    @FXML
    private PasswordField SeconnecterPass1;

    @FXML
    private Text SeconnecterTxt1;

    @FXML
    private Text SeconnecterTxt2;

    @FXML
    private Button Seconnecterbtn1;

    @FXML
    private Button Seconnecterbtn2;

    @FXML
    private ToggleGroup radiobuttons;

    @FXML
    private TextField Seconnecterfield1;

    @FXML
    private Text Seconnecterheader;

    @FXML
    private Button btnSeconnecter;

    @FXML
    private AnchorPane leftPane;

    @FXML
    private RadioButton rbtn1;

    @FXML
    private RadioButton rbtn2;

    @FXML
    private RadioButton rbtn3;

    @FXML
    private RadioButton rbtn4;

    @FXML
    private PasswordField registerPass2;



    UserService us = new UserService();
    CAlert cAlert = new CAlert();

    private User CurrentUser ;
    public void setData(User us) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        this.CurrentUser = us;
        Seconnecterfield1.setText(us.getEmail());
        SeconnecterPass1.setText(us.getPassword());

    }

    public void initialize() throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        faible.setVisible(false);
        moyen.setVisible(false);
        moyen2.setVisible(false);
        fort.setVisible(false);
        fort1.setVisible(false);
        fort2.setVisible(false);



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


        UnaryOperator<TextFormatter.Change> filterpass = change -> {
            String newText = change.getControlNewText();
            if ( newText.length()<=20) {
                return change;
            }
            return null;
        };

        UnaryOperator<TextFormatter.Change> filternom = change -> {
            String newText = change.getControlNewText();
            if ( newText.length()<=20) {
                return change;
            }
            return null;
        };
        UnaryOperator<TextFormatter.Change> filteremail = change -> {
            String newText = change.getControlNewText();
            if ( newText.length()<=30) {
                return change;
            }
            return null;
        };


        Registerfield2.setTextFormatter(new TextFormatter<>(filteremail));
        Seconnecterfield1.setTextFormatter(new TextFormatter<>(filteremail));
        Registerfield1.setTextFormatter(new TextFormatter<>(filternom));
        Registerfield111numero.setTextFormatter(new TextFormatter<>(filterPhone));
        Registerfield21age.setTextFormatter(new TextFormatter<>(filterAge));
        Registerpass1.setTextFormatter(new TextFormatter<>(filterpass));
        registerPass2.setTextFormatter(new TextFormatter<>(filterpass));
        SeconnecterPass1.setTextFormatter(new TextFormatter<>(filterpass));

    }

    public boolean isValidName(String name){

        return name.matches("^(?!\\s)(?!.*\\s$)[a-zA-Zéàè ]*(?:[a-zA-Zéàè][a-zA-Zéàè ]*){0,3}$");


    }
    public boolean isValidEmail(String email){
        return email.matches("^[a-zA-Z][a-zA-Z0-9._-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
    }
    public boolean isValidPassword(String password) {
        if (password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[-_@#$%^&+=])[a-zA-Z][-_a-zA-Z0-9@#$%^&+=]*[a-zA-Z0-9]$") && password.length() >= 8) {
            faible.setVisible(false);
            moyen.setVisible(false);
            moyen2.setVisible(false);
            fort.setVisible(true);
            fort1.setVisible(true);
            fort2.setVisible(true);
            return true;
        } else if (password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*$") && password.length() >= 8) {
            // Medium password with characters, digits, and uppercase letters
            faible.setVisible(false);
            moyen.setVisible(true);
            moyen2.setVisible(true);
            fort.setVisible(false);
            fort1.setVisible(false);
            fort2.setVisible(false);
            return true;
        } else if (password.matches("^(?=.*[a-zA-Z])[a-zA-Z]*$") && password.length() >= 8) {
            // Weak password with characters and digits
            faible.setVisible(true);
            moyen.setVisible(false);
            moyen2.setVisible(false);
            fort.setVisible(false);
            fort1.setVisible(false);
            fort2.setVisible(false);
            return false;
        } else {
            // Password does not meet any strength criteria
            faible.setVisible(true);
            moyen.setVisible(false);
            moyen2.setVisible(false);
            fort.setVisible(false);
            fort1.setVisible(false);
            fort2.setVisible(false);
            return false;
        }
    }



    public void SinscrireSlideBtn(ActionEvent actionEvent) {
        moyen.setVisible(false);
        moyen2.setVisible(false);
        fort.setVisible(false);
        fort1.setVisible(false);
        fort2.setVisible(false);
       Seconnecterheader.setVisible(false);
        SeconnecterLabel1.setVisible(false);
        Seconnecterfield1.setVisible(false);
        SeconnecterLabel2.setVisible(false);
        SeconnecterPass1.setVisible(false);
        Seconnecterbtn1.setVisible(false);
        Seconnecterbtn2.setVisible(false);
        SeconnecterTxt1.setVisible(false);
        SeconnecterTxt2.setVisible(false);
        BtnSinscrire.setVisible(false);

        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(1), leftPane);
        transition1.setToX(leftPane.getTranslateX() + 300); // Move left by 50 pixels (adjust as needed)
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), RightPane);
        transition2.setToX(RightPane.getTranslateX() - 880 ); // Move left by 50 pixels (adjust as needed)
        transition1.play();
        transition2.play();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            Registeragelabel.setVisible(true);
            Registerfield111numero.setVisible(true);
            Registerfield21age.setVisible(true) ;
                    RegisterLabel111numero.setVisible(true);
            RegisterBtnSinscrire.setVisible(true);
            RegisterHeader.setVisible(true);
            RegisterLabel1.setVisible(true);
            RegisterLabel2.setVisible(true);
            RegisteraddressLabel.setVisible(true);
            Registerfield1.setVisible(true);
            Registerfield2.setVisible(true);
            Registerlabel12.setVisible(true);
            Registerpass1.setVisible(true);
            Registertxt2.setVisible(true);
            Registrertxt1.setVisible(true);
            btnSeconnecter.setVisible(true);
            rbtn1.setVisible(true);
            rbtn2.setVisible(true);
            rbtn3.setVisible(true);
            rbtn4.setVisible(true);
            registerPass2.setVisible(true);

        }));
        timeline.play();

    }

    public void gotoSeconnecter(){
        Registeragelabel.setVisible(false);
        Registerfield111numero.setVisible(false);
        Registerfield21age.setVisible(false) ;
        RegisterLabel111numero.setVisible(false);
        RegisterBtnSinscrire.setVisible(false);
        RegisterHeader.setVisible(false);
        RegisterLabel1.setVisible(false);
        RegisterLabel2.setVisible(false);
        RegisteraddressLabel.setVisible(false);
        Registerfield1.setVisible(false);
        Registerfield2.setVisible(false);
        Registerlabel12.setVisible(false);
        Registerpass1.setVisible(false);
        Registertxt2.setVisible(false);
        Registrertxt1.setVisible(false);
        btnSeconnecter.setVisible(false);
        rbtn1.setVisible(false);
        rbtn2.setVisible(false);
        rbtn3.setVisible(false);
        rbtn4.setVisible(false);
        registerPass2.setVisible(false);
        faible.setVisible(false);
        moyen.setVisible(false);
        moyen2.setVisible(false);
        fort.setVisible(false);
        fort1.setVisible(false);
        fort2.setVisible(false);

        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(1), leftPane);
        transition1.setToX(leftPane.getTranslateX() - 300); // Move left by 50 pixels (adjust as needed)
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), RightPane);
        transition2.setToX(RightPane.getTranslateX() + 880); // Move left by 50 pixels (adjust as needed)
        transition1.play();
        transition2.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            Seconnecterheader.setVisible(true);
            SeconnecterLabel1.setVisible(true);
            Seconnecterfield1.setVisible(true);
            SeconnecterLabel2.setVisible(true);
            SeconnecterPass1.setVisible(true);
            Seconnecterbtn1.setVisible(true);
            Seconnecterbtn2.setVisible(true);
            SeconnecterTxt1.setVisible(true);
            SeconnecterTxt2.setVisible(true);
            BtnSinscrire.setVisible(true);
            faible.setVisible(false);
            moyen.setVisible(false);
            moyen2.setVisible(false);
            fort.setVisible(false);
            fort1.setVisible(false);
            fort2.setVisible(false);

        }));
        timeline.play();
    }

    public void SeconnecterSlideBtn(ActionEvent actionEvent) {
        gotoSeconnecter();

    }
    @FXML
    public void Oublietlemotdepass(ActionEvent event) throws Exception {

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/VerificationCode.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

    }

    @FXML
    public void Seconnecter(ActionEvent event) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException, InterruptedException, MailjetException {


        if(Objects.equals(Seconnecterfield1.getText(), "Admin") && Objects.equals(SeconnecterPass1.getText(), "Admin")){
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/AdminPage.fxml"));
            AnchorPane root = loader.load();


            MainPane.getChildren().setAll(root);
        return;
        }
        if(Seconnecterfield1.getText().isEmpty() || SeconnecterPass1.getText().isEmpty()){

            cAlert.generateAlert("WARNING","Tous les champs sont requis");
            
            return;
        }


        if (!us.Login(Seconnecterfield1.getText(),SeconnecterPass1.getText())) {

            cAlert.generateAlert("WARNING","Email ou mot de passe incorrect");
            
            return;
        }


        User u = us.getByEmail(Seconnecterfield1.getText());
        if( !u.getStatus()){
            if( cAlert.generateConfirmation("Your account is currently deactivated. Do you want to reactivate and log in?")){

                us.InvertStatus(u.getEmail());

            }else{
                return;
            }
        }
        try {
            UserActivityLogger UAL = new UserActivityLogger();
            UAL.logAction(Seconnecterfield1.getText() ,  " connecte");

            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("GestionUser/Acceuil.fxml"));
            AnchorPane root = loader.load();

            AcceuilController acceuilController = loader.getController();


            acceuilController.setData(us.getByEmail(Seconnecterfield1.getText()));


            MainPane.getChildren().setAll(root);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }



















    @FXML
    void sinscrire(ActionEvent event) throws Exception {


        if (Registerfield2.getText().isEmpty()
                || Registerfield1.getText().isEmpty()
                || Registerpass1.getText().isEmpty()
                || Registerfield111numero.getText().isEmpty()
                || Registerfield21age.getText().isEmpty()

        ){

            cAlert.generateAlert("WARNING","Tous les champs sont requis");
            return;
        }

        if(!isValidName(Registerfield1.getText())){

            cAlert.generateAlert("WARNING","le nom ne doit contenir que des lettres");
            return;
            
        }
        if(!isValidEmail(Registerfield2.getText())){

            cAlert.generateAlert("WARNING","l'email doit être valide");
           
            return;
        }

        if(!isValidPassword(Registerpass1.getText())){

            cAlert.generateAlert("WARNING","le mot de passe est invalid");
            return;
           
        }
        if(Integer.parseInt(Registerfield21age.getText()) < 12){

            cAlert.generateAlert("WARNING","L'age doit être superieur à 12 ans");
            
            return;
        }

        if(!registerPass2.getText().equals(Registerpass1.getText()) ){

            cAlert.generateAlert("WARNING","mot de passe et confirmer le mot de mot de passe devraient avoir le même contenu");
           


            return;
        }
        if(us.userExist(Registerfield2.getText())){
            cAlert.generateAlert("WARNING","l'utilisateur existe dejà");
            return;
        }


        Roles role;
        User u1;
        if(rbtn1.isSelected()){
            role = Roles.Fournisseur ;



            u1 = new Fournisseur(
                    Registerfield2.getText(),
                    registerPass2.getText(),
                    Registerfield1.getText(),
                    Integer.parseInt(Registerfield21age.getText()),
                    Integer.parseInt(Registerfield111numero.getText()),
                    role
            );
            us.addFournisseur((Fournisseur) u1);
        } else if (rbtn2.isSelected()) {
            role = Roles.Proprietaire_de_Terrain;


            u1 = new Proprietaire_de_terrain(
                    Registerfield2.getText(),
                    registerPass2.getText(),
                    Registerfield1.getText(),
                    Integer.parseInt(Registerfield21age.getText()),
                    Integer.parseInt(Registerfield111numero.getText()),
                    role);
            us.addProprietairedeTerarin((Proprietaire_de_terrain) u1);
        } else if (rbtn3.isSelected()) {
            role = Roles.Organisateur;


            u1 = new Organisateur(
                    Registerfield2.getText(),
                    registerPass2.getText(),
                    Registerfield1.getText(),
                    Integer.parseInt(Registerfield21age.getText()),
                    Integer.parseInt(Registerfield111numero.getText()),
                    role);

            us.addOrganisateur((Organisateur)u1);
        }else{
            role = Roles.Joueur;


            u1 = new Joueur(
                    Registerfield2.getText(),
                    registerPass2.getText(),
                    Registerfield1.getText(),
                    Integer.parseInt(Registerfield21age.getText()),
                    Integer.parseInt(Registerfield111numero.getText()),
                    role);
            us.addJoueur((Joueur)u1);
        }



        cAlert.generateAlert("INFORMATION","Votre Compte a ete cree avec succès");
        

        gotoSeconnecter();
        Seconnecterfield1.setText(Registerfield2.getText());
        SeconnecterPass1.setText(registerPass2.getText());






    }



}





























