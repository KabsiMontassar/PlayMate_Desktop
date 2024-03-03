//package services.GestionUser;
//
//import com.google.api.client.googleapis.json.GoogleJsonError;
//import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.client.http.HttpRequestInitializer;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.services.gmail.Gmail;
//import com.google.api.services.gmail.GmailScopes;
//import com.google.api.services.gmail.model.Message;
//import com.google.auth.http.HttpCredentialsAdapter;
//import com.google.auth.oauth2.GoogleCredentials;
//import org.apache.commons.codec.binary.Base64;
//
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Properties;
//
///* Class to demonstrate the use of Gmail Create Email API  */
//public class SendEmail {
//    public static HttpRequestInitializer createRequestInitializer() throws IOException {
//        // Path to your JSON key file
//        String credentialsPath = "C://Users//admin//Desktop//clegoogle//aqueous-vial-413318-a9137025d6aa.json";
//
//        // Load credentials from JSON key file
//        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath))
//                .createScoped(GmailScopes.GMAIL_SEND);
//
//        // Create HttpRequestInitializer using the loaded credentials
//        return new HttpCredentialsAdapter(credentials);
//    }
//    public static Message sendEmail(
//                                    String toEmailAddress)
//            throws MessagingException, IOException {
//
//        String fromEmailAddress = "playmatepidev@gmail.com";
//        /* Load pre-authorized user credentials from the environment.
//           TODO(developer) - See https://developers.google.com/identity for
//            guides on implementing OAuth2 for your application.*/
//
//        HttpRequestInitializer requestInitializer = createRequestInitializer();
//
//        // Create the gmail API client
//        Gmail service = new Gmail.Builder(new NetHttpTransport(),
//                GsonFactory.getDefaultInstance(),
//                requestInitializer)
//                .setApplicationName("Gmail samples")
//                .build();
//
//        // Create the email content
//        String messageSubject = "Test message";
//        String bodyText = "lorem ipsum.";
//
//        // Encode as MIME message
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//        MimeMessage email = new MimeMessage(session);
//        email.setFrom(new InternetAddress(fromEmailAddress));
//        email.addRecipient(javax.mail.Message.RecipientType.TO,
//                new InternetAddress(toEmailAddress));
//        email.setSubject(messageSubject);
//        email.setText(bodyText);
//
//        // Encode and wrap the MIME message into a gmail message
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        email.writeTo(buffer);
//        byte[] rawMessageBytes = buffer.toByteArray();
//        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
//        Message message = new Message();
//        message.setRaw(encodedEmail);
//
//        try {
//            // Create send message
//            message = service.users().messages().send("me", message).execute();
//            System.out.println("Message id: " + message.getId());
//            System.out.println(message.toPrettyString());
//            return message;
//        } catch (GoogleJsonResponseException e) {
//            // TODO(developer) - handle error appropriately
//            GoogleJsonError error = e.getDetails();
//            if (error.getCode() == 403) {
//                System.err.println("Unable to send message: " + e.getDetails());
//            } else {
//                throw e;
//            }
//        }
//        return null;
//    }
//}