package com.bridgelabz.dagger.component;

import com.bridgelabz.FundooHrLoginActivity;
import com.bridgelabz.FundooHrOtpActivity;
import com.bridgelabz.FundooHrSearchActivity;
import com.bridgelabz.FundooHrToolbarSearch;
import com.bridgelabz.dagger.module.AppModule;
import com.bridgelabz.dagger.module.NetModule;
import com.bridgelabz.service.HttpService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by eshvar289 on 3/7/16.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(FundooHrLoginActivity activity);
    void inject(HttpService service);
    void inject(FundooHrOtpActivity activity);
    void inject(FundooHrSearchActivity activity);
    void inject(FundooHrToolbarSearch activity);
}
