package com.example.mark7;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

public class DrawerToggleListner extends ActionBarDrawerToggle {
    public DrawerToggleListner(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar , int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar,openDrawerContentDescRes, closeDrawerContentDescRes);
    }

}
