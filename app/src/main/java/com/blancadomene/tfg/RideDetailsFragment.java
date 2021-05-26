package com.blancadomene.tfg;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RideDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RideDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Ride ride;

    public RideDetailsFragment() {
        // Required empty public constructor
    }

    public RideDetailsFragment(Ride pRide) {
        this.ride = pRide;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RideDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RideDetailsFragment newInstance(String param1, String param2) {
        RideDetailsFragment fragment = new RideDetailsFragment();
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

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ride_details, container, false);

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
        text.setText(ride.getPricePerSeat().toString() + "€");
        text = view.findViewById(R.id.fragment_ride_details_available_seats);
        text.setText(ride.getAvailableSeats() + " seats left");
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
        //availableDaysOfWeek viewAv = (availableDaysOfWeek) getActivity().findViewById(R.id.fragment_ride_details_days_of_week_view);
        //boolean[] rdfAvailableDaysOfWeek = ride.getAvailableDaysOfWeek();
        //boolean[] rdfAvailableDaysOfWeek = new boolean[7];
        //viewAv.setEnabledDaysOfWeek(rdfAvailableDaysOfWeek);


        // TODO: set default if URL doesn't exist
        new DownloadImageTask(view.findViewById(R.id.fragment_ride_details_driver_image)).execute("https://www.gravatar.com/avatar/205e460b479e2e5b48aeg07710c08d50?s=450&r=pg&d=retro");

        text = view.findViewById(R.id.fragment_ride_details_driver_name);
        text.setText(ride.getDriver().getName() + " " + ride.getDriver().getSurName());
        text = view.findViewById(R.id.fragment_ride_details_driver_preferences);
        text.setText(ride.getDriver().getPreferences());
        text = view.findViewById(R.id.fragment_ride_details_driver_car);
        text.setText(ride.getDriver().getCar());


        return view;
    }
}