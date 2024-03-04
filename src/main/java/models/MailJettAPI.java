package models;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
public class MailJettAPI {

    public static void send(String recipient, String senderr,String Code) throws MailjetException {
        final String mailjetApiKey = "dc4dcc4442be8f232d88ae3820edc0f5";
        final String mailjetSecretKey = "52ff90665f96dfecf6f22f6bb1efa0df";
        ClientOptions options =
                ClientOptions.builder().apiKey(mailjetApiKey).apiSecretKey(mailjetSecretKey).build();
        MailjetClient client = new MailjetClient(options);

        MailJettAPI sender = new MailJettAPI();
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
                                                        .put(Emailv31.Message.SUBJECT, "Confirmation de payment !")
                                                        .put(
                                                                Emailv31.Message.TEXTPART,
                                                                "Chers clients, bienvenue à PlayMate!"
                                                                        + "\n" +
                                                                        "Nous avons le plaisir de vous confirmer votre réservation!" + Code)
                                                        .put(
                                                                Emailv31.Message.HTMLPART,
                                                                "<h3>Chers clients, bienvenue à PlayMate!</h3>"
                                                                        + "<br />\n" +
                                                                        "Nous avons le plaisir de vous confirmer votre réservation ! <h3> " + Code + "</h3>")));

        try {
            // trigger the API call
            MailjetResponse response = client.post(email);
            // Read the response data and status
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        } catch (MailjetException e) {
            System.out.println("Mailjet Exception: "+ e );
        }
    }

}
