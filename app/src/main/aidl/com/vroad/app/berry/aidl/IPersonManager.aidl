// IPersonManager.aidl
package com.vroad.app.berry.aidl;
import com.vroad.app.berry.data.pojo.Person;

// Declare any non-default types here with import statements

interface IPersonManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
  List<Person> getList();
  void add(in Person person);
}