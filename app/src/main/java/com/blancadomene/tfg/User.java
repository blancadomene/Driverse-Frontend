package com.blancadomene.tfg;

import android.media.Image;

import java.util.Calendar;
import java.util.UUID;

public class User {
    private Image photo;
    private Calendar birthDate;
    private String car;
    private String email;
    private String mobilePhone;
    private String name;
    private String preferences;
    private String surName;
    private String userID;

    public User(String id, String name, String surName, Calendar bDate, String email, String phone, String pref, String car) {
        this.userID = id;
        this.name = name;
        this.surName = surName;
        this.birthDate = bDate;
        this.email = email;
        this.mobilePhone = phone;
        this.preferences = pref;
        this.car = car;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public int calculateAge() {
        Calendar birthDate = getBirthDate();
        if ((birthDate != null)) {
            Calendar currentDate = Calendar.getInstance();
            int diff = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
            if (birthDate.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH) ||
                    (birthDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                            birthDate.get(Calendar.DAY_OF_MONTH) > currentDate.get(Calendar.DAY_OF_MONTH)))
                diff--;
            return diff;
        } else return 0;
    }
}
