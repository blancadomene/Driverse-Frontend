package com.blancadomene.tfg;

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

    private void getUserInfo(View view, Bundle extrasBundle) {
        String extraEmail = extrasBundle.getString("EXTRA_EMAIL");
        TextView textView = view.findViewById(R.id.fragment_profile_email);
        textView.setText(extraEmail);

        AsyncTask.execute(() -> {
            try {
                URL url = new URL(String.format("http://%s/users/info?Email=%s", getString(R.string.backend_address), extraEmail));
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
                            case ("name"):
                                TextView tv = view.findViewById(R.id.fragment_profile_name);
                                tv.setText(jsonReader.nextString());
                                break;
                            case ("surname"):
                                tv = view.findViewById(R.id.fragment_profile_sur_name);
                                tv.setText(jsonReader.nextString());
                                break;
                            case ("birthdate"):
                                tv = view.findViewById(R.id.fragment_profile_birth_date);
                                tv.setText(jsonReader.nextString());
                                break;
                            case ("car"):
                                tv = view.findViewById(R.id.fragment_profile_car);
                                tv.setText(jsonReader.nextString());
                                break;
                            case ("image"):
                                ImageView imView = view.findViewById(R.id.fragment_profile_image);
                                new DownloadImageTask(imView).execute(jsonReader.nextString());
                                break;
                            case ("mobilephone"):
                                tv = view.findViewById(R.id.fragment_profile_mobile_number);
                                tv.setText(jsonReader.nextString());
                                break;
                            case ("preferences"):
                                tv = view.findViewById(R.id.fragment_profile_preferences);
                                tv.setText(jsonReader.nextString());
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
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}