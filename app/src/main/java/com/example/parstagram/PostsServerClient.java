package com.example.parstagram;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseQuery;

public class PostsServerClient {
    public static final String TAG = "PostsServerClient";
    PostsAdapter adapter;

    public void fetchTimelineAsync(FindCallback<Post> callback){
        Log.i(TAG, "fetching timeline");
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        // limit query to latest 20 items
        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");

        // start an asynchronous call for posts
        query.findInBackground(callback);
    }
}
