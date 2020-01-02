package com.hexin.znkflib.support.log;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hexin.znkflib.EntranceInfo;
import com.hexin.znkflib.R;
import com.hexin.znkflib.ZnkfModel;
import com.hexin.znkflib.util.ZnkfUtil;
import com.hexin.znkflib.util.config.ConfigManager;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * @author sunxianglei@myhexin.com
 * @date 2019/9/20.
 */

public class LogWindow {

    private static final String TAG = "LogWindow";
    private WindowManager windowManager;
    private WindowManager.LayoutParams paramsFloatView;
    private WindowManager.LayoutParams paramsListView;
    private TextView floatDraftView;
    private Context context;
    public static boolean isLogOpen;
    public static List<String> logList = new ArrayList<>();

    public LogWindow(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        paramsFloatView = new WindowManager.LayoutParams();
        paramsListView = new WindowManager.LayoutParams();
        floatDraftView = new TextView(context);
        floatDraftView.setText("调试");
        floatDraftView.setTextColor(Color.WHITE);
        floatDraftView.setBackgroundColor(Color.GREEN);
        floatDraftView.setGravity(Gravity.CENTER);
        this.context = context;
    }

    public void showDebugView(){
        isLogOpen = true;
        paramsFloatView = new WindowManager.LayoutParams();
        paramsFloatView.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;
        paramsFloatView.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        paramsFloatView.width = ZnkfUtil.dip2px(context, 50);
        paramsFloatView.height = ZnkfUtil.dip2px(context, 50);
        paramsFloatView.gravity = Gravity.LEFT | Gravity.TOP;
        paramsFloatView.x = 0;
        paramsFloatView.y = 100;
        windowManager.addView(floatDraftView, paramsFloatView);
        floatDraftView.setOnClickListener((v) -> showLogWindow());
        addSomeLogs();
    }

    private void showLogWindow(){

        View contentView = LayoutInflater.from(context).inflate(R.layout.znkf_log_window, null);
        paramsListView = new WindowManager.LayoutParams();
        paramsListView.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;
        paramsListView.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        paramsListView.width = ZnkfUtil.getWindowWidth(context) - ZnkfUtil.dip2px(context, 100);
        paramsListView.height = ZnkfUtil.getWindowHeight(context) * 2 / 3;
        paramsListView.x = 0;
        paramsListView.y = 0;
        windowManager.addView(contentView, paramsListView);

        ListView listView = contentView.findViewById(R.id.lv_log);
        ArrayAdapter adapter = new ArrayAdapter<>(context, R.layout.znkf_item_log_window, R.id.tv_log_content, logList);
        listView.setAdapter(adapter);
        contentView.findViewById(R.id.iv_close).setOnClickListener((v) -> windowManager.removeView(contentView));
        // 退出调试
        contentView.findViewById(R.id.btn_exit).setOnClickListener((v) -> {
            windowManager.removeView(floatDraftView);
            windowManager.removeView(contentView);
            isLogOpen = false;
            logList.clear();
        });
        // 清空日志
        contentView.findViewById(R.id.btn_clear_log).setOnClickListener((v) -> {
            logList.clear();
            adapter.notifyDataSetChanged();
        });
    }

    /**
     * 开启时添加一些必要的日志
     */
    private void addSomeLogs(){
        String appid = ConfigManager.getInstance().getConfig(ConfigManager.VOICE_APP_ID);
        String appkey = ConfigManager.getInstance().getConfig(ConfigManager.VOICE_APP_KEY);
        String ip = ConfigManager.getInstance().getConfig(ConfigManager.VOICE_IP_URL);
        String port = ConfigManager.getInstance().getConfig(ConfigManager.VOICE_PORT_URL);
        String syn_ip = ConfigManager.getInstance().getConfig(ConfigManager.VOICE_IP_SYNTHESIZE_URL);
        String syn_port = ConfigManager.getInstance().getConfig(ConfigManager.VOICE_PORT_SYNTHESIZE_URL);
        String hotPortStr = ConfigManager.getInstance().getConfig(ConfigManager.VOICE_HOT_PORT_URL);
        ZnkfLog.d(TAG, "RecognizerSdk init, appid = " + appid + ", appkey = " + appkey +
                ", recognize ip = " + ip + ", recognize port = " + port +
                ", synthesize ip = " + syn_ip + ", synthesize port = " + syn_port +
                ", hotPortStr = " + hotPortStr);
    }
}
