package com.example.neild_000.happinessjar.Camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

public class CameraData {
	
	
	private boolean checkCameraHardware(Context context){
		if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Camera getCameraInstance(){
		
		Camera c = null; 
		
		try{
			c = Camera.open();
		}catch(Exception e){
			Log.e(CameraData.class.toString(), "Failer to open camera");
			
		}
		return c;
	
	}
	
	

}
