package com.monte_carlo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import org.json.JSONObject;

public class FetchPrices {
    public static Double priceQuote(String ticker){
        try{
        String apiURL = "https://finnhub.io/api/v1/quote?symbol=" + ticker;
        String apiKey = "cl1vbshr01qinfqo8ligcl1vbshr01qinfqo8lj0";

        URL url = new URL(apiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Finnhub-Token", apiKey);

        int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject object = new JSONObject(response.toString());
                Double finalPrice = object.getDouble("c");

                connection.disconnect();

                return finalPrice;

            } else {
                System.out.println("Error: " + responseCode);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
