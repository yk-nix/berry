package com.vroad.app.berry.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person implements Parcelable, Serializable {
  private String name;
  private int age;

  protected Person(Parcel in) {
    name = in.readString();
    age = in.readInt();
  }

  public static final Creator<Person> CREATOR = new Creator<>() {
    @Override
    public Person createFromParcel(Parcel in) {
      return new Person(in);
    }

    @Override
    public Person[] newArray(int size) {
      return new Person[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(@NonNull Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeInt(age);
  }
}
