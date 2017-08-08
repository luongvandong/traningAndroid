package com.android.project1.view.ui.prefs;

import android.preference.PreferenceManager;

import com.android.project1.ProjectApplication;

/**
 * @Date : 10/05/2017
 * @Author : ka
 */
public class Preferences {

    private static final String PREFS_IS_ALARM_ON = "PREFS_IS_ALARM_ON";

    public static boolean isAlarmOn() {
        return PreferenceManager.getDefaultSharedPreferences(ProjectApplication.getInstance())
                .getBoolean(PREFS_IS_ALARM_ON, false);
    }

    public static void setAlarmOn(boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(ProjectApplication.getInstance())
                .edit()
                .putBoolean(PREFS_IS_ALARM_ON, isOn)
                .apply();
    }
}
