package com.klen.blackward;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.IBinder;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class BlackWardTileService extends TileService {
    public static final String TAG = BlackWardTileService.class.getSimpleName();
    Context applicationContext = null;
    Intent serviceIntent = null;
    private Icon activeIcon = null;
    private Icon disabledIcon = null;

    private void startWardService() {
        if (applicationContext == null) {
            Log.d(TAG, "applicationContext is null");
            return;
        }
        if (serviceIntent == null) {
            Log.d(TAG, "serviceIntent is null");
            return;
        }
    }

    private void stopWardService() {
        if (applicationContext == null) {
            Log.d(TAG, "applicationContext is null");
            return;
        }
        if (serviceIntent == null) {
            Log.d(TAG, "serviceIntent is null");
            return;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: " + intent);
        if (applicationContext == null) {
            applicationContext = getBaseContext();
        }
        if (serviceIntent == null) {
            serviceIntent = new Intent(getBaseContext(), BlackWardWardService.class);
        }
        if (activeIcon == null) {
            activeIcon = Icon.createWithResource(getApplicationContext(), R.drawable.on);
        }

        if (disabledIcon == null) {
            disabledIcon = Icon.createWithResource(getApplicationContext(), R.drawable.off);
        }
        return super.onBind(intent);
    }

    @Override
    public void onTileAdded() {
        Log.d(TAG, "onTileAdded");
    }

    @Override
    public void onStartListening() {
        Tile tile = getQsTile();
        Log.d(TAG, "onStartListening: " + tile.getLabel());
    }

    @Override
    public void onClick() {
        Tile tile = getQsTile();
        Icon icon;
        String log = "onClick: state changed form " + tile.getState();

        if (tile.getState() == Tile.STATE_INACTIVE) {
            startWard();
            tile.setState(Tile.STATE_ACTIVE);
            icon = activeIcon;
        } else {
            stopWard();
            tile.setState(Tile.STATE_INACTIVE);
            icon = disabledIcon;
        }

        tile.setIcon(icon);
        tile.updateTile();
        Log.d(TAG, log + " to " + tile.getState());
    }

    private void startWard() {
        startService(serviceIntent);
    }

    private void stopWard() {
        stopService(serviceIntent);
    }
}