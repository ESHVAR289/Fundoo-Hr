package com.bridgelabz.view;

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
import android.widget.Toast;

import com.bridgelabz.R;
import com.bridgelabz.callback.LoginCallbackListener;
import com.bridgelabz.controller.LoginController;
import com.bridgelabz.util.App;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class FundooHrLoginActivity extends AppCompatActivity implements View.OnClickListener, LoginCallbackListener {
    @Inject
    Retrofit retrofit;
    private ProgressDialog progressDialog;
    private EditText etMobileNo, etConfirmOtp;
    private String mo_number;
    private LoginController mLoginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fundoo_hr_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((App) getApplication()).getmNetComponent().inject(this);
        mLoginController = new LoginController(FundooHrLoginActivity.this, retrofit);
        etMobileNo = (EditText) findViewById(R.id.editTextPhone);
        AppCompatButton btnSendOtp = (AppCompatButton) findViewById(R.id.buttonRegister);
        Button btnNext = (Button) findViewById(R.id.btnNext);

        btnSendOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.buttonRegister:
                sendOtpToMobile();
                break;
        }
    }

    private void confirmOtp() {
        //creating layout inflater object for the dialog box
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        //creating the view to get the dialog box
        View confirmDialogBox = layoutInflater.inflate(R.layout.otp_confirmation, null);

        //Initializing the confirm button for dialog box and the edit text of dialog box
        AppCompatButton btnConfirmOtp = (AppCompatButton) confirmDialogBox.findViewById(R.id.buttonConfirm);
        etConfirmOtp = (EditText) confirmDialogBox.findViewById(R.id.editTextOtp);
        //creating an alert dialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //adding out dialog box to the activity
        alert.setView(confirmDialogBox);
        //creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        //Displaying the alert dialog
        alertDialog.show();

        btnConfirmOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hiding the alert dialog
                alertDialog.dismiss();
                //Displaying a progressbar
                progressDialog = ProgressDialog.show(FundooHrLoginActivity.this, "Authenticating", "Please wait while we check the entered code", false, false);
                //Getting the user entered otp from edittext
                final String otp = etConfirmOtp.getText().toString().trim();
                mLoginController.getOtpConfirmation(mo_number, otp);
            }
        });
    }

    //this method send the otp to the mobile no.
    private void sendOtpToMobile() {
        //Displaying the Progress dialog
        progressDialog = ProgressDialog.show(this, "Getting otp", "Please wait.....", false, false);
        mo_number = "+91" + etMobileNo.getText().toString();
        String regexString = "^[+][0-9]{10,13}$";
        if (mo_number.length() < 10 || mo_number.length() > 13 || mo_number.matches(regexString)) {
            mLoginController.getTheOtp(mo_number);
        }
    }

    @Override
    public void mobileNoResponse(Boolean status) {
        progressDialog.dismiss();
        confirmOtp();
    }

    @Override
    public void checkForOtpConfirmation(Boolean status) {
        if (status) {
            progressDialog.dismiss();
            startActivity(new Intent(getApplicationContext(), FundooHrToolbarSearch.class));
        } else {
            //Displaying a toast if the otp entered is wrong
            Toast.makeText(FundooHrLoginActivity.this, "Wrong OTP Please Try Again", Toast.LENGTH_LONG).show();
            confirmOtp();
        }
    }

    @Override
    public void onFailureMobileNoResponse() {
        Toast.makeText(getApplicationContext(), "Something happens wrong ! Please call to our contact person", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailureOtpResponse() {
        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
    }
}
