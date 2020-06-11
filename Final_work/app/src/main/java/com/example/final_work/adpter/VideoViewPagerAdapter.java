package com.example.final_work.adpter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.final_work.entity.Video;
import com.example.final_work.fragment.VideoFragment;

import java.util.Arrays;

public class VideoViewPagerAdapter extends FragmentStateAdapter {

    private Video[] videos;
    private static final String logger="MYSY";

    private int count = 10;

    public VideoViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Video[] videos) {
        super(fragmentActivity);
        this.videos = videos;
        if (videos == null)
            count = 0;
        else count = videos.length;
        Log.i(logger,"adpter get videos"+ Arrays.toString(videos));
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        count += 1;
        int the_mod=1;
        if (videos != null) {
            the_mod=videos.length;
            return VideoFragment.getNewInstance(videos[position %the_mod]);
        }
        return VideoFragment.getNewInstance(null);
    }

    @Override
    public int getItemCount() {
        Log.i(logger,"adapter get itemcount "+count);
        return count;
    }


    public void setData(Video[] videos) {
        this.videos = videos;
        count = videos.length;
        Log.i(logger,"adapter set the data "+ Arrays.toString(videos));
    }
}