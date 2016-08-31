package com.example.neild_000.happinessjar.Registration;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by neild_000 on 19/01/2015.
 */
public class Registration extends Email {


    private String name;
    private String email;
    private String password;

    private String user_id;
    private int DOB;
    private char gender;

    private boolean registration_pass;



    public boolean isRegistration_pass() {
        return registration_pass;
    }

    public void setRegistration_pass(boolean registration_pass) {
        this.registration_pass = registration_pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDOB() {
        return DOB;
    }

    public void setDOB(int DOB) {
        this.DOB = DOB;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }


    public JSONObject parseToJSON(){
        JSONObject jsonObj = new JSONObject();


        try{
            jsonObj.put("name", name );
            jsonObj.put("password", password);
            jsonObj.put("email", email);
            jsonObj.put("dob", DOB);
            jsonObj.put("gender", Character.toString(gender));

        }catch(JSONException e){
            e.printStackTrace();
        }
        try{
        Log.d(Registration.class.toString(), jsonObj.getString("gender"));
        }catch (JSONException e){
            e.printStackTrace();
        }
            return jsonObj;
    }

}