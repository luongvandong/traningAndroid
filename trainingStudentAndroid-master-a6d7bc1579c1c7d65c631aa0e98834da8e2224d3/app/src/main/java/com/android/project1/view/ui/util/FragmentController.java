package com.android.project1.view.ui.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.project1.R;
import com.android.project1.view.ui.MainActivity;


/**
 * Created by Pc on 5/14/2017.
 */

public class FragmentController {
    public static void replaceFragmentAndAddBackStack(Context mContext, Fragment fragment, String nameClass) {
        FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_body, fragment);
        fragmentTransaction.addToBackStack(nameClass);
        fragmentTransaction.commit();
    }

    public static void replaceFragment(Context mContext, Fragment fragment) {
        FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_body, fragment);
        fragmentTransaction.commit();
    }

    public static void addFragmentAndAddBackStack(Context mContext, Fragment fragment, String nameClass) {
        FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_body, fragment);
        fragmentTransaction.addToBackStack(nameClass);
        fragmentTransaction.commit();
    }

    public static void addFragment(Context mContext, Fragment fragment) {
        FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_body, fragment);
        fragmentTransaction.commit();
    }
}
