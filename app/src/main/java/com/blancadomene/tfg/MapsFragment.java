package com.blancadomene.tfg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Locale;

public class MapsFragment extends Fragment {
    private String data;
    private GoogleMap mMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            LatLng defaultLocation = new LatLng(40.4165, -3.70256);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), getString(R.string.google_maps_key), Locale.US);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Button buttonOK = view.findViewById(R.id.fragment_maps_OK);
        buttonOK.setOnClickListener(v -> {
            fragmentManager.popBackStack();
            fragmentManager.beginTransaction().commit();
        });

        Button buttonCancel = view.findViewById(R.id.fragment_maps_cancel);
        buttonCancel.setOnClickListener(v -> {
            fragmentManager.popBackStack();
            fragmentManager.beginTransaction().commit();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        // Initializes the AutocompleteSupportFragment
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specifies the types of place data to return
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));

        // Sets up a PlaceSelectionListener to handle the response
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // Gets info about the selected place.
                int zoomLevel = 15;
                data = (place.getId() + "__" + place.getName() + "__" + place.getLatLng());
                LatLng location = place.getLatLng();

                // Moves map and adds marker.
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(location).title("Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
            }

            @Override
            public void onError(@NonNull Status status) {
                System.out.println("An error occurred: " + status);
            }
        });

    }

    private FragmentCallBacks fragmentCallBacks;

    public interface FragmentCallBacks {
        void onCallBack(String data);
    }

    public void setFragmentCallBacks(FragmentCallBacks fragmentCallBacks) {
        this.fragmentCallBacks = fragmentCallBacks;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentCallBacks.onCallBack(data);
    }

}

