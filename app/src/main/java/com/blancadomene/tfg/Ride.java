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

import com.blancadomene.tfg.databinding.LayoutAvailableDaysOfWeekBinding;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.lang.String;
import java.util.UUID;

public class Ride {
    private UUID rideID;
    private Calendar startDate;
    private Calendar endDate;
    private Calendar departureHour;
    private Calendar arrivalHour;
    private String departurePoint;
    private String arrivalPoint;
    private int availableSeats;
    private BigDecimal pricePerSeat;
    private User driver;
    // private ArrayList<User> passengersArray; // TODO: add passengers & passengers < availableSeats & deletePassenger if cancelled ride
    private boolean[] week;

    public Ride(Calendar start, Calendar end, String depPoint, Calendar depHour, String arrPoint, Calendar arrHour, int avaSeats, BigDecimal price, User userDriver) {
        // TODO: check nulls
        // TODO: check start no sea menor que end, y que tampoco sean antes de hoy
        // TODO: check departureHour < arrivalHour
        // TODO. availableSeats < 8
        this.rideID = UUID.randomUUID();
        this.startDate = start;
        this.endDate = end;
        this.departurePoint = depPoint;
        this.departureHour = depHour;
        this.arrivalPoint = arrPoint;
        this.arrivalHour = arrHour;
        this.availableSeats = avaSeats;
        this.pricePerSeat = price;
        this.driver = userDriver;
        //passengersArray = new ArrayList<>();
        week = new boolean[7];
    }

    public UUID getRideID() {
        return rideID;
    }

    public void setRideID(UUID rideID) {
        this.rideID = rideID;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getDepartureHour() {
        return departureHour;
    }

    public void setDepartureHour(Calendar departureHour) {
        this.departureHour = departureHour;
    }

    public Calendar getArrivalHour() {
        return arrivalHour;
    }

    public void setArrivalHour(Calendar arrivalHour) {
        this.arrivalHour = arrivalHour;
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

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public BigDecimal getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(BigDecimal pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

/*    public ArrayList<User> getPassengersArray() { TODO
        return passengersArray;
    }

    public void setPassengersArray(ArrayList<User> passengersArray) {
        this.passengersArray = passengersArray;
    }*/

    public boolean[] getWeek() {
        return week;
    }

    public void setWeek(boolean[] week) {
        this.week = week;
    }

    public View getRideCardView(Activity context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_ride_card, null);

        // Get view items and set text
        TextView text;
        text = view.findViewById(R.id.layout_ride_card_departure_hour);
        text.setText(String.format(
                "%02d:%02d",
                getDepartureHour().get(Calendar.HOUR_OF_DAY),
                getDepartureHour().get(Calendar.MINUTE)));
        text = view.findViewById(R.id.layout_ride_card_departure_point);
        text.setText(getDeparturePoint());
        text = view.findViewById(R.id.layout_ride_card_arrival_hour);

        text.setText(String.format(
                "%02d:%02d",
                getArrivalHour().get(Calendar.HOUR_OF_DAY),
                getArrivalHour().get(Calendar.MINUTE)));
        text = view.findViewById(R.id.layout_ride_card_arrival_point);
        text.setText(getArrivalPoint());
        text = view.findViewById(R.id.layout_ride_card_price_per_seat);
        text.setText(getPricePerSeat().toString() + "€");

        // TODO: set daysOfWeek
        // TODO: set photo (add name? rating?)
        // TODO: set name
        text = view.findViewById(R.id.layout_ride_card_name);
        text.setText(getDriver().getName());

        return view;
    }
}
