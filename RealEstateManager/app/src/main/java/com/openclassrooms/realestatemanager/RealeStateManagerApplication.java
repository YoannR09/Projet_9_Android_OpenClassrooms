package com.openclassrooms.realestatemanager;

import android.app.Application;
import android.content.Context;

import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase;

public class RealeStateManagerApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        RealEstateManagerDatabase.createInstance(this);
        context = this;
    }


    public static Context getContextApp() {
        return context;
    }
}
