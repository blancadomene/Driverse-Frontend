package com.blancadomene.tfg;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = null;
    private Bundle extrasBundle;
    private Button notificationsViewButton;
    private Button profileViewButton;
    private Button publishViewButton;
    private Button searchViewButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Hide keyboard at activity start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Get bundle from previous activity
        extrasBundle = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        // Sets each listener, changing the color of the buttons according to current button pressed
        searchViewButton = this.findViewById(R.id.activity_main_search_button);
        searchViewButton.setOnClickListener(v -> {
            setGreyButtons();
            searchViewButton.setTextColor(getResources().getColor(R.color.color_primary_variant));
            switchToSearchView();
        });

        publishViewButton = this.findViewById(R.id.activity_main_publish_button);
        publishViewButton.setOnClickListener(v -> {
            setGreyButtons();
            publishViewButton.setTextColor(getResources().getColor(R.color.color_primary_variant));
            this.switchToPublishView();
        });

        notificationsViewButton = this.findViewById(R.id.activity_main_notifications_button);
        notificationsViewButton.setOnClickListener(v -> {
            setGreyButtons();
            notificationsViewButton.setTextColor(getResources().getColor(R.color.color_primary_variant));
            switchToNotificationsView();
        });

        profileViewButton = this.findViewById(R.id.activity_main_profile_button);
        profileViewButton.setOnClickListener(v -> {
            setGreyButtons();
            profileViewButton.setTextColor(getResources().getColor(R.color.color_primary_variant));
            switchToProfileView();
        });

        setGreyButtons();
        searchViewButton.setTextColor(getResources().getColor(R.color.color_primary_variant));
        switchToSearchView();
    }

    // Method to access the variables of LoginScreenActivity
    public Bundle getExtraData() {
        return extrasBundle;
    }

    // Adds a different fragment depending on which button is pressed
    private void switchToSearchView() {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, SearchFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("searchView")
                .commit();
    }

    private void switchToPublishView() {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, PublishFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("publishView")
                .commit();
    }

    private void switchToNotificationsView() {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, NotificationsFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("notificationsView")
                .commit();
    }

    private void switchToProfileView() {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, ProfileFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("profileView")
                .commit();
    }

    private void setGreyButtons() {
        searchViewButton.setTextColor(getResources().getColor(R.color.disabled_text));
        publishViewButton.setTextColor(getResources().getColor(R.color.disabled_text));
        notificationsViewButton.setTextColor(getResources().getColor(R.color.disabled_text));
        profileViewButton.setTextColor(getResources().getColor(R.color.disabled_text));
    }

}