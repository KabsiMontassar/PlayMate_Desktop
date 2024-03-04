package test.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.User;
import services.GestionUser.UserService;
import test.Controllers.Common.CAlert;

import java.sql.SQLException;
import java.util.function.UnaryOperator;

public class   VerificationCodeController {

    public Text motdepasstxt1;
    public Text napasrecutext;
    public Text saissircodetext;
    public ImageView imglockid;
    public AnchorPane MainPane;
    public Text motdepasstxt2;
    public Text mettreajourtxt;
    public PasswordField passfield1;
    public PasswordField passfield2;
    public Button UpdatePassbtn;
    public TextField emailtf;
    public Text Saisissezemailtxt;
    public Button emailbtn;
    @FXML
    private Button btnrenvoyer;

    @FXML
    private TextField field1;

    @FXML
    private TextField field2;

    @FXML
    private TextField field3;

    User u = null ;
    @FXML
    private TextField field4;

    @FXML
    private TextField field5;

    @FXML
    private TextField field6;

    @FXML
    private Button verifierbtn;
    private String Code ;
    private User CurrentUser ;
    CAlert cAlert = new CAlert();



    public void initialize() throws SQLException {



        UnaryOperator<TextFormatter.Change> filterfield = change -> {
            String newText = change.getControlNewText();// and to only  and letters upper and lower
            if ( newText.length() <= 1) {
                return change;
            }
            return null;
        };
        field1.setTextFormatter(new TextFormatter<>(filterfield));
        field2.setTextFormatter(new TextFormatter<>(filterfield));
        field3.setTextFormatter(new TextFormatter<>(filterfield));
        field4.setTextFormatter(new TextFormatter<>(filterfield));
        field5.setTextFormatter(new TextFormatter<>(filterfield));
        field6.setTextFormatter(new TextFormatter<>(filterfield));

        
    }
    private int count = 1;
    @FXML
    void renvoyerCode(ActionEvent event) throws Exception {
        if(count < 5){
            count++;
          //  SMSAPI sms = new SMSAPI();
           // sms.run(String.valueOf(u.getPhone()), u.getVerificationCode());
        }else{
            cAlert.generateAlert("WARNING","Vous avez atteint le nombre maximal de tentatives");
        }

    }

    @FXML
    void verifierCode(ActionEvent event) throws SQLException {
        u = us.getByEmail(emailtf.getText());

        String inputtedCode = field1.getText() + field2.getText() + field3.getText() + field4.getText() + field5.getText() + field6.getText();

        if(!inputtedCode.equals(u.getVerificationCode())){
            System.out.println(inputtedCode);
            System.out.println(u.getVerificationCode());
            cAlert.generateAlert("WARNING","Code Invalid");
            return;
        }

            field1.setVisible(false);
            field2.setVisible(false);
            field3.setVisible(false) ;
            field4.setVisible(false);
            field5.setVisible(false);
            field6.setVisible(false);
            imglockid.setVisible(false);
            saissircodetext.setVisible(false);
            napasrecutext.setVisible(false);
            btnrenvoyer.setVisible(false);
            verifierbtn.setVisible(false);
            motdepasstxt1.setVisible(true);
            motdepasstxt2.setVisible(true);
            mettreajourtxt.setVisible(true) ;
            passfield1.setVisible(true);
            passfield2.setVisible(true);
            UpdatePassbtn.setVisible(true);







    }
    public boolean isValidPassword(String password){
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }

    UserService us = new UserService();
    @FXML
     void UpdatePassword(ActionEvent actionEvent) throws Exception {
        if(!isValidPassword(passfield1.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Mot de passe invalide");
            alert.setContentText("le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial");
            alert.setHeaderText("CAlert Alert");
            alert.showAndWait();
            passfield1.setText("");
            passfield2.setText("");

            return;

        }
        if(!passfield1.getText().equals(passfield2.getText()) ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("mots de passe ne correspondent pas");
            alert.setContentText("mot de passe et confirmer le mot de mot de passe devraient avoir le même contenu");
            alert.setHeaderText("CAlert Alert");
            alert.showAndWait();
            passfield1.setText("");
            passfield1.setText("");

            return;
        }

        us.update(new User(u.getEmail() , passfield1.getText(), u.getName() , u.getAge() , u.getPhone() , u.getRole()));
        System.out.println("password updated");
    
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();

    }

    public void verifemailbtn(ActionEvent actionEvent) throws Exception {
        if(!us.userExist(emailtf.getText())){
            cAlert.generateAlert("WARNING","Email n'existe pas");
            return;
            
        }


            u = us.getByEmail(emailtf.getText());

            Saisissezemailtxt.setVisible(false);
            emailtf.setVisible(false);
            emailbtn.setVisible(false);

            field1.setVisible(true);
            field2.setVisible(true);
            field3.setVisible(true) ;
            field4.setVisible(true);
            field5.setVisible(true);
            field6.setVisible(true);
            imglockid.setVisible(true);
            saissircodetext.setVisible(true);
            napasrecutext.setVisible(true);
            btnrenvoyer.setVisible(true);
            verifierbtn.setVisible(true);
           // SMSAPI sms = new SMSAPI();

            //sms.run(String.valueOf(u.getPhone()), u.getVerificationCode());


    }
}
