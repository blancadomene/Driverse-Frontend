package com.blancadomene.tfg;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class AvailableDaysOfWeek extends LinearLayout {
    View view;
    View monday;
    View tuesday;
    View wednesday;
    View thursday;
    View friday;
    View saturday;
    View sunday;

    public AvailableDaysOfWeek(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.layout_available_days_of_week, this, true);

        monday = view.findViewById(R.id.monday_chip);
        tuesday = view.findViewById(R.id.tuesday_chip);
        wednesday = view.findViewById(R.id.wednesday_chip);
        thursday = view.findViewById(R.id.thursday_chip);
        friday = view.findViewById(R.id.friday_chip);
        saturday = view.findViewById(R.id.saturday_chip);
        sunday = view.findViewById(R.id.sunday_chip);

        monday.setOnClickListener(v -> v.setActivated(!v.isActivated()));
        tuesday.setOnClickListener(v -> v.setActivated(!v.isActivated()));
        wednesday.setOnClickListener(v -> v.setActivated(!v.isActivated()));
        thursday.setOnClickListener(v -> v.setActivated(!v.isActivated()));
        friday.setOnClickListener(v -> v.setActivated(!v.isActivated()));
        saturday.setOnClickListener(v -> v.setActivated(!v.isActivated()));
        sunday.setOnClickListener(v -> v.setActivated(!v.isActivated()));
    }

    public void disableClick() {
        monday.setClickable(false);
        tuesday.setClickable(false);
        wednesday.setClickable(false);
        thursday.setClickable(false);
        friday.setClickable(false);
        saturday.setClickable(false);
        sunday.setClickable(false);
    }

    public void setEnabledDaysOfWeek(boolean[] week) {
        monday.setEnabled(week[0]);
        tuesday.setEnabled(week[1]);
        wednesday.setEnabled(week[2]);
        thursday.setEnabled(week[3]);
        friday.setEnabled(week[4]);
        saturday.setEnabled(week[5]);
        sunday.setEnabled(week[6]);
    }

    public boolean[] getSelectedDaysOfWeek() {
        boolean[] weekDays = new boolean[7];
        weekDays[0] = monday.isActivated();
        weekDays[1] = tuesday.isActivated();
        weekDays[2] = wednesday.isActivated();
        weekDays[3] = thursday.isActivated();
        weekDays[4] = friday.isActivated();
        weekDays[5] = saturday.isActivated();
        weekDays[6] = sunday.isActivated();
        return weekDays;
    }

}
