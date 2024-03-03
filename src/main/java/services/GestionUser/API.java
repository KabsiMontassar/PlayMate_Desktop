//package services.GestionUser;
//
//import com.mailjet.client.ClientOptions;
//import com.mailjet.client.MailjetClient;
//import com.mailjet.client.MailjetRequest;
//import com.mailjet.client.MailjetResponse;
//import com.mailjet.client.errors.MailjetException;
//import com.mailjet.client.errors.MailjetSocketTimeoutException;
//import com.mailjet.client.resource.SMSMessages;
//
//public class APIJAVA {
//    public static void main(String[] args) {
//        // Remplacez 'YOUR_API_KEY' et 'YOUR_API_SECRET' par vos clés API
//        String apiKey = "YOUR_API_KEY";
//        String apiSecret = "YOUR_API_SECRET";
//
//        // Créez un client Mailjet
//        MailjetClient client = new MailjetClient(apiKey, apiSecret, new ClientOptions("v4"));
//
//        // Créez une requête pour envoyer un SMS
//        MailjetRequest request = new MailjetRequest(SMSMessages.resource)
//                .property(SMSMessages.FROM, "Your Sender Name")
//                .property(SMSMessages.TO, "Recipient Phone Number")
//                .property(SMSMessages.TEXT, "Hello, this is a test SMS from Mailjet!");
//
//        try {
//            // Exécutez la requête pour envoyer le SMS
//            MailjetResponse response = client.post(request);
//            System.out.println(response.getStatus());
//            System.out.println(response.getData());
//        } catch (MailjetException e) {
//            e.printStackTrace();
//
//        }
//    }
//}