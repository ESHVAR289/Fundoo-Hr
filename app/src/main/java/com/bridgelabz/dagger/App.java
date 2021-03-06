package com.bridgelabz.dagger;

import android.app.Application;

import com.bridgelabz.dagger.component.DaggerNetComponent;
import com.bridgelabz.dagger.component.NetComponent;
import com.bridgelabz.dagger.module.AppModule;
import com.bridgelabz.dagger.module.NetModule;


public class App extends Application {

    private NetComponent mNetComponent;
    @Override
    public void onCreate() {
        super.onCreate();

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
}
