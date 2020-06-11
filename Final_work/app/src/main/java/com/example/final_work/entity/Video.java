package com.example.final_work.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

import lombok.Data;

@Data
public class Video implements Serializable {
    String feedurl;
    String nickname;
    String description;
    int likecount;
    String avatar;
    Bitmap the_avatar;

    public String getSecureFeedurl() {
        return this.feedurl.replace("http://", "https://");
    }

    public String getSecureAvatar() {
        return this.avatar.replace("http://", "https://");
    }
}
