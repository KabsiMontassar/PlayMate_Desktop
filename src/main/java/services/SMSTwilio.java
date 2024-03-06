package services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import test.Controllers.UserController.CAlert;

import java.io.IOException;

public class SMSTwilio {



        public static final String ACCOUNT_SID = "AC12eed8a71e731ee34e7436a42dfdb038";
        public static final String AUTH_TOKEN = "e129bbb54e4b2a90529c6bd97603fc5c";

        static {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        }
        public static void sendSms(int toPhoneNumber, String messageBody) throws IOException, InterruptedException {

            CAlert c = new CAlert() ;
            if(!ValidPhoneNumber.validateNumber(String.valueOf(toPhoneNumber))){
                c.generateAlert("WARNING","Vous n'êtes pas en mesure de recevoir des sms car votre adresse numero n'existe pas.");
                return ;
            }
            System.out.println("Code sent using twilio"+ messageBody);
//            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//            Message message = Message.creator(
//
//
//                   // toPhoneNumber = 28153435;
//                    new PhoneNumber("+21628153435"),
//                    new PhoneNumber("+15169792720"),
//                    messageBody
//            ) .create();
//
//            System.out.println("SMS Sent with SID: " + message.getSid());
        }
    }

