package com.dingshuwang.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tx on 2017/7/26.
 */

public class TestItem implements Parcelable {


    private String id;
    private String name;

    protected TestItem(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<TestItem> CREATOR = new Creator<TestItem>() {
        @Override
        public TestItem createFromParcel(Parcel in) {
            return new TestItem(in);
        }

        @Override
        public TestItem[] newArray(int size) {
            return new TestItem[size];
        }
    };

    @Override
    public String toString() {
        return "TestItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
