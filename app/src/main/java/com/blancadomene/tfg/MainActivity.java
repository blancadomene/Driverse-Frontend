package com.blancadomene.tfg;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    Button searchViewButton;
    Button publishViewButton;
    Button notificationsViewButton;
    Button profileViewButton;
    FragmentManager fragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        searchViewButton = this.findViewById(R.id.activity_main_search_button);
        searchViewButton.setOnClickListener(v -> {
            setGreyButtons();
            searchViewButton.setTextColor(getResources().getColor(R.color.black));
            showSearchView(v);
        });

        publishViewButton = this.findViewById(R.id.activity_main_publish_button);
        publishViewButton.setOnClickListener(v -> {
            setGreyButtons();
            publishViewButton.setTextColor(getResources().getColor(R.color.black));
            this.showPublishView(v);
        });

        notificationsViewButton = this.findViewById(R.id.activity_main_notifications_button);
        notificationsViewButton.setOnClickListener(v -> {
            setGreyButtons();
            notificationsViewButton.setTextColor(getResources().getColor(R.color.black));
            showNotificationsView(v);
        });

        profileViewButton = this.findViewById(R.id.activity_main_profile_button);
        profileViewButton.setOnClickListener(v -> {
            setGreyButtons();
            profileViewButton.setTextColor(getResources().getColor(R.color.black));
            showProfileView(v);
        });

        setGreyButtons();
        searchViewButton.setTextColor(getResources().getColor(R.color.black));
        showSearchView(null);
    }

    public void showSearchView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, SearchFragment.class, null)   // allows the FragmentManager to handle instantiation using its FragmentFactory
                .setReorderingAllowed(true)                                                             // optimizes the state changes of the fragments involved
                .addToBackStack("searchView")                                                           // commits the transaction to the back stack
                .commit();
    }

    public void showPublishView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, PublishFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("publishView")
                .commit();
    }

    public void showNotificationsView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container_view, NotificationsFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("notificationsView")
                .commit();
    }

    public void showProfileView(View view) {
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