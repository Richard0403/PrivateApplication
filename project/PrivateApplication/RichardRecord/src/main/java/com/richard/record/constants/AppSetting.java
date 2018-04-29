package com.richard.record.constants;




import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Environment;

import com.richard.record.base.BaseApplication;
import com.richard.record.utils.FileUtils;
import com.richard.record.utils.SDCardTool;

public class AppSetting {
	
	// http://apitest.beehoo.cn/user/register?cellphone=18511995224&verify_code=758068&aaa=87ewqr
		
	public static final String SERVER_IP ="apionline.beehoo.cn";   // 正式服务器 apionline.beehoo.cn； 测试服务器：apitest.beehoo.cn
	
	public static final String BaseURL =  "http://" + SERVER_IP ;

	//URL
	public static final String URL_USER_REGISTER_README = BaseURL + "/message/agreement";	

	
	
	//本地目录
    public static final String APP_NAME = "beehootravel";
    public static final String APP_PATH = getSdCard()+"/" + APP_NAME;
    public static final String APP_IMAGE_PATH = APP_PATH + "/image";
    public static final String APP_IMAGE_TMP_PATH = APP_PATH + "/image/tmp";
    public static final String APP_CACHE_PATH = APP_PATH + "/cache";
    public static final String APP_FFMPEG = APP_PATH + "/ffmpeg";
    
    public static final String APP_VIDEO_PATH = APP_PATH +"/video";
    
    public static final String APP_VIDEO_BACKGROUND_PATH = APP_PATH +"/video_background";
	
    
    public static final String APP_CATCH_LOG = APP_PATH +"/CatchedLog.txt";
	
    
	public static final String DEVICE_TYPE = "Android";
	
	public static final String SEC_KEY = "0S+25d;asdf62.A891,bee|hoo";
	
	
	//讯飞语音
	public static final String XF_APP_KEY = "582d8010";
	

	//微信APP设置
	public static final String WX_APP_ID = "wxacd96fb2315b7588";
	public static final String WX_APP_SECRET = "bb838a569e3dd5b433e96ab8dc528c0b";
	
	//腾讯
	public static final String TC_APP_ID ="1105206798";
	public static final String TC_APP_KEY="Wq4h5uCs8s4k96lf";
	//微博
	public static final String WB_APP_SECRET ="ffafabdcf4cf7daa9d085f193cf9086d";
	public static final String WB_APP_KEY="3187631952";
	
	public static final String EXIST_UNREAD_MSG="exist_unread_msg";
	public static final String EXIST_UNREAD_VIDEO="exist_unread_video";
	

		
	//抓拍视频时间长度
	public static final long CAPTURE_TIME_LENGTH =10;


	
	
	



	public static final String VERTIFY_USERID = "963123"; // "963123";
	public static final String VERTIFY_ACCOUT = "Admin";
	public static final String VERTIFY_PASSWORD = "123456"; // "123456";

	public static final String VERTIFY_SIGN = "【壁虎安全�??";


//	public static final String BEEHOOVALUE_SCREENSHOT = getSdCard()
//			+ "/beehoo/beehoovalue.png";
//
//	public static final String SHARE_BACKGROUD = getSdCard()
//			+ "/beehoo/share_bg.png";
//
//	public static final String HISTORY_MESSAGES = getSdCard()
//			+ "/beehoo/historymessages.xml";

	private static String getSdCard() {
		String sdCardState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(sdCardState)) {
			return Environment.getExternalStorageDirectory().getPath();
		}
		return null;
	}
	/**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     * 
     * @return
     */
    private static ArrayList<String> getDevMountList() {
        String[] toSearch = FileUtils.readFile("/system/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }
/**
     * 获取扩展SD卡存储目录
     * 
     * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     * 否则：返回内置SD卡目录
     * 
     * @return
     */
    public static String getExternalSdCardPath() {
 
        if (SDCardTool.hasSDCard()) {
        	List storageList = SDCardTool.listAvaliableStorage(BaseApplication.getInstance().getApplicationContext());
        	if(null!=storageList && storageList.size()==1){
        		return ((SDCardTool.StorageInfo)storageList.get(0)).path;
        	}
        	String path = null;
        	for(int i=0; i< storageList.size(); i++){
        		SDCardTool.StorageInfo storage = (SDCardTool.StorageInfo) storageList.get(i);
        		if(storage.isRemoveable){
        			return storage.path;
        		}else{
        			path = storage.path;
        		}
        	}
        	return path;
        	
//            File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//            return sdCardFile.getAbsolutePath();
        }
 
        String path = null;
 
        File sdCardFile = null;
 
        ArrayList<String> devMountList = getDevMountList();
 
        for (String devMount : devMountList) {
            File file = new File(devMount);
 
            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();
 
                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                File testWritable = new File(path, "test_" + timeStamp);
 
                if (testWritable.mkdirs()) {
                    testWritable.delete();
                } else {
                    path = null;
                }
            }
        }
 
        if (path != null) {
            sdCardFile = new File(path);
            return sdCardFile.getAbsolutePath();
        }
 
        return null;
    }
}
