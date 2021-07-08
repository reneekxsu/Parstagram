package com.example.parstagram;

import android.app.Application;

import com.example.parstagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Wb8nL59BsZ47BqC3ZPyAuZiKle7p1D7zzci00Q0K")
                .clientKey("iwDg9dLbDdvy8Gu0DBGxhWNMNzd2rJbejPhSUI3J")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
