package com.blancadomene.tfg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.concurrent.ExecutionException;

public class LoginScreenActivity extends AppCompatActivity {
    private EditText edEmail;
    private EditText edPassword;
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

    // Checks user's email and password
    private void checkLogin() {
        APIClient client = new APIClient(getString(R.string.backend_address));

        String edPasswordMD5 = new String(Hex.encodeHex(DigestUtils.md5(edPassword.getText().toString())));
        APIClient.Request request = new APIClient.Request();
        request.method = "POST";
        request.query = "users/login";
        request.body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}",
                edEmail.getText(), edPasswordMD5);

        try {
            APIClient.Response response = client.execute(request).get();

            if (response.retcode == 200) {
                final JsonObject obj = response.json.getAsJsonObject();
                EXTRA_ID = obj.get("id").getAsString();

                switchToMainActivityView();

            } else {
                Context context = getApplicationContext();
                CharSequence text = "Wrong user or password, try again.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Switches to main activity after login has been checked
    // Intent passes user ID, so it can be accessed from fragments
    private void switchToMainActivityView() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EXTRA_ID", EXTRA_ID);
        startActivity(intent);
    }
}