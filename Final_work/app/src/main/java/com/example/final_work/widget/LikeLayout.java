package com.example.final_work.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.final_work.R;
import com.like.LikeButton;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.lang.ref.WeakReference;

public class LikeLayout extends FrameLayout {

    public LikeLayout(Context context) {
        super(context);
    }

    public LikeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LikeLayout(Context context, AttributeSet attributeSet, int defStyAttr) {
        super(context, attributeSet, defStyAttr);
    }

    public void onPauseListener() {
        StandardGSYVideoPlayer videoPlayer = getRootView().findViewById(R.id.videoPlayer);
        if (videoPlayer.getGSYVideoManager().isPlaying()) {
            videoPlayer.onVideoPause();
        } else {
            videoPlayer.onVideoResume();
        }
    }

    public void onLikeListener() {
        LikeButton likeButton = getRootView().findViewById(R.id.likeBtn);
        if (!likeButton.isLiked()) {
            likeButton.performClick();
        }
        mClickCount=0;
    }

    private int mClickCount = 0;
    private Handler mHandler = new LikeLayoutHandler(this);


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mClickCount++;
            mHandler.removeCallbacksAndMessages(null);
            if (mClickCount >= 2) {
                onLikeListener();
                mHandler.sendEmptyMessageDelayed(1, 500);
            } else {
                mHandler.sendEmptyMessageDelayed(0, 500);
            }
        }
        return true;
    }

    private void pauseClick() {
        if (mClickCount == 1) {
            onPauseListener();
        }
        mClickCount = 0;
    }


    public void onPause() {
        mClickCount = 0;
        mHandler.removeCallbacksAndMessages(null);
    }


    public static class LikeLayoutHandler extends Handler {
        private WeakReference<LikeLayout> mView;

        public LikeLayoutHandler(LikeLayout view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                mView.get().pauseClick();
            } else if (msg.what == 1) {
                mView.get().onLikeListener();
            }
        }

    }
}