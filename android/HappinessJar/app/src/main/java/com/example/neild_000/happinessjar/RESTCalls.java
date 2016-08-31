package com.example.neild_000.happinessjar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class RESTCalls {

    private Context mContext;

	public RESTCalls(Context mContext){
		this.mContext = mContext;
	}

    static public boolean isNetworkAvailable(Context mContext){

        ConnectivityManager connectionManager = (ConnectivityManager)
               mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
    //REST GET validation
    static public Boolean getJSONFromServerValidation(String url){

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        //192.168.173.1/otherbits/nyx/index.php/useremail/qwert@gmail.com

        try{
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if(statusCode == 200){
                return true;

            }

        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return false;
    }
    //REST GET
    static public String getJSONFromServer(String url){


        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try{
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            Log.i(RESTCalls.class.toString(), "status code: " + statusCode);
            if(statusCode == 200 ){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line = reader.readLine())!= null){
                    builder.append(line);
                }
            } else {
                Log.e(RESTCalls.class.toString(), "Failed achieve request");
            }

        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return builder.toString();

    }

    //REST POST
    static public String postJSONToServer(String url, JSONObject jsonObject){
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = null;
        HttpResponse httpResponse;

        String responseText = null;

        try {
            stringEntity = new StringEntity(jsonObject.toString());
            stringEntity.setContentType("application/json");
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            Log.d(RESTCalls.class.toString(), stringEntity.toString());



            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());

            return responseText;
        }catch(UnsupportedEncodingException e ){
            Log.e(RESTCalls.class.toString(), "Error in post request stringEntity" );
            e.printStackTrace();
        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return responseText;
    }

}
