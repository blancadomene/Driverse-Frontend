package com.blancadomene.tfg;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class SearchFragment extends Fragment {
    private boolean[] sfAvailableDaysOfWeek;
    private ArrayList<Ride> searches = new ArrayList<>();
    private EditText eTextDPStart;
    private EditText eTextDPEnd;
    private EditText eTextNP;
    private EditText eTextTP;
    private LinearLayout linearLayout = null;
    private String sfArrivalLatLng;
    private String sfArrivalPoint;
    private String sfArrivalPointRadius;
    private String sfNumberPassengers;
    private String sfDepartureHour;
    private String sfDepartureLatLng;
    private String sfDeparturePoint;
    private String sfDeparturePointRadius;
    private String sfEndDate;
    private String sfStartDate;
    private View view = null;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        linearLayout = view.findViewById(R.id.fragment_search_result_container);

        eTextDPStart = view.findViewById(R.id.fragment_search_start_date);
        eTextDPStart.setOnClickListener(v -> showDatePickerDialog(eTextDPStart));

        eTextDPEnd = view.findViewById(R.id.fragment_search_end_date);
        eTextDPEnd.setOnClickListener(v -> showDatePickerDialog(eTextDPEnd));

        EditText eTextGMapDepPoint = view.findViewById(R.id.fragment_search_departure_point);
        eTextGMapDepPoint.setOnClickListener(v -> switchToGoogleMapsFragment(R.id.fragment_search_departure_point));

        EditText eTextGMapArrPoint = view.findViewById(R.id.fragment_search_arrival_point);
        eTextGMapArrPoint.setOnClickListener(v -> switchToGoogleMapsFragment(R.id.fragment_search_arrival_point));

        eTextNP = view.findViewById(R.id.fragment_search_passengers_number);
        eTextNP.setOnClickListener(v -> showNumberPickerDialog());

        eTextTP = view.findViewById(R.id.fragment_search_departure_hour);
        eTextTP.setOnClickListener(v -> showTimePickerDialog());

        Button button = view.findViewById(R.id.fragment_search_travel_button);
        button.setOnClickListener(v -> getMatchingRides(v));

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

                if (edTextID == R.id.fragment_search_departure_point)
                    sfDepartureLatLng = parts[2];
                else
                    sfArrivalLatLng = parts[2];
            }
        });
    }


    private void showDatePickerDialog(EditText edText) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("DefaultLocale") DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                (dp, dpYear, dpMonth, dpDay) -> edText.setText(String.format("%02d/%02d/%02d", dpDay, dpMonth, dpYear)),
                year,
                month,
                day);
        datePicker.show();
    }

    @SuppressLint("SetTextI18n")
    private AlertDialog showNumberPickerDialog() {
        final NumberPicker np = new NumberPicker(getActivity());
        np.setMaxValue(8);
        np.setMinValue(1);
        np.setValue(1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(np);
        builder.setTitle("Number of passengers");
        builder.setMessage("Choose a value :");
        builder.setPositiveButton("OK", (dialog, which) -> {
            eTextNP.setText(Integer.toString(np.getValue()));
        });
        builder.setNegativeButton("CANCEL", (dialog, which) -> {

        });
        builder.create();
        return builder.show();
    }

    private void showTimePickerDialog() {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);

        @SuppressLint("DefaultLocale") TimePickerDialog timePicker = new TimePickerDialog(getActivity(),
                (tp, tpHour, tpMinutes) -> eTextTP.setText(String.format("%02d:%02d", tpHour, tpMinutes)),
                hour,
                minutes,
                true);
        timePicker.show();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void getMatchingRides(View view) {
        searches.clear();
        linearLayout.removeAllViews();
        retrieveDataFromEditTexts();

        String processedDepartureLatLng = formatLatLngString(sfDepartureLatLng);
        String processedArrivalLatLng = formatLatLngString(sfArrivalLatLng);
        int processedAvailableDays = formatBooleanToInt(sfAvailableDaysOfWeek);

        APIClient client = new APIClient(getString(R.string.backend_address));

        APIClient.Request request = new APIClient.Request();
        request.method = "GET";
        request.query = String.format("rides/info?StartDate=%s&EndDate=%s" +
                        "&DepartureLatLng=%s&DeparturePointRadius=%s&ArrivalLatLng=%s&ArrivalPointRadius=%s" +
                        "&DepartureHour=%s&NumberOfSeats=%s&AvailableDaysOfWeek=%d",
                sfStartDate, sfEndDate, processedDepartureLatLng, sfDeparturePointRadius,
                processedArrivalLatLng, sfArrivalPointRadius, sfDepartureHour, sfNumberPassengers, processedAvailableDays);

        try {
            APIClient.Response response = client.execute(request).get();

            if (response.retcode == 200) {
                if (response.json.isJsonArray()) {
                    final JsonArray values = response.json.getAsJsonArray();
                    for (final JsonElement value : values) {
                        final JsonObject obj = value.getAsJsonObject();
                        Ride r = new Ride(obj);
                        System.out.println("Mete r en searches");
                        searches.add(r);
                    }
                }
            } else {
                Context context = getActivity();
                CharSequence text = "Unable to connect.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Print showresultlist");
        showResultList();
    }

    public void showResultList() {
        for (int i = 0; i < searches.size(); i++) {
            System.out.println("Valor de i: " + i);
            Ride ride = searches.get(i);
            View card = getInstrumentRideCard(ride);
            linearLayout.addView(card);
        }
    }

    private void retrieveDataFromEditTexts() {
        EditText edText;
        edText = getActivity().findViewById(R.id.fragment_search_start_date);
        sfStartDate = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_end_date);
        sfEndDate = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_departure_point);
        sfDeparturePoint = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_departure_point_radius);
        sfDeparturePointRadius = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_arrival_point);
        sfArrivalPoint = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_arrival_point_radius);
        sfArrivalPointRadius = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_departure_hour);
        sfDepartureHour = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_passengers_number);
        sfNumberPassengers = edText.getText().toString();

        AvailableDaysOfWeek viewAv = getActivity().findViewById(R.id.fragment_search_days_of_week_view);
        sfAvailableDaysOfWeek = viewAv.getSelectedDaysOfWeek();
    }

    private View getInstrumentRideCard(Ride ride) {
        View view = ride.getRideCardView(getActivity());
        view.setOnClickListener(v -> switchToDetailedRideView(ride));

        return view;
    }

    private void switchToDetailedRideView(Ride ride) {
        RideDetailsFragment rdf = new RideDetailsFragment(ride);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, rdf, null)
                .setReorderingAllowed(true)
                .addToBackStack("DetailedRideView")
                .commit();
    }

    private int formatBooleanToInt(boolean[] daysWeek) {
        int processed_int = 0;
        for (int i = 6; i >= 0; i--) {
            processed_int |= (daysWeek[i] ? 1 : 0);
            processed_int <<= 1;
        }
        return processed_int;
    }

    // Preprocessing: splits string, then deletes lat/lng: ( ) from both substrings
    private String formatLatLngString(String psStringLatLng) {
        return psStringLatLng.substring(10, psStringLatLng.length() - 1);
    }

}

