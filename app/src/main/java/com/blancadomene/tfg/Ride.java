package com.blancadomene.tfg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;

public class Ride {
    private boolean[] availableDaysOfWeek;
    private int availableSeats;
    private BigDecimal pricePerSeat;
    private Calendar arrivalHour;
    private String arrivalPoint;
    private Calendar departureHour;
    private String departurePoint;
    private Calendar endDate;
    private Calendar startDate;
    private User driver;
    private UUID rideID;


    public Ride(Calendar start, Calendar end, String depPoint, Calendar depHour, String arrPoint, Calendar arrHour, int avaSeats, BigDecimal price, User userDriver, boolean[] days) {
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
        this.availableDaysOfWeek = days;
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

    public boolean[] getAvailableDaysOfWeek() {
        return availableDaysOfWeek;
    }

    public void setAvailableDaysOfWeek(boolean[] week) {
        this.availableDaysOfWeek = week;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
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

        AvailableDaysOfWeek viewAv = view.findViewById(R.id.layout_ride_card_available_days_of_week);
        viewAv.setEnabledDaysOfWeek(this.getAvailableDaysOfWeek());
        viewAv.disableClick();

        text = view.findViewById(R.id.layout_ride_card_name);
        text.setText(getDriver().getName());

        return view;
    }
}
