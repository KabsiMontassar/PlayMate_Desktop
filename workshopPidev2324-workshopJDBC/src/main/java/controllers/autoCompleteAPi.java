package controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
//*******************************************************************************************
public class autoCompleteAPi {
    public static List<String> autoCompleteAddress(String query) {
        List<String> addresses = new ArrayList<>();
        try {
            String url = "https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(query, "UTF-8") + "&format=json";
            String response = sendGetRequest(url);
            JSONArray jsonResponse = new JSONArray(response);
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject address = jsonResponse.getJSONObject(i);
                addresses.add(address.getString("display_name"));}
        } catch (Exception e) {e.printStackTrace();}
        return addresses;}
    //*******************************************************************************************
    public static String sendGetRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {response.append(inputLine);}}
        return response.toString();}}