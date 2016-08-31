package com.example.neild_000.happinessjar.WriteUpPrototypes;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;


public class HTTPNotAsync extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url = new URL("192.168.173.1/otherbits/nyx/server/index.php/getuserid/1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            
            Log.i(HTTPNotAsync.class.toString(), con.getInputStream().toString());

            } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
