package com.example.neild_000.happinessjar.Camera;


import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{

	private SurfaceHolder mHolder;

    public void setCamera(Camera camera) {
        this.mCamera = camera;
    }

    @SuppressWarnings( "deprecation" )
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
		super(context);
		
		mCamera = camera;
		
		mHolder = getHolder();
		mHolder.addCallback(this);

		//mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		try{
			mCamera.setPreviewDisplay(holder);

		}catch(IOException e){
			Log.d(CameraPreview.class.toString(), "Error setting camera preview: " + e.getMessage());
		}
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
/*		// TODO Auto-generated method stub
        Log.i(CameraPreview.class.toString(), "In surfaceChange");
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width,selected.height);
        mCamera.setParameters(params);

        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();*/


        if (mHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            //You can ignore this, because this means the Preview doesn't Exist
            //So, no need to try stopping
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            //Catch this
        }
		
		
	}

	

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
