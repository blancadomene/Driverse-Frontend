package com.blancadomene.tfg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class NotificationsFragment extends Fragment {

    public NotificationsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.fragment_notifications_container);

        Notification not = new Notification("Testing notifications.");
        View card = not.getNotificationCardView(getActivity());
        linearLayout.addView(card);

        return view;
    }
}