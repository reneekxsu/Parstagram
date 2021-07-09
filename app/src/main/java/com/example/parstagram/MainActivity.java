package com.example.parstagram;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.parstagram.fragments.HomeFragment;
import com.example.parstagram.fragments.PostFragment;
import com.example.parstagram.fragments.ProfileFragment;
import com.example.parstagram.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements PostFragment.PostFragmentListener{
    private final int REQUEST_CODE = 20;
    public final String TAG = "MainActivity";
    public BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        final Fragment fragment1 = new HomeFragment();
        final Fragment fragment2 = new PostFragment();
        final Fragment fragment3 = new ProfileFragment();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_main);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem homeTab = menu.findItem(R.id.action_home);
        MenuItem postTab = menu.findItem(R.id.action_post);
        MenuItem profTab = menu.findItem(R.id.action_profile);

        Drawable homeOutline = getDrawable(R.drawable.instagram_home_outline_24);
        Drawable homeFilled = getDrawable(R.drawable.instagram_home_filled_24);
        homeTab.setIcon(homeOutline);

        Drawable postOutline = getDrawable(R.drawable.instagram_new_post_outline_24);
        Drawable postFilled = getDrawable(R.drawable.instagram_new_post_filled_24);
        postTab.setIcon(postOutline);

        Drawable profOutline = getDrawable(R.drawable.instagram_user_outline_24);
        Drawable profFilled = getDrawable(R.drawable.instagram_user_filled_24);
        profTab.setIcon(profOutline);

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
                                item.setIcon(homeFilled);
                                postTab.setIcon(postOutline);
                                profTab.setIcon(profOutline);
                                break;
                            case R.id.action_post:

                                Log.i("MainActivity", "post fragment");
                                fragment = fragment2;
                                item.setIcon(postFilled);
                                homeTab.setIcon(homeOutline);
                                profTab.setIcon(profOutline);
                                break;
                            case R.id.action_profile:
                                Log.i("MainActivity", "profile fragment");
                                fragment = fragment3;
                                item.setIcon(profFilled);
                                homeTab.setIcon(homeOutline);
                                postTab.setIcon(postOutline);
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

    @Override
    public void goHomeFragment(Post post) {
        Log.i(TAG, "going to home fragment");
        Fragment homeFragment = new HomeFragment();
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}