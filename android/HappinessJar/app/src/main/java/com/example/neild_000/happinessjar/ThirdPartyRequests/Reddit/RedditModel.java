package com.example.neild_000.happinessjar.ThirdPartyRequests.Reddit;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by neild_000 on 12/03/2015.
 */
public class RedditModel {

    private String id;
    private String title;
    private String description;
    private String image_link;
    private String permalink;
    private String error_message;
    private Bitmap image_bitmap;


    public Bitmap getImage_bitmap() {
        return image_bitmap;
    }

    public void setImage_bitmap(Bitmap image_bitmap) {
        this.image_bitmap = image_bitmap;
    }


    public boolean parseRedditData(String json_from_server){
        try{
            JSONObject reader = new JSONObject(json_from_server);
            if(reader.getBoolean("error") == false){
                id = reader.getString("id");
                title = reader.getString("title");
                description = reader.getString("description");
                image_link = reader.getString("image");
                permalink = reader.getString("permalink");

                return true;
            }else{
                error_message = reader.getString("error_message");
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

}
