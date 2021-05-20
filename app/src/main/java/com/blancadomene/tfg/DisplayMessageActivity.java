package com.blancadomene.tfg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

public class DisplayMessageActivity extends AppCompatActivity {
    FragmentManager fragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        fragmentManager = getSupportFragmentManager();
    }

    public void showSearchView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, SearchFragment.class, null)   // allows the FragmentManager to handle instantiation using its FragmentFactory
                .setReorderingAllowed(true)                                             // optimizes the state changes of the fragments involved
                .addToBackStack("searchView")                                           // commits the transaction to the back stack
                .commit();
    }

    public void showPublishView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, PublishFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("publishView") // name can be null
                .commit();
    }

    public void showNotificationsView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, NotificationsFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("notificationsView")
                .commit();
    }

    public void showProfileView(View view) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, ProfileFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("profileView")
                .commit();
    }
}