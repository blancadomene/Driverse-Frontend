package com.blancadomene.tfg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class RideDetailsFragment extends Fragment {
    private EditText eTextNP;
    private Ride ride;

    public RideDetailsFragment() {
    }

    public RideDetailsFragment(Ride pRide) {
        this.ride = pRide;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // TODO: separate get and set from onCreate
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ride_details, container, false);

        eTextNP = view.findViewById(R.id.fragment_ride_details_available_seats);
        eTextNP.setOnClickListener(v -> showNumberPickerDialog());

        // Get view items and set text
        TextView text;
        text = view.findViewById(R.id.fragment_ride_details_departure_hour);
        text.setText(String.format(
                "%02d:%02d",
                ride.getDepartureHour().get(Calendar.HOUR_OF_DAY),
                ride.getDepartureHour().get(Calendar.MINUTE)));

        text = view.findViewById(R.id.fragment_ride_details_departure_point);
        text.setText(ride.getDeparturePoint());

        text = view.findViewById(R.id.fragment_ride_details_arrival_hour);
        text.setText(String.format(
                "%02d:%02d",
                ride.getArrivalHour().get(Calendar.HOUR_OF_DAY),
                ride.getArrivalHour().get(Calendar.MINUTE)));

        text = view.findViewById(R.id.fragment_ride_details_arrival_point);
        text.setText(ride.getArrivalPoint());

        text = view.findViewById(R.id.fragment_ride_details_price_per_seat);
        text.setText(ride.getPricePerSeat() + "€");

        text = view.findViewById(R.id.fragment_ride_details_start_date);
        text.setText(String.format(
                "%02d/%02d/%02d",
                ride.getStartDate().get(Calendar.DAY_OF_MONTH),
                ride.getStartDate().get(Calendar.MONTH),
                ride.getStartDate().get(Calendar.YEAR)));

        text = view.findViewById(R.id.fragment_ride_details_end_date);
        text.setText(String.format(
                "%02d/%02d/%02d",
                ride.getEndDate().get(Calendar.DAY_OF_MONTH),
                ride.getEndDate().get(Calendar.MONTH),
                ride.getEndDate().get(Calendar.YEAR)));

        AvailableDaysOfWeek viewAv = view.findViewById(R.id.fragment_ride_details_days_of_week_view);
        viewAv.setEnabledDaysOfWeek(ride.getAvailableDaysOfWeek());

        new DownloadImageTask(view.findViewById(R.id.fragment_ride_details_driver_image)).execute("https://www.gravatar.com/avatar/205e460b479e2e5b48aeg07710c08d50?s=450&r=pg&d=retro");

        text = view.findViewById(R.id.fragment_ride_details_driver_name);
        text.setText(ride.getDriver().getName() + " " + ride.getDriver().getSurName());

        text = view.findViewById(R.id.fragment_ride_details_driver_age);
        text.setText(ride.getDriver().calculateAge() + " years old");

        text = view.findViewById(R.id.fragment_ride_details_driver_preferences);
        text.setText(ride.getDriver().getPreferences());

        text = view.findViewById(R.id.fragment_ride_details_driver_car);
        text.setText(ride.getDriver().getCar());

        return view;
    }

    private AlertDialog showNumberPickerDialog() {
        final NumberPicker np = new NumberPicker(getActivity());
        np.setMaxValue(ride.getAvailableSeats());
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
}