package test.Controllers.ReservationController;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.MailJettAPI;
import models.Paiement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import models.PaymentAPI;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.GestionReservation.*;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaimentController implements Initializable {


    @FXML
    private TextField CVV;

    @FXML
    private Label CVVInvalide;

    @FXML
    private TextField NOM;

    @FXML
    private Label NomInvalide;

    @FXML
    private Button P;

    @FXML
    private TextField date;

    @FXML
    private Label dateInvalide;

    @FXML
    private TextField numero;

    @FXML
    private Label numeroInvalide;


    private int idReservation;
    private int IdUser;

    public void SetIdReservation(int idReservation) {

        this.idReservation = idReservation;
    }

    public void SetIdUser(int idUser) {

        this.IdUser = idUser;
    }

    public int GetIdReservation() {
        return this.idReservation;
    }

    public int GetIdUser() {
        return this.IdUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numeroInvalide.setVisible(false);
        dateInvalide.setVisible(false);
        CVVInvalide.setVisible(false);
        NomInvalide.setVisible(false);

    }

    // verifer qu 3 nombres puis il est que nombre
    public boolean controleSaisieCVV(String cvv) {
        try {
            if (cvv.length() != 3) {
                CVVInvalide.setVisible(true);
                return false;
            }

            Integer.parseInt(cvv);
            return true;
        } catch (NumberFormatException e) {
            CVVInvalide.setVisible(true);
            return false;
        }
    }

    // Vérifie si le numéro correspond au format attendu 16 caractere
    public boolean controleSaisieNumero(String numero) {
        // Utilisez une expression régulière pour vérifier le format
        String regex = "^(\\d{4}\\s?){3}\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);
        if (matcher.matches()) {

            return true;
        } else {
            numeroInvalide.setVisible(true);
            return false;
        }

    }

    // verifer la date d expiration  mettre sous la forme mm/yy puis verfier le code n est pas au passe / puis ne depasse pas 4 ans
    public boolean controleSaisieDateCarteBancaire(String dateExprisation) {

        try {
            YearMonth dateActuelle = YearMonth.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth dateCarte = YearMonth.parse(dateExprisation, formatter);

            if (dateCarte.isBefore(dateActuelle)) {
                dateInvalide.setVisible(true);
                return false;
            }

            YearMonth dateLimite = dateActuelle.plusYears(4);
            if (dateCarte.isAfter(dateLimite)) {
                dateInvalide.setVisible(true);
                return false;
            }
            return true;
        } catch (Exception e) {
            dateInvalide.setVisible(true);
            return false;
        }
    }

    // qu des caractere de letres
    public boolean controleSaisieNomProprietaire(String nomProprietaire) {
        if (nomProprietaire.matches("^[a-zA-Z\\s]+$")) {
            return true;
        } else {
            NomInvalide.setVisible(true);
            return false;
        }
    }



    public Paiement creerPaiment() throws SQLException {

        LocalDate dateCourrant = LocalDate.now();
        LocalTime timeCourrant = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = dateCourrant.format(formatter);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        String heureEnString = timeCourrant.format(formatter2);


        Paiement paiement = new Paiement(this.GetIdUser(),this.GetIdReservation(), dateString, heureEnString , " ");

        return  paiement ;

    }


    public void appelPaymentAPI(int prix) throws SQLException {
        PaymentAPI paymentAPI = new PaymentAPI();
        try {
        PaiementService paiementService = new PaiementService();

            Paiement paiement = new Paiement();
            paiement = creerPaiment();

            // Initialise le paiement
            String response = paymentAPI.initPayment(paiement,prix);  // null

            // Récupère l'URL de paiement
            String payUrl = paymentAPI.extractPayUrlFromResponse(response);

            if (Desktop.isDesktopSupported() && !payUrl.isEmpty()) {
                try {
                    // Ouvre l'URL de paiement dans le navigateur
                    Desktop.getDesktop().browse(new URI(payUrl));

                    // Attendez un certain temps pour que l'utilisateur effectue le paiement
                    // Vous pouvez ajuster cette valeur en fonction de vos besoins
                    Thread.sleep(1 * 60 * 1000); // Attend 1 min (par exemple)

                    // Vérifie si le paiement est réussi en utilisant la référence de paiement
                    boolean paymentSuccessful = paymentAPI.isPaymentSuccessful();
                    System.out.println("Payment Successful: " + paymentSuccessful);

                    if (paymentSuccessful) {
                        // Récupère la référence de paiement
                        String paymentRef = paymentAPI.getPaymentRef();
                        System.out.println("Payment Reference: " + paymentRef);


                        paiementService.AjouterPaiement(paiement,paymentRef);
                        MailJettAPI mailJettAPI = new MailJettAPI();
                        mailJettAPI.send("aziztaraji1@gmail.com","playmatepidev@gmail.com", paymentRef);

                    } else {

                        System.out.println("erreur de paiemnt");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Gérez l'erreur, éventuellement montrez une alerte à l'utilisateur
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'erreur liée à la base de données, éventuellement montrez une alerte à l'utilisateur
        }
    }

/*    pdf noussayba
    @FXML
    public void pdf(ActionEvent actionEvent) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            float y = 700; // Starting Y-coordinate

            // Reservation Details (Red and Centered)
            contentStream.setNonStrokingColor(255, 0, 0); // Red color
            String text = "Reservation Details:";
            float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000f * 12;
            float startX = (page.getMediaBox().getWidth() - textWidth) / 2; // Centered
            contentStream.newLineAtOffset(startX, y);
            contentStream.showText(text);
            contentStream.newLineAtOffset(-startX, 0); // Adjust for centering
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // Reset color to default (black)
            contentStream.setNonStrokingColor(0, 0, 0);

            // Name
            contentStream.showText("Name: " + username.getText());
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // Email
            contentStream.showText("Email: " + email.getText());
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // Number of Places
            contentStream.showText("Number of Places: " + nb.getText());
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // Event Name
            contentStream.showText("Event Name: " + nameE.getText());
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // Montant
            contentStream.showText("Montant: " + montant.getText());
            y -= 20; // Move to the next line
            contentStream.newLineAtOffset(0, -20);

            // Heure
            contentStream.showText("Heure: " + heure.getText());
            y -= 20; // Move to the next line

            contentStream.endText();
            contentStream.close();

            document.save("Reservation_Details.pdf");
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("PDF file generated successfully!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while generating PDF file!");
            alert.showAndWait();
        }
    }
*/
}


/*     maadch button tw bech nrj3 payment*/
/*    public void payer() throws SQLException {

        if (controleSaisieCVV(CVV.getText()) && controleSaisieNumero(numero.getText()) && controleSaisieNomProprietaire(NOM.getText()) && controleSaisieDateCarteBancaire(date.getText())) {
            LocalDate dateCourrant = LocalDate.now();
            LocalTime timeCourrant = LocalTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = dateCourrant.format(formatter);

            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
            String heureEnString = timeCourrant.format(formatter2);


            Paiement paiement = new Paiement(8,this.GetIdReservation(), dateString, heureEnString , " ");

      }
    }
*/