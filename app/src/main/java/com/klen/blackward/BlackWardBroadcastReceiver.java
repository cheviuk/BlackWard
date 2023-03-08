package com.klen.blackward;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BlackWardBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // TODO: something
            // Intent serviceIntent = new Intent(context, BlackWardWardService.class);
            // context.startForegroundService(serviceIntent);
        }
    }
}
