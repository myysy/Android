package com.example.final_work.entity;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface AvatarService {
    @GET
    Call<ResponseBody> download(@Url String Url);
}
