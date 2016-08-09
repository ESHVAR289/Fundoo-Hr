package com.bridgelabz.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bridgelabz.R;
import com.bridgelabz.callback.LoginCallbackListener;
import com.bridgelabz.controller.LoginController;
import com.bridgelabz.dagger.AppController;
import com.bridgelabz.shared_preference.SessionManager;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class FundooHrLoginActivity extends AppCompatActivity implements View.OnClickListener, LoginCallbackListener {
    private static boolean mobileValidationFlag = false;
    private static boolean pswdValidatingFlag = false;
    @Inject
    Retrofit retrofit;
    private ProgressDialog progressDialog;
    private EditText etMobileNo, etConfirmOtp, etPassword;
    private String mo_number, mo_err_msg, loginPassword, login_err_msg;
    private CoordinatorLayout mCoordinatorLayout;
    private LoginController mLoginController;
    private AppCompatButton btnLogin;
    private StringBuilder snackBarMsg;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundoo_hr_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((AppController) getApplication()).getmNetComponent().inject(this);
        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to FundooHrToolbarSearch activity
            Intent intent = new Intent(FundooHrLoginActivity.this, MessageSearch.class);
            startActivity(intent);
            finish();
        }
        snackBarMsg = new StringBuilder("Check your ");
        initView();
    }

    private void initView() {
        mLoginController = new LoginController(FundooHrLoginActivity.this, retrofit);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etPassword = (EditText) findViewById(R.id.etLoginPswd);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        btnLogin = (AppCompatButton) findViewById(R.id.btnLogIn);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnLogIn:
                validateMobileNo();
                validatePassword();
                if (etMobileNo.getText().toString().isEmpty() ||
                        etPassword.getText().toString().isEmpty()) {
                    Snackbar snackbar = Snackbar
                            .make(mCoordinatorLayout, "Please fill the empty fields first", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    if (mobileValidationFlag || pswdValidatingFlag) {
                        Snackbar snackbar = Snackbar
                                .make(mCoordinatorLayout, snackBarMsg.toString() + mo_err_msg + login_err_msg, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        checkLogin();
                    }
                }
                break;
        }
    }

    private void checkLogin() {
        //Displaying the Progress dialog
        progressDialog = ProgressDialog.show(this, "Log In", "Please wait.....", false, false);
        mo_number = "+91" + etMobileNo.getText().toString();
        loginPassword = etPassword.getText().toString();
        String regexString = "^[+][0-9]{10,13}$";
        if (mo_number.length() < 10 || mo_number.length() > 13 || mo_number.matches(regexString)) {
            mLoginController.checkLoginDetails(mo_number, loginPassword);
        }
    }

    private void validateMobileNo() {
        String mobileNo = etMobileNo.getText().toString();
        String regexString = "^[+][0-9]{10,13}$", mobileWithCountryCode = "+91" + mobileNo;

        if (mobileNo.length() < 10 || mobileWithCountryCode.length() > 13 || !mobileWithCountryCode.matches(regexString)) {
            mo_err_msg = "Mobile No. ";
            mobileValidationFlag = true;
        } else {
            mo_err_msg = "";
            mobileValidationFlag = false;
        }
    }

    private void validatePassword() {
        String pswd = etPassword.getText().toString();
        if (pswd.length() < 10 || pswd.length() > 10) {
            login_err_msg = "Login Password ";
            pswdValidatingFlag = true;
        } else {
            login_err_msg = "";
            pswdValidatingFlag = false;
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

    @Override
    public void loginResponse() {
        session.setLogin(true);//setting the shared preference that user has logged in.
        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, "Logging successful....!", Snackbar.LENGTH_LONG);
        snackbar.show();
        progressDialog.dismiss();
        Intent intent = new Intent(this,MessageSearch.class);
        session.setMobileNo(etMobileNo.getText().toString());
        startActivity(intent);
    }

    @Override
    public void loginErrorResponse(String err) {
        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, err, Snackbar.LENGTH_LONG);
        snackbar.show();
        progressDialog.dismiss();
    }
}
