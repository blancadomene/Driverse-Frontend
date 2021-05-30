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
import android.widget.NumberPicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class PublishFragment extends Fragment {
    private EditText eTextTP;
    private EditText eTextDPStart;
    private EditText eTextDPEnd;
    private EditText eTextNP;

    public PublishFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO ?
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, container, false);

        eTextTP = view.findViewById(R.id.fragment_publish_departure_hour);
        eTextTP.setOnClickListener(v -> showTimePickerDialog());

        eTextDPStart = view.findViewById(R.id.fragment_publish_start_date);
        eTextDPStart.setOnClickListener(v -> showDatePickerDialog(eTextDPStart));

        eTextDPEnd = view.findViewById(R.id.fragment_publish_end_date);
        eTextDPEnd.setOnClickListener(v -> showDatePickerDialog(eTextDPEnd));

        eTextDPEnd = view.findViewById(R.id.fragment_publish_end_date);
        eTextDPEnd.setOnClickListener(v -> showDatePickerDialog(eTextDPEnd));

        eTextNP = view.findViewById(R.id.fragment_publish_available_seats);
        eTextNP.setOnClickListener(v -> showNumberPickerDialog());

        Button button = view.findViewById(R.id.fragment_publish_travel_button);
        button.setOnClickListener(this::publishNewRide);

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

    public void publishNewRide(View view) {
        // TODO: Take data from editTexts
        // TODO: not null
        EditText edText;
        edText = getActivity().findViewById(R.id.fragment_publish_start_date);
        String psStartDate = edText.getText().toString();
        edText = getActivity().findViewById(R.id.fragment_publish_end_date);
        String psEndDate = edText.getText().toString();
        edText = getActivity().findViewById(R.id.fragment_publish_departure_point);
        String psDeparturePoint = edText.getText().toString();
        edText = getActivity().findViewById(R.id.fragment_publish_arrival_point);
        String psArrivalPoint = edText.getText().toString();
        edText = getActivity().findViewById(R.id.fragment_publish_departure_hour);
        String psDepartureHour = edText.getText().toString();
        edText = getActivity().findViewById(R.id.fragment_publish_available_seats);
        String psAvailableSeats = edText.getText().toString();
        edText = getActivity().findViewById(R.id.fragment_publish_price_per_seat);
        String psPricePerSeat = edText.getText().toString();
        AvailableDaysOfWeek viewAv = getActivity().findViewById(R.id.fragment_publish_days_of_week_view);
        boolean[] psAvailableDaysOfWeek = viewAv.getSelectedDaysOfWeek();

        System.out.println("fsStartDate" + " " + psStartDate);
        System.out.println("fsEndDate" + " " + psEndDate);
        System.out.println("fsDeparturePoint" + " " + psDeparturePoint);
        System.out.println("fsArrivalPoint" + " " + psArrivalPoint);
        System.out.println("fsDepartureHour" + " " + psDepartureHour);
        System.out.println("fsAvailableSeats" + " " + psAvailableSeats);
        System.out.println("fsPricePerSeat" + " " + psPricePerSeat);
        for (boolean b : psAvailableDaysOfWeek) {
            if (b) System.out.println("True");
        }


        // TODO: Create ride

        // new Ride(fsStartDate, fsEndDate, fsDeparturePoint, fsDepartureHour, fsArrivalPoint, exArrHour, fsAvailableSeats, new BigDecimal("1.11"), System.getProperty("user.name"));

    }
}