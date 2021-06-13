package com.blancadomene.tfg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class Ride {
    private BigDecimal pricePerSeat;
    private boolean[] availableDaysOfWeek;
    private int availableSeats;
    private Calendar arrivalHour;
    private String arrivalPoint;
    private Calendar departureHour;
    private String departurePoint;
    private Calendar endDate;
    private Calendar startDate;
    private User driver;
    private String rideID;


    public Ride(String id, User userDriver, Calendar start, Calendar end, String depPoint, Calendar depHour, String arrPoint, Calendar arrHour, int avaSeats, BigDecimal price, boolean[] days) {
        this.rideID = id;
        this.driver = userDriver;
        this.startDate = start;
        this.endDate = end;
        this.departurePoint = depPoint;
        this.departureHour = depHour;
        this.arrivalPoint = arrPoint;
        this.arrivalHour = arrHour;
        this.availableSeats = avaSeats;
        this.pricePerSeat = price;
        this.availableDaysOfWeek = days;
    }

    public Ride(JsonObject jsonObj) {
        this.rideID = jsonObj.get("id").getAsString();
        this.driver = getUserInfo(jsonObj.get("driver").getAsString());
        this.startDate = formatStringToCalendar(jsonObj.get("startDate").getAsString());
        this.endDate = formatStringToCalendar(jsonObj.get("endDate").getAsString());
        this.departurePoint = jsonObj.get("departurePoint").getAsString();
        this.departureHour = formatStringToCalendarHour(jsonObj.get("departureHour").getAsString());
        this.arrivalPoint = jsonObj.get("arrivalPoint").getAsString();
        this.arrivalHour = formatStringToCalendarHour(jsonObj.get("arrivalHour").getAsString());
        this.availableSeats = jsonObj.get("availableSeats").getAsInt();
        this.pricePerSeat = jsonObj.get("pricePerSeat").getAsBigDecimal();
        this.availableDaysOfWeek = formatIntToBoolean(jsonObj.get("availableDaysOfWeek").getAsInt());
    }

    private Calendar formatStringToCalendar(String date) {
        String[] splitDate = date.split("-");
        return new GregorianCalendar(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2]));
    }

    private Calendar formatStringToCalendarHour(String date) {
        String[] splitDate = date.split(":");
        return new GregorianCalendar(2000, 01, 01, Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2]));
    }

    private User getUserInfo(String id) {
        String objEmail;
        String objName;
        String objSurname;
        Calendar objBirthDate;
        String objCar;
        String objMobilePhone;
        String objPreferences;
        String objImage;
        
        APIClient client = new APIClient(MainActivity.ctx.getString(R.string.backend_address));

        APIClient.Request request = new APIClient.Request();
        request.method = "GET";
        request.query = String.format("users/info?ID=%s", id);

        try {
            APIClient.Response response = client.execute(request).get();

            if (response.retcode == 200) {
                final JsonObject obj = response.json.getAsJsonObject();
                objEmail = obj.get("email").getAsString();
                objName = obj.get("name").getAsString();
                objSurname = obj.get("surname").getAsString();
                objBirthDate = formatStringToCalendar(obj.get("birthdate").getAsString());
                objCar = obj.get("car").getAsString();
                objMobilePhone = obj.get("mobilephone").getAsString();
                objPreferences = obj.get("preferences").getAsString();
                objImage = obj.get("image").getAsString();

                return new User(id, objName, objSurname, objBirthDate, objEmail, objMobilePhone, objPreferences, objCar, objImage);
            } else {
                System.out.println("Failed to get user.");
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getRideID() {
        return rideID;
    }

    public void setRideID(String rideID) {
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

    private boolean[] formatIntToBoolean(int daysWeek) {
        boolean[] processed_boolean_array = new boolean[7];
        for (int i = 0; i < 7; i++) {
            processed_boolean_array[i] = ((daysWeek >> i) & 1) == 1;
        }

        return processed_boolean_array;
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

        ImageView imView = view.findViewById(R.id.layout_ride_card_driver_image);
        new DownloadImageTask(imView).execute(getDriver().getPhoto());

        return view;
    }
}
