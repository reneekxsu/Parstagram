package com.example.parstagram;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.activities.PostDetailsActivity;
import com.example.parstagram.models.ParcelablePost;
import com.example.parstagram.models.Post;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Log.i("PostsAdapter", "trying to go to details view");
                        Post post = posts.get(position);
                        ParcelablePost p = new ParcelablePost(post);
                        Intent i = new Intent(context, PostDetailsActivity.class);
                        i.putExtra(ParcelablePost.class.getSimpleName(), Parcels.wrap(p));
                        context.startActivity(i);
                    }
                }
            });
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                ivImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(image.getUrl()).into(ivImage);
            } else {
                ivImage.setVisibility(View.GONE);
            }
        }
    }

    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list){
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
