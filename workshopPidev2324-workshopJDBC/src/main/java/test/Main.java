package test;

import com.mailjet.client.errors.MailjetException;
import controllers.JavaMailJett;
import services.AvisService;
import java.sql.SQLException;

//*******************************************************************************************
public class Main{
    public static void main(String[] args) throws SQLException, MailjetException {
        AvisService as = new AvisService();
        System.out.println(as.getPhoneNumberForAvis(49));
        JavaMailJett.send("bendjemia.malek30@gmail.com","hgj");
       // smsAPi.sendSms(0,"tataaatta");
    }}