package services;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import test.Controllers.UserController.CAlert;

import java.io.IOException;

public class JavaMailJett {
    public static void send(String recipient, String senderr,String Code ) throws MailjetException, IOException, InterruptedException {
        ValidateEmail Ve = new ValidateEmail();
CAlert c = new CAlert() ;
        if(!Ve.ValideEmail(recipient)){
            c.generateAlert("WARNING","Vous n'êtes pas en mesure de recevoir des emails car votre adresse e-mail n'existe pas.");
            return ;
        }
        final String mailjetApiKey = "dc4dcc4442be8f232d88ae3820edc0f5";
        final String mailjetSecretKey = "52ff90665f96dfecf6f22f6bb1efa0df";
        ClientOptions options =
                ClientOptions.builder().apiKey(mailjetApiKey).apiSecretKey(mailjetSecretKey).build();
        MailjetClient client = new MailjetClient(options);

        JavaMailJett sender = new JavaMailJett();
        sender.sendMailjet(recipient, senderr , Code , client);
    }

    public void sendMailjet(String recipient, String sender , String Code, MailjetClient client) {
        MailjetRequest email =
                new MailjetRequest(Emailv31.resource)
                        .property(
                                Emailv31.MESSAGES,
                                new JSONArray()
                                        .put(
                                                new JSONObject()
                                                        .put(
                                                                Emailv31.Message.FROM,
                                                                new JSONObject().put("Email", sender).put("Name", "PlayMate"))
                                                        .put(
                                                                Emailv31.Message.TO,
                                                                new JSONArray().put(new JSONObject().put("Email", recipient)))
                                                        .put(Emailv31.Message.SUBJECT, "notification de Playmate.!")
                                                        .put(
                                                                Emailv31.Message.TEXTPART,
                                                                "Chers clients, bienvenue à PlayMate!"
                                                                        +  Code)
                                                        .put(
                                                                Emailv31.Message.HTMLPART,
                                                                "<h3>Chers clients, bienvenue à PlayMate!</h3>"
                                                                        + "<br /> <h3> " + Code + "</h3>")));

        try {
            MailjetResponse response = client.post(email);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        } catch (MailjetException e) {
            System.out.println("Mailjet Exception: "+ e );
        }
    }
}