package com.vroad.app.berry.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.vroad.app.libui.base.BasicService;
import com.vroad.app.berry.aidl.IPersonManager;
import com.vroad.app.berry.data.pojo.Person;

import java.util.ArrayList;
import java.util.List;

public class AidlService extends BasicService {
  public AidlService() {
    super(true);
  }

  @Override
  protected IBinder getBinder(Intent intent) {
    return binder;
  }

  private final List<Person> list = new ArrayList<>();

  private final Binder binder = new IPersonManager.Stub() {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) {
    }

    @Override
    public List<Person> getList() {
      return list;
    }

    @Override
    public void add(Person person) {
      list.add(person);
    }
  };
}
