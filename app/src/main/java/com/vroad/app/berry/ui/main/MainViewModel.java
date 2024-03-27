package com.vroad.app.berry.ui.main;

import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.vroad.app.berry.data.pojo.User;
import com.vroad.app.berry.workers.TestWorker;
import com.vroad.app.libui.base.AbstractApplication;
import com.vroad.app.libui.base.BasicViewModel;

import java.util.List;

import lombok.Getter;

public class MainViewModel extends BasicViewModel {
  public static String WORKER_TAG_TEST = "TEST_WORKER";
  @Getter
  private LiveData<List<WorkInfo>> workInfo;
  private final WorkManager workManager;

  public MainViewModel(AbstractApplication application) {
    super(application);
    workManager = WorkManager.getInstance(application.getApplicationContext());
    workInfo = workManager.getWorkInfosByTagLiveData(WORKER_TAG_TEST);
  }

  public void startWorker() {
    workManager.beginUniqueWork("TEST_WORK", ExistingWorkPolicy.REPLACE, new OneTimeWorkRequest.Builder(TestWorker.class)
            .setInputData(new Data.Builder().putByteArray(User.TAG, new User("yui", "cool").toBytes()).build())
            .build())
        .then(new OneTimeWorkRequest.Builder(TestWorker.class)
            .addTag(WORKER_TAG_TEST)
            .build())
        .enqueue();
  }
}
