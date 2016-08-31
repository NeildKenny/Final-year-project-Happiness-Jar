package com.example.neild_000.happinessjar.Registration;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.Login.AsyncResponse;
import com.example.neild_000.happinessjar.Login.LoginActivity;
import com.example.neild_000.happinessjar.R;
import com.example.neild_000.happinessjar.RESTCalls;

import org.json.JSONException;
import org.json.JSONObject;


public class RegistrationActivity extends ActionBarActivity implements AsyncResponse {

    private EditText editText_name;
    private EditText editText_password;
    private EditText editText_email;
    private EditText editText_dob;

    private TextView testingTextview;

    private RadioGroup radioGroupGender;
    private RadioButton radioButtonGender;

    private Registration registrationObj;


   /* private String server_url = "http://192.168.173.1/otherbits/nyx/index.php";
    private String registration_url = "/registeruser/";
    private String email_validation_url = "/useremail/";*/

    private Handler handler;
    private Context context;
    private Dialog dialog;
    private TextView popup_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        context = this;

        editText_name = (EditText) findViewById(R.id.registration_name_editText);
        editText_password = (EditText) findViewById(R.id.registration_password_editText);
        editText_email = (EditText) findViewById(R.id.registration_email_editText);
        editText_dob = (EditText) findViewById(R.id.registration_dob_editText);

        editText_name.setText("Neil");
        editText_dob.setText("16021990");
        editText_email.setText("qwert@gmail.com");
        editText_password.setText("qwert");

        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
       // testingTextview = (TextView) findViewById(R.id.textView5);

    }


    public void  launchLogin(View view){
        Intent i = new Intent();
        startActivity(i);
    }

    /*This could be made a lot smaller*/
    public void registerUser(View view){

        registrationObj = new Registration();


        if(RESTCalls.isNetworkAvailable(this)) {
            Log.i(RegistrationActivity.class.toString(), "Networks available");
            int selectedRadioButton = radioGroupGender.getCheckedRadioButtonId();
            radioButtonGender = ( RadioButton ) findViewById(selectedRadioButton);
            if (registrationObj.validateEmailAddress(editText_email.getText().toString())) {
                if (radioButtonGender.isChecked() && !isEmpty(editText_name) && !isEmpty(editText_password) && !isEmpty(editText_email)
                        && !isEmpty(editText_dob)) {

                    registrationObj.setName(editText_name.getText().toString());
                    registrationObj.setPassword(editText_password.getText().toString());
                    registrationObj.setEmail(editText_email.getText().toString());
                    registrationObj.setDOB(Integer.parseInt(editText_dob.getText().toString()));
                    registrationObj.setGender(GenderFromRadioButton(radioButtonGender));
                    Log.i(RegistrationActivity.class.toString(), "Registration data set");

                    PostRegistration task = new PostRegistration(this);
                    task.execute();
                } else {
                    Toast toast =Toast.makeText(this, "Some details not set.", Toast.LENGTH_LONG);
                    toast.show();

                }

            } else {
                Toast toast = Toast.makeText(this, "Email invalid", Toast.LENGTH_LONG);
                toast.show();
            }
        }else{
            Log.d(RegistrationActivity.class.toString(), "Networks not available");
            Toast toast = Toast.makeText(this, "Networks not available", Toast.LENGTH_LONG);
            toast.show();

        }

    }





    private class PostRegistration extends AsyncTask< String, Void, String >{

        public AsyncResponse listener;

        private PostRegistration(AsyncResponse listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {

            String email_validation_response = RESTCalls.getJSONFromServer( ConfigURLs.URL_CHECK_EMAIL + registrationObj.getEmail());
            Log.i( RegistrationActivity.class.toString(), "Validation url:"+ ConfigURLs.URL_CHECK_EMAIL + registrationObj.getEmail() );

            Log.i( RegistrationActivity.class.toString(), "Validation response:"+ email_validation_response );
            registrationObj.parseEmailValidation( email_validation_response );

            if( !registrationObj.isDoes_user_exist() ){
                Log.i(RegistrationActivity.class.toString(), "User doesnt exist posting registration data");
                String response = RESTCalls.postJSONToServer(ConfigURLs.URL_REGISTRATION, registrationObj.parseToJSON());
                return response;
            }else{
                return email_validation_response;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            listener.processFinish(response);
        }
    }

    @Override
    public void processFinish(String response) {
        parseResponse(response);

        showPopup( registrationObj.isRegistration_pass() );
    }


    private void parseResponse(String response){
        try{
            JSONObject reader = new JSONObject(response);
            if(registrationObj.isDoes_user_exist() == false){
                Log.i(RegistrationActivity.class.toString(), reader.getString("error_message"));

                registrationObj.setUser_id( reader.getString("user_id") );
                registrationObj.setRegistration_pass(true);
                registrationObj.setEmail_error_message(reader.getString("error_message"));

                Log.i(RegistrationActivity.class.toString(), "Registration passed");
            }else{
                Log.i(RegistrationActivity.class.toString(), reader.getString("error_message"));

                registrationObj.setRegistration_pass(false);
                registrationObj.setEmail_error_message(reader.getString("error_message"));
                Log.i(RegistrationActivity.class.toString(), "Parse response: registration didnt pass");

            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void showPopup( Boolean regisrtation_pass ){
        handler = new Handler();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.login_popup);
        dialog.setTitle("Registration ");
        popup_textView = (TextView) dialog.findViewById(R.id.login_popup_textview);
        if(regisrtation_pass) {
            popup_textView.setText("Registration passed");
            dialog.show();
            handler.postDelayed( new Runnable(){
                @Override
                public void run() {
                    dialog.dismiss();
                    Intent i = new Intent(context, LoginActivity.class);
                    i.putExtra( "user_id", registrationObj.getUser_id() );
                    i.putExtra( "user_email", registrationObj.getEmail() );
                    i.putExtra( "user_password", registrationObj.getPassword());
                    startActivity(i);
                }
            }, 3000);

        }else{
            popup_textView.setText("registration failed");
            Log.e(RegistrationActivity.class.toString(), registrationObj.getEmail_error_message() );
            dialog.show();;

        }

    }

    private char GenderFromRadioButton(RadioButton genderRadioButton){
        if(genderRadioButton == findViewById(R.id.radioButton_male)){
            return 'M';
        }else if(genderRadioButton == findViewById(R.id.radioButton_female)){
            return 'F';
        }else return 'N';
    }


    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}
