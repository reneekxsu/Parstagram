package com.example.parstagram;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.parstagram.fragments.HomeFragment;
import com.example.parstagram.fragments.PostFragment;
import com.example.parstagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        final Fragment fragment1 = new HomeFragment();
        final Fragment fragment2 = new PostFragment();
        final Fragment fragment3 = new ProfileFragment();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_main);

        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.action_home:
                            Log.i("MainActivity", "home fragment");
                            fragment = fragment1;
                            break;
                        case R.id.action_post:
                            Log.i("MainActivity", "post fragment");
                            fragment = fragment2;
                            break;
                        case R.id.action_profile:
                            Log.i("MainActivity", "profile fragment");
                            fragment = fragment3;
                            break;
                        default:
                            fragment = fragment1;
                            break;
                    }
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                    return true;
                }
            });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}