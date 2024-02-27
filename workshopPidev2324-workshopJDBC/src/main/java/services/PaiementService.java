package services;

import com.alibaba.fastjson.JSONObject;
import models.Paiement;
import models.PaymentRequest;
import utils.MyDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaiementService {
    private Connection connection;
    public PaiementService(){
        connection = MyDatabase.getInstance().getConnection();
    }
/*

    private static final String API_BASE_URL = "https://api.preprod.konnect.network/api/v2/";
    private static final String API_KEY = "65db3ac24e8dd3df3f9691d4:gLTHG7FIRGzdO8QLe5";

    public String initiatePayment(PaymentRequest paymentRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), paymentRequest.toJson().toString());
        Request request = new Request.Builder()
                .url(API_BASE_URL + "payments/init-payment")
                .addHeader("x-api-key", API_KEY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                return jsonResponse.getString("payUrl");
            }
        }

        return null;
    }



 */

    public boolean AjouterPaiement(Paiement paiement) throws SQLException {
        String query = "INSERT INTO Payment (idMembre, idReservation, datePayment, horairePayment,Payed) VALUES (?,?,?,?,?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, paiement.getIdmembre());
        ps.setInt(2, paiement.getIdreservation());
        ps.setString(3, paiement.getDate());
        ps.setString(4, paiement.getHeure());
        ps.setBoolean(5,false);

        ps.executeUpdate();
        int lignesAffectees = ps.executeUpdate();

        return lignesAffectees > 0;


    }
    public boolean confirmerPaiment(int idpaiement){
        String query = "UPDATE payment SET Payed = TRUE WHERE idPayment = ?;";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, idpaiement);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
