package com.bridgelabz.view;

import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.R;
import com.bridgelabz.callback.ResponseCallbackListener;
import com.bridgelabz.controller.AttendanceController;
import com.bridgelabz.dagger.AppController;
import com.bridgelabz.fragment.AttendanceFragment;
import com.bridgelabz.model.ConfirmationResponse;
import com.bridgelabz.model.TimeEntryResponse;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class Test extends AppCompatActivity implements ResponseCallbackListener {
    @Inject
    Retrofit retrofit;
    EditText editText;
    Button btnSend;
    AttendanceController mAttendanceController;

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
        LinearLayout parent = (LinearLayout) findViewById(R.id.parentLinearLayout);
        animateUp(parent, 1500);

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
        //editText.setText("");
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
    public void confirmationResponse(ConfirmationResponse confirmationResponse) {

    }

    @Override
    public void onFailureMessageResponse(Throwable t) {
        Toast.makeText(this, t.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailureOtpConfirmation() {

    }
}
