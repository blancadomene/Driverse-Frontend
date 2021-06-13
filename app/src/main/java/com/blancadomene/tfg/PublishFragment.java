package com.blancadomene.tfg;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.UUID;

public class PublishFragment extends Fragment {
    private boolean[] pfAvailableDaysOfWeek;
    private EditText eTextNP;
    private EditText eTextTP;
    private String pfArrivalLatLng;
    private String pfArrivalPoint;
    private String pfAvailableSeats;
    private String pfDepartureHour;
    private String pfDepartureLatLng;
    private String pfDeparturePoint;
    private String pfEndDate;
    private String pfPricePerSeat;
    private String pfStartDate;
    private View view;

    public PublishFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publish, container, false);

        eTextTP = view.findViewById(R.id.fragment_publish_departure_hour);
        eTextTP.setOnClickListener(v -> showTimePickerDialog());

        EditText eTextDPStart = view.findViewById(R.id.fragment_publish_start_date);
        eTextDPStart.setOnClickListener(v -> showDatePickerDialog(R.id.fragment_publish_start_date));

        EditText eTextDPEnd = view.findViewById(R.id.fragment_publish_end_date);
        eTextDPEnd.setOnClickListener(v -> showDatePickerDialog(R.id.fragment_publish_end_date));

        EditText eTextGMapDepPoint = view.findViewById(R.id.fragment_publish_departure_point);
        eTextGMapDepPoint.setOnClickListener(v -> switchToGoogleMapsFragment(R.id.fragment_publish_departure_point));

        EditText eTextGMapArrPoint = view.findViewById(R.id.fragment_publish_arrival_point);
        eTextGMapArrPoint.setOnClickListener(v -> switchToGoogleMapsFragment(R.id.fragment_publish_arrival_point));

        eTextNP = view.findViewById(R.id.fragment_publish_available_seats);
        eTextNP.setOnClickListener(v -> showNumberPickerDialog());

        // Get info from DB
        // Argument: bundle from previous activity
        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;

        Button button = view.findViewById(R.id.fragment_publish_travel_button);
        button.setOnClickListener(v -> publishNewRide(view, activity.getExtraData()));

        return view;
    }

    private void switchToGoogleMapsFragment(int edTextID) {
        MapsFragment fragment = new MapsFragment();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, fragment, null)
                .setReorderingAllowed(true)
                .addToBackStack("MapsView")
                .commit();

        fragment.setFragmentCallBacks(data -> {
            if (data != null) {
                String[] parts = data.split("__");
                EditText edView = view.findViewById(edTextID);
                edView.setText(parts[1]);

                if (edTextID == R.id.fragment_publish_departure_point)
                    pfDepartureLatLng = parts[2];
                else
                    pfArrivalLatLng = parts[2];
            }
        });
    }

    private void showDatePickerDialog(int edTextID) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        EditText edView = view.findViewById(edTextID);
        @SuppressLint("DefaultLocale") DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                (dp, dpYear, dpMonth, dpDay) -> edView.setText(String.format("%02d/%02d/%02d", dpDay, dpMonth, dpYear)), year, month, day);
        datePicker.show();
    }

    @SuppressLint("SetTextI18n")
    private void showNumberPickerDialog() {
        final NumberPicker np = new NumberPicker(getActivity());
        np.setMaxValue(8);
        np.setMinValue(1);
        np.setValue(1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(np);
        builder.setTitle("Number of available seats");
        builder.setMessage("Choose a value :");
        builder.setPositiveButton("OK", (dialog, which) -> eTextNP.setText(Integer.toString(np.getValue())));
        builder.setNegativeButton("CANCEL", (dialog, which) -> {

        });
        builder.create();
        builder.show();
    }

    private void showTimePickerDialog() {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);

        @SuppressLint("DefaultLocale") TimePickerDialog timePicker = new TimePickerDialog(getActivity(),
                (tp, tpHour, tpMinutes) -> eTextTP.setText(String.format("%02d:%02d", tpHour, tpMinutes)), hour, minutes, true);
        timePicker.show();
    }

    private void publishNewRide(View view, Bundle extrasBundle) {
        String EXTRA_ID = extrasBundle.getString("EXTRA_ID");
        retrieveDataFromEditTexts(view);

        AsyncTask.execute(() -> {
            try {
                URL url = new URL(String.format("http://%s/rides/info", getString(R.string.backend_address)));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                
                String processedArrivalHour = estimateArrivalHour(pfDepartureHour);
                String processedDepartureLatLng = formatLatLngString(pfDepartureLatLng);
                String processedArrivalLatLng = formatLatLngString(pfArrivalLatLng);
                int processedAvailableDays = formatBooleanToInt(pfAvailableDaysOfWeek);
                @SuppressLint("DefaultLocale") String myData = String.format("{\"id\": \"%s\", \"driver\": \"%s\", \"departurePoint\": \"%s\", \"departureLatLng\": \"%s\", \"departureHour\": \"%s\", " +
                                "\"arrivalPoint\": \"%s\", \"arrivalLatLng\": \"%s\", \"arrivalHour\": \"%s\", \"availableSeats\": \"%s\", \"pricePerSeat\": \"%s\", \"startDate\": " +
                                "\"%s\", \"endDate\": \"%s\", \"availableDaysOfWeek\": %d}",
                        UUID.randomUUID(), EXTRA_ID, pfDeparturePoint, processedDepartureLatLng, pfDepartureHour, pfArrivalPoint, processedArrivalLatLng, processedArrivalHour,
                        pfAvailableSeats, pfPricePerSeat, pfStartDate, pfEndDate, processedAvailableDays);
                connection.setDoOutput(true); // true for POST and PUT
                connection.getOutputStream().write(myData.getBytes());

                if (connection.getResponseCode() != 200) {
                    Context context = getActivity();
                    CharSequence text = "Incomplete ride details.";
                    int duration = Toast.LENGTH_SHORT;

                    getActivity().runOnUiThread(() -> {
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    });
                }
                connection.disconnect();

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        });

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().commit();
    }

    private void retrieveDataFromEditTexts(View view) {
        EditText edText;
        edText = view.findViewById(R.id.fragment_publish_start_date);
        pfStartDate = edText.getText().toString();

        edText = view.findViewById(R.id.fragment_publish_end_date);
        pfEndDate = edText.getText().toString();

        edText = view.findViewById(R.id.fragment_publish_departure_point);
        pfDeparturePoint = edText.getText().toString();

        edText = view.findViewById(R.id.fragment_publish_arrival_point);
        pfArrivalPoint = edText.getText().toString();

        edText = view.findViewById(R.id.fragment_publish_departure_hour);
        pfDepartureHour = edText.getText().toString();

        edText = view.findViewById(R.id.fragment_publish_available_seats);
        pfAvailableSeats = edText.getText().toString();

        edText = view.findViewById(R.id.fragment_publish_price_per_seat);
        pfPricePerSeat = edText.getText().toString();

        AvailableDaysOfWeek viewAv = view.findViewById(R.id.fragment_publish_days_of_week_view);
        pfAvailableDaysOfWeek = viewAv.getSelectedDaysOfWeek();
    }

    // This method uses straight line distance and a constant average speed to calculate the estimated time of arrival
    // Future work: use Google Matrix API
    private String estimateArrivalHour(String DepartureHour) throws ParseException {
        Location locDep = getLocationFromString(pfDepartureLatLng);
        Location locArr = getLocationFromString(pfArrivalLatLng);

        float distanceInM = locDep.distanceTo(locArr);
        float speedInKmH = 60;
        float time = distanceInM / ((speedInKmH / 60) * 1000);

        // String to date, then adds estimated time to departure time
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Calendar gc = new GregorianCalendar();
        gc.setTime(Objects.requireNonNull(df.parse(DepartureHour)));
        gc.add(Calendar.MINUTE, Math.round(time));

        @SuppressLint("DefaultLocale") String res = String.format(
                "%02d:%02d",
                gc.get(Calendar.HOUR_OF_DAY),
                gc.get(Calendar.MINUTE));
        return res;
    }

    // Transforms a preprocessed string to a Location data type
    private Location getLocationFromString(String psStringLatLng) {
        String tmp = formatLatLngString(psStringLatLng);
        String[] latLng = tmp.split(",");

        Location loc = new Location("");
        loc.setLatitude(Double.parseDouble(latLng[0]));
        loc.setLongitude(Double.parseDouble(latLng[1]));

        return loc;
    }

    // Preprocessing: splits string, then deletes lat/lng: ( ) from both substrings
    private String formatLatLngString(String psStringLatLng) {
        return psStringLatLng.substring(10, psStringLatLng.length() - 1);
    }

    private int formatBooleanToInt(boolean[] daysWeek) {
        int processed_int = 0;
        for (int i = 6; i >= 0; i--) {
            processed_int |= (daysWeek[i] ? 1:0);
            processed_int <<= 1;
        }
        return processed_int;
    }

}