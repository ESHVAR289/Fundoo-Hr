package com.bridgelabz;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.adapter.SearchAdapter;
import com.bridgelabz.shared_preference.SharedPreference;
import com.bridgelabz.util.GpsLocationTracker;
import com.bridgelabz.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class FundooHrToolbarSearch extends AppCompatActivity {
    Toolbar toolbar;
    private ArrayList<String> mCountries;
    double latitude,longitude;
    GpsLocationTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundoo_hr_toolbar_search);
        toolBarData();
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
    protected void onStop() {
        super.onStop();
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
                loadToolBarSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
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
}
