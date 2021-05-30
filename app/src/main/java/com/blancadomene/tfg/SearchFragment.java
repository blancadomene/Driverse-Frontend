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
    private EditText eTexDPStart;
    private EditText eTexDPEnd;
    private EditText eTextNP;
    private EditText eTextTP;
    private LinearLayout linearLayout = null;
    private View view = null;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO ?
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        linearLayout = view.findViewById(R.id.fragment_search_result_container);

        eTexDPStart = view.findViewById(R.id.fragment_search_start_date);
        eTexDPStart.setOnClickListener(v -> showDatePickerDialog(eTexDPStart));

        eTexDPEnd = view.findViewById(R.id.fragment_search_end_date);
        eTexDPEnd.setOnClickListener(v -> showDatePickerDialog(eTexDPEnd));

        eTextNP = view.findViewById(R.id.fragment_search_passengers_number);
        eTextNP.setOnClickListener(v -> showNumberPickerDialog());

        eTextTP = view.findViewById(R.id.fragment_search_departure_hour);
        eTextTP.setOnClickListener(v -> showTimePickerDialog());

        Button button = view.findViewById(R.id.fragment_search_travel_button);
        button.setOnClickListener(v -> showResultList(v));

        return view;
    }

    private void showDatePickerDialog(EditText edText) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("DefaultLocale") DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                (dp, dpYear, dpMonth, dpDay) -> edText.setText(String.format("%02d/%02d/%02d", dpDay, dpMonth, dpYear)), year, month, day);
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
        builder.setTitle("Changing the Hue");
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
                (tp, tpHour, tpMinutes) -> eTextTP.setText(String.format("%02d:%02d", tpHour, tpMinutes)), hour, minutes, true);
        timePicker.show();
    }

    public void showResultList(View view) {
        // TODO: Take data from editTexts
        // TODO: not null
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

        // TODO: Delete prints
        System.out.println("fsStartDate" + " " + fsStartDate);
        System.out.println("fsEndDate" + " " + fsEndDate);
        System.out.println("fsDeparturePoint" + " " + fsDeparturePoint);
        System.out.println("fsDeparturePointRadius" + " " + fsDeparturePointRadius);
        System.out.println("fsArrivalPoint" + " " + fsArrivalPoint);
        System.out.println("fsArrivalPointRadius" + " " + fsArrivalPointRadius);
        System.out.println("fsDepartureHour" + " " + fsDepartureHour);
        System.out.println("fsPassengersNumber" + " " + fsPassengersNumber);
        for (boolean b : fsAvailableDaysOfWeek) {
            if (b) System.out.println("True");
        }


        // TODO: Search with editTexts parameters and add searches to array (same name)
        // TODO: delete examples and iterate searches array
        searches.clear();
        Calendar exStartDate = new GregorianCalendar(2001, 1, 1);
        Calendar exEndDate = new GregorianCalendar(2002, 2, 2);
        Calendar exDepHour = new GregorianCalendar(1999, 9, 9, 13, 01);
        Calendar exArrHour = new GregorianCalendar(1999, 9, 9, 17, 14);
        User exUser = new User("Eren", "Yaeger", "erenthetitan@gmail.com", "111111111");
        boolean[] days = new boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};

        searches.add(new Ride(exStartDate, exEndDate, "Almería", exDepHour, "Granada", exArrHour, 1, new BigDecimal("1.11"), exUser, days));
        searches.add(new Ride(exStartDate, exEndDate, "Valencia", exDepHour, "Madriz", exArrHour, 2, new BigDecimal("2.22"), exUser, days));
        searches.add(new Ride(exStartDate, exEndDate, "Barcelona", exDepHour, "Badajoz", exArrHour, 3, new BigDecimal("3.33"), exUser, days));
        searches.add(new Ride(exStartDate, exEndDate, "Alfacar", exDepHour, "Viznar", exArrHour, 4, new BigDecimal("4.44"), exUser, days));
        searches.add(new Ride(exStartDate, exEndDate, "Toledo", exDepHour, "Lugo", exArrHour, 5, new BigDecimal("5.55"), exUser, days));
        //TODO: delete examples and iterate searches array

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
                .addToBackStack("searchView")
                .commit();
    }

}

