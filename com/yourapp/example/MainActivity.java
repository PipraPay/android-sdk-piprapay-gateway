package com.yourapp.example;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.yourapp.sdk.PipraPay;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PipraPay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PipraPay pipraPay = new PipraPay("YOUR_API_KEY", "https://sandbox.piprapay.com", "BDT");

        Map<String, Object> data = new HashMap<>();
        data.put("full_name", "Test User");
        data.put("email_mobile", "test@example.com");
        data.put("amount", "10");
        data.put("redirect_url", "https://example.com/success");
        data.put("cancel_url", "https://example.com/cancel");
        data.put("webhook_url", "https://example.com/ipn");

        pipraPay.createCharge(data, new PipraPay.Callback() {
            @Override
            public void onSuccess(Map<String, Object> response) {
                Log.d(TAG, "Success: " + response);
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, "Error: " + error);
            }
        });
    }
}
