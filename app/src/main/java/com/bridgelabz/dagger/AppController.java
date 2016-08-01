package com.bridgelabz.dagger;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bridgelabz.dagger.component.DaggerNetComponent;
import com.bridgelabz.dagger.component.NetComponent;
import com.bridgelabz.dagger.module.AppModule;
import com.bridgelabz.dagger.module.NetModule;


public class AppController extends Application {
    private static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    private RequestQueue mRequestQueue;
    private NetComponent mNetComponent;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                        /*.netModule(new NetModule("http://192.168.0.132/"))*/
                .netModule(new NetModule("http://funduhr-backend.herokuapp.com/"))
                        /*.netModule(new NetModule("http://192.168.0.141:3009/"))*/
                .build();
    }

    public NetComponent getmNetComponent() {
        return mNetComponent;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
