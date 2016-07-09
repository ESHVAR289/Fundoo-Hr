package com.bridgelabz.util;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by bridgeit007 on 9/7/16.
 */

public class SetCurrentTime implements View.OnFocusChangeListener,TimePickerDialog.OnTimeSetListener {
    private EditText etCurrentTime;
    private Calendar calendar;
    Context context;

    public SetCurrentTime(EditText etCurrentTime, Context context) {
        this.context = context;
        this.etCurrentTime=etCurrentTime;
        this.etCurrentTime.setOnFocusChangeListener(this);
        this.calendar=Calendar.getInstance();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus){
            int hour=calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            new TimePickerDialog(context,this,hour,minute,true).show();
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        this.etCurrentTime.setText( hourOfDay + ":" + minute);
    }
}
