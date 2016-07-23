package com.bridgelabz.dagger.component;

import com.bridgelabz.controller.AttendanceController;
import com.bridgelabz.dagger.module.AppModule;
import com.bridgelabz.dagger.module.NetModule;
import com.bridgelabz.service.HttpService;
import com.bridgelabz.view.FundooHrLoginActivity;
import com.bridgelabz.view.FundooHrToolbarSearch;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(FundooHrLoginActivity activity);
    void inject(HttpService service);
    void inject(FundooHrToolbarSearch activity);
    void inject(AttendanceController controller);
}
