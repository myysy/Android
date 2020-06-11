package com.example.final_work.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.final_work.R;
import com.example.final_work.entity.TheEventBus;
import com.example.final_work.entity.Video;
import com.example.final_work.widget.LikeLayout;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;


public class VideoFragment extends Fragment {
    private static final String logger="MYSY";

    public static VideoFragment getNewInstance(Video video) {
        Bundle bundle = new Bundle();
        bundle.putString("feedurl", video.getSecureFeedurl());
        bundle.putString("nickname",video.getNickname());
        bundle.putString("description",video.getDescription());
        bundle.putInt("likecount",video.getLikecount());
        bundle.putParcelable("avatar",  video.getThe_avatar());
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(bundle);
        Log.i(logger,"fragement new instance bundle  is "+bundle);
        return videoFragment;
    }

    private Video video=new Video();
    private TextView nickName;
    private TextView description;
    private TextView likeCount;
    private long mCurrentPosition = 0;
    private CircleImageView avatar;
    private StandardGSYVideoPlayer videoPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null ) {
            video.setFeedurl(bundle.getString("feedurl"));
            video.setNickname(bundle.getString("nickname"));
            video.setDescription(bundle.getString("description"));
            video.setLikecount(bundle.getInt("likecount"));
            video.setThe_avatar(bundle.getParcelable("avatar"));
        } else video=null;
        Log.i(logger,"fragement creat with bundle"+bundle);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCurrentPosition = 0;
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        videoPlayer = view.findViewById(R.id.videoPlayer);
        nickName=view.findViewById(R.id.nickname);
        description=view.findViewById(R.id.description);
        likeCount=view.findViewById(R.id.likecount);
        avatar=view.findViewById(R.id.avatar);
        Log.i(logger,"fragment creat the view");
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(logger,"fragment set the view");
        nickName.setText(video.getNickname());
        description.setText(video.getDescription());
        likeCount.setText(Integer.toString(video.getLikecount()));
        avatar.setImageBitmap(video.getThe_avatar());
        if (videoPlayer != null) {
            videoPlayer.getBackButton().setVisibility(View.GONE);
            videoPlayer.getTitleTextView().setVisibility(View.GONE);
            videoPlayer.getFullscreenButton().setVisibility(View.GONE);
            videoPlayer.setNeedShowWifiTip(false);
            videoPlayer.setLooping(true);
            videoPlayer.setDismissControlTime(0);
            videoPlayer.setUpLazy(video.getSecureFeedurl(), false, null, null, null);
        }

    }

    @Override

    public void onResume() {
        super.onResume();
        Log.i(logger,"fragment is resume");
        if (mCurrentPosition > 0) {
            videoPlayer.onVideoResume();
        } else {
            Runnable action = new Runnable() {
                @Override
                public void run() {
                    videoPlayer.startPlayLogic();
                }
            };
            videoPlayer.postDelayed(action, 200);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventClearPosition(TheEventBus.ClearPositionEvent event) {
        if (event.isClear) {
            mCurrentPosition = 0;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(logger,"fragment is pause");
        LikeLayout likeLayout = Objects.requireNonNull(getActivity()).findViewById(R.id.likeLayout);
        likeLayout.onPause();
        videoPlayer.onVideoPause();
        mCurrentPosition = videoPlayer.getGSYVideoManager().getCurrentPosition();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
