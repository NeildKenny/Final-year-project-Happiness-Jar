package com.example.neild_000.happinessjar.CardFragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.ImageHandler;
import com.example.neild_000.happinessjar.R;
import com.example.neild_000.happinessjar.RESTCalls;
import com.example.neild_000.happinessjar.RedundantCode.Card.CardActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by neild_000 on 10/03/2015.
 */
public class CardViewFragment extends Fragment {
    private ImageButton imageButton_main_Image;
    private ImageButton imageButton_left_Image;
    private ImageButton imageButton_center_Image;
    private ImageButton imageButton_right_Image;

    private TextView TextView_title;
    private TextView TextView_description;
    private TextView TextView_tag;


    private View rootView;

    private String user_id = "1";

    private onEditButtonClickedListener listener;
    private Card cardObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        cardObject = new Card();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.card_view_fragment, container, false);
        this.rootView = rootView;
        setHasOptionsMenu(true);

        initCardView(rootView);

        getCardFromServer();

        return rootView;


    }

    private void initCardView(View rootView){
        TextView_title = (TextView) rootView.findViewById( R.id.card_activity_fragment_title_TextView );
        TextView_description = (TextView) rootView.findViewById( R.id.card_activity_fragment_description_TextView );
        TextView_tag = (TextView) rootView.findViewById( R.id.card_activity_fragment_tag_TextView );

        imageButton_main_Image = (ImageButton) rootView.findViewById(R.id.card_activity_fragment_main_imageButton);
        imageButton_left_Image = (ImageButton) rootView.findViewById(R.id.card_activity_fragment_left_imageButton);
        imageButton_center_Image = (ImageButton) rootView.findViewById(R.id.card_activity_fragment_center_imageButton);
        imageButton_right_Image = (ImageButton) rootView.findViewById(R.id.card_activity_fragment_right_imageButton);


        imageButton_center_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImages(imageButton_center_Image);
            }
        });

        imageButton_left_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImages(imageButton_left_Image);
            }
        });

        imageButton_right_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImages(imageButton_right_Image);
            }
        });


    }



    public void switchImages(ImageButton imagebutton) {

        Drawable buffer_image_clicked = imagebutton.getDrawable();
        imagebutton.setImageDrawable(imageButton_main_Image.getDrawable());
        imageButton_main_Image.setImageDrawable(buffer_image_clicked);
        Log.i(CardNewFragment.class.toString(), "in switch image");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_card_edit_icon){
            Log.i(CardViewFragment.class.toString(), "Edit button hit");
            callCardEdit();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_card_view, menu);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof onEditButtonClickedListener){
            listener = (onEditButtonClickedListener) activity;
        }else{
            throw new ClassCastException(activity.toString() + "must implement onEditButtonClickListener");
        }
    }




    private void getCardFromServer() {
        cardObject.setCard_id("52");
        user_id = "1";
        if( RESTCalls.isNetworkAvailable(getActivity()) ) {

            String url = ConfigURLs.URL_GET_CARD_FROM_SERVER + cardObject.getCard_id() + "/" + user_id;
            Log.i(CardViewFragment.class.toString(), url);
            new CallCardFromServer().execute(ConfigURLs.URL_GET_CARD_FROM_SERVER + cardObject.getCard_id() + "/" + user_id);
        }else{
            Log.i(CardViewFragment.class.toString(), "network not available");
        }
    }


    private class CallCardFromServer extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... url) {
            try{
                String card_data_from_server = RESTCalls.getJSONFromServer(url[0]);
                Log.i( CallCardFromServer.class.toString(), card_data_from_server );
                cardObject.parseCardJSON(card_data_from_server);


            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            getImageFromServer();
        }
    }

    private void getImageFromServer(){
        if(RESTCalls.isNetworkAvailable(getActivity())){
            new addImagesToView().execute();
        }else{
            Toast toast = Toast.makeText(getActivity(), "Network not available", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private class addImagesToView extends AsyncTask<Void, Void, Void>{


        @Override
        protected Void doInBackground(Void... params) {

            try{
                if( cardObject.getImage_one_string().isEmpty() || cardObject.getImage_one_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_one_string() );
                    cardObject.setImage_one_bitmap(ImageHandler.decodeUrl(url_image));
                    Log.i(CardActivity.class.toString(), "image one set");
                }
                if( cardObject.getImage_two_string().isEmpty() || cardObject.getImage_two_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_two_string() );
                    cardObject.setImage_two_bitmap(ImageHandler.decodeUrl(url_image));
                    Log.i(CardActivity.class.toString(), "image one set");
                }
                if( cardObject.getImage_three_string().isEmpty() || cardObject.getImage_three_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_three_string() );
                    cardObject.setImage_three_bitmap(ImageHandler.decodeUrl(url_image));
                    Log.i(CardActivity.class.toString(), "image one set");
                }
                if( cardObject.getImage_four_string().isEmpty() || cardObject.getImage_four_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_four_string() );
                    cardObject.setImage_four_bitmap(ImageHandler.decodeUrl(url_image));
                    Log.i(CardActivity.class.toString(), "image one set");
                }
               /* if( cardObject.getImage_one_string().isEmpty() || cardObject.getImage_one_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_one_string() );
                    cardObject.setImage_one_bitmap(BitmapFactory.decodeStream(url_image.openConnection().getInputStream()));
                    Log.i(CardActivity.class.toString(), "image one set");
                }

                if( cardObject.getImage_one_string().isEmpty() || cardObject.getImage_one_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_one_string() );
                    cardObject.setImage_one_bitmap(ImageHandler.decodeUrl(url_image));
                    Log.i(CardActivity.class.toString(), "image one set");
                }
                if( cardObject.getImage_two_string().isEmpty() || cardObject.getImage_two_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_two_string() );
                    cardObject.setImage_two_bitmap(BitmapFactory.decodeStream(url_image.openConnection().getInputStream()));

                    Log.i(CardActivity.class.toString(), "image two set");

                }

                if( cardObject.getImage_three_string().isEmpty() || cardObject.getImage_three_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_three_string() );
                    cardObject.setImage_three_bitmap(BitmapFactory.decodeStream(url_image.openConnection().getInputStream()));

                    Log.i(CardActivity.class.toString(), "image three set");

                }

                if( cardObject.getImage_four_string().isEmpty() || cardObject.getImage_four_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_four_string() );
                    cardObject.setImage_four_bitmap( BitmapFactory.decodeStream( url_image.openConnection().getInputStream()) );

                    Log.i(CardActivity.class.toString(), "image four set");

                }

                */

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            TextView_title.setText(cardObject.getTitle());

            TextView_description.setText(cardObject.getDescription());
            TextView_tag.setText(cardObject.getTag());
            URL url_image = null;

            imageButton_main_Image.setImageBitmap( cardObject.getImage_one_bitmap());
            imageButton_left_Image.setImageBitmap( cardObject.getImage_two_bitmap() );
            imageButton_center_Image.setImageBitmap( cardObject.getImage_three_bitmap() );
            imageButton_right_Image.setImageBitmap(cardObject.getImage_four_bitmap());

            View card_edit_view = rootView.findViewById(R.id.card_secondary_view);
            View card_load_image = rootView.findViewById(R.id.card_activity_fragment_load);
            card_edit_view.setVisibility(View.VISIBLE);
            card_load_image.setVisibility(View.INVISIBLE);



        }
    }

    public interface onEditButtonClickedListener{
        void onEditButtonClick(String card_id);
    }

    private void callCardEdit(){
        listener.onEditButtonClick(cardObject.getCard_id());
    }


}
