package com.klen.blackward;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BlackWardWorker extends Worker {
    public static final String TAG = BlackWardWardService.class.getSimpleName();
    public BlackWardWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "Work success");
        return Result.retry();
    }
}
