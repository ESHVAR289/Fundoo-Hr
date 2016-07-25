package com.bridgelabz.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    // Shared preferences file name
    private static final String PREF_NAME = "FundooHrLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    private SharedPreferences mPreference;
    private SharedPreferences.Editor editor;
    private Context mContext;
    // Shared mPreference mode
    private int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.mContext = context;
        mPreference = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = mPreference.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return mPreference.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}