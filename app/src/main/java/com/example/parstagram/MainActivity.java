package com.example.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.parstagram.fragments.HomeFragment;
import com.example.parstagram.fragments.PostFragment;
import com.example.parstagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 20;
    public final String TAG = "MainActivity";
    public final Fragment fragment1 = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
//        final Fragment fragment1 = new HomeFragment();
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
//                            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                            break;
                        case R.id.action_post:
                            Log.i("MainActivity", "post fragment");
                            fragment = fragment2;
//                            Log.i("MainActivity", "clicked post button");
//                            Intent i = new Intent(MainActivity.this, PostActivity.class);
//                            startActivityForResult(i, 20);
                            break;
                        case R.id.action_profile:
                            Log.i("MainActivity", "profile fragment");
                            fragment = fragment3;
//                            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                            break;
                        default:
                            fragment = fragment1;
//                            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        Log.i(TAG, "finished posting");
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
//            // get data from intent (post)
//            ParcelablePost p = ((ParcelablePost) Parcels.unwrap(data.getParcelableExtra("post")));
//            Post post = p.getPost();
//            // update recycler view with new post
//            // modify data source of posts
//            allPosts.add(0,post);
//            // update the adapter
//            adapter.notifyItemInserted(0);
//            rvPosts.smoothScrollToPosition(0);
//        }
        super.onActivityResult(requestCode, resultCode, data);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment1).commit();

    }
}