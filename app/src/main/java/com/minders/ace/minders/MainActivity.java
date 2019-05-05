package com.minders.ace.minders;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class MainActivity extends AppCompatActivity {


    //private TextView mTextMessage;
    void openurl(String url)
    {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(getSupportFragmentManager().findFragmentByTag("Home") != null) {
                        getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("Home")).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_place, new HomeFragment(), "Home").commit();
                    }
                    if(getSupportFragmentManager().findFragmentByTag("Events") != null){
                        getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("Events")).commit();
                    }
                    if(getSupportFragmentManager().findFragmentByTag("Sponsors") != null){
                        getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("Sponsors")).commit();
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if(getSupportFragmentManager().findFragmentByTag("Events") != null) {
                        getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("Events")).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_place, new EventsFragment(), "Events").commit();
                    }
                    if(getSupportFragmentManager().findFragmentByTag("Home") != null){
                        getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("Home")).commit();
                    }
                    if(getSupportFragmentManager().findFragmentByTag("Sponsors") != null){
                        getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("Sponsors")).commit();
                    }
                    return true;
                case R.id.navigation_notifications:
                    if(getSupportFragmentManager().findFragmentByTag("Sponsors") != null) {
                        getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("Sponsors")).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_place, new SponsersFragment(), "Sponsors").commit();
                    }
                    if(getSupportFragmentManager().findFragmentByTag("Home") != null){
                        getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("Home")).commit();
                    }
                    if(getSupportFragmentManager().findFragmentByTag("Events") != null){
                        getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("Events")).commit();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_place, fragment,"Home").commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        }


    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentByTag("Home") != null){
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("Home")).commit();
        }
        if(fragmentManager.findFragmentByTag("Events") != null){
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("Events")).commit();
        }
        if(fragmentManager.findFragmentByTag("Sponsors") != null){
            fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("Sponsors")).commit();
        }
    }

    public void TestFirebase()
    {
        FirebaseHandler handler = new FirebaseHandler();
        handler.GetEvents(new FirebaseHandler.FirebaseCallback() {
            @Override
            public void doJob(List<FirebaseHandler.EventSponsor> result) {

            }
        });
    }
}
