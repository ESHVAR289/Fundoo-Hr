package com.bridgelabz.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.bridgelabz.R;

/**
 * Created by bridgeit007 on 1/8/16.
 */

public class MovingTest extends AppCompatActivity {
    TextInputEditText textInputEditText;
    EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_edit_text_search);
        editText = (EditText) findViewById(R.id.etSearchMsg);
        textInputEditText = (TextInputEditText) findViewById(R.id.textInputLayout);

        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.push_layout_up);

// Start animation
        textInputEditText.startAnimation(slide_up);
    }
}
