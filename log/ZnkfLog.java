package com.hexin.znkflib.support.log;

import android.util.Log;

import com.hexin.znkflib.BuildConfig;

/**
 * desc:
 *
 * @author sunxianglei@myhexin.com
 * @date 2019/9/20.
 */

public class ZnkfLog {

    public static void v(String tag, String msg){
        if(LogWindow.isLogOpen){
            LogWindow.logList.add("[" + tag + "] " + msg);
        }
        if(BuildConfig.DEBUG){
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(LogWindow.isLogOpen){
            LogWindow.logList.add("[" + tag + "] " + msg);
        }
        if(BuildConfig.DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(LogWindow.isLogOpen){
            LogWindow.logList.add("[" + tag + "] " + msg);
        }
        if(BuildConfig.DEBUG){
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if(LogWindow.isLogOpen){
            LogWindow.logList.add("[" + tag + "] " + msg);
        }
        if(BuildConfig.DEBUG){
            Log.i(tag, msg);
        }
    }

}
