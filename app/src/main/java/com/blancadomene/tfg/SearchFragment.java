package com.blancadomene.tfg;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SearchFragment extends Fragment {
    private ArrayList<Ride> searches = new ArrayList<>();
    private EditText eTextDPStart;
    private EditText eTextDPEnd;
    private EditText eTextNP;
    private EditText eTextTP;
    private LinearLayout linearLayout = null;
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
        button.setOnClickListener(v -> showResultList(v));

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
                String[] parts = data.split("_");
                EditText edView = view.findViewById(edTextID);
                System.out.println(data);
                edView.setText(parts[1]); //parts3 contains latlng
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

    public void showResultList(View view) {
        EditText edText;
        edText = getActivity().findViewById(R.id.fragment_search_start_date);
        String fsStartDate = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_end_date);
        String fsEndDate = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_departure_point);
        String fsDeparturePoint = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_departure_point_radius);
        String fsDeparturePointRadius = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_arrival_point);
        String fsArrivalPoint = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_arrival_point_radius);
        String fsArrivalPointRadius = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_departure_hour);
        String fsDepartureHour = edText.getText().toString();

        edText = getActivity().findViewById(R.id.fragment_search_passengers_number);
        String fsPassengersNumber = edText.getText().toString();

        AvailableDaysOfWeek viewAv = getActivity().findViewById(R.id.fragment_search_days_of_week_view);
        boolean[] fsAvailableDaysOfWeek = viewAv.getSelectedDaysOfWeek();


        // TODO: Search with editTexts parameters and add searches to array (same name)
        // TODO: delete examples and iterate searches array
        searches.clear();
        Calendar exStartDate = new GregorianCalendar(2111, 1, 1);
        Calendar exEndDate = new GregorianCalendar(2222, 2, 2);
        Calendar exDepHour = new GregorianCalendar(1999, 9, 9, 13, 01);
        Calendar exArrHour = new GregorianCalendar(1999, 9, 9, 17, 14);
        Calendar exBirthDate = new GregorianCalendar(1997, 14, 11, 9, 13, 01);
        User exUser = new User("Eren", "Yaeger", exBirthDate, "erenthetitan@gmail.com", "111111111");
        boolean[] days = new boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};

        searches.add(new Ride("1", exUser, exStartDate, exEndDate, "Estación intermodal de Almería", exDepHour, "Estación de autobuses de Granada", exArrHour, 1, new BigDecimal("1.11"), days));
        searches.add(new Ride("2", exUser, exStartDate, exEndDate, "Valencia", exDepHour, "Madriz", exArrHour, 2, new BigDecimal("2.22"), days));
        searches.add(new Ride("3", exUser, exStartDate, exEndDate, "Barcelona", exDepHour, "Badajoz", exArrHour, 3, new BigDecimal("3.33"), days));
        searches.add(new Ride("4", exUser, exStartDate, exEndDate, "Alfacar", exDepHour, "Viznar", exArrHour, 4, new BigDecimal("4.44"), days));
        searches.add(new Ride("5", exUser, exStartDate, exEndDate, "Toledo", exDepHour, "Lugo", exArrHour, 5, new BigDecimal("5.55"), days));
        /***************************************/

        linearLayout.removeAllViews();

        for (int i = 0; i < searches.size(); i++) {
            Ride ride = searches.get(i);
            View card = getInstrumentRideCard(ride);
            linearLayout.addView(card);
        }
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

}

