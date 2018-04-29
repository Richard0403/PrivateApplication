package com.richard.record.constants;


public class Constants {
		
	public static final String SERVER_IP = "query.beehoo.cn";  //"test.beehoo.cn";//"query.beehoo.cn";  //"182.254.217.18"; //
	public static final String USERNAME = "username";
	public static final String PASSWORD ="password";
	public static final String PASSWORD_NEW ="newpassword";
	public static final String BIRTHDAY ="birthday";
	public static final String GENDER ="sex";
	public static final String AGE ="age";
	public static final String EMAIL ="email";
	public static final String HEAD_IMAGE_URI ="head_image_uri";
	public static final String HEAD_IMAGE_URL ="head_image_url";	
	public static final String NICK ="nickname";
	public static final String CELLPHONE ="cellphone";
	public static final String PHONE ="cellphone";
	public static final String NAME ="name";
	public static final String IDCARD ="idcard";	
	public static final String MESSAGE ="message";
	public static final String DEVICE_ID = "device-id";
	public static final String VERIFY_CODE = "verify_code";
	public static final String TOKEN = "token";
	public static final String WEALTH ="wealth";
	public static final String HEAD_IMAGE ="head_image";
	public static final String AREA ="area";
	public static final String TIME ="time";
	public static final String HEIGHT ="height";
	public static final String WEIGHT ="weight";
	public static final String EMERGENCY_CONTACT ="emergency_contact";
	
	
	public static final String PUSH_USER_ID ="push-id";
	public static final String PUSH_CHANNEL_ID ="channel-id";
    public static final String BEEHOO_DEV ="beehoo-dev";
    
    public static final String DEVICE_TYPE = "device-type";
    
    
    public static final String VERSION = "version";
	        
	public static final String BAIDU_PUSH_UID ="baidu_push_userId";
	public static final String BAIDU_PUSH_CID="baidu_push_channelId";
	
	public static final String LOGIN_TYPE="login_type";
	public static final String LOGIN_UID="uid";
	public static final String LOGIN_GENDER="gender";
	public static final String LOGIN_SCREEN_NAME="screen_name";
	public static final String LOGIN_PROFILE_IMAGE_URL="profile_image_url";
	
	
	public static final String SWITCH_GESTURE = "gesture";
	
	//七牛
	public static final String QINIU_KEY = "key";
	public static final String QINIU_TOKEN = "token";
	public static final String QINIU_DOMAIN = "domain";
	
	public static final String LOCATION = "location";
	
	public static final String UID = "userid";
	public static final String PAGE_SIZE= "pageNum";
	public static final String WATCH_PHONE ="watchphone";
	
	public static final String LOC_LAT = "lat";
	public static final String LOC_LNG = "lng";	
	
	public static final String IS_FIRST = "isFirst";
	public static final String AUTO_LOGIN = "AUTO_ISCHECK";
	
	public static final String IMAGE_PATH = "imageFile";   //上传头像文件路径
	
	public static final String CONTENT = "content";    //反馈内容
	public static final String IMAGE_URL = "imageurl";    //头像url

	public static final String LOOKAFTER_NAME = "aftername";    //头像url
	
	public static final String MAIN_TAB_INDEX = "tab_index";    //头像url	
	
	public static final String LOOKAFTER_TYPE = "lookaftertype";    //头像url
	
	public static final String FRIST_BEEHOO_VALUE = "first_beehoovalue";
	public static final String FRIST_SAFETYWALK = "first_safetywalk";
	
	public static final String FILE_PATH = "filepath";
	
	
	public static final String LOOKAFTER_SETTING_USER_PHONE = "la_setting_user";
	public static final String LOOKAFTER_STATUS = "la_status";
	
