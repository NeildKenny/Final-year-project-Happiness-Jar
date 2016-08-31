package com.example.neild_000.happinessjar;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParseUser {
    private String id = "id";
    private String name = "name";
    private String password = "passowrd";
    private String email = "neildkenny@gmail.com";
    private String dob = "dob";
    private String gender = "gender";

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public void parseJSON(String jsonInput) {

        try {
            JSONObject reader = new JSONObject(jsonInput);
            id = reader.getString("id");
            Log.d("bleh", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
	

