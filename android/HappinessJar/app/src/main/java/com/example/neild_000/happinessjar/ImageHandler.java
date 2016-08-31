package com.example.neild_000.happinessjar;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by neild_000 on 11/03/2015.
 */
public class ImageHandler {

    private static final int REQUIRED_SIZE = 400;


    public static Bitmap decodeUri(Uri selectedImage, ContentResolver contentResolver) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(contentResolver.openInputStream(selectedImage), null, o);



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

    public static Bitmap decodeUrl(URL url_image) throws IOException {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(url_image.openConnection().getInputStream(), null, bmOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width_tmp = bmOptions.outWidth, height_tmp = bmOptions.outHeight;
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
        BitmapFactory.Options bmOptions2 = new BitmapFactory.Options();

        bmOptions2.inSampleSize = scale;
        return BitmapFactory.decodeStream(url_image.openConnection().getInputStream(), null, bmOptions2);
    }

    private static String getRealPathFromURI(Uri contentURI, ContentResolver contentResolver) {
        String result;
        Cursor cursor = contentResolver.query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
