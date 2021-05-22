package com.blancadomene.tfg;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class availableDaysOfWeek extends LinearLayout {
    View view;
    int num = 0;

    public availableDaysOfWeek(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.layout_available_days_of_week, this, true);
    }

    public void ShowNum() {
        System.out.println(num++);
    }
}
