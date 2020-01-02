package com.hexin.znkflib.support.log;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * desc:
 * @author sunxianglei@myhexin.com
 * @date 2019/9/20.
 */

public class LogWindowService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        openLog();
        return super.onStartCommand(intent, flags, startId);
    }

    public void openLog() {
        LogWindow logWindow = new LogWindow(getApplicationContext());
        logWindow.showDebugView();
    }

}
