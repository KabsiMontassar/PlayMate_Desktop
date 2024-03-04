package controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class smsAPi {

    public static final String ACCOUNT_SID = "AC3b86181a71ed65d3d7b62c900fa844a0";
    public static final String AUTH_TOKEN = "cc3cc54215e06d8e4e04a12b73e33055";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
    public static void sendSms(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+21626564986"), // verifie
                new PhoneNumber("+19497102455"),//virtuel
                "Vous avez reçu un nouvel avis sur votre terrain"
        ) .create();

        System.out.println("SMS Sent with SID: " + message.getSid());
    }
}