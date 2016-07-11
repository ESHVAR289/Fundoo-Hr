package com.bridgelabz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.model.MobileAndOtpModel;
import com.bridgelabz.model.MobileNoOtpGson;
import com.bridgelabz.restservice.RestApi;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FundooHrLoginActivity extends AppCompatActivity implements View.OnClickListener{
    @Inject
    Retrofit retrofit;
    TextView txtViewForRetrofit;
    EditText etMobileNo,etConfirmOtp;
    AppCompatButton btnSendOtp, btnConfirmOtp;
    Button btnNext;
    String mo_number, regexString = "^[+][0-9]{10,13}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fundoo_hr_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((App) getApplication()).getmNetComponent().inject(this);

        etMobileNo = (EditText) findViewById(R.id.editTextPhone);
        btnSendOtp = (AppCompatButton) findViewById(R.id.buttonRegister);
        btnNext = (Button) findViewById(R.id.btnNext);

        btnSendOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.buttonRegister :
                sendOtpToMobile();
                break;

        }
    }

    private void confirmOtp() {
        //creating layout inflater object for the dialog box
        LayoutInflater layoutInflater=LayoutInflater.from(this);

        //creating the view to get the dialog box
        View confirmDialogBox = layoutInflater.inflate(R.layout.otp_confirmation,null);

        //Initializing the confirm button for dialog box and the edit text of dialog box
        btnConfirmOtp = (AppCompatButton) confirmDialogBox.findViewById(R.id.buttonConfirm);
        etConfirmOtp = (EditText) confirmDialogBox.findViewById(R.id.editTextOtp);
        //creating an alert dialog builder
        AlertDialog.Builder alert=new AlertDialog.Builder(this);

        //adding out dialog box to the activity
        alert.setView(confirmDialogBox);

        //creating an alert dialog
        final AlertDialog alertDialog=alert.create();

        //Displaying the alert dialog
        alertDialog.show();

        btnConfirmOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hiding the alert dialog
                alertDialog.dismiss();

                //Displaying a progressbar
                final ProgressDialog loading = ProgressDialog.show(FundooHrLoginActivity.this, "Authenticating", "Please wait while we check the entered code", false,false);

                //Getting the user entered otp from edittext
                final String otp = etConfirmOtp.getText().toString().trim();
                Call<MobileNoOtpGson> otpGson = retrofit.create(RestApi.class).getOtpStatus(new MobileAndOtpModel(mo_number, etConfirmOtp.getText().toString().trim()));
                otpGson.enqueue(new Callback<MobileNoOtpGson>() {
                    @Override
                    public void onResponse(Call<MobileNoOtpGson> call, Response<MobileNoOtpGson> response) {
                        if (response.body().getStatus()) {
                            loading.dismiss();
                            startActivity(new Intent(getApplicationContext(), FundooHrToolbarSearch.class));
                        }else {
                            //Displaying a toast if the otp entered is wrong
                            Toast.makeText(FundooHrLoginActivity.this,"Wrong OTP Please Try Again",Toast.LENGTH_LONG).show();
                            confirmOtp();
                        }
                    }
                    @Override
                    public void onFailure(Call<MobileNoOtpGson> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    //this method send the otp to the mobile no.
    public  void sendOtpToMobile(){
        //Displaying the Progress dialog
        final ProgressDialog progressDialog=ProgressDialog.show(this,"Getting otp", "Please wait.....",false,false);
        mo_number = "+91" + etMobileNo.getText().toString();
        if (mo_number.length() < 10 || mo_number.length() > 13 || mo_number.matches(regexString)) {

            Call<MobileNoOtpGson> mobileNoOtpGson = retrofit.create(RestApi.class).getMobileNoStatus(new MobileAndOtpModel(mo_number));
            mobileNoOtpGson.enqueue(new Callback<MobileNoOtpGson>() {
                @Override
                public void onResponse(Call<MobileNoOtpGson> call, Response<MobileNoOtpGson> response) {
                    if (response.body().getStatus()){
                        progressDialog.dismiss();
                        confirmOtp();
                    }
                }
                @Override
                public void onFailure(Call<MobileNoOtpGson> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Something happens wrong ! Please call to our contact person",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
