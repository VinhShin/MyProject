package com.example.yongquan.couponapp.ListCoupon;

/**
 * Created by DELL on 9/12/2018.
 */

public class CouponModel {
    String title;
    String content;
    String count;
    boolean isTitle;

    public CouponModel(String title, String content, String count, boolean isTitle) {
        this.title = title;
        this.content = content;
        this.count = count;
        this.isTitle = isTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }
}
