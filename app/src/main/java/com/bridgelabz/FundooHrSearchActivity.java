package com.bridgelabz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.model.TimeEntryGson;
import com.bridgelabz.model.TimeEntryMessage;
import com.bridgelabz.restservice.RestApi;
import com.bridgelabz.util.DateFormater;

import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FundooHrSearchActivity extends AppCompatActivity {
    @Inject
    Retrofit retrofit;

    EditText etSearch, etDate, etTime;
    ImageButton imgBtnSearch;
    TextView txtJson, txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundoo_hr_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((App) getApplication()).getmNetComponent().inject(this);

        etSearch = (EditText) findViewById(R.id.etSearch);
        imgBtnSearch = (ImageButton) findViewById(R.id.imgBtnSearch);
        txtJson = (TextView) findViewById(R.id.txtJson);
        txtTime = (TextView) findViewById(R.id.txtTime);
        etDate = (EditText) findViewById(R.id.etDate);
        etTime = (EditText) findViewById(R.id.etTime);

        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

    }

}
