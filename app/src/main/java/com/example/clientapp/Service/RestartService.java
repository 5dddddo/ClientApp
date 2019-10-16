package com.example.clientapp.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("000 RestartService", "RestartService called : " + intent.getAction());

        /**
         * 서비스 죽일때 알람으로 다시 서비스 등록
         */
        if (intent.getAction().equals("ACTION.RESTART.PersistentService")) {

            Log.i("000 RestartService", "ACTION.RESTART.PersistentService ");

            Intent i = new Intent(context, PersistentService.class);
            context.startService(i);
        }

        /**
         * 폰 재시작 할때 서비스 등록
         */
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            Log.i("RestartService", "ACTION_BOOT_COMPLETED");
            Intent i = new Intent(context, PersistentService.class);
            context.startService(i);

        }
    }
}



