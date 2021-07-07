package com.example.parstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.io.File;

public class PostActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private EditText etCaption;
    private Button btnCamera;
    private ImageView ivPreview;
    private Button btnPost;
    private Button btnLogout;
    private Button btnFeed;

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCaption = findViewById(R.id.etCaption);
        btnCamera = findViewById(R.id.btnCamera);
        ivPreview = findViewById(R.id.ivPreview);
        btnPost = findViewById(R.id.btnPost);
        
        btnLogout = findViewById(R.id.btnLogout);
        btnFeed = findViewById(R.id.btnFeed);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get description, user, and image to post
                String description = etCaption.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(PostActivity.this, "Caption cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    if (photoFile == null || ivPreview.getDrawable() == null){
                        Toast.makeText(PostActivity.this, "No image", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    savePost(description, currentUser, photoFile);
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "clicked logout button");
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                Intent i = new Intent(PostActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "clicked feed button");
                Intent i = new Intent(PostActivity.this, FeedActivity.class);
                startActivity(i);
            }
        });
    }

    private void launchCamera() {
        Log.i(TAG, "camera launched");
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(PostActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP
                Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(takenImage, 100);
                // Load the taken image into a preview
                ivPreview.setImageBitmap(resizedBitmap);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    private void savePost(String description, ParseUser currentUser, File photoFile) {
        Post post = new Post();
        post.setDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Could not save", e);
                    Toast.makeText(PostActivity.this, "Could not save", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.i(TAG, "Post was saved to backend");
                    Toast.makeText(PostActivity.this, "Post was saved", Toast.LENGTH_SHORT).show();
                    etCaption.setText("");
                    // set to empty image
                    ivPreview.setImageResource(0);
                    Intent intent = new Intent();
                    ParcelablePost p = new ParcelablePost(post);
                    intent.putExtra("post", Parcels.wrap(p));
                    // set result code and bundle data for response
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}