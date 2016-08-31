package com.example.neild_000.happinessjar.Camera;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.neild_000.happinessjar.R;

import java.io.ByteArrayOutputStream;


public class CameraActivity extends Activity{

	private Camera camera;
	private CameraPreview mPreview;
    FrameLayout preview;
    ImageView imageView;
    private boolean in_image_preview = false;
    private byte[] image_as_byte ;
    private PhotoHandler photoHandler;

    private Camera.Parameters camera_parameters;

    private OrientationEventListener oreintationListener = null;
    private boolean submit = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.camera_preview_layout);

        camera = CameraData.getCameraInstance();
        photoHandler = new PhotoHandler(this);
        mPreview = new CameraPreview(this, camera);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            camera.setDisplayOrientation(90);

        Log.d(CameraActivity.class.toString(), Integer.toString(getResources().getConfiguration().orientation));

		preview = (FrameLayout) findViewById(R.id.camera_preview);
        imageView = (ImageView) findViewById(R.id.camera_preview_imageViewer);
		preview.addView(mPreview);

        camera_parameters = camera.getParameters();
        camera_parameters.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);
        camera.setParameters( camera_parameters );


        final Button cancel = (Button) findViewById(R.id.cancel);
		final Button captureButtonPicture = (Button) findViewById(R.id.button_capture_picture);


        captureButtonPicture.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

                if(in_image_preview == false) {

                    in_image_preview = true;
                    camera.takePicture(null, null, photoHandler );


                    Log.i(CameraActivity.class.toString(), "in phototaken");
                    cancel.setVisibility(View.VISIBLE);

                    captureButtonPicture.setText("Submit");
                }else{
                    Log.i(CameraActivity.class.toString(), "in photo taken else" );
                        submit = true;
                        finish();
                }
            }

		});

		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                Log.i(CameraActivity.class.toString(), "in cancel");

                in_image_preview = false;
                cancel.setVisibility(imageView.INVISIBLE);
                camera.startPreview();
			}
		});
	}

private Camera.PreviewCallback previewCallbac = new Camera.PreviewCallback(){
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
};

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i(CameraActivity.class.toString(), "in onPictureTaken");

            Log.i( CameraActivity.class.toString(),"data length" + Integer.toString(data.length) );
            Bitmap image_bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image_as_byte = stream.toByteArray();
            Log.i( CameraActivity.class.toString(),"image_as_byte length" + Integer.toString(image_as_byte.length) );





            //     imageView.setImageBitmap(image_bitmap);
        //    imageView.setVisibility(View.VISIBLE);

            camera.stopPreview();

        }


    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }


    private void releaseCamera(){
       if(camera != null){
           camera.release();
           camera = null;
       }
    }
    @Override
    public void finish() {
        Log.i(CameraActivity.class.toString(), "in finish()");
        if(submit) {

            camera.release();
            Intent data = new Intent();
            data.putExtra("data", photoHandler.getImage_uri().toString());
            photoHandler = null;
            System.gc();
            setResult(RESULT_OK, data);
        }
        super.finish();

    }
}
