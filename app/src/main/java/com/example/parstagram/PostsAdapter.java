package com.example.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Viewholder>{

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    @NotNull
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PostsAdapter.Viewholder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }


    class Viewholder extends RecyclerView.ViewHolder {
        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivPost);
            tvDescription = itemView.findViewById(R.id.tvCaption);
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
        }
    }
}
