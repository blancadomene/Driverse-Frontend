package com.blancadomene.tfg;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = null;
    Button notificationsViewButton;
    Button profileViewButton;
    Button publishViewButton;
    Button searchViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        // Sets each listener, changing the color of the buttons according to current button pressed.
        searchViewButton = this.findViewById(R.id.activity_main_search_button);
        searchViewButton.setOnClickListener(v -> {
            setGreyButtons();
            searchViewButton.setTextColor(getResources().getColor(R.color.color_primary_variant));
            switchToSearchView(v);
        });

        publishViewButton = this.findViewById(R.id.activity_main_publish_button);
        publishViewButton.setOnClickListener(v -> {
            setGreyButtons();
            publishViewButton.setTextColor(getResources().getColor(R.color.color_primary_variant));
            this.switchToPublishView(v);
        });

        notificationsViewButton = this.findViewById(R.id.activity_main_notifications_button);
        notificationsViewButton.setOnClickListener(v -> {
            setGreyButtons();
            notificationsViewButton.setTextColor(getResources().getColor(R.color.color_primary_variant));
            switchToNotificationsView(v);
        });

        profileViewButton = this.findViewById(R.id.activity_main_profile_button);
        profileViewButton.setOnClickListener(v -> {
            setGreyButtons();
            profileViewButton.setTextColor(getResources().getColor(R.color.color_primary_variant));
            switchToProfileView(v);
        });

        setGreyButtons();
        searchViewButton.setTextColor(getResources().getColor(R.color.color_primary_variant));
        switchToSearchView(null);
    }

    public void switchToSearchView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, SearchFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("searchView")
                .commit();
    }

    public void switchToPublishView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, PublishFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("publishView")
                .commit();
    }

    public void switchToNotificationsView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, NotificationsFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("notificationsView")
                .commit();
    }

    public void switchToProfileView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, ProfileFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("profileView")
                .commit();
    }

    public void setGreyButtons() {
        searchViewButton.setTextColor(getResources().getColor(R.color.disabled_text));
        publishViewButton.setTextColor(getResources().getColor(R.color.disabled_text));
        notificationsViewButton.setTextColor(getResources().getColor(R.color.disabled_text));
        profileViewButton.setTextColor(getResources().getColor(R.color.disabled_text));
    }
}