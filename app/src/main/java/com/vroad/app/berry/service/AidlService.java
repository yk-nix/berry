package com.vroad.app.berry.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.vroad.app.basic.BasicService;
import com.vroad.app.berry.aidl.IPersonManager;
import com.vroad.app.berry.data.pojo.Person;

import java.util.ArrayList;
import java.util.List;

public class AidlService extends BasicService {
  public AidlService() {
    super(true);
  }

  private final List<Person> list = new ArrayList<>();

  private final Binder binder = new IPersonManager.Stub() {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
    }

    @Override
    public List<Person> getList() throws RemoteException {
      return list;
    }

    @Override
    public void add(Person person) throws RemoteException {
      list.add(person);
    }
  };

  @Override
  public IBinder onBind(Intent intent) {
    super.onBind(intent);
    return binder;
  }
}
