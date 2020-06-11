package com.example.final_work;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.final_work.adpter.VideoViewPagerAdapter;
import com.example.final_work.entity.ApiService;
import com.example.final_work.entity.AvatarService;
import com.example.final_work.entity.TheEventBus;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;

import com.example.final_work.entity.Video;

import java.io.File;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends FragmentActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0x99;
    private static final String API = "https://beiyou.bytedance.com/api/";
    private Video[] videos;
    private VideoViewPagerAdapter mAdapter;
    private final String logger = "MYSY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission_group.CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getVideos().enqueue(new Callback<Video[]>() {
            @Override
            public void onResponse(@NonNull Call<Video[]> call, @NonNull Response<Video[]> response) {
                if (response.body() != null) {
                    videos = response.body();
                    Log.i(logger, Arrays.toString(videos));
                    for (int i = 0; i < videos.length; i++) {
                        getAvatar(videos[i].getSecureAvatar(), i);
                    }
                    mAdapter.setData(videos);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Video[]> call, @NonNull Throwable t) {
                Log.i(logger, "failed");
            }
        });

    }

    public void getAvatar(String url, int position) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url.split("t/")[0]+ "t/")
                .build();


        AvatarService avatarService = retrofit.create(AvatarService.class);
        avatarService.download(url.split("t/")[1]).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    Log.i(logger, "get the avatar");
                    videos[position].setThe_avatar(BitmapFactory.decodeStream(response.body().byteStream()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i(logger, "failed");
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        final ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        mAdapter = new VideoViewPagerAdapter(this, videos);
        viewPager2.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                                    @Override
                                                    public void onPageSelected(int position) {
                                                        super.onPageSelected(position);
                                                        EventBus.getDefault().post(new TheEventBus.ClearPositionEvent(true));
                                                    }
                                                }
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

}

