package com.blancadomene.tfg;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ride_details, container, false);

        // Get view items and set text
        TextView text;
        text = view.findViewById(R.id.fragment_ride_details_departure_hour);
        text.setText(ride.getDepartureHour().get(Calendar.HOUR_OF_DAY) + ":" + ride.getDepartureHour().get(Calendar.MINUTE));
        text = view.findViewById(R.id.fragment_ride_details_departure_point);
        text.setText(ride.getDeparturePoint());
        text = view.findViewById(R.id.fragment_ride_details_arrival_hour);
        text.setText(ride.getArrivalHour().get(Calendar.HOUR_OF_DAY) + ":" + ride.getArrivalHour().get(Calendar.MINUTE)); //TODO: change (same as in Ride)
        text = view.findViewById(R.id.fragment_ride_details_arrival_point);
        text.setText(ride.getArrivalPoint());
        text = view.findViewById(R.id.fragment_ride_details_price_per_seat);
        text.setText(ride.getPricePerSeat().toString() + "€");

        // TODO: set daysOfWeek
        availableDaysOfWeek daysOfWeekView = view.findViewById(R.id.fragment_ride_details_days_of_week_view);


        // TODO: set photo (add name? rating?)

        // TODO: set name

        return view;
    }
}