package com.bridgelabz.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bridgelabz.R;
import com.bridgelabz.callback.ResponseCallbackListener;
import com.bridgelabz.adapter.AttendaceRecyclerViewAdapter;
import com.bridgelabz.adapter.SearchAdapter;
import com.bridgelabz.controller.AttendanceController;
import com.bridgelabz.model.AttendanceDataModel;
import com.bridgelabz.model.ConfirmationResponse;
import com.bridgelabz.model.TimeEntryResponse;
import com.bridgelabz.restservice.RestApi;
import com.bridgelabz.shared_preference.SharedPreference;
import com.bridgelabz.util.App;
import com.bridgelabz.util.DateFormater;
import com.bridgelabz.util.GpsLocationTracker;
import com.bridgelabz.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FundooHrToolbarSearch extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, ResponseCallbackListener {
    private static final String TAG = FundooHrToolbarSearch.class.getSimpleName();
    @Inject
    Retrofit retrofit;
    String gsonString="{\"data\":{\"userId\":\"+919923911289\",\"inTime\":\"2016-07-22 11:27:49 +05:30\",\"outTime\":\"0\",\"totalTime\":\"0\",\"type\":\"attendance\"}}";
    AttendanceController mAttendanceController;
    Toolbar toolbar;
    private ArrayList<String> mCountries;
    private ArrayList<String> mMessages;
    double latitude, longitude;
    boolean updateStatus;
    GpsLocationTracker gps;
    EditText editToolSearch, etSearchMsg, etEdtDate, etEdtInTime, etEdtOutTime;
    Button btnConfirm;
    ListView listView;
    View editTextView;
    Switch aSwitch;
    TimeEntryResponse mTimeEntryResponse;
    AttendanceDataModel dataModel;
    TextView txtViewEditMsg, txtMsgEmpty, txtOutTime;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeconds;
    private RecyclerView recyclerView;
    ArrayList<AttendanceDataModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundoo_hr_toolbar_search);
        ((App) getApplication()).getmNetComponent().inject(this);
        mAttendanceController = new AttendanceController(FundooHrToolbarSearch.this,retrofit);
        toolBarData();
        initializeComponent();
        setOnClickListenerToComponent();
        setFocusListenerToComponent();
        TimeEntryResponse timeEntryResponse=new Gson().fromJson(gsonString,TimeEntryResponse.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        etSearchMsg = (EditText) findViewById(R.id.etSearchMsg);

        etSearchMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    /*sendEditTextDataToRest();*/
                    mAttendanceController.getResponseForMessage("+919923911289",etSearchMsg.getText().toString());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etSearchMsg.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

    }

    public void initializeComponent() {
        editTextView = FundooHrToolbarSearch.this.getLayoutInflater().inflate(R.layout.activity_fundoo_hr_toolbar_search, null);
        /*etSearchMsg = (EditText) editTextView.findViewById(R.id.etSearchMsg);*/
        listView = (ListView) editTextView.findViewById(R.id.list_msg_search);
        txtMsgEmpty = (TextView) editTextView.findViewById(R.id.txt_msg_empty);
        etEdtDate = (EditText) findViewById(R.id.etEtDate);
        etEdtInTime = (EditText) findViewById(R.id.etEdtInTime);
        etEdtOutTime = (EditText) findViewById(R.id.etEdtOutTime);
        txtOutTime = (TextView) findViewById(R.id.txtOutTime);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        aSwitch = (Switch) findViewById(R.id.switchEdtTimeDate);
        txtViewEditMsg = (TextView) findViewById(R.id.txtWantToEdit);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewAttendance);
    }

    public void setOnClickListenerToComponent() {
        etEdtDate.setOnClickListener(this);
        etEdtInTime.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        //etSearchMsg.setOnClickListener(this);
    }

    public void setFocusListenerToComponent() {
        etEdtDate.setOnFocusChangeListener(this);
        etEdtInTime.setOnFocusChangeListener(this);
        // etSearchMsg.setOnFocusChangeListener(this);
    }

    public void etSearchClick(View view) {
        //Toast.makeText(FundooHrToolbarSearch.this,"Clicked",Toast.LENGTH_LONG).show();
        //sendEditTextDataToRest();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            /*case R.id.etSearchMsg:
                Toast.makeText(FundooHrToolbarSearch.this,"Clicked",Toast.LENGTH_LONG).show();
                loadEditTextSearch();*/
            case R.id.etEtDate:
                // Toast.makeText(FundooHrToolbarSearch.this,"Clicked",Toast.LENGTH_LONG).show();
                // getCurrentDate();
                break;
            case R.id.etEdtInTime:
                //Toast.makeText(FundooHrToolbarSearch.this,"Clicked",Toast.LENGTH_LONG).show();
                // getCurrentTime();
                break;
            case R.id.btnConfirm:
                //sendEditTextDataToRest();
                /*Call<MessageData> timeEntryConfirmation = retrofit.create(RestApi.class)
                        .sentTimeEntryConfirmation(
                                new TimeEntryResponse(
                                        new TimeEntryResponseDataModel(mTimeEntryResponse.getTimeEntryResponseDataModel().getUserId(),
                                                                        etEdtInTime.getText().toString(),
                                                                        etEdtDate.getText().toString(),
                                                                        mTimeEntryResponse.getTimeEntryResponseDataModel().getTotalTime()),
                                                                        updateStatus));
                timeEntryConfirmation.enqueue(new Callback<MessageData>() {
                    @Override
                    public void onResponse(Call<MessageData> call, Response<MessageData> response) {
                        if (response.body().isData()) {

                        } else {
                            Toast.makeText(FundooHrToolbarSearch.this, "Your data saved successfully", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageData> call, Throwable t) {

                    }
                });
                break;*/
                mAttendanceController.sendResponseConfirmation(mTimeEntryResponse.getTimeEntryResponseDataModel().getUserId(),
                        etEdtDate.getText().toString(),
                        etEdtInTime.getText().toString(),
                        etEdtOutTime.getText().toString(),
                        mTimeEntryResponse.getTimeEntryResponseDataModel().getTotalTime(),
                        updateStatus);
            case R.id.txtWantToEdit:

                // btnConfirm.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        int id = view.getId();
        switch (id) {
            case R.id.etSearchMsg:
                // Toast.makeText(FundooHrToolbarSearch.this,"focused edit text",Toast.LENGTH_LONG).show();
                break;
            case R.id.etEtDate:
                Toast.makeText(FundooHrToolbarSearch.this, "focused edit text", Toast.LENGTH_LONG).show();
                getCurrentDate();
                break;
            case R.id.etEdtInTime:
                getCurrentTime();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*gps=new GpsLocationTracker(this);
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
        }*/
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
                //loadEditTextSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadEditTextSearch() {
        ArrayList<String> msgStored = SharedPreference.loadList(FundooHrToolbarSearch.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES);
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
        final SearchAdapter searchAdapter = new SearchAdapter(FundooHrToolbarSearch.this, msgStored, false);

        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(searchAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String message = String.valueOf(adapterView.getItemAtPosition(i));
                SharedPreference.addList(FundooHrToolbarSearch.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES, message);
                etSearchMsg.setText(message);
                if (etSearchMsg.getText().toString() == "give me attendance log") {
                   /* list=getTheAttendaceLog();
                    AttendaceRecyclerViewAdapter adapter=new AttendaceRecyclerViewAdapter(FundooHrToolbarSearch.this,list);
                    recyclerView.setAdapter(adapter);*/
                } else {

                }
                listView.setVisibility(View.GONE);
            }
        });

        etSearchMsg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    listView.setVisibility(View.GONE);
                    txtMsgEmpty.setVisibility(View.GONE);
                    etSearchMsg.setHint("");
                }
            }
        });
        etSearchMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] message = FundooHrToolbarSearch.this.getResources().getStringArray(R.array.saved_messages);
                mMessages = new ArrayList<>(Arrays.asList(message));
                listView.setVisibility(View.VISIBLE);

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

    private void getCurrentDate() {
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

    private void getCurrentTime() {
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
                        etEdtInTime.setText(hourOfDay + ":" + minute + ":" + mSeconds);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    private void toolBarData() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FundooHr");
        toolbar.setTitleTextColor(getResources().getColor(R.color.blue));
        setSupportActionBar(toolbar);
    }

    public void getTheAttendaceLog() {

        Call<String> attendanceData = retrofit.create(RestApi.class).getAttendanceData();
        attendanceData.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG, "onResponse: .................." + response.body());
                final Type typeToken = new TypeToken<ArrayList<AttendanceDataModel>>() {

                }.getType();
                ArrayList<AttendanceDataModel> dataModel = new Gson().fromJson(response.body(), typeToken);

                AttendaceRecyclerViewAdapter adapter = new AttendaceRecyclerViewAdapter(FundooHrToolbarSearch.this, dataModel);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i(TAG, "onFailure: ..................." + t.toString());
            }
        });
    }

    @Override
    public void messageResponse(TimeEntryResponse timeEntryResponse) {
        this.mTimeEntryResponse=timeEntryResponse;
        if (Objects.equals(timeEntryResponse.getTimeEntryResponseDataModel().getOutTime(), "0")){
            Date dateIn = DateFormater.getDate(timeEntryResponse.getTimeEntryResponseDataModel().getInTime()/*"2016-07-22T15:05:56.235-0530"*/);
            etEdtDate.setText(DateFormater.getCurrentDateString(dateIn));
            etEdtInTime.setText(DateFormater.getCurrentTimeString(dateIn));

            etEdtOutTime.setVisibility(View.INVISIBLE);
            txtOutTime.setVisibility(View.INVISIBLE);
        } else {
            Date dateIn = DateFormater.getDate(mTimeEntryResponse.getTimeEntryResponseDataModel().getInTime());
            etEdtDate.setText(DateFormater.getCurrentDateString(dateIn));
            etEdtInTime.setText(DateFormater.getCurrentTimeString(dateIn));

            Date dateOut = DateFormater.getDate(mTimeEntryResponse.getTimeEntryResponseDataModel().getOutTime());
            txtOutTime.setVisibility(View.VISIBLE);
            etEdtOutTime.setText(DateFormater.getCurrentTimeString(dateOut));
            etEdtOutTime.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void confirmationResponse(ConfirmationResponse confirmationResponse) {
        if (confirmationResponse.getMessage().equals("update"))
            Toast.makeText(this,"Updated successfully",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"There is some problem",Toast.LENGTH_LONG).show();
    }
}
