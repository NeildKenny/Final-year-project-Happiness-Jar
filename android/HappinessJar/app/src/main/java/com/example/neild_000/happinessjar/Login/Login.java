package com.example.neild_000.happinessjar.Login;

import com.example.neild_000.happinessjar.Registration.Email;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by neild_000 on 21/01/2015.
 */
public class Login extends Email {

    private String email;
    private String password;

    private JSONObject jsonObject;


    private boolean login_pass;
    private String user_id = null;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isLogin_pass() {
        return login_pass;
    }

    public void setLogin_pass(boolean login_pass) {
        this.login_pass = login_pass;
    }

    public JSONObject parseLoginToJSON(){
         jsonObject = new JSONObject();

        try{
            jsonObject.put("email", email);
            jsonObject.put("password", password);

        }catch(JSONException e){
            e.printStackTrace();
        }

        return jsonObject;

    }


}
