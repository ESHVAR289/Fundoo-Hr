package com.bridgelabz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.model.MobileAndOtpModel;
import com.bridgelabz.model.MobileNoOtpGson;
import com.bridgelabz.restservice.RestApi;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FundooHrLoginActivity extends AppCompatActivity {
    @Inject
    Retrofit retrofit;
    TextView txtViewForRetrofit;
    EditText etMobileNo;
    Button btnNext;
    String mo_number, regexString = "^[+][0-9]{10,13}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundoo_hr_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((App) getApplication()).getmNetComponent().inject(this);

        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mo_number = "+91" + etMobileNo.getText().toString();
                if (mo_number.length() < 10 || mo_number.length() > 13 || mo_number.matches(regexString)) {

                    Call<MobileNoOtpGson> mobileNoOtpGson = retrofit.create(RestApi.class).getMobileNoStatus(new MobileAndOtpModel(mo_number));
                    mobileNoOtpGson.enqueue(new Callback<MobileNoOtpGson>() {
                        @Override
                        public void onResponse(Call<MobileNoOtpGson> call, Response<MobileNoOtpGson> response) {

                            /*try {
                                *//*JSONObject jsonObject=new JSONObject(response.body().toString());
                                Boolean status = jsonObject.getBoolean(response.body().getStatus());*//*
                                if (response.body().getStatus()){
                                    Intent intent=new Intent(FundooHrLoginActivity.this,FundooHrOtpActivity.class);
                                    intent.putExtra("mobile",mo_number);
                                    startActivity(intent);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                            if (response.body().getStatus()) {
                                Intent intent = new Intent(FundooHrLoginActivity.this, FundooHrOtpActivity.class);
                                intent.putExtra("mobile", mo_number);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<MobileNoOtpGson> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Something happens wrong ! Please call to our contact person",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fundoo_hr_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
