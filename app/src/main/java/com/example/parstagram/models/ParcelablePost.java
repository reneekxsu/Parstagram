package com.example.parstagram.models;

import org.parceler.Parcel;

@Parcel
public class ParcelablePost {
    public Post p;
    public ParcelablePost(){}

    public ParcelablePost(Post p){
        this.p = p;
    }

    public Post getPost(){
        return p;
    }
}
