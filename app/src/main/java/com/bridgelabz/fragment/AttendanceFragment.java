package com.bridgelabz.fragment;


import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bridgelabz.R;
import com.bridgelabz.controller.AttendanceController;
import com.bridgelabz.model.TimeEntryResponse;
import com.bridgelabz.util.DateFormater;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AttendanceFragment extends Fragment {
    private EditText etDate, etInTime, etOutTime;
    private AttendanceController mAttendanceController;
    private TimeEntryResponse timeEntryResponse;
    private int mHour, mMinute, am_pm;
    private Calendar calendar;
    private TimePickerDialog timePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAttendanceController = new AttendanceController(getActivity());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        AppCompatButton btnConfirm = (AppCompatButton) view.findViewById(R.id.btnConfirm);
        etDate = (EditText) view.findViewById(R.id.etEtDate);
        etInTime = (EditText) view.findViewById(R.id.etEdtInTime);
        etOutTime = (EditText) view.findViewById(R.id.etEdtOutTime);
        TextView txtOutTime = (TextView) view.findViewById(R.id.txtOutTime);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAttendanceController
                        .sendResponseConfirmation(timeEntryResponse.getTimeEntryResponseDataModel().getUserId(),
                                DateFormater.serverSendDate(etDate.getText().toString().trim(),
                                        etInTime.getText().toString().trim()),
                                etOutTime.getText().toString().trim(),
                        timeEntryResponse.getTimeEntryResponseDataModel().getTotalTime(),
                        true);
            }
        });

        etInTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);
                am_pm = calendar.get(Calendar.AM_PM);
                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                etInTime.setText(hourOfDay + ":" + minute + " " + am_pm);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        etOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                etOutTime.setText(hourOfDay + ":" + minute + " " + am_pm);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        timeEntryResponse = (TimeEntryResponse) getArguments().getSerializable("responseData");

        assert timeEntryResponse != null;
        if (Objects.equals(timeEntryResponse.getTimeEntryResponseDataModel().getOutTime(), "0")) {
            Date dateIn = DateFormater.getDate(timeEntryResponse.getTimeEntryResponseDataModel().getInTime());
            etDate.setText(DateFormater.getCurrentDateString(dateIn));
            etInTime.setText(DateFormater.getCurrentTimeString(dateIn));
            etDate.setBackgroundColor(getResources().getColor(R.color.grey_white));
            etOutTime.setText(timeEntryResponse.getTimeEntryResponseDataModel().getOutTime());
            etOutTime.setVisibility(View.INVISIBLE);
            txtOutTime.setVisibility(View.INVISIBLE);
        } else {
            Date dateIn = DateFormater.getDate(timeEntryResponse.getTimeEntryResponseDataModel().getInTime());
            etDate.setText(DateFormater.getCurrentDateString(dateIn));
            etInTime.setText(DateFormater.getCurrentTimeString(dateIn));
            etInTime.setEnabled(false);
            Date dateOut = DateFormater.getDate(timeEntryResponse.getTimeEntryResponseDataModel().getOutTime());
            txtOutTime.setVisibility(View.VISIBLE);
            etOutTime.setText(DateFormater.getCurrentTimeString(dateOut));
            etOutTime.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
