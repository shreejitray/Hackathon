package com.example.mark7;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);

        navigationView = findViewById(R.id.navigation_view_left);

        DrawerToggleListner drawerToggleListner = new DrawerToggleListner(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer);

        drawerLayout.addDrawerListener(drawerToggleListner);
        drawerToggleListner.syncState();

        MenuClickListener menuClickListener = new MenuClickListener(drawerLayout,navigationView,this);
        navigationView.setNavigationItemSelectedListener(menuClickListener);
    }
}