	public static final String LOOKAFTER_SETTING_MESSAGE_INTERVAL = "la_setting_msg_interval";
    public static final String LOOKAFTER_SETTING_IS_SEND_SMS ="la_setting_msg_notice";
    public static final String LOOKAFTER_SETTING_IS_LOCATION_CHANGE ="la_setting_is_location_change";
    
    
   // public static final String MESSAGE_SETTING_SEND_MESSAGE ="msg_setting_send";
	//public static final String MESSAGE_SETTING_RECEIVE_MESSAGE ="msg_setting_receive";    
    public static final String CONTACT_LIST_STRING = "contact_list_json";
    public static final String CONTACT_BROADCAST = "get_contact";

    public static final String MESSAGE_SETTING_SEND_MESSAGE ="send_msg";
	public static final String MESSAGE_SETTING_RECEIVE_MESSAGE ="receive_msg";    
    
	
	
	public static final String MESSAGE_INTERVAL = "heart_minutes"; 
	
	public static final String IS_SEND_SMS = "is_send_sms"; 
	public static final String IS_ADDRESS_CHANGE = "is_address_change"; 
	
	public static final String PARAM_JSON ="paramJSON";
	
	public static final int NETWORK_STATUS_OK = 200;
	public static final int NETWORK_STATUS_NO_PERMISSION = 403;
	public static final int NETWORK_STATUS_NO_PHONE = 403;
	public static final int NETWORK_STATUS_NO_VERTIFY = 902;	
	public static final int NETWORK_STATUS_FAIL = 800;
	
	
	public static final int MAP_TYPE_RISK = 0;
	public static final int MAP_TYPE_SERVICE = 1;

	public static final String MAP_TYPE_RISK_STRING = "RISK";
	public static final String MAP_TYPE_SERVICE_STRING = "SERVICE";
	
	public static final String MAP_TYPE = "MAP_TYPE";
	public static final String MAP_LOCATION = "MAP_LOCATION";
	public static final String MAP_CENTER_LOCATION = "CENTER_LOCATION";
	
	public static final String BEEHOO_SEARCH_TYPE ="beehoo_search_type";
	
	//讯飞语音：默认词
	public static final String ASR_WORDS = "ASR_WORDS";
			


	
	public static final String[] SAFETY_LIST = {"旅游安全", "治安侵害","行车安全","儿童安全","身体健康"};
	
	//public static final ArrayList<Map<String, String[]>> SAFETY_TYPE_MAP = new ArrayList<Map<String, String[]>>(){ ("旅游安全", ["黑摩的","黑导游","宰客","毒虫蛰咬","洪水落石","色情","毒品","黑餐饮","迷路"])
		
		
	//		};
			
			//MAP {"旅游安全", "治安侵害","行车安全","儿童安全","身体健康"};
	
	public static final String[] SAFETY_LIST_TRAVEL = {"黑摩的","黑导游","宰客","毒虫蛰咬","洪水落石","色情","毒品","黑餐饮","迷路"};
	public static final String[] SAFETY_LIST_PUBLIC_ORDER = {"盗窃","诈骗","抢劫","暴恐",	"黑社会","绑架","欺诈传销","邪教活动","强奸"};
	public static final String[] SAFETY_LIST_DRIVER = {"车祸频发",	"砸车盗窃","拦车抢劫","路况差",	"酒驾","碰瓷",	"大货车多"};
	public static final String[] SAFETY_LIST_CHILDER = {"拐卖","走失","误食"	,"坠落","落水","烫伤","狗咬"};
	public static final String[] SAFETY_LIST_PHYSICAL = {"小儿多发病","烈性传染病","地方常见病","水污染","空气污染","食品不安全"};
	
	
	// search history
	public static final String SEARCH_SEX="SEARCH_SEX";
	public static final String SEARCH_BIRTHDAY="SEARCH_BIRTHDAY";
	public static final String SEARCH_HOUR="SEARCH_HOUR";
	public static final String SEARCH_MINUTE="SEARCH_MINUTE";
	public static final String SEARCH_ADDRESS="SEARCH_ADDRESS";	
	public static final String SEARCH_GPS_LAT="SEARCH_GPS_LAT";
	public static final String SEARCH_GPS_LON="SEARCH_GPS_LON";
}
