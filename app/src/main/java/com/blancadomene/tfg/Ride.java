package com.blancadomene.tfg;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.math.BigDecimal;
import java.util.Calendar;
import java.lang.String;
import java.util.UUID;

public class Ride {
    private UUID rideID;
    private String departurePoint;
    private Calendar departureDate; // startdate
    private String arrivalPoint;
    private Calendar arrivalDate;   // enddate
    private int availableSeats;
    private Calendar departureHour;
    private Calendar arrivalHour;
    private String driver;
    //private User driver;
    //ArrayList<User> passengers;
    private BigDecimal pricePerSeat =  BigDecimal.ZERO;

    public Ride(String depPoint, Calendar depDate, String arrPoint, Calendar arrDate, int avaSeats, BigDecimal price) {
        // TODO: check nulls
        this.rideID = UUID.randomUUID();
        this.departurePoint = depPoint;
        this.departureDate = depDate;
        this.arrivalPoint = arrPoint;
        this.arrivalDate = arrDate;
        this.availableSeats = avaSeats;
        // TODO: Driver and passengers assigned
        this.pricePerSeat = price;
    }
    public UUID getRideID() {
        return rideID;
    }

    public void setRideID(UUID rideID) {
        this.rideID = rideID;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getArrivalPoint() {
        return arrivalPoint;
    }

    public void setArrivalPoint(String arrivalPoint) {
        this.arrivalPoint = arrivalPoint;
    }

    public Calendar getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Calendar departureDate) {
        this.departureDate = departureDate;
    }

    public Calendar getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Calendar arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public View getRideCardView(Activity context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.layout_ride_card, null);
    }
}
