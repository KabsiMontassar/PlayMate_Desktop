package models;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import services.GestionReservation.HistoriqueService;
import services.GestionReservation.PaiementService;
import services.GestionUser.UserService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class PaymentAPI {

    private final String API_URL = "https://api.preprod.konnect.network/api/v2/payments/init-payment";
    private final String API_KEY = "65e2061d0ed588b99337c12b:lfeWlefzQi2IKdIYGZa5vjfCh9jDdbR";
    private String paymentRef = "";


    public String initPayment(Paiement paiement ,int prix) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("x-api-key", API_KEY);

            String json = buildJsonPayload(paiement ,  prix);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);

            var response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            paymentRef = extractpaymentRef(responseString);
            System.out.println(responseString);
            return responseString; // Or parse this JSON to extract the payment URL
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Or handle the error appropriately
        }
    }




    public String extractpaymentRef(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.optString("paymentRef", "");
    }

    public String extractPayUrlFromResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.optString("payUrl", "");         // voir
    }

    private String buildJsonPayload(Paiement paiement ,int prix ) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        // Construct the JSON payload based on Paiement object properties
        // Example payload. Adapt fields according to your Paiement properties and API requirements

        User user = new User();
        UserService userService = new UserService();
        user = userService.getByid(paiement.getIdmembre());
        PaiementService paiementService = new PaiementService();
        Terrain terrain = paiementService.getTerrainByIdPayment(paiement.getIdPaiement());

        int price = prix*1000;
        return "{"
                + "\"receiverWalletId\": \"65e2061d0ed588b99337c12f\","
                + "\"token\": \"TND\","
                + "\"amount\": " + price + "," // Convert to Millimes if TND, for example
                + "\"description\": \"Payment for " + "playmate" + "\","
                + "\"acceptedPaymentMethods\": [\"wallet\",\"bank_card\"],"
              + "\"firstName\": \""+ user.getName() +"\","
              + "\"lastName\": \"NoLastName\","
               + "\"email\": \""+ user.getEmail()+"\","
              + "\"orderId\": \"" +658435 + "\""
                + "}";

    }

    public String getPaymentRef() {
        return paymentRef;
    }

    public boolean isPaymentSuccessful() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String apiUrl = "https://api.preprod.konnect.network/api/v2/payments/" + paymentRef;
            HttpGet httpGet = new HttpGet(apiUrl);

            HttpResponse response = client.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                JSONObject jsonResponse = new JSONObject(responseString);

                // Vérifier si la transaction est réussie
                int successfulTransactions = jsonResponse.getJSONObject("payment").getInt("successfulTransactions");
                return successfulTransactions > 0;
            } else {
                System.err.println("Échec de la requête. Code d'erreur : " + response.getStatusLine().getStatusCode());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

 /*

    public String initPayment(Paiement paiement) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("x-api-key", API_KEY);


            String json = buildJsonPayload(paiement);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);

            var response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            paymentRef = extractpaymentRef(responseString);
            System.out.println(responseString);
            return responseString; // Or parse this JSON to extract the payment URL
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Or handle the error appropriately
        }
    }


     */