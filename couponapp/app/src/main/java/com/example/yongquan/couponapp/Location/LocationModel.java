package com.example.yongquan.couponapp.Location;

/**
 * Created by DELL on 9/11/2018.
 */

public class LocationModel {
    String name1;
    String name2;
    String count;

    public LocationModel(String name1, String name2, String count) {
        this.name1 = name1;
        this.name2 = name2;
        this.count = count;
    }



    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
