package com.bridgelabz;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bridgelabz.model.TimeEntryGson;
import com.bridgelabz.model.TimeEntryMessage;
import com.bridgelabz.restservice.RestApi;
import com.bridgelabz.util.DateFormater;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FundooHrSearchActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    Retrofit retrofit;

    EditText etSearch, etDate, etTime, etEditDate, etEditTime;
    ImageButton imgBtnSearch, imgBtnTxtEditDate, imgBtnTxtEditTime, imgBtnEditDate, imgBtnEditTime;
    TextView txtJson, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundoo_hr_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((App) getApplication()).getmNetComponent().inject(this);

        etSearch = (EditText) findViewById(R.id.etSearch);
        imgBtnSearch = (ImageButton) findViewById(R.id.imgBtnSearch);
        imgBtnEditTime = (ImageButton) findViewById(R.id.btnEditTime);
        imgBtnEditDate = (ImageButton) findViewById(R.id.btnEditDate);
        txtJson = (TextView) findViewById(R.id.txtJson);
        txtTime = (TextView) findViewById(R.id.txtTime);
        etDate = (EditText) findViewById(R.id.etDate);
        etTime = (EditText) findViewById(R.id.etTime);
        imgBtnEditDate.setOnClickListener(this);
        imgBtnEditTime.setOnClickListener(this);
        imgBtnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == imgBtnEditDate) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == imgBtnEditTime) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            mSeconds = c.get(Calendar.SECOND);
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            etTime.setText(hourOfDay + ":" + minute + ":" + mSeconds);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (view == imgBtnSearch) {
            String mobile_no = "+919923911289"/*getIntent().getStringExtra("mobile")*/;
            String search_text = etSearch.getText().toString();
            Call<TimeEntryGson> timeEntryResponse = retrofit.create(RestApi.class).getTimeEntryMsg(new TimeEntryMessage(mobile_no, search_text));
            timeEntryResponse.enqueue(new Callback<TimeEntryGson>() {
                @Override
                public void onResponse(Call<TimeEntryGson> call, Response<TimeEntryGson> response) {
                    TimeEntryGson gsonData = response.body();
                    Date date;
                    date = DateFormater.getDate(gsonData.getData().getInTime());

                    txtJson.setText(DateFormater.getCurrentDateString(date));
                    txtTime.setText(DateFormater.getCurrentTimeString(date));
                    etDate.setText(DateFormater.getCurrentDateString(date));
                    etTime.setText(DateFormater.getCurrentTimeString(date));
                }

                @Override
                public void onFailure(Call<TimeEntryGson> call, Throwable t) {
                    Toast.makeText(FundooHrSearchActivity.this, "Something happen wrong ...........", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

