package com.example.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    public final String TAG = "FeedActivity";
    private SwipeRefreshLayout swipeContainer;
    private FloatingActionButton fabPost;
    public PostsServerClient client;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        client = new PostsServerClient();

        rvPosts = findViewById(R.id.rvPosts);

        fabPost = findViewById(R.id.fabPost);

        fabPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("FeedActivity", "clicked post button");
                Intent i = new Intent(FeedActivity.this, PostActivity.class);
                startActivityForResult(i, 20);
            }
        });

        // initialize the array that will hold posts and create a PostsAdapter
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(this, allPosts);
        // set the adapter on the recycler view
        rvPosts.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        // query posts from Parstagram
        fetchTimelineAsync();
    }

    private void fetchTimelineAsync() {
        client.fetchTimelineAsync(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG,"Cannot get posts", e);
                    return;
                } else {
                    for (Post post : posts){
                        Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                    }
                    adapter.clear();
                    // save received posts to list and notify adapter of new data
                    adapter.addAll(posts);
                }
            }
        });
        // Now we call setRefreshing(false) to signal refresh has finished
        if (swipeContainer.isRefreshing()){
            swipeContainer.setRefreshing(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        Log.i(TAG, "finished posting");
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            // get data from intent (post)
            ParcelablePost p = ((ParcelablePost) Parcels.unwrap(data.getParcelableExtra("post")));
            Post post = p.getPost();
            // update recycler view with new post
            // modify data source of posts
            allPosts.add(0,post);
            // update the adapter
            adapter.notifyItemInserted(0);
            rvPosts.smoothScrollToPosition(0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}