package com.blancadomene.tfg;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        Button searchViewButton = this.findViewById(R.id.activity_main_search_button);
        searchViewButton.setOnClickListener(this::showSearchView);

        Button publishViewButton = this.findViewById(R.id.activity_main_publish_button);
        publishViewButton.setOnClickListener(this::showPublishView);

        Button notificationsViewButton = this.findViewById(R.id.activity_main_notifications_button);
        notificationsViewButton.setOnClickListener(this::showNotificationsView);

        Button profileViewButton = this.findViewById(R.id.activity_main_profile_button);
        profileViewButton.setOnClickListener(this::showProfileView);
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
}