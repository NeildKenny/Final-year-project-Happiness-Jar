package com.example.neild_000.happinessjar.CardFragments;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by neild_000 on 27/01/2015.
 */
public class Card {

    private String title = "null";
    private String description = "null";
    private String card_id = "null";
    private String tag = "null";
    private String error_message;


    private String image_one_string = "null";
    private String image_two_string = "null";
    private String image_three_string = "null";
    private String image_four_string = "null";

    private Bitmap image_one_bitmap = null;
    private Bitmap image_two_bitmap = null;
    private Bitmap image_three_bitmap = null;
    private Bitmap image_four_bitmap = null;

    private InputStream inputStream_image1 = null;
    private InputStream inputStream_image2 = null;
    private InputStream inputStream_image3 = null;
    private InputStream inputStream_image4 = null;

    public int recyleBitmaps(){

        int count=0;
        if(image_one_bitmap != null && !image_one_bitmap.isRecycled()) {
            image_one_bitmap.recycle();
            count++;
        }
        if(image_two_bitmap != null && !image_two_bitmap.isRecycled()) {
            image_two_bitmap.recycle();
            count++;
        }
        if(image_three_bitmap != null && !image_three_bitmap.isRecycled()) {
            image_three_bitmap.recycle();
            count++;
        }
        if(image_four_bitmap !=null && !image_four_bitmap.isRecycled()) {
            image_four_bitmap.recycle();
            count++;
        }
        return count;
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

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getTag() {
        return tag;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage_one_string() {
        return image_one_string;
    }

    public void setImage_one_string(String image_one_string) {
        this.image_one_string = image_one_string;
    }

    public String getImage_two_string() {
        return image_two_string;
    }

    public void setImage_two_string(String image_two_string) {
        this.image_two_string = image_two_string;
    }

    public String getImage_three_string() {
        return image_three_string;
    }

    public void setImage_three_string(String image_three_string) {
        this.image_three_string = image_three_string;
    }

    public String getImage_four_string() {
        return image_four_string;
    }

    public void setImage_four_string(String image_four_string) {
        this.image_four_string = image_four_string;
    }

    public Bitmap getImage_one_bitmap() {
        return image_one_bitmap;
    }

    public void setImage_one_bitmap(Bitmap image_one_bitmap) {
        this.image_one_bitmap = image_one_bitmap;
    }

    public Bitmap getImage_two_bitmap() {
        return image_two_bitmap;
    }

    public void setImage_two_bitmap(Bitmap image_two_bitmap) {
        this.image_two_bitmap = image_two_bitmap;
    }

    public Bitmap getImage_three_bitmap() {
        return image_three_bitmap;
    }

    public void setImage_three_bitmap(Bitmap image_three_bitmap) {
        this.image_three_bitmap = image_three_bitmap;
    }

    public Bitmap getImage_four_bitmap() {
        return image_four_bitmap;
    }

    public void setImage_four_bitmap(Bitmap image_four_bitmap) {
        this.image_four_bitmap = image_four_bitmap;
    }

    public InputStream getInputStream_image1() {
        return inputStream_image1;
    }

    public void setInputStream_image1(InputStream inputStream_image1) {
        this.inputStream_image1 = inputStream_image1;
    }

    public InputStream getInputStream_image2() {
        return inputStream_image2;
    }

    public void setInputStream_image2(InputStream inputStream_image2) {
        this.inputStream_image2 = inputStream_image2;
    }

    public InputStream getInputStream_image3() {
        return inputStream_image3;
    }

    public void setInputStream_image3(InputStream inputStream_image3) {
        this.inputStream_image3 = inputStream_image3;
    }

    public InputStream getInputStream_image4() {
        return inputStream_image4;
    }

    public void setInputStream_image4(InputStream inputStream_image4) {
        this.inputStream_image4 = inputStream_image4;
    }

    public boolean parseCardJSON(String json_from_server) {

        try{
            JSONObject reader = new JSONObject(json_from_server);
            if( reader.getBoolean("error") == false ) {

                JSONObject card = reader.getJSONObject("card");
                Log.i(Card.class.toString(), card.toString());

                title = card.getString("title");
                description = card.getString("description");
                tag = card.getString("tag");
                image_one_string = card.getString("image_one");
                image_two_string = card.getString("image_two");
                image_three_string = card.getString("image_three");
                image_four_string = card.getString("image_four");

                return true;

            }else{
                error_message = reader.getString("error_message");
                return false;
            }

        }catch(JSONException e){
            e.printStackTrace();
            return false;
        }

    }


}
