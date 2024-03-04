package services;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class ValidPhoneNumber {
        public static boolean validateNumber(String phoneNumber) throws IOException, InterruptedException {




            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://phonenumbervalidatefree.p.rapidapi.com/ts_PhoneNumberValidateTest.jsp?number=%2B216" + phoneNumber + "&country=TN"))
                    .header("X-RapidAPI-Key", "c0a2b22995msh6ce47480e2079aap18244fjsn3bce8bed76a7")
                    .header("X-RapidAPI-Host", "phonenumbervalidatefree.p.rapidapi.com")
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            return jsonResponse.getBoolean("isValidNumber");
        }
    }


