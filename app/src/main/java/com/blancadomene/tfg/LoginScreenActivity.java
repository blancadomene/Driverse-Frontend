package com.blancadomene.tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreenActivity extends AppCompatActivity {
    private Button button;

    public static final String EXTRA_MESSAGE = "com.example.tfg.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        button = this.findViewById(R.id.activity_login_screen_log_in_button);
        button.setOnClickListener(v -> sendMessage(v));
    }

    /**
     * TODO: Log in, try catch
     * TODO: change
     **/
    public void sendMessage(View view) {
        // Starts new activity
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = findViewById(R.id.activity_login_screen_username);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}