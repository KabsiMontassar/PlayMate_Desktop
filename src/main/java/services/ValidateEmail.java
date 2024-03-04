package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class ValidateEmail {

    public boolean ValideEmail(String email) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://email-validation-and-verification-end-to-end-checks.p.rapidapi.com/" + email))
                .header("X-RapidAPI-Key", "c0a2b22995msh6ce47480e2079aap18244fjsn3bce8bed76a7")
                .header("X-RapidAPI-Host", "email-validation-and-verification-end-to-end-checks.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());


        JSONObject jsonResponse = new JSONObject(response.body());



        return jsonResponse.getBoolean("can_connect_smtp");
    }

}
