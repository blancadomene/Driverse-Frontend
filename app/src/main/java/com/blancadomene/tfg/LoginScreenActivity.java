package com.blancadomene.tfg;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginScreenActivity extends AppCompatActivity {
    private EditText edEmail;
    private EditText edPassword;
    private String edPasswordMD5;
    private String EXTRA_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        edEmail = findViewById(R.id.activity_login_screen_username);
        edPassword = findViewById(R.id.activity_login_screen_password);

        Button button = this.findViewById(R.id.activity_login_screen_log_in_button);
        button.setOnClickListener(v -> checkLogin());
    }

    // Switches to main activity after login has been checked
    // Intent passes user ID, so it can be accessed from fragments
    private void switchToMainActivityView() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EXTRA_ID", EXTRA_ID);
        startActivity(intent);
    }

    private void checkLogin() {
        AsyncTask.execute(() -> {
            try {
                URL url = new URL(String.format("http://%s/users/login", getString(R.string.backend_address)));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                edPasswordMD5 = new String(Hex.encodeHex(DigestUtils.md5(edPassword.getText().toString())));
                String myData = String.format("{\"email\": \"%s\", \"password\": \"%s\"}",
                        edEmail.getText(), edPasswordMD5);
                connection.setDoOutput(true); // true for POST and PUT
                connection.getOutputStream().write(myData.getBytes());

                if (connection.getResponseCode() == 200) { // Takes ID, closes reader/connection and switches activity
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, StandardCharsets.UTF_8);

                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String key = jsonReader.nextName();
                        if (key.equals("id")) {
                            EXTRA_ID = jsonReader.nextString();
                            break;
                        } else {
                            jsonReader.skipValue();
                        }
                    }

                    jsonReader.close();
                    connection.disconnect();
                    switchToMainActivityView();
                } else {
                    connection.disconnect();

                    Context context = getApplicationContext();
                    CharSequence text = "Wrong user or password, try again.";
                    int duration = Toast.LENGTH_SHORT;

                    this.runOnUiThread(() -> {
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