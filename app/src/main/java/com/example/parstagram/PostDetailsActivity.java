package com.example.parstagram;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;

public class PostDetailsActivity extends AppCompatActivity {
    Post post;
    TextView tvPostUsername;
    ImageView ivPostDetails;
    TextView tvPostCaption;
    TextView tvTimeStamp;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("PostDetailsActivity", "in post details activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        post = ((ParcelablePost) Parcels.unwrap(getIntent().getParcelableExtra(ParcelablePost.class.getSimpleName()))).getPost();
        tvPostUsername = (TextView) findViewById(R.id.tvPostUsername);
        ivPostDetails = (ImageView) findViewById(R.id.ivPostDetails);
        tvPostCaption = (TextView) findViewById(R.id.tvPostCaption);
        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        context = (Context) this;

        tvPostUsername.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image != null) {
            ivPostDetails.setVisibility(View.VISIBLE);
            Glide.with(context).load(image.getUrl()).into(ivPostDetails);
        } else {
            ivPostDetails.setVisibility(View.GONE);
        }
        tvPostCaption.setText(post.getDescription());
        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        tvTimeStamp.setText(timeAgo);
    }
}