package com.example.neild_000.happinessjar.Camera;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.neild_000.happinessjar.CardFragments.CardNewFragment;
import com.example.neild_000.happinessjar.R;

import java.io.FileNotFoundException;


public class CameraTest extends ActionBarActivity {

    private final int CAMERA_JAR_REQUEST_CODE = 1;
    private final int CAMERA_OTHER_REQUEST_CODE = 2;
    private final int GALLERY_REQUEST_CODE = 3;
    private ImageView imageButton_main_Image;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

        //imageView = (ImageView) findViewById(R.id.camera_test_image_view);
        imageButton_main_Image = (ImageButton) findViewById(R.id.camera_test_image_view);
        final Uri  uri = Uri.parse("file:///storage/emulated/0/DCIM/HappinessJar/IMG_20153109083111.jpg");

       // imageButton_main_Image.setImageURI(uri);

        /*ViewTreeObserver vto = imageButton_main_Image.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout() {
                int targetW = imageButton_main_Image.getWidth();
                int targetH = imageButton_main_Image.getHeight();
                Log.i(CameraTest.class.toString(), " targetW " + Integer.toString(targetW) + " targetH "  + Integer.toString(targetH));

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.drawable.balloon);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image

                Log.i(CameraTest.class.toString(), " PhotoW " + Integer.toString(photoW) + " PhotoH " + Integer.toString(photoH));

                Log.i(CameraTest.class.toString(), " targetW " + Integer.toString(targetW) + " targetH "  + Integer.toString(targetH));

                int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.balloon, bmOptions);
                imageButton_main_Image.setImageBitmap(bitmap);
            }
        });*/

        // Get the dimensions of the View



    }


    public void onCameraThirdLaunchClick(View view) {
        Intent cameraActivity = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraActivity, CAMERA_OTHER_REQUEST_CODE);

    }

    public void onCameraSelfLaunchClick(View view) {
        Intent cameraSelfActivity = new Intent(this, CameraActivity.class);
        startActivityForResult(cameraSelfActivity, CAMERA_JAR_REQUEST_CODE);

    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(CardNewFragment.class.toString(), "In activity result");
        Bitmap image = null;

        if (resultCode == Activity.RESULT_OK ) {
            Bundle extras = data.getExtras();
            if (requestCode != GALLERY_REQUEST_CODE && !extras.isEmpty()){

                if (requestCode == CAMERA_OTHER_REQUEST_CODE) {

                    Log.i(CardNewFragment.class.toString(), " Third party camera");

                    image = (Bitmap) extras.get("data");


                } else if (requestCode == CAMERA_JAR_REQUEST_CODE) {

                    Log.i(CardNewFragment.class.toString(), " jar camera");

                    Uri image_uri = Uri.parse(extras.getString("data"));
                    image = BitmapFactory.decodeFile(image_uri.getPath());
                }

                if (imageButton_main_Image != null && imageButton_main_Image.getDrawable() != null) {
                    Log.i(CardNewFragment.class.toString(), " image != null");
                    Bitmap bitmap = imageButton_main_Image.getDrawingCache();
                    if (bitmap != null && !bitmap.isRecycled()) {
                        ((BitmapDrawable) imageButton_main_Image.getDrawable()).getBitmap().recycle();
                    }
                } else {
                    Log.i(CardNewFragment.class.toString(), " image == null");
                    imageButton_main_Image = (ImageButton) rootView.findViewById(R.id.card_edit_fragment_main_imageButton);
                }

                imageButton_main_Image.setImageBitmap(image);
                if (image.isRecycled())
                    image.recycle();
            }else if(requestCode == GALLERY_REQUEST_CODE ) {
                imageButton_main_Image.setImageURI(data.getData());
            }
        }

    }*/


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            Log.i(CameraTest.class.toString(), "In activity result");

            Log.i(CardNewFragment.class.toString(), "In activity result");
            Bitmap image = null;

            if (resultCode == Activity.RESULT_OK ) {
                Bundle extras = data.getExtras();
                if (requestCode != GALLERY_REQUEST_CODE && !extras.isEmpty()) {

                    if (requestCode == CAMERA_OTHER_REQUEST_CODE) {

                        Log.i(CardNewFragment.class.toString(), " Third party camera");

                        image = (Bitmap) extras.get("data");


                    } else if (requestCode == 4) {

                        Log.i(CardNewFragment.class.toString(), " jar camera");

                        Uri image_uri = Uri.parse(extras.getString("data"));
                        image = BitmapFactory.decodeFile(image_uri.getPath());
                    } else if (requestCode == CAMERA_JAR_REQUEST_CODE) {
                        try {
                            imageButton_main_Image.setImageBitmap( decodeUri( Uri.parse(extras.getString("data")), getContentResolver() ) );
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                  /*  if (imageButton_main_Image != null && imageButton_main_Image.getDrawable() != null) {
                        Log.i(CardNewFragment.class.toString(), " image != null");
                        Bitmap bitmap = imageButton_main_Image.getDrawingCache();
                        if (bitmap != null && !bitmap.isRecycled()) {
                            ((BitmapDrawable) imageButton_main_Image.getDrawable()).getBitmap().recycle();
                        }
                    } else {
                        Log.i(CardNewFragment.class.toString(), " image == null");
                        imageButton_main_Image = (ImageButton) findViewById(R.id.card_edit_fragment_main_imageButton);
                    }

                    imageButton_main_Image.setImageBitmap(image);
                    if (image.isRecycled())
                        image.recycle();
                }else if(requestCode == GALLERY_REQUEST_CODE ) {
                    if (imageButton_main_Image != null && imageButton_main_Image.getDrawable() != null) {
                        Log.i(CardNewFragment.class.toString(), " image != null");
                        Bitmap bitmap = imageButton_main_Image.getDrawingCache();
                        if (bitmap != null && !bitmap.isRecycled()) {
                            ((BitmapDrawable) imageButton_main_Image.getDrawable()).getBitmap().recycle();
                        }
                    }

                }*/
                }
            }

        }

    public static Bitmap decodeUri(Uri selectedImage, ContentResolver contentResolver) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(contentResolver.openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(contentResolver.openInputStream(selectedImage), null, o2);

    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera_test, menu);
        return true;
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
}
