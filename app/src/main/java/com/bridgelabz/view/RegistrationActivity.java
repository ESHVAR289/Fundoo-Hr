package com.bridgelabz.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bridgelabz.R;

public class RegistrationActivity extends AppCompatActivity {
    private EditText etMobileNo, etEmailId, etPswd, etRePswd;
    private AppCompatButton btnSignUp;
    private CoordinatorLayout mCoordinatorLayout;
    private String snackMobileNoMsg, snackEmailMsg, snackPswdMsg;
    private StringBuilder snackBarMsg;
    private static boolean mobileValidationFlag=false;
    private static boolean emailValidationFlag = false;
    private static boolean pswdValidationFlag=false;
    private static boolean rePswdValidationFlag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeTheView();
    }

    private void initializeTheView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etPswd = (EditText) findViewById(R.id.etRegistrationPswd);
        etRePswd = (EditText) findViewById(R.id.etRegistrationRePswd);

       /* etMobileNo.addTextChangedListener(new CustomTextWatcher(etMobileNo));
        etEmailId.addTextChangedListener(new CustomTextWatcher(etEmailId));
        etPswd.addTextChangedListener(new CustomTextWatcher(etPswd));
        etRePswd.addTextChangedListener(new CustomTextWatcher(etRePswd));*/

        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, "Please fill your credentials", Snackbar.LENGTH_LONG);
        snackbar.show();
        snackBarMsg = new StringBuilder();

        btnSignUp = (AppCompatButton) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateMobileNo();
                validateEmail();
                validatePassword();
                if (etMobileNo.getText().toString().isEmpty() ||
                        etEmailId.getText().toString().isEmpty() ||
                        etPswd.getText().toString().isEmpty() ||
                        etRePswd.getText().toString().isEmpty()) {
                    Snackbar snackbar = Snackbar
                            .make(mCoordinatorLayout, "Please fill the empty fields first", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    if (mobileValidationFlag || emailValidationFlag || pswdValidationFlag){
                        Snackbar snackbar = Snackbar
                                .make(mCoordinatorLayout, snackBarMsg.toString(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else{
                        Snackbar snackbar = Snackbar
                                .make(mCoordinatorLayout, "Registered successfully....!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            }
        });
    }

    /*private boolean submitRegistrationDetails() {
        snackBarMsg = new StringBuilder("Check your ");
        if (!validateMobileNo(etMobileNo.getText().toString())) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validatePassword(etPswd.getText().toString(), etRePswd.getText().toString())) {
            return;
        }
        if (validateMobileNo(etMobileNo.getText().toString()) ||
            validateEmail() ||
            validatePassword(etPswd.getText().toString(), etRePswd.getText().toString())){
            return false;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        return true;
    }*/

    private void validateMobileNo() {
        String mobileNo =etMobileNo.getText().toString();
        String regexString = "^[+][0-9]{10,13}$", mobileWithCountryCode = "+91" + mobileNo;

        if (mobileNo.length() < 10 || mobileWithCountryCode.length() > 13 || !mobileWithCountryCode.matches(regexString)) {
            snackBarMsg.append("Mobile No. ");
            mobileValidationFlag = true;
        }else
            mobileValidationFlag = false;
    }

    private void validateEmail() {
        String email = etEmailId.getText().toString().trim();

        if (!isValidEmail(email)) {
            snackBarMsg.append("Email Id ");
            emailValidationFlag = true;
        }else
            emailValidationFlag = false;
    }

    private void validatePassword() {
        String pswd = etPswd.getText().toString();
        String rePswd =etRePswd.getText().toString();
        if (!pswd.equals(rePswd)) {
            snackBarMsg.append("Password not match ");
            pswdValidationFlag = true;
        }else
            pswdValidationFlag = false;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

//    private class CustomTextWatcher implements TextWatcher {
//
//        private View view;
//
//        private CustomTextWatcher(View view) {
//            this.view = view;
//        }
//
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        }
//
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        }
//
//        public void afterTextChanged(Editable editable) {
//            switch (view.getId()) {
//                case R.id.etMobileNo:
//                    validateMobileNo("+91"+etMobileNo.getText().toString());
//                    break;
//                case R.id.etEmailId:
//                    validateEmail();
//                    break;
//                case R.id.etRegistrationRePswd:
//                    validatePassword(etPswd.toString(), etRePswd.toString());
//                    break;
//            }
//        }
//
//    }

}
