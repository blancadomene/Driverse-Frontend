package com.blancadomene.tfg;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

public class Notification {
    private String notificationText;
    private UUID notificationID;

    public Notification(String text) {
        this.notificationID = UUID.randomUUID();
        this.notificationText = text;
    }

    public UUID getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(UUID notificationID) {
        this.notificationID = notificationID;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public View getNotificationCardView(Activity context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_notification_card, null);

        // Get view items and set text
        TextView text;
        text = view.findViewById(R.id.layout_notification_card_message);
        text.setText(getNotificationText());

        new DownloadImageTask(view.findViewById(R.id.layout_notification_card_image)).execute("https://www.gravatar.com/avatar/205e460b479e2e5b48aeg07710c08d50?s=450&r=pg&d=retro");

        return view;
    }

}
