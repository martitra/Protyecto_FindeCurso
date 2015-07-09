package com.example.soft12.parte_trabajo.activities.slidescreen;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by soft12 on 03/07/2015.
 */
public class RangeTimePickerDialog extends TimePickerDialog {

    private final static int TIME_PICKER_INTERVAL = 5;
    private final TimePickerDialog.OnTimeSetListener callback;
    private int minHour = -1;
    private int minMinute = -1;
    private int maxHour = 25;
    private int maxMinute = 25;
    private int currentHour = 0;
    private int currentMinute = 0;
    private Calendar calendar = Calendar.getInstance();
    private TimePicker timePicker;


    public RangeTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener callBack,
                                 int hourOfDay, int minute, boolean is24HourView) {
        super(context, callBack, hourOfDay, minute / TIME_PICKER_INTERVAL, is24HourView);
        currentHour = hourOfDay;
        currentMinute = minute;
        DateFormat.getTimeInstance(DateFormat.SHORT);
        this.callback = callBack;

        try {
            Class<?> superclass = getClass().getSuperclass();
            Field mTimePickerField = superclass.getDeclaredField("mTimePicker");
            mTimePickerField.setAccessible(true);
            TimePicker mTimePicker = (TimePicker) mTimePickerField.get(this);
            mTimePicker.setOnTimeChangedListener(this);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(callback != null && timePicker != null){
            timePicker.clearFocus();
            callback.onTimeSet(timePicker, 0,
                    timePicker.getCurrentMinute() * TIME_PICKER_INTERVAL);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field timePickerField = classForid.getField("timePicker");
            this.timePicker = (TimePicker) findViewById(timePickerField
                    .getInt(null));
            Field field = classForid.getField("minute");

            NumberPicker mMinuteSpinner = (NumberPicker) timePicker
                    .findViewById(field.getInt(null));
            mMinuteSpinner.setMinValue(0);
            mMinuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            mMinuteSpinner.setDisplayedValues(displayedValues
                    .toArray(new String[displayedValues.size()]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMin(int hour, int minute) {
        minHour = hour;
        minMinute = minute;
    }

    public void setMax(int hour, int minute) {
        maxHour = hour;
        maxMinute = minute;
    }


    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        boolean validTime = true;
        if (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute)){
            validTime = false;
        }

        if (hourOfDay  > maxHour || (hourOfDay == maxHour && minute > maxMinute)){
            validTime = false;
        }

        if (validTime) {
            currentHour = hourOfDay;
            currentMinute = minute;
        }

        updateTime(currentHour, currentMinute);
        updateDialogTitle(currentHour, currentMinute);
    }

    private void updateDialogTitle(int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
    }
}
