package com.example.contacts;

import android.graphics.Bitmap;

import java.sql.Blob;

public class User {
    String username;
    String userphnno;
    Bitmap userImg;

    public User(String username, String userphnno, Bitmap userImg) {
        this.username = username;
        this.userphnno = userphnno;
        this.userImg = userImg;
    }

    public String getUsername() {
        return username;
    }

    public String getUserphnno() {
        return userphnno;
    }

    public Bitmap getUserImg() {
        return userImg;
    }
}
