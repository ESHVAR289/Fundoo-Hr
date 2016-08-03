package com.bridgelabz.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bridgelabz.R;
import com.bridgelabz.model.TimeEntryResponse;
import com.bridgelabz.util.DateFormater;

import java.util.Date;
import java.util.Objects;

public class AttendanceFragment extends Fragment {
    TextView txtOutTime;
    private AppCompatButton btnConfirm;
    private EditText etDate, etInTime, etOutTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        btnConfirm = (AppCompatButton) view.findViewById(R.id.btnConfirm);
        etDate = (EditText) view.findViewById(R.id.etEtDate);
        etInTime = (EditText) view.findViewById(R.id.etEdtInTime);
        etOutTime = (EditText) view.findViewById(R.id.etEdtOutTime);
        txtOutTime = (TextView) view.findViewById(R.id.txtOutTime);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TimeEntryResponse timeEntryResponse = (TimeEntryResponse) getArguments().getSerializable("responseData");

        if (Objects.equals(timeEntryResponse.getTimeEntryResponseDataModel().getOutTime(), "0")) {
            Date dateIn = DateFormater.getDate(timeEntryResponse.getTimeEntryResponseDataModel().getInTime()/*"2016-07-22T15:05:56.235-0530"*/);
            etDate.setText(DateFormater.getCurrentDateString(dateIn));
            etInTime.setText(DateFormater.getCurrentTimeString(dateIn));

            etOutTime.setVisibility(View.INVISIBLE);
            txtOutTime.setVisibility(View.INVISIBLE);
        } else {
            Date dateIn = DateFormater.getDate(timeEntryResponse.getTimeEntryResponseDataModel().getInTime());
            etDate.setText(DateFormater.getCurrentDateString(dateIn));
            etInTime.setText(DateFormater.getCurrentTimeString(dateIn));

            Date dateOut = DateFormater.getDate(timeEntryResponse.getTimeEntryResponseDataModel().getOutTime());
            txtOutTime.setVisibility(View.VISIBLE);
            etOutTime.setText(DateFormater.getCurrentTimeString(dateOut));
            etOutTime.setVisibility(View.VISIBLE);
        }

        return view;
    }

}
