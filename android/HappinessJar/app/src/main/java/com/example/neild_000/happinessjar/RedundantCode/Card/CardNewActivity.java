package com.example.neild_000.happinessjar.RedundantCode.Card;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.neild_000.happinessjar.CardFragments.Card;
import com.example.neild_000.happinessjar.ConfigFiles.ConfigURLs;
import com.example.neild_000.happinessjar.RedundantCode.Drawer.NavigationDrawerFragment;
import com.example.neild_000.happinessjar.Login.LoginActivity;
import com.example.neild_000.happinessjar.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by neild_000 on 27/01/2015.
 */
public class CardNewActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private ImageButton imageButton_main_Image;
    private ImageButton imageButton_left_Image;
    private ImageButton imageButton_center_Image;
    private ImageButton imageButton_right_Image;

    private EditText editText_title;
    private EditText editText_description;
    private EditText editText_tag;
    private Card cardObject;

    RelativeLayout edit_image_buttons;


    private final int CAMERA_JAR_REQUEST_CODE = 1;
    private final int CAMERA_OTHER_REQUEST_CODE = 2;


    private String user_id;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle = "Happiness jar";

    private final String shared_preferences_settings = "setting";
    private final String getShared_preferences_settins_camera = "camera";


    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        cardObject = new Card();

        Bundle extras = getIntent().getExtras();

        //testing
        user_id = "1";
        cardObject.setCard_id("51");
        //testing should be !=null
        if(extras != null){

            //user_id  !=1 testing
            if( extras.getString("user_id") == "0" || extras.getString("user_id") == null || extras.getString("user_id").isEmpty() || user_id != "1"){
                Log.i(CardNewActivity.class.toString(), "user id not set");
                Toast toast = Toast.makeText(this, "You aren't logged in", Toast.LENGTH_LONG );
                toast.show();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("error", "0");
                startActivity(intent);
            }
          //  user_id = extras.getString("user_id");


        ///    cardObject.setCard_id(extras.getString("card_id"));

            //testing

        }
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.card_edit_relative);
        ViewTreeObserver vto = relativeLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initCardView();
            }
        });


    }


    private void initCardView(){
        editText_title = (EditText) findViewById( R.id.card_edit_title_editText );
        editText_description = (EditText) findViewById( R.id.card_edit_description_editText );
        editText_tag = (EditText) findViewById( R.id.card_edit_tag_editText );

        imageButton_main_Image = (ImageButton) findViewById(R.id.card_edit_main_imageButton);
        imageButton_left_Image = (ImageButton) findViewById(R.id.card_edit_leftimage_imageButton);
        imageButton_center_Image = (ImageButton) findViewById(R.id.card_edit_centerImage_imageButton);
        imageButton_right_Image = (ImageButton) findViewById(R.id.card_edit_rightImage_imageButton);



    }

    public void onCameraClick(View view){
/*       SharedPreferences sharedPreferences = getSharedPreferences(shared_preferences_settings, Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(getShared_preferences_settins_camera, false) ||  Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP  ){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_OTHER_REQUEST_CODE);

        }else{
            Intent jarCameraIntent = new Intent(this, CameraActivity.class);
            startActivityForResult(jarCameraIntent, CAMERA_JAR_REQUEST_CODE);
        }*/
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_OTHER_REQUEST_CODE);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.i(CardNewActivity.class.toString(), "In activity result");
        Bitmap image = null;

        if(resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            if(!extras.isEmpty()) {

                if (requestCode == CAMERA_OTHER_REQUEST_CODE) {

                    Log.i(CardNewActivity.class.toString(), " Third party camera");

                    image  = (Bitmap) extras.get("data");


                } else if (requestCode == CAMERA_JAR_REQUEST_CODE) {

                    Log.i(CardNewActivity.class.toString(), " jar camera");

                    Uri image_uri = Uri.parse(extras.getString("data"));
                    image = BitmapFactory.decodeFile(image_uri.getPath());
                }

                if( imageButton_main_Image !=  null && imageButton_main_Image.getDrawable() != null) {
                    Log.i(CardNewActivity.class.toString(), " image != null");
                    Bitmap bitmap = imageButton_main_Image.getDrawingCache();
                    if (bitmap != null && !bitmap.isRecycled()){
                        ((BitmapDrawable) imageButton_main_Image.getDrawable()).getBitmap().recycle();
                    }
                }else{
                    Log.i(CardNewActivity.class.toString(), " image == null");
                    imageButton_main_Image = (ImageButton) findViewById(R.id.card_edit_main_imageButton);
                }

                imageButton_main_Image.setImageBitmap(image);
                if(image.isRecycled())
                    image.recycle();
            }
        }

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
       /* switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }*/
    }

    public void restoreActionBar(){
        actionBar = getSupportActionBar();


        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.show();
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
        if( id == R.id.action_submit ){
            onUploadClick(this.getWindow().getDecorView().findViewById(R.id.card_edit_relative));
        }
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
            View rootView = inflater.inflate(R.layout.card_edit, container, false);

            final RelativeLayout edit_image_buttons = (RelativeLayout) rootView.findViewById(R.id.card_edit_buttons);
            ImageButton imageButton_main_Image = (ImageButton) rootView.findViewById(R.id.card_edit_main_imageButton);
            imageButton_main_Image.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    Log.i(CardNewActivity.class.toString(), "in on long click");



                    if(edit_image_buttons.getVisibility() == View.GONE) {
                        edit_image_buttons.setVisibility(View.VISIBLE);
                        edit_image_buttons.requestFocus();
                    }else if(edit_image_buttons.getVisibility() == View.VISIBLE){
                        edit_image_buttons.setVisibility(View.GONE);


                    }

                    return false;
                }
            });

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((CardNewActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }



    public void switchImages(View view) {

        if(view.getId()!= R.id.card_edit_main_imageButton){

            ImageButton clicked_image_button = (ImageButton) findViewById(view.getId());
            Drawable buffer_image_clicked = clicked_image_button.getDrawable();
            clicked_image_button.setImageDrawable(imageButton_main_Image.getDrawable());
            imageButton_main_Image.setImageDrawable(buffer_image_clicked);
            Log.i(CardNewActivity.class.toString(), " Image switched");

        }
    }





    public void onUploadClick(View view){

        /* For testing: get bitmap from drawable folder */
        Bitmap bm  = BitmapFactory.decodeResource(this.getResources(), R.drawable.jar);

        if( !isEmpty(editText_title) ) {
            cardObject.setTitle(editText_title.getText().toString());
        }

        if( !isEmpty(editText_description) ){
            cardObject.setDescription(editText_description.getText().toString());
        }

        if( !isEmpty(editText_tag) ){
            cardObject.setDescription(editText_tag.getText().toString());
        }



        if(imageButton_center_Image.getDrawable() != null)
            cardObject.setImage_one_bitmap(((BitmapDrawable) imageButton_center_Image.getDrawable()).getBitmap());

        if(imageButton_left_Image.getDrawable() != null)
            cardObject.setImage_two_bitmap(((BitmapDrawable)imageButton_left_Image.getDrawable()).getBitmap());

        if(imageButton_main_Image.getDrawable() !=  null)
            cardObject.setImage_three_bitmap(((BitmapDrawable)imageButton_main_Image.getDrawable()).getBitmap());

        if(imageButton_right_Image.getDrawable() != null)
            cardObject.setImage_four_bitmap(((BitmapDrawable)imageButton_right_Image.getDrawable()).getBitmap());



        new UploadMediaTask().execute();
    }


    public class UploadMediaTask extends AsyncTask< Void, Void, Void > {
        @Override
        protected Void doInBackground(Void... params) {


            DefaultHttpClient httpClient = new DefaultHttpClient();

            if( cardObject.getImage_one_bitmap() != null ) {
                cardObject.setInputStream_image1(ImagePNGToByteArray(cardObject.getImage_one_bitmap()));
            }


            if( cardObject.getImage_two_bitmap() != null )
                cardObject.setInputStream_image2(ImagePNGToByteArray(cardObject.getImage_two_bitmap()));

            if( cardObject.getImage_three_bitmap() != null )
                cardObject.setInputStream_image3(ImagePNGToByteArray(cardObject.getImage_three_bitmap()));

            if( cardObject.getImage_four_bitmap() != null)
                cardObject.setInputStream_image4(ImagePNGToByteArray(cardObject.getImage_four_bitmap()));

            int recycle_count = cardObject.recyleBitmaps();
            Log.i(CardNewActivity.class.toString(),"bitmaps recycled" + Integer.toString(recycle_count));

            try{
                long i  = 0;
                HttpPost httpPost = new HttpPost( ConfigURLs.URL_PHOTO_UPLOAD );
                //HttpPost httpPost = new HttpPost(TestConfigURL.UPLOAD_MULTIPLE_TEST);
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();

                //////////////////
                /// Image post logic
                /////////////////
                if( cardObject.getInputStream_image1() != null ){
                    builder.addBinaryBody("image[]", cardObject.getInputStream_image1(), ContentType.create("image/jpg"), System.currentTimeMillis()+ i + ".jpg");
                    Log.i(CardNewActivity.class.toString(), "image1 inputStream set");
                }
                //
                if( cardObject.getInputStream_image2() != null ){
                    builder.addBinaryBody("image[]", cardObject.getInputStream_image2(), ContentType.create("image/jpg"), System.currentTimeMillis()+ (i++) + ".jpg");
                    Log.i(CardNewActivity.class.toString(), "image2 inputStream set");
                }
                if( cardObject.getInputStream_image3() != null ){
                    builder.addBinaryBody("image[]", cardObject.getInputStream_image3(), ContentType.create("image/jpg"), System.currentTimeMillis()+ (i++) + ".jpg");
                    Log.i(CardNewActivity.class.toString(), "image3 inputStream set");
                }

                if( cardObject.getInputStream_image4() != null ){
                    builder.addBinaryBody("image[]", cardObject.getInputStream_image4(), ContentType.create("image/jpg"), System.currentTimeMillis()+(i++) + ".jpg");
                    Log.i(CardNewActivity.class.toString(), "image4 inputStream set");
                }


                ///////////
                /// Text post logic
                //////////

                builder.addTextBody("user_id", "1");
                if( cardObject.getTitle() != null && !cardObject.getTitle().isEmpty()) {
                    builder.addTextBody( "title", cardObject.getTitle() );
                }

                if( cardObject.getDescription() != null && !cardObject.getDescription().isEmpty() ) {
                    builder.addTextBody( "description", cardObject.getDescription() );
                }

                if( cardObject.getTag() != null && !cardObject.getTitle().isEmpty()){
                    builder.addTextBody( "tag", cardObject.getTag() );
                }

                httpPost.setEntity( builder.build() );

                Log.i(CardNewActivity.class.toString(), "entity set");


                HttpResponse response = null;
                try{
                    response = httpClient.execute(httpPost);
                }catch(ClientProtocolException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
                try{
                    if(response != null){
                           Log.i(CardNewActivity.class.toString(),"response: " + response.getStatusLine().toString());

                        String responseText = EntityUtils.toString(response.getEntity());
                           Log.d(CardNewActivity.class.toString(),"full response" + responseText );
                    }
                } catch(IOException e) {

                }finally{

                }
            }finally {

            }


           return null;
        }



        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);

                        Log.i(CardNewActivity.class.toString(), "data posted");
        }
    }


    private InputStream ImagePNGToByteArray( Bitmap bitmap ){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        InputStream in = new ByteArrayInputStream(stream.toByteArray());
        return in;
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}
