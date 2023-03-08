package com.klen.blackward;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class BlackWardWardService extends Service {
    public static final String TAG = BlackWardWardService.class.getSimpleName();
    private SensorManager sensorManager = null;
    private Sensor significantMotionSensor = null;
    private SensorEventListener sensorEventListener = null;

    private void listenSensor() {
        if (sensorManager == null) {
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        }
        if (significantMotionSensor == null) {
            significantMotionSensor = sensorManager
                    .getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        }

        if (sensorEventListener == null) {
            sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    Log.d(TAG, String.format("onSensorChanged: x=%.3f, y=%.3f, z=%.3f",
                            event.values[0], event.values[1], event.values[2]));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    Log.d(TAG, String.format("onAccuracyChanged: accuracy=%d", accuracy));
                }
            };
        }
        sensorManager.registerListener(
                sensorEventListener,
                significantMotionSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String CHANNEL_ID = "Foreground Service ID";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_LOW
        );

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentText("Service is running")
                .setContentTitle("Service enabled")
                .setSmallIcon(R.drawable.ic_launcher_background);

        startForeground(1001, notification.build());

        listenSensor();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
