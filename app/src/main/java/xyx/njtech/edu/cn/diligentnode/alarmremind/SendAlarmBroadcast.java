package xyx.njtech.edu.cn.diligentnode.alarmremind;

import android.app.Activity;
import android.content.Intent;

public class SendAlarmBroadcast {

    public static void startAlarmService(Activity activity){
        Intent startAlarmServiceIntent = new Intent(activity,AlarmServiceBroadcastReceiver.class);
        activity.sendBroadcast(startAlarmServiceIntent,null);
    }
}
