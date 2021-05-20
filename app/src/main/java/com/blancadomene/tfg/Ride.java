package com.blancadomene.tfg;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.math.BigDecimal;
import java.util.Calendar;

public class Ride {
    private String rideID;
    private String departurePoint;
    private Calendar departureDate;
    private String arrivalPoint;
    private Calendar arrivalDate;
    private int availableSeats;
    private String driver;
    //private User driver;
    //ArrayList<User> passengers;
    private BigDecimal pricePerSeat =  BigDecimal.ZERO;

    public Ride(String id, String depPoint, Calendar depDate, String arrPoint, Calendar arrDate, int avaSeats, BigDecimal price) {
        // TODO: check nulls
        this.rideID = id;
        this.departurePoint = depPoint;
        this.departureDate = depDate;
        this.arrivalPoint = arrPoint;
        this.arrivalDate = arrDate;
        this.availableSeats = avaSeats;
        // TODO: Driver and passengers assigned
        this.pricePerSeat = price;
    }
    public String getRideID() {
        return rideID;
    }

    public void setRideID(String rideID) {
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

    public CardView getRideCardView(Activity context) { // TODO: completar resto de campos de la card
        CardView card = createBlankRideCard(context);
        TextView departureDateTV = new TextView(context);
        TextView departurePointTV = new TextView(context);
        TextView arrivalDateTV = new TextView(context);
        TextView arrivalPointTV = new TextView(context);

        // Main vertical layout
        LinearLayout verticalLayout = new LinearLayout(context);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        // Top horizontal layout
        LinearLayout horizontalTopLayout = new LinearLayout(context);
        horizontalTopLayout.setOrientation(LinearLayout.HORIZONTAL);

        //LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //departureDateTV.setLayoutParams(lparams);
        departureDateTV.setTextSize(20);
        departureDateTV.setText(departureDate.HOUR_OF_DAY + ":" + departureDate.MINUTE);
        horizontalTopLayout.addView(departureDateTV);

        departurePointTV.setTextSize(20);
        departurePointTV.setText(departurePoint);
        horizontalTopLayout.addView(departurePointTV);


        // Bottom horizontal layout
        LinearLayout horizontalBotLayout = new LinearLayout(context);
        horizontalBotLayout.setOrientation(LinearLayout.HORIZONTAL);

        arrivalDateTV.setTextSize(20);
        arrivalDateTV.setText(arrivalDate.HOUR_OF_DAY + ":" + arrivalDate.MINUTE);
        horizontalBotLayout.addView(arrivalDateTV);

        arrivalPointTV.setTextSize(20);
        arrivalPointTV.setText(arrivalPoint);
        horizontalBotLayout.addView(arrivalPointTV);

        verticalLayout.addView(horizontalTopLayout);
        verticalLayout.addView(horizontalBotLayout);
        card.addView(verticalLayout);
        return card;

        // hola que tal
    }

    private CardView createBlankRideCard(Activity context) {
        CardView card = new CardView(context);

        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = dpToPx(context, 8);
        layoutparams.setMargins(margin, margin, margin, margin);
        card.setLayoutParams(layoutparams);
        card.setRadius(15);
        card.setPadding(25, 25, 25, 25);
        card.setContentPadding(25, 25, 25, 25);

        card.setCardElevation(20);

        return card;
    }

    private int dpToPx(Activity context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
