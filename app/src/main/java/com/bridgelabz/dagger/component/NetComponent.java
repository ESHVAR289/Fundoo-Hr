package com.bridgelabz.dagger.component;

import com.bridgelabz.FundooHrLoginActivity;
import com.bridgelabz.dagger.module.AppModule;
import com.bridgelabz.dagger.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by eshvar289 on 3/7/16.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(FundooHrLoginActivity activity);
}
