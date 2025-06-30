package com.yourapp.sdk;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PipraPay {
    private String apiKey;
    private String baseUrl;
    private String currency;

    public interface Callback {
        void onSuccess(Map<String, Object> response);
        void onFailure(String error);
    }

    public PipraPay(String apiKey, String baseUrl, String currency) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl.replaceAll("/+$", "");
        this.currency = currency;
    }

    public void createCharge(Map<String, Object> data, Callback callback) {
        data.put("currency", currency);
        post("/api/create-charge", data, callback);
    }

    public void verifyPayment(String ppId, Callback callback) {
        Map<String, Object> data = new HashMap<>();
        data.put("pp_id", ppId);
        post("/api/verify-payments", data, callback);
    }

    private void post(String endpoint, Map<String, Object> data, Callback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(baseUrl + endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("accept", "application/json");
                conn.setRequestProperty("mh-piprapay-api-key", apiKey);
                conn.setDoOutput(true);

                JSONObject jsonInput = new JSONObject(data);
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(jsonInput.toString().getBytes());
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                in.close();

                JSONObject responseJson = new JSONObject(result.toString());
                Map<String, Object> responseMap = jsonToMap(responseJson);
                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(responseMap));
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onFailure(e.getMessage()));
            }
        }).start();
    }

    private Map<String, Object> jsonToMap(JSONObject json) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = json.get(key);
            if (value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }
}
