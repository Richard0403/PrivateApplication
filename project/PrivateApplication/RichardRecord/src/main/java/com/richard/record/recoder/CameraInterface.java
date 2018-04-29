package com.richard.record.recoder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.Log;

import com.richard.record.constants.AppSetting;

public class CameraInterface {
	private static final String TAG = "CameraInterface";  
    private Camera mCamera;  
    private Camera.Parameters mParams;  
    private boolean isPreviewing = false;  
    private float mPreviwRate = -1f;  
    //分别为 默认摄像头（后置）、默认调用摄像头的分辨率、被选择的摄像头（前置或者后置）
  	int defaultCameraId = -1, defaultScreenResolution = -1 , cameraSelection = 0;

    
    
    private static CameraInterface mCameraInterface;  
	
    public interface CamOpenOverCallback{  
        public void cameraHasOpened();  
    }  
  
    private CameraInterface(){  
  
    }  
    public static synchronized CameraInterface getInstance(){  
        if(mCameraInterface == null){  
            mCameraInterface = new CameraInterface();  
        }  
        return mCameraInterface;  
    } 
    
    /**打开Camera 
     * @param callback 
     */  
    public void doOpenCamera(CamOpenOverCallback callback){  
        Log.i(TAG, "Camera open....");  
        openCamera();  
        Log.i(TAG, "Camera open over....");  
        callback.cameraHasOpened();  
    }  
 
	/**使用TextureView预览Camera
	 * @param surface
	 * @param previewRate
	 */
	public void doStartPreview(SurfaceTexture surface, float previewRate){
		Log.i(TAG, "doStartPreview...");
		if(isPreviewing){
			mCamera.stopPreview();
			return;
		}
		if(mCamera != null){
			try {
				mCamera.setPreviewTexture(surface);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initCamera(previewRate);
			
			mCamera.startPreview();//开启预览
            
			isPreviewing = true;
		}
		
	}
    /** 
     * 停止预览，释放Camera 
     */  
    public void doStopCamera(){  
        if(null != mCamera)  
        {  
            mCamera.setPreviewCallback(null);  
            mCamera.stopPreview();   
            isPreviewing = false;   
            mPreviwRate = -1f;  
            mCamera.release();  
            mCamera = null;       
        }  
    } 	
	private void initCamera(float previewRate){
		if(mCamera != null){

			mParams = mCamera.getParameters();
			mParams.setPictureFormat(PixelFormat.JPEG);//设置拍照后存储的图片格式
//			CamParaUtil.getInstance().printSupportPictureSize(mParams);
//			CamParaUtil.getInstance().printSupportPreviewSize(mParams);
			
			//设置PreviewSize和PictureSize
			Size pictureSize = CamParaUtil.getInstance().getPropPictureSize(
					mParams.getSupportedPictureSizes(),previewRate, 800);
			mParams.setPictureSize(pictureSize.width, pictureSize.height);
			Size previewSize = CamParaUtil.getInstance().getPropPreviewSize(
					mParams.getSupportedPreviewSizes(), previewRate, 800);
			mParams.setPreviewSize(previewSize.width, previewSize.height);
			mCamera.setDisplayOrientation(90);
			CamParaUtil.getInstance().printSupportFocusMode(mParams);
			List<String> focusModes = mParams.getSupportedFocusModes();
			if(focusModes.contains("continuous-video")){
				mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			}
			mCamera.setParameters(mParams);	
			
			mPreviwRate = previewRate;
			mParams = mCamera.getParameters(); //重新get一次
			Log.i(TAG, "最终设置:PreviewSize--With = " + mParams.getPreviewSize().width
					+ "Height = " + mParams.getPreviewSize().height);
		}
	}
	/**
	 * 
	 * 
	 * @param type 1：pic; 2:video
	 * @return
	 */
	public static File newFile(int type)
	  {
	    File localFile = new File(AppSetting.APP_VIDEO_PATH);
	    String newFileName;
	    String dateString = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    switch(type){
	    
	    case 1:
	    	newFileName = localFile.getPath() + File.separator + "IMG_" + dateString + ".jpg";
	    	break;
	    case 2:
	    	newFileName = localFile.getPath() + File.separator + "VID_" + dateString + ".mp4";
	    	break;
	    case 3:
	    	newFileName = localFile.getPath() + File.separator + "VID_Candid_" + dateString + ".mp4";
	    	break;
	    default:
	    	return null;
	    }
	    return new File(newFileName);
	    
	 }
	public Camera getCamera(){
		return this.mCamera;
	}
	
	private boolean openCamera(){
		try
		{
			
			if(Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO)
			{
				int numberOfCameras = Camera.getNumberOfCameras();
				
				CameraInfo cameraInfo = new CameraInfo();
				for (int i = 0; i < numberOfCameras; i++) {
					Camera.getCameraInfo(i, cameraInfo);
					if (cameraInfo.facing == cameraSelection) {
						defaultCameraId = i;
					}
				}
			}
			stopPreview();
			if(mCamera != null){
				mCamera.release();
				mCamera = null;
			}
			
			if(defaultCameraId >= 0)
				mCamera = Camera.open(defaultCameraId);
			else
				mCamera = Camera.open();

		}
		catch(Exception e)
		{	
			return false;
		}
		return true;
	}
	/**
	 * 关闭摄像头的预览
	 */
	public void stopPreview() {
		if (isPreviewing && mCamera != null) {
			isPreviewing = false;
			mCamera.stopPreview();

		}
	}
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
}

