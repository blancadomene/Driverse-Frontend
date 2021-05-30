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
    private UUID userID;

    public User(String name, String surName, String email, String phone) {
        // TODO: Check valids: birthday, phone and email
        this.userID = UUID.randomUUID();
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.mobilePhone = phone;
        this.preferences = "No preferences added.";
        this.car = "No car added.";
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
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

/*
    public int calculateAge(Calendar birthDate) {
        LocalDate birthLocalDate = LocalDate.ofInstant(birthDate.toInstant(), ZoneId.systemDefault());

        if ((birthLocalDate != null)) {
            return Period.between(birthLocalDate, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }*/
}
