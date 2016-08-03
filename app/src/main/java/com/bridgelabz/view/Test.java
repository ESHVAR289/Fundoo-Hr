package com.bridgelabz.view;

import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bridgelabz.R;
import com.bridgelabz.callback.ResponseCallbackListener;
import com.bridgelabz.controller.AttendanceController;
import com.bridgelabz.dagger.AppController;
import com.bridgelabz.fragment.AttendanceFragment;
import com.bridgelabz.model.TimeEntryResponse;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class Test extends AppCompatActivity implements ResponseCallbackListener {
    @Inject
    Retrofit retrofit;
    private EditText editText;
    private Button btnSend;
    private AttendanceController mAttendanceController;
    private LinearLayout mLinearLayout, editTextLinearLayout;

    private static void animateUp(View view, int height) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", height, 0);
        animator.setDuration(1500);
        animator.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        editText = (EditText) findViewById(R.id.etMessage);
        btnSend = (Button) findViewById(R.id.btn_send);
        ((AppController) getApplication()).getmNetComponent().inject(this);
        mAttendanceController = new AttendanceController(Test.this, retrofit);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        editTextLinearLayout = (LinearLayout) findViewById(R.id.parentLinearLayout);
        animateUp(editTextLinearLayout, 1500);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEditTextDataToRest();
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    sendEditTextDataToRest();
                    return true;
                }
                return false;
            }
        });


    }

    private void sendEditTextDataToRest() {
        mAttendanceController.getResponseForMessage("+919923911289", editText.getText().toString());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void messageResponse(TimeEntryResponse mTimeEntryResponseData) {
        AttendanceFragment fragment = new AttendanceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("responseData", mTimeEntryResponseData);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void confirmationResponse(String message) {
        Snackbar snackbar = Snackbar
                .make(mLinearLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void attendanceErrorResponse(String err) {
        Snackbar snackbar = Snackbar
                .make(mLinearLayout, err, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
