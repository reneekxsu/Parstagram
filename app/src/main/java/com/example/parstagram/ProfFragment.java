package com.example.parstagram;

import android.util.Log;
import android.view.View;

import com.example.parstagram.fragments.HomeFragment;
import com.example.parstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfFragment extends HomeFragment {
    @Override
    protected void fetchTimelineAsync() {
        Log.i(TAG, "fetching timeline");
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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
                    pb.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
