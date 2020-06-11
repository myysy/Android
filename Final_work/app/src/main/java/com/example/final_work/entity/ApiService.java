package com.example.final_work.entity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("invoke/video/invoke/video/")
    Call<Video[]> getVideos();
}
