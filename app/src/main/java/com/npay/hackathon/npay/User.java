package com.npay.hackathon.npay;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016-09-08.
 */

public class User {
    private int userImage;
    private String userName;
    private boolean isOk;
    private int step;
    private boolean isChecked = false;
    private int pk;

    public User(int userImage, String userName, boolean isOk, int step, boolean isChecked, int pk) {
        this.userImage = userImage;
        this.userName = userName;
        this.isOk = isOk;
        this.step = step;
        this.isChecked = isChecked;
        this.pk = pk;
    }

    public User() {
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "User{" +
                "userImage=" + userImage +
                ", userName='" + userName + '\'' +
                ", isOk=" + isOk +
                ", step=" + step +
                ", isChecked=" + isChecked +
                ", pk=" + pk +
                '}';
    }
}
