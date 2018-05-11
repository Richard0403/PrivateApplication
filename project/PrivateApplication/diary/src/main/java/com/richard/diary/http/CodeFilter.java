package com.richard.diary.http;

import android.content.Intent;

import com.richard.diary.common.base.App;
import com.richard.diary.view.main.SignInActivity;

/**
 * By Richard on 2018/5/2.
 */

public class CodeFilter {
    public static void filter(int code, String msg) {
        switch (code){
            case 401:
                SignInActivity.start(App.getInstance(), SignInActivity.class);
                break;
        }
    }
}
