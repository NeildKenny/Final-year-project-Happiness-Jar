package com.example.neild_000.happinessjar.Camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.neild_000.happinessjar.ConfigFiles.ImagePath;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoHandler implements PictureCallback {



	private final Context context;
	public PhotoHandler(Context context){
		this.context = context;
	}

    public Uri getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(Uri image_uri) {
        this.image_uri = image_uri;
    }

    private Uri image_uri;
    private String image_directory = "HappinessJar";

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        // TODO Auto-generated method stub
        camera.stopPreview();

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        ByteArrayOutputStream bitmap_output_stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bitmap_output_stream);
        data = bitmap_output_stream.toByteArray();
        bitmap.recycle();
        bitmap_output_stream = null;
        File pictureFileDir = getDir();

        if(!pictureFileDir.exists() && !pictureFileDir.mkdir()){
            Log.d(PhotoHandler.class.toString(), "Cannot find/make directory to save image");
            Toast.makeText(context, "Cannot create directory to save image", Toast.LENGTH_LONG).show();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "IMG_" + date + ".jpg";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;

        File pictureFile = new File(filename);
        image_uri = Uri.fromFile(pictureFile);

        try{
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            data = null;
            System.gc();

            Toast.makeText(context, "New image saved:" + photoFile, Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Log.e(PhotoHandler.class.toString(), "Could not save image");

            Toast.makeText(context, "Could not save image", Toast.LENGTH_LONG).show();
        }



    }
	
/*	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
        byteToImageToFileDir(data);

    }*/

    private Bitmap byteToBitmap(byte[] data){
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        return bitmap;
    }

    private byte[] imageToCompressedByte(byte[] data){


        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        ByteArrayOutputStream bitmap_output_stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bitmap_output_stream );
        bitmap.recycle();
        data = null;
        return bitmap_output_stream.toByteArray();
    }

    private void byteToImageToFileDir(byte[] data){

       data = imageToCompressedByte(data);
        File pictureFileDir = getDir();

        if(!pictureFileDir.exists() && !pictureFileDir.mkdir()){
            Log.d(PhotoHandler.class.toString(), "Cannot find/make directory to save image");
            Toast.makeText(context, "Cannot create directory to save image", Toast.LENGTH_LONG).show();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "IMG_" + date + ".jpg";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;

        File pictureFile = new File(filename);
        image_uri = Uri.fromFile(pictureFile);

        try{
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            data = null;

            Toast.makeText(context, "New image saved:" + photoFile, Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Log.e(PhotoHandler.class.toString(), "Could not save image");

            Toast.makeText(context, "Could not save image", Toast.LENGTH_LONG).show();
        }

    }


	private File getDir() {
		// TODO Auto-generated method stub
		File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		return new File(sdDir, ImagePath.image_directory_test);
	}

    private File getDirWithName( String file_name ){
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(sdDir, ImagePath.image_directory + file_name);

    }

}
