package com.richard.stepcount.db;

import android.content.Context;
import android.content.SharedPreferences;
import com.richard.stepcount.entity.bean.UserInfo;

public class Config {

    public static final String PREFERENCE_NAME = "config";
    public static final String STEP_COUNt = "STEP_COUNt";
    public static final String USER_INFO = "USER_INFO";

//    public static final String ABORT_GROUP_MSG = "ABORT_GROUP_MSG";


    private static Config current;
    private SharedPreferences preferences;
    private Context mContext;


    public static synchronized Config getConfig(Context context) {
        if (current == null) {
            current = new Config(context.getApplicationContext());
        }
        return current;
    }

    private Config(Context context) {
        mContext = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }

    public void saveStepCount(int steps){
        SharedPreferenceHelper.save(STEP_COUNt,steps);
    }
    public int getStepCount(){
        return SharedPreferenceHelper.getInt(STEP_COUNt,0);
    }

    public void saveUserInfo(UserInfo userInfo){
        if(userInfo == null){
            SharedPreferenceHelper.deleteObject(USER_INFO);
        }else{
            SharedPreferenceHelper.setObject(USER_INFO, userInfo);
        }

    }

    public UserInfo getUserInfo(){
        return SharedPreferenceHelper.getObject(USER_INFO, UserInfo.class);
    }


//    public void saveStepInfo(StepInfoEntity stepInfo) {
//        SharedPreferenceHelper.setObject(STEP_INFO, stepInfo);
//    }
//
//    public StepInfoEntity getStepInfo() {
//        return SharedPreferenceHelper.getObject(STEP_INFO,StepInfoEntity.class);
//    }
//
//
//    public void saveMorSignInfo(MorningSignInfoEntity morningSignInfoEntity) {
//        SharedPreferenceHelper.setObject(MORNING_SIGN_INFO, morningSignInfoEntity);
//    }
//
//    public MorningSignInfoEntity getMorSignInfo() {
//        return SharedPreferenceHelper.getObject(MORNING_SIGN_INFO,MorningSignInfoEntity.class);
//    }

//    public void saveStepInfo(StepInfoEntity stepInfo, String userId) {
//        SharedPreferenceHelper.setObject(STEP_INFO + userId, stepInfo);
//    }
//
//    public StepInfoEntity getStepInfo(String userId) {
//        return SharedPreferenceHelper.getObject(STEP_INFO + userId, StepInfoEntity.class);
//    }
//
//
//    public void saveMorSignInfo(MorningSignInfoEntity morningSignInfoEntity, String userId) {
//        SharedPreferenceHelper.setObject(MORNING_SIGN_INFO + userId, morningSignInfoEntity);
//    }
//
//    public MorningSignInfoEntity getMorSignInfo(String userId) {
//        return SharedPreferenceHelper.getObject(MORNING_SIGN_INFO + userId, MorningSignInfoEntity.class);
//    }


//    public List<String> getAbortGroupMsg(){
//        return SharedPreferenceHelper.getObjectList(ABORT_GROUP_MSG,String[].class);
//    }
//
//    public void setAbortGroupMsg(List<String> abortGroupsId){
//        SharedPreferenceHelper.setObjectList(ABORT_GROUP_MSG,abortGroupsId);
//    }

}
