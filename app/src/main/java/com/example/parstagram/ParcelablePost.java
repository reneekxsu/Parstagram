package com.example.parstagram;

import org.parceler.Parcel;

@Parcel
public class ParcelablePost {
    Post p;
    public ParcelablePost(){}

    public ParcelablePost(Post p){
        this.p = p;
    }

    public Post getPost(){
        return p;
    }
}
