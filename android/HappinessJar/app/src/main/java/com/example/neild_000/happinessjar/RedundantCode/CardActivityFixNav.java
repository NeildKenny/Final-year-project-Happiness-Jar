package com.example.neild_000.happinessjar.RedundantCode;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.RedundantCode.Drawer.NavigationDrawerFragment;
import com.example.neild_000.happinessjar.R;
import com.example.neild_000.happinessjar.RESTCalls;
import com.example.neild_000.happinessjar.CardFragments.Card;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class CardActivityFixNav extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private Card cardObject;
    private String user_id;
    private String card_id;

    private TextView textView_title;
    private TextView textView_description;
    private TextView textView_tags;

    private ImageButton imageButton_main_Image;
    private ImageButton imageButton_left_Image;
    private ImageButton imageButton_center_Image;
    private ImageButton imageButton_right_Image;


    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    public void setCard_layout(RelativeLayout card_layout) {
        this.card_layout = card_layout;
    }

    private RelativeLayout card_layout = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardObject = new Card();

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            user_id = extras.getString("user_id");
            cardObject.setCard_id( card_id = extras.getString("card_id") );
            Log.i(CardActivityFixNav.class.toString(), "User id: " + user_id + "  card_id: " + cardObject.getCard_id());
        }


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        getCardFromServer();

    }

    private void initCardView(){



        textView_title = (TextView) findViewById(R.id.card_title_TextView);
        textView_description = (TextView) findViewById(R.id.card_description_TextView);
        textView_tags = (TextView) findViewById(R.id.card_tag_TextView);

        imageButton_main_Image = (ImageButton) findViewById(R.id.card_main_imageButton);
        imageButton_left_Image = (ImageButton) findViewById(R.id.card_left_imageButton);
        imageButton_center_Image = (ImageButton) findViewById(R.id.card_center_imageButton);
        imageButton_right_Image = (ImageButton) findViewById(R.id.card_right_imageButton);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View view = inflater.inflate(R.layout.card_activty, container, false);


            return view;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((CardActivityFixNav) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    private void getCardFromServer() {
        cardObject.setCard_id("48");
        user_id = "1";
        if(RESTCalls.isNetworkAvailable(this)) {

            String url = ConfigURLs.URL_GET_CARD_FROM_SERVER + cardObject.getCard_id() + "/" + user_id;
            Log.i(CardActivityFixNav.class.toString(), url);
            new CallCardFromServer().execute(ConfigURLs.URL_GET_CARD_FROM_SERVER + cardObject.getCard_id() + "/" + user_id);
        }else{
            Log.i(CardActivityFixNav.class.toString(), "network not available");
        }
    }

    private class CallCardFromServer extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... url) {
            try{
                String card_data_from_server = RESTCalls.getJSONFromServer( url[0] );
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
        if(RESTCalls.isNetworkAvailable(this)){
            new addImagesToView().execute();
        }
    }

    private class addImagesToView extends AsyncTask<Void, Void, Void>{


        @Override
        protected Void doInBackground(Void... params) {

            try{

                if( cardObject.getImage_one_string().isEmpty() || cardObject.getImage_one_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_one_string() );
                    cardObject.setImage_one_bitmap(BitmapFactory.decodeStream(url_image.openConnection().getInputStream()));
                    Log.i(CardActivityFixNav.class.toString(), "image one set");
                }

                if( cardObject.getImage_two_string().isEmpty() || cardObject.getImage_two_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_two_string() );
                    cardObject.setImage_two_bitmap(BitmapFactory.decodeStream(url_image.openConnection().getInputStream()));

                    Log.i(CardActivityFixNav.class.toString(), "image two set");

                }

                if( cardObject.getImage_three_string().isEmpty() || cardObject.getImage_three_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_three_string() );
                    cardObject.setImage_three_bitmap(BitmapFactory.decodeStream(url_image.openConnection().getInputStream()));

                    Log.i(CardActivityFixNav.class.toString(), "image three set");

                }

                if( cardObject.getImage_four_string().isEmpty() || cardObject.getImage_four_string() != null ){
                    URL url_image = new URL( ConfigURLs.URL_IMAGE_UPLOAD_FOLDER + cardObject.getImage_four_string() );
                    cardObject.setImage_four_bitmap( BitmapFactory.decodeStream( url_image.openConnection().getInputStream()) );

                    Log.i(CardActivityFixNav.class.toString(), "image four set");

                }

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

            initCardView();

            textView_title.setText(cardObject.getTitle());
            textView_description.setText(cardObject.getDescription());
            textView_tags.setText(cardObject.getTag());

            imageButton_main_Image.setImageBitmap( cardObject.getImage_one_bitmap() );
            imageButton_left_Image.setImageBitmap( cardObject.getImage_two_bitmap() );
            imageButton_center_Image.setImageBitmap( cardObject.getImage_three_bitmap() );
            imageButton_right_Image.setImageBitmap( cardObject.getImage_four_bitmap() );

        }
    }

    public void switchImages(View view) {

        if(view.getId()!= R.id.card_edit_main_imageButton){

            ImageButton clicked_image_button = (ImageButton) findViewById(view.getId());
            Drawable buffer_image_clicked = clicked_image_button.getDrawable();
            clicked_image_button.setImageDrawable(imageButton_main_Image.getDrawable());
            imageButton_main_Image.setImageDrawable(buffer_image_clicked);

            Log.i(CardActivityFixNav.class.toString(), " Image switched");

        }
    }
}
