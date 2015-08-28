/*
 * Copyright (c) 2015 Kam6512. All Rights Reserved.
 * https://github.com/kam6512/GMS_Meal
 */

package com.gms.gms_meal;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by user on 2015-08-27.
 */

@ReportsCrashes(resToastText = R.string.app_name,
mode =  ReportingInteractionMode.DIALOG,
resDialogIcon = android.R.drawable.ic_dialog_info,
resDialogTitle = R.string.err_title,
resDialogText = R.string.err_text,
mailTo = "kam6512@gmail.com")
public class AppManager extends Application{
    private static AppManager ourInstance = new AppManager();

    public static AppManager getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }
}
