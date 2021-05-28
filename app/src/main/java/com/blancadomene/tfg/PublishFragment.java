package com.blancadomene.tfg;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublishFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText eTextTP;
    private EditText eTexDPStart;
    private EditText eTexDPEnd;
    private Button button;
    private TimePickerDialog timePicker;
    private DatePickerDialog datePicker;

    public PublishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PublishFragment newInstance(String param1, String param2) {
        PublishFragment fragment = new PublishFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publish, container, false);

        eTextTP = view.findViewById(R.id.fragment_publish_departure_hour);
        eTextTP.setOnClickListener(v -> showTimePickerDialog(v));

        eTexDPStart = view.findViewById(R.id.fragment_publish_start_date);
        eTexDPStart.setOnClickListener(v -> showDatePickerDialog(eTexDPStart));

        eTexDPEnd = view.findViewById(R.id.fragment_publish_end_date);
        eTexDPEnd.setOnClickListener(v -> showDatePickerDialog(eTexDPEnd));

        button = view.findViewById(R.id.fragment_publish_travel_button);
        button.setOnClickListener(this::publishNewRide);

        return view;
    }

    private void showTimePickerDialog(View view) {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);

        timePicker = new TimePickerDialog(getActivity(),
                (tp, tpHour, tpMinutes) -> eTextTP.setText(String.format("%02d:%02d", tpHour, tpMinutes)), hour, minutes, true);
        timePicker.show();
    }

    private void showDatePickerDialog(EditText edText) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePicker = new DatePickerDialog(getActivity(),
                (dp, dpYear, dpMonth, dpDay) -> edText.setText(String.format("%02d/%02d/%02d", dpDay, dpMonth, dpYear)), year, month, day);
        datePicker.show();
    }

    public void publishNewRide(View view) {
        // TODO: Take data from editTexts
        // TODO: not null
        EditText edText;
        edText = (EditText) getActivity().findViewById(R.id.fragment_publish_start_date);
        String fsStartDate = edText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_publish_end_date);
        String fsEndDate = edText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_publish_departure_point);
        String fsDeparturePoint = edText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_publish_arrival_point);
        String fsArrivalPoint = edText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_publish_departure_hour);
        String fsDepartureHour = edText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_publish_available_seats);
        String fsAvailableSeats = edText.getText().toString();
        edText = (EditText) getActivity().findViewById(R.id.fragment_publish_price_per_seat);
        String fsPricePerSeat = edText.getText().toString();
        AvailableDaysOfWeek viewAv = (AvailableDaysOfWeek) getActivity().findViewById(R.id.fragment_publish_days_of_week_view);
        boolean[] fsAvailableDaysOfWeek = viewAv.getSelectedDaysOfWeek();


        // TODO: Create ride

        // new Ride(fsStartDate, fsEndDate, fsDeparturePoint, fsDepartureHour, fsArrivalPoint, exArrHour, fsAvailableSeats, new BigDecimal("1.11"), System.getProperty("user.name"));

    }
}