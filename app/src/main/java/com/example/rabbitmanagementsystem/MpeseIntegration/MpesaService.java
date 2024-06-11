package com.example.witxsalon.MpeseIntegration;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Base64;

public class MpesaService {

    private OkHttpClient client = new OkHttpClient();

    public void generateAccessToken(Callback callback) {
        String keys = MpesaConfig.CONSUMER_KEY + ":" + MpesaConfig.CONSUMER_SECRET;
        String encodedKeys = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encodedKeys = Base64.getEncoder().encodeToString(keys.getBytes());
        }

        Request request = new Request.Builder()
                .url(MpesaConfig.BASE_URL + "oauth/v1/generate?grant_type=client_credentials")
                .get()
                .addHeader("Authorization", "Basic " + encodedKeys)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
