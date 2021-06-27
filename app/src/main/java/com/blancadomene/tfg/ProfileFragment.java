package com.blancadomene.tfg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProfileFragment extends Fragment {
    private ArrayList<Ride> userRides = new ArrayList<>();
    private LinearLayout linearLayout = null;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        linearLayout = view.findViewById(R.id.fragment_profile_result_container);

        // Gets info from DB
        // Argument: bundle from previous activity
        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;
        getUserInfo(view, activity.getExtraData());
        getUserRides(view, activity.getExtraData());

        return view;
    }

    // Gets info for current user
    @SuppressLint("SetTextI18n")
    private void getUserInfo(View view, Bundle extrasBundle) {
        String extraID = extrasBundle.getString("EXTRA_ID");

        APIClient client = new APIClient(getString(R.string.backend_address));

        APIClient.Request request = new APIClient.Request();
        request.method = "GET";
        request.query = String.format("users/info?ID=%s", extraID);

        try {
            APIClient.Response response = client.execute(request).get();

            if (response.retcode == 200) {
                TextView tmpTV;
                final JsonObject obj = response.json.getAsJsonObject();

                tmpTV = view.findViewById(R.id.fragment_profile_email);
                tmpTV.setText("Email: " + obj.get("email").getAsString());


                tmpTV = view.findViewById(R.id.fragment_profile_name);
                tmpTV.setText("Name: " + obj.get("name").getAsString());


                tmpTV = view.findViewById(R.id.fragment_profile_sur_name);
                tmpTV.setText("Surname: " + obj.get("surname").getAsString());


                tmpTV = view.findViewById(R.id.fragment_profile_birth_date);
                tmpTV.setText("Birth date: " + obj.get("birthdate").getAsString());


                tmpTV = view.findViewById(R.id.fragment_profile_car);
                tmpTV.setText("Car: " + obj.get("car").getAsString());


                ImageView imView = view.findViewById(R.id.fragment_profile_image);
                new DownloadImageTask(imView).execute(obj.get("image").getAsString());


                tmpTV = view.findViewById(R.id.fragment_profile_mobile_number);
                tmpTV.setText("Mobile phone: " + obj.get("mobilephone").getAsString());


                tmpTV = view.findViewById(R.id.fragment_profile_preferences);
                tmpTV.setText("Preferences: " + obj.get("preferences").getAsString());

            } else {
                Context context = getActivity();
                CharSequence text = "Unknown error retrieving data from user.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getUserRides(View view, Bundle extrasBundle) {
        String extraID = extrasBundle.getString("EXTRA_ID");

        APIClient client = new APIClient(getString(R.string.backend_address));

        APIClient.Request request = new APIClient.Request();
        request.method = "GET";
        request.query = String.format("rides/book?ID=%s", extraID);

        try {
            APIClient.Response response = client.execute(request).get();

            if (response.retcode == 200) {
                if (response.json.isJsonArray()) {
                    final JsonArray values = response.json.getAsJsonArray();
                    for (final JsonElement value : values) {
                        final JsonObject obj = value.getAsJsonObject();
                        Ride r = new Ride(obj);
                        userRides.add(r);
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

        showResultList();
    }

    public void showResultList() {
        for (int i = 0; i < userRides.size(); i++) {
            Ride ride = userRides.get(i);
            View card = ride.getRideCardView(getActivity());
            linearLayout.addView(card);
        }
    }

}