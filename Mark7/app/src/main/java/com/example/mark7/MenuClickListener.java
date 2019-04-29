package com.example.mark7;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

public class MenuClickListener implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Context context;

    public MenuClickListener(DrawerLayout drawerLayout, NavigationView navigationView, Context context){
        this.drawerLayout = drawerLayout;
        this.context = context;
        this.navigationView = navigationView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(!menuItem.isChecked());
        drawerLayout.closeDrawers();
        switch (menuItem.getItemId()){
            case R.id.inbox:{
                Intent intent = new Intent(context,Inbox.class);
                context.startActivity(intent);
                break;
            }
        }
        return false;
    }
}
