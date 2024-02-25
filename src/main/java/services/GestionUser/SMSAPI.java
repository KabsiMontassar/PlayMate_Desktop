package services.GestionUser;



import com.sinch.sdk.SinchClient;
import com.sinch.sdk.domains.sms.models.BatchText;
import com.sinch.sdk.domains.sms.models.requests.SendSmsBatchTextRequest;
import com.sinch.sdk.models.Configuration;
import com.sinch.sdk.models.SMSRegion;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;
public class SMSAPI {
    private static final Logger LOGGER = Logger.getLogger(SMSAPI.class.getName());

    public SMSAPI() throws IOException {}

    public static void main(String[] args) {
        try {
            String recipientPhoneNumber = "RECIPIENT_PHONE_NUMBER";
            String messageBody = "This is a test SMS message using the Sinch Java SDK.";


            new SMSAPI().run(recipientPhoneNumber, messageBody);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            e.printStackTrace();
        }
    }


    public void run(String recipientPhoneNumber, String messageBody) {

//        String senderPhoneNumber = "28153435"; // Constant sender phone number
//
//        SinchClient client = new SinchClient(Configuration.builder()
//                .setKeyId("b4b6dc23-3a41-445a-aa72-dd90c3573619")
//                .setKeySecret("dkmUVvomAZdJsaZm--SHBek8AC")
//                .setProjectId("70f05c72-e4b8-4738-b2dc-dab477ac431d")
//                .setSmsRegion(SMSRegion.US)
//                .build());
//
//
//        LOGGER.info("Send Text");
//        BatchText value =
//                client
//                        .sms()
//                        .batches()
//                        .send(
//                                SendSmsBatchTextRequest.builder()
//                                        .setTo(Collections.singletonList(recipientPhoneNumber))
//                                        .setBody(messageBody)
//                                        .setFrom(senderPhoneNumber)
//                                        .build());
//
//        LOGGER.info("Response: " + value);
        System.out.println( "header : message sent to " + recipientPhoneNumber + " body : code ->" + messageBody);
    }



}