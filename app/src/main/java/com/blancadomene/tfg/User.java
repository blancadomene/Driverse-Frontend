package com.blancadomene.tfg;

import android.media.Image;

import java.util.UUID;

public class User {
    private UUID userID;
    private String name;
    private String surName;
    private String email; // TODO: mandar email con datos de viaje? Check valid
    private String mobilePhone; // TODO: check valid
    private Image photo;
    private String car;

    public User(String name, String surName, String email, String phone) {
        this.userID = UUID.randomUUID();
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.mobilePhone = phone;
        // TODO: Default photo
        this.car = "No car added.";
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
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


}
