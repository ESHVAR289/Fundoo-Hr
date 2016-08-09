package com.bridgelabz.view;

import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
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
import com.bridgelabz.model.TimeEntryResponse;
import com.bridgelabz.shared_preference.SessionManager;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MessageSearch extends AppCompatActivity  implements ResponseCallbackListener,NavigationView.OnNavigationItemSelectedListener{
    @Inject
    Retrofit retrofit;

    private ProgressDialog progressDialog;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar toolbar;
    private EditText editText;
    private Button btnSend;
    private AttendanceController mAttendanceController;
    private LinearLayout editTextLinearLayout;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_search);
        toolbar = (Toolbar) findViewById(R.id.message_toolbar);
        setSupportActionBar(toolbar);
        session = new SessionManager(getApplicationContext());
        ((AppController) getApplication()).getmNetComponent().inject(this);

        //Initialize the view component
        initView();

        //Initialize the drawer layout
        initializeDrawerLayout();
        animateUp(editTextLinearLayout, 1500);
        mAttendanceController = new AttendanceController(this, retrofit);

        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Displaying the Progress dialog
                progressDialog = ProgressDialog.show(MessageSearch.this, "Getting Response", "Please wait.....", false, false);
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

    private void initView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.message_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.nvView);
        mNavigationView.setNavigationItemSelectedListener(this);

        editText = (EditText) findViewById(R.id.etMessage);
        btnSend = (Button) findViewById(R.id.btn_send);
        editTextLinearLayout = (LinearLayout) findViewById(R.id.parentLinearLayout);
    }

    private void initializeDrawerLayout() {
        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
    private static void animateUp(View view, int height) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", height, 0);
        animator.setDuration(1500);
        animator.start();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        //Checking if the item is in checked state or not, if not make it in checked state
        if (menuItem.isChecked())
            menuItem.setChecked(false);
        else
            menuItem.setChecked(true);
        //Closing drawer on item click
        mDrawerLayout.closeDrawers();

        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.sign_out:
                Toast.makeText(getApplicationContext(), "Sign out successfully", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(mDrawerLayout, "Sign out successfully", Snackbar.LENGTH_LONG);
                snackbar.show();
                SessionManager sessionManager = new SessionManager(this);
                sessionManager.setLogin(false);
                startActivity(new Intent(this, FundooHrLoginActivity.class));
                return true;

            case R.id.about:
                Toast.makeText(getApplicationContext(), "About clicked", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.edt_profile:
                Toast.makeText(getApplicationContext(), "This section will comming soon", Toast.LENGTH_SHORT).show();

            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;
        }
    }

    private void sendEditTextDataToRest() {
        mAttendanceController.getResponseForMessage("+91"+session.getMobileNo(), editText.getText().toString());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void messageResponse(TimeEntryResponse mTimeEntryResponseData) {
        progressDialog.dismiss();
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
        progressDialog.dismiss();
        Snackbar snackbar = Snackbar
                .make(mDrawerLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void attendanceErrorResponse(String err) {
        progressDialog.dismiss();
        Snackbar snackbar = Snackbar
                .make(mDrawerLayout, err, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
