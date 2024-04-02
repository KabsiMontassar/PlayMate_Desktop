package test.Controllers.TerrainController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class smsAPi {

    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
    public static void sendSms(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+"), // verifie
                new PhoneNumber("+"),//virtuel
                "Vous avez reçu un nouvel avis sur votre terrain"
        ) .create();

        System.out.println("SMS Sent with SID: " + message.getSid());
    }
}
