package com.example.kamakshipt2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.verify.VerifyClient;
import com.nexmo.client.verify.VerifyRequest;
import com.nexmo.client.verify.VerifyResponse;
import com.nexmo.client.verify.VerifyStatus;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NexmoClient client = new NexmoClient.Builder()
                .apiKey("756db2f8")
                .apiSecret("xo02qQsPsnPDbjTF")
                .build();
        VerifyClient verifyClient = client.getVerifyClient();
        VerifyRequest request = new VerifyRequest("16476248004", "Vonage");
        request.setLength(4);

        VerifyResponse response = null;
        try {
            response = verifyClient.verify(request);
            if (response.getStatus() == VerifyStatus.OK) {
                System.out.printf("RequestID: %s", response.getRequestId());
            } else {
                System.out.printf("ERROR! %s: %s",
                        response.getStatus(),
                        response.getErrorText()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NexmoClientException e) {
            e.printStackTrace();
        }


    }
}