package com.bridgelabz;

import android.app.Application;

import com.bridgelabz.dagger.component.DaggerNetComponent;
import com.bridgelabz.dagger.component.NetComponent;
import com.bridgelabz.dagger.module.AppModule;
import com.bridgelabz.dagger.module.NetModule;

/**
 * Created by eshvar289 on 3/7/16.
 */

public class App extends Application {

    private NetComponent mNetComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                        .appModule(new AppModule(this))
                        .netModule(new NetModule("http://funduhr-backend.herokuapp.com/"))
                        .build();
    }

    public NetComponent getmNetComponent() {
        return mNetComponent;
    }
}
