package com.blancadomene.tfg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ProfileFragment extends Fragment {

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

        // Get info from DB
        // Argument: bundle from previous activity
        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;
        getUserInfo(view, activity.getExtraData());

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void getUserInfo(View view, Bundle extrasBundle) {
        String extraID = extrasBundle.getString("EXTRA_ID");

        AsyncTask.execute(() -> {
            try {
                URL url = new URL(String.format("http://%s/users/info?ID=%s", getString(R.string.backend_address), extraID));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(false); // false for GET

                if (connection.getResponseCode() == 200) {
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, StandardCharsets.UTF_8);

                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginObject();                   // Start processing the JSON object
                    while (jsonReader.hasNext()) {              // Loop through all keys
                        switch (jsonReader.nextName()) {
                            case ("email"):
                                TextView tv = view.findViewById(R.id.fragment_profile_email);
                                tv.setText("Email: " + jsonReader.nextString());
                                break;
                            case ("name"):
                                tv = view.findViewById(R.id.fragment_profile_name);
                                tv.setText("Name: " + jsonReader.nextString());
                                break;
                            case ("surname"):
                                tv = view.findViewById(R.id.fragment_profile_sur_name);
                                tv.setText("Surname: " + jsonReader.nextString());
                                break;
                            case ("birthdate"):
                                tv = view.findViewById(R.id.fragment_profile_birth_date);
                                tv.setText("Birth date: " + jsonReader.nextString());
                                break;
                            case ("car"):
                                tv = view.findViewById(R.id.fragment_profile_car);
                                tv.setText("Car: " + jsonReader.nextString());
                                break;
                            case ("image"):
                                ImageView imView = view.findViewById(R.id.fragment_profile_image);
                                new DownloadImageTask(imView).execute(jsonReader.nextString());
                                break;
                            case ("mobilephone"):
                                tv = view.findViewById(R.id.fragment_profile_mobile_number);
                                tv.setText("Mobile phone: " + jsonReader.nextString());
                                break;
                            case ("preferences"):
                                tv = view.findViewById(R.id.fragment_profile_preferences);
                                tv.setText("Preferences: " + jsonReader.nextString());
                                break;
                            default:
                                jsonReader.skipValue(); // Skip values of other keys
                        }
                    }
                    jsonReader.close();
                    connection.disconnect();

                } else {
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Unknown problem related to user credentials.";
                    int duration = Toast.LENGTH_SHORT;

                    getActivity().runOnUiThread(() -> {
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    });
                    connection.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}