package com.bridgelabz;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bridgelabz.adapter.SearchAdapter;
import com.bridgelabz.model.Data;
import com.bridgelabz.model.MessageData;
import com.bridgelabz.model.TimeEntryGson;
import com.bridgelabz.model.TimeEntryMessage;
import com.bridgelabz.restservice.RestApi;
import com.bridgelabz.shared_preference.SharedPreference;
import com.bridgelabz.util.DateFormater;
import com.bridgelabz.util.GpsLocationTracker;
import com.bridgelabz.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FundooHrToolbarSearch extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener,TextView.OnEditorActionListener{
    @Inject
    Retrofit retrofit;

    Toolbar toolbar;
    private ArrayList<String> mCountries;
    private ArrayList<String> mMessages;
    double latitude,longitude;
    boolean updateStatus;
    GpsLocationTracker gps;
    EditText editToolSearch,etSearchMsg, etEdtDate,etEdtTime;
    Button btnConfirm;
    ListView listView;
    View editTextView;
    Switch aSwitch;
    TimeEntryGson gsonData;
    TextView txtViewEditMsg,txtMsgEmpty;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundoo_hr_toolbar_search);
        ((App) getApplication()).getmNetComponent().inject(this);
        toolBarData();
        initializeComponent();
        setOnClickListenerToComponent();
        setFocusListenerToComponent();

    }
    public void initializeComponent(){
        editTextView = FundooHrToolbarSearch.this.getLayoutInflater().inflate(R.layout.activity_fundoo_hr_toolbar_search, null);
        etSearchMsg = (EditText) editTextView.findViewById(R.id.etSearchMsg);
        listView = (ListView) editTextView.findViewById(R.id.list_msg_search);
        txtMsgEmpty = (TextView) editTextView.findViewById(R.id.txt_msg_empty);
        etEdtDate = (EditText) findViewById(R.id.etEtDate);
        etEdtTime = (EditText) findViewById(R.id.etEtTime);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        aSwitch = (Switch) findViewById(R.id.switchEdtTimeDate);
        txtViewEditMsg = (TextView) findViewById(R.id.txtWantToEdit);
    }

    public void setOnClickListenerToComponent(){
        etEdtDate.setOnClickListener(this);
        etEdtTime.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        //etSearchMsg.setOnClickListener(this);
    }public void setFocusListenerToComponent(){
        etEdtDate.setOnFocusChangeListener(this);
        etEdtTime.setOnFocusChangeListener(this);
        etSearchMsg.setOnFocusChangeListener(this);
    }

    public void etSearchClick(View view){
        Toast.makeText(FundooHrToolbarSearch.this,"Clicked",Toast.LENGTH_LONG).show();
        loadEditTextSearch();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.etEtDate :
               // Toast.makeText(FundooHrToolbarSearch.this,"Clicked",Toast.LENGTH_LONG).show();
                getCurrentDate();
                break;
            case R.id.etEtTime :
                Toast.makeText(FundooHrToolbarSearch.this,"Clicked",Toast.LENGTH_LONG).show();
                getCurrentTime();
                break;
            case R.id.btnConfirm :
                Call<MessageData> timeEntryConfirmation = retrofit.create(RestApi.class)
                        .sentTimeEntryConfirmation(
                                new TimeEntryGson(
                                        new Data(gsonData.getData().getUserId(),etEdtTime.getText().toString(),etEdtDate.getText().toString(),
                                                gsonData.getData().getTotalTime()),updateStatus));
                timeEntryConfirmation.enqueue(new Callback<MessageData>() {
                    @Override
                    public void onResponse(Call<MessageData> call, Response<MessageData> response) {
                        if (response.body().isData()){

                        }else {
                            Toast.makeText(FundooHrToolbarSearch.this,"Your data saved successfully",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageData> call, Throwable t) {

                    }
                });
                break;
            case R.id.switchEdtTimeDate:
                btnConfirm.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        int id = view.getId();
        switch (id){
            case R.id.etSearchMsg :
                Toast.makeText(FundooHrToolbarSearch.this,"focused edit text",Toast.LENGTH_LONG).show();
               // loadEditTextSearch();
                break;
            case R.id.etEtDate :
                Toast.makeText(FundooHrToolbarSearch.this,"focused edit text",Toast.LENGTH_LONG).show();
                getCurrentDate();
                break;
            case R.id.etEtTime :
                getCurrentTime();
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        gps=new GpsLocationTracker(this);
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fundoo_hr_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_search:
                loadEditTextSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadEditTextSearch(){
        ArrayList<String> msgStored = SharedPreference.loadList(FundooHrToolbarSearch.this,Utils.PREFS_NAME,Utils.KEY_COUNTRIES);
        Utils.setListViewHeightBasedOnChildren(listView);

        etSearchMsg.setHint("Enter your message");
        final Dialog toolbarSearchDialog = new Dialog(FundooHrToolbarSearch.this, R.style.MyMaterialTheme);
        toolbarSearchDialog.setContentView(editTextView);
        toolbarSearchDialog.setCancelable(false);
        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();

        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        msgStored = (msgStored != null && msgStored.size() > 0) ? msgStored : new ArrayList<String>();
        final SearchAdapter searchAdapter = new SearchAdapter(FundooHrToolbarSearch.this,msgStored,false);

        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(searchAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String message = String.valueOf(adapterView.getItemAtPosition(i));
                SharedPreference.addList(FundooHrToolbarSearch.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES, message);
                etSearchMsg.setText(message);
                sendEditTextDataToRest();
                listView.setVisibility(View.GONE);
            }
        });

        etSearchMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] message = FundooHrToolbarSearch.this.getResources().getStringArray(R.array.saved_messages);
                mMessages = new ArrayList<String>(Arrays.asList(message));
                listView.setVisibility(View.VISIBLE);
                sendEditTextDataToRest();
                searchAdapter.updateList(mMessages, true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                ArrayList<String> filterList = new ArrayList<String>();
                boolean isNodata = false;
                if (charSequence.length() > 0) {
                    for (int i = 0; i < mMessages.size(); i++) {
                        if (mMessages.get(i).toLowerCase().startsWith(charSequence.toString().trim().toLowerCase())) {
                            filterList.add(mMessages.get(i));

                            listView.setVisibility(View.VISIBLE);
                            searchAdapter.updateList(filterList, true);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listView.setVisibility(View.GONE);
                        txtMsgEmpty.setVisibility(View.VISIBLE);
                        txtMsgEmpty.setText("No data found");
                    }
                } else {
                    listView.setVisibility(View.GONE);
                    txtMsgEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void getCurrentDate(){
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
                        etEdtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void sendEditTextDataToRest(){
        String mobile_no = "+919923911289"/*getIntent().getStringExtra("mobile")*/;
        String search_text = etSearchMsg.getText().toString();
        Call<TimeEntryGson> timeEntryResponse = retrofit.create(RestApi.class).getTimeEntryMsg(new TimeEntryMessage(mobile_no, search_text));
        timeEntryResponse.enqueue(new Callback<TimeEntryGson>() {
            @Override
            public void onResponse(Call<TimeEntryGson> call, Response<TimeEntryGson> response) {
                aSwitch.setVisibility(View.VISIBLE);
                txtViewEditMsg.setVisibility(View.VISIBLE);

                TimeEntryGson gsonData = response.body();
                Date date;
                date = DateFormater.getDate(gsonData.getData().getInTime());
                etEdtDate.setText(DateFormater.getCurrentDateString(date));
                etEdtTime.setText(DateFormater.getCurrentTimeString(date));
            }

            @Override
            public void onFailure(Call<TimeEntryGson> call, Throwable t) {
                Toast.makeText(FundooHrToolbarSearch.this, "Something happen wrong ...........", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void getCurrentTime(){
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
                        etEdtTime.setText(hourOfDay + ":" + minute + ":" + mSeconds);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void loadToolBarSearch() {
        ArrayList<String> countryStored = SharedPreference.loadList(FundooHrToolbarSearch.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES);

        View view = FundooHrToolbarSearch.this.getLayoutInflater().inflate(R.layout.view_toolbar_search, null);
        LinearLayout parentToolbarSearch = (LinearLayout) view.findViewById(R.id.parent_toolbar_search);
        ImageView imgToolBack = (ImageView) view.findViewById(R.id.img_tool_back);
        final EditText editToolSearch = (EditText) view.findViewById(R.id.edt_tool_search);
        ImageView imgToolMic = (ImageView) view.findViewById(R.id.img_tool_mic);
        final ListView listSearch = (ListView) view.findViewById(R.id.list_search);
        final TextView txtEmpty = (TextView) view.findViewById(R.id.txt_empty);

        Utils.setListViewHeightBasedOnChildren(listSearch);

        editToolSearch.setHint("Search your country");

        final Dialog toolbarSearchDialog = new Dialog(FundooHrToolbarSearch.this, R.style.MyMaterialTheme);
        toolbarSearchDialog.setContentView(view);
        toolbarSearchDialog.setCancelable(false);
        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();

        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        countryStored = (countryStored != null && countryStored.size() > 0) ? countryStored : new ArrayList<String>();
        final SearchAdapter searchAdapter = new SearchAdapter(FundooHrToolbarSearch.this, countryStored, false);

        listSearch.setVisibility(View.VISIBLE);
        listSearch.setAdapter(searchAdapter);

        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String country = String.valueOf(adapterView.getItemAtPosition(i));
                SharedPreference.addList(FundooHrToolbarSearch.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES, country);
                editToolSearch.setText(country);
                listSearch.setVisibility(View.GONE);
            }
        });

        editToolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] country = FundooHrToolbarSearch.this.getResources().getStringArray(R.array.countries_array);
                mCountries = new ArrayList<String>(Arrays.asList(country));
                listSearch.setVisibility(View.VISIBLE);
                searchAdapter.updateList(mCountries, true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                ArrayList<String> filterList = new ArrayList<String>();
                boolean isNodata = false;
                if (charSequence.length() > 0) {
                    for (int i = 0; i < mCountries.size(); i++) {
                        if (mCountries.get(i).toLowerCase().startsWith(charSequence.toString().trim().toLowerCase())) {
                            filterList.add(mCountries.get(i));

                            listSearch.setVisibility(View.VISIBLE);
                            searchAdapter.updateList(filterList, true);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No data found");
                    }
                } else {
                    listSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarSearchDialog.dismiss();
            }
        });

        imgToolMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editToolSearch.setText("");
            }
        });
    }

    private void toolBarData() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FundooHr");
        toolbar.setTitleTextColor(getResources().getColor(R.color.blue));
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendEditTextDataToRest();
            handled = true;
        }
        return handled;
    }
}
