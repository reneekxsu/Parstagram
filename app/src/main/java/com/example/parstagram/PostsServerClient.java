package com.example.parstagram;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class PostsServerClient {
    public static final String TAG = "PostsServerClient";
    public void fetchTimelineAsync(PostsAdapter adapter) {
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
        query.findInBackground(new FindCallback<Post>() {
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
    }
}
