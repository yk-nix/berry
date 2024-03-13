package com.vroad.app.berry.workers;

import android.content.Context;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.io.FileUtils;
import com.vroad.app.berry.data.pojo.User;

public class TestWorker extends Worker {
  public TestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
  }

  @NonNull
  @Override
  public Result doWork() {
    //Context appContext = getApplicationContext();
    Data data = getInputData();
    SystemClock.sleep(20000);
    XLog.i("--- Worker is doing its job %s : %s----", Thread.currentThread(), FileUtils.readAs(getInputData().getByteArray(User.TAG)));
    return Result.success(new Data.Builder().putByteArray(User.TAG, new User("lisa", "cool").toBytes()).build());
  }
}
