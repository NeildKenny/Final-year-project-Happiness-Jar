package com.example.neild_000.happinessjar.Registration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by neild_000 on 21/01/2015.
 *
 *
 */
public class Email {

    private static Pattern email_pattern;
    private static Matcher email_matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private boolean email_validation;

    public boolean isDoes_user_exist() {
        return does_user_exist;
    }

    public void setDoes_user_exist(boolean does_user_exist) {
        this.does_user_exist = does_user_exist;
    }

    private boolean does_user_exist;

    private String email_error_message;

    public String getEmail_error_message() {
        return email_error_message;
    }

    public void setEmail_error_message(String email_error_message) {
        this.email_error_message = email_error_message;
    }

    public boolean isEmail_validation() {
        return email_validation;
    }

    public void setEmail_validation(boolean email_validation) {
        this.email_validation = email_validation;
    }

    public void parseEmailValidation(String response){

        try{
            JSONObject jsonObject = new JSONObject(response);

            email_validation = jsonObject.getBoolean("error");
            does_user_exist = jsonObject.getBoolean("does_exist");
            email_error_message = jsonObject.getString("error_message");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public JSONObject parseEmailToJson(String email){
        JSONObject jsonObj = new JSONObject();

        try{
            jsonObj.put("email", email);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return jsonObj;
    }

    public boolean validateEmailAddress(String email_address){
        if(email_address != null) {
            email_pattern = Pattern.compile(EMAIL_PATTERN);
            email_matcher = email_pattern.matcher(email_address);
            return email_matcher.matches();
        }else{
            return false;
        }
    }
}
