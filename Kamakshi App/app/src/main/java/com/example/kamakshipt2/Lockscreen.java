package com.example.kamakshipt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Lockscreen extends AppCompatActivity {

    public static String makePostRequest(String url, HashMap<String, String> params) throws IOException, InterruptedException {
        String form = params.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(params.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        System.out.println(form);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);
    }

    public void mainActivity(View v)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public static void main(String[] args) throws IOException, InterruptedException {

        String API_KEY = "384b5be7";
        String API_SECRET = "GSnccM860MTtiY98";
        String number = "17787754444";
        String brand = "Acme Inc";

        HashMap<String, String> parameters = new HashMap<>() {{
            put("api_key", API_KEY);
            put("api_secret", API_SECRET);
            put("number", number);
            put("brand", brand);
        }};

        String verifyEndpoint = "https://api.nexmo.com/verify/json";

        String response = makePostRequest(verifyEndpoint, parameters);

        JSONObject obj = new JSONObject(response);
        String requestId = obj.getString("request_id");
        System.out.println(requestId);

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a code");
        String code = sc.nextLine();

        String checkCodeEndpoint = "https://api.nexmo.com/verify/check/json";
        parameters = new HashMap<String, String>() {{
            put("api_key", API_KEY);
            put("api_secret", API_SECRET);
            put("request_id", requestId);
            put("code", code);
        }};

        response = makePostRequest(checkCodeEndpoint, parameters);
        System.out.println(response);
    }
}

}