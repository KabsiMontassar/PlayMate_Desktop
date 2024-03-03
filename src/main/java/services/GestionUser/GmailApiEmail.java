//package services.GestionUser;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;
//
//public class GmailApiEmail {
//    private final String username;
//    private final String password;
//    private Session session;
//
//   // kabsi1.montassar1@gmail.com
////montassarmcree&12340066
//    public GmailApiEmail(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    public void send(String email, String subject, String content) {
//        if (hasSession()) {
//            try {
//                Transport.send(createMimeMessage(email, session, subject, content));
//                System.out.printf("Email sent to %s%n", email);
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private boolean hasSession() {
//        if (session == null) {
//            session = startSessionTLS();
//        }
//        return true;
//    }
//
//    private Session startSessionTLS() {
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//
//        return Session.getInstance(props, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//    }
//
//    private MimeMessage createMimeMessage(String email, Session session, String subject, String body) throws MessagingException {
//        MimeMessage message = new MimeMessage(session);
//        message.setFrom(new InternetAddress("noreply@email.com"));
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
//        message.setSubject(subject);
//        message.setText(body);
//        return message;
//    }
//}
