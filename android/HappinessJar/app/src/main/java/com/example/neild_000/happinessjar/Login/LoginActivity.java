package com.example.neild_000.happinessjar.Login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.MainActivityFragments;
import com.example.neild_000.happinessjar.R;
import com.example.neild_000.happinessjar.RESTCalls;
import com.example.neild_000.happinessjar.RedundantCode.Card.CardActivity;
import com.example.neild_000.happinessjar.Registration.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by neild_000 on 21/01/2015.
 */
public class LoginActivity extends Activity implements AsyncResponse{

    private EditText editText_User_Email;
    private EditText editText_User_Password;

    private Login loginObj;

    private Dialog dialog;
    private TextView popup_textView;


    private Handler handler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        editText_User_Email = (EditText) findViewById(R.id.editText_registration_email);
        editText_User_Password = (EditText) findViewById(R.id.editText_registration_password);
        context = this;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if(!extras.getString("user_email").isEmpty() && extras.getString("user_email")!="" && !extras.getString("user_password").isEmpty() && extras.getString("user_password") != ""){
                editText_User_Email.setText(extras.getString("user_email"));
                editText_User_Password.setText(extras.getString("user_password"));
            }
        }
        editText_User_Email.setText("davidbkenny@gmail.com");
        editText_User_Password.setText("Balbriggan");

    }

    public void launchRegistration( View view){
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
    }

    public void loginUser( View view ){
        boolean notEmpty = false;
        if(!isEmpty(editText_User_Email) && !isEmpty(editText_User_Password)){
            loginObj = new Login();

            loginObj.setEmail(editText_User_Email.getText().toString());
            loginObj.setPassword(editText_User_Password.getText().toString());

            notEmpty = true ;


        }else {
            Log.d(LoginActivity.class.toString(), "EditText empty");
            Toast toast =  Toast.makeText(this, "All fields not set", Toast.LENGTH_LONG);
            toast.show();
        }


        if(RESTCalls.isNetworkAvailable(this) && notEmpty == true ){
            Log.i(LoginActivity.class.toString(), "Network avaialbe");

            LoginOnServer task = new LoginOnServer(this);
            task.execute();
        }else if(notEmpty == true){
            Log.d(LoginActivity.class.toString(), " Internet connection not available");
            Toast toast =  Toast.makeText(this, "Internet connection not available", Toast.LENGTH_LONG);
            toast.show();

        }


    }

    private class LoginOnServer extends AsyncTask< String, Void, String>{

        public AsyncResponse listener;

        private LoginOnServer(AsyncResponse listener) {
            Log.i(LoginOnServer.class.toString(), "in constructor");

            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {

            String response = RESTCalls.postJSONToServer( ConfigURLs.URL_LOGIN, loginObj.parseLoginToJSON() );
            Log.i(LoginOnServer.class.toString(), response);

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            listener.processFinish(response);
        }
    }

    public void processFinish(String response){
        parseResponse(response);

        showPopup(loginObj.isLogin_pass());
    }

    private void showPopup(Boolean login_pass){
        handler = new Handler();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.login_popup);
        dialog.setTitle("Login: ");
        popup_textView = (TextView) dialog.findViewById(R.id.login_popup_textview);
        if(login_pass) {
            popup_textView.setText("Login passed");
            dialog.show();
            handler.postDelayed( new Runnable(){
                @Override
                public void run() {
                    dialog.dismiss();
                    Intent i = new Intent(context, MainActivityFragments.class);
                    i.putExtra("user_id", loginObj.getUser_id());
                    startActivity(i);
                }
            }, 3000);

        }else{
            popup_textView.setText("Login failed");
            dialog.show();;

        }

    }


    private void parseResponse(String response){
        try{
            JSONObject reader = new JSONObject(response);
            if( reader.getBoolean("error") == false ){
                loginObj.setUser_id( reader.getString("user_id") );
                loginObj.setLogin_pass(true);


            }else{
                loginObj.setLogin_pass(false);
            }

        }catch(JSONException e){
            e.printStackTrace();
        }

    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

}
