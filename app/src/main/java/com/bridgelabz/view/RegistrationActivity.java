package com.bridgelabz.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bridgelabz.R;

public class RegistrationActivity extends AppCompatActivity {
    private EditText etMobileNo, etEmailId, etPswd, etRePswd;
    private AppCompatButton btnSignUp;
    private TextInputLayout inputMobileNo, inputEmailId, inputPswd, inputRePswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeTheView();
    }

    private void initializeTheView() {
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etPswd = (EditText) findViewById(R.id.etRegistrationPswd);
        etRePswd = (EditText) findViewById(R.id.etRegistrationRePswd);

        inputMobileNo = (TextInputLayout) findViewById(R.id.inputMobileNo);
        inputEmailId = (TextInputLayout) findViewById(R.id.inputEmailId);
        inputPswd = (TextInputLayout) findViewById(R.id.inputPswd);
        inputRePswd = (TextInputLayout) findViewById(R.id.inputRePswd);

        btnSignUp = (AppCompatButton) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRegistrationDetails();
            }
        });
    }

    private void submitRegistrationDetails() {
        if (!validateMobileNo("+91" + etMobileNo.getText().toString())) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword(etPswd.getText().toString().trim(), etPswd)) {
            return;
        }
        if (!validatePassword(etRePswd.getText().toString().trim(), etRePswd)) {
            return;
        }
        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateMobileNo(String mobileNo) {
        String regexString = "^[+][0-9]{10,13}$";
        if (mobileNo.trim().isEmpty() && mobileNo.length() < 10 && mobileNo.length() > 13 && mobileNo.matches(regexString)) {
            inputMobileNo.setError(getString(R.string.err_msg_mobile));
            requestFocus(etMobileNo);
            return false;
        } else {
            inputMobileNo.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        String email = etEmailId.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputEmailId.setError(getString(R.string.err_msg_email));
            requestFocus(etEmailId);
            return false;
        } else {
            inputEmailId.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword(String pswd, View view) {
        if (pswd.isEmpty()) {
            inputPswd.setError(getString(R.string.err_msg_password));
            requestFocus(view);
            return false;
        } else {
            inputPswd.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    class CustomTextWatcher implements TextWatcher {

        private View view;

        private CustomTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.etMobileNo:
                    validateMobileNo("+91" + etMobileNo.getText().toString());
                    break;
                case R.id.etEmailId:
                    validateEmail();
                    break;
                case R.id.etRegistrationPswd:
                    validatePassword(etPswd.getText().toString().trim(), etPswd);
                    break;
                case R.id.etRegistrationRePswd:
                    validatePassword(etRePswd.getText().toString().trim(), etRePswd);
                    break;
            }
        }

    }

}
