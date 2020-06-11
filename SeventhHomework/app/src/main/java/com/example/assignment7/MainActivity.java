package com.example.assignment7;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaPlayer mediaPlayer;

    private ImageButton playButton;
    private boolean playing = true;

    private TextView progressText;
    private TextView durationText;
    private SeekBar progressBar;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.videoView = this.findViewById(R.id.videoView);
        this.playButton = this.findViewById(R.id.playButton);
        this.progressText = this.findViewById(R.id.progressText);
        this.durationText = this.findViewById(R.id.durationText);
        this.progressBar = this.findViewById(R.id.progressBar);

        Intent intent = this.getIntent();
        Uri uri = intent.getData();
        if (uri == null) {
            this.playButton.setEnabled(false);
            this.progressBar.setEnabled(false);
        }

        this.videoView.setVideoURI(uri);
        this.videoView.start();

        this.videoView.setOnPreparedListener(mp -> {
            this.mediaPlayer = mp;
            this.playButton.setImageResource(android.R.drawable.ic_media_pause);
            this.updateProgress();
        });

        this.playButton.setOnClickListener(v -> {
            if (this.playing) {
                this.onVideoPause();
            } else {
                this.onVideoResume();
            }
        });

        this.progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    MainActivity.this.mediaPlayer.seekTo(progress, MediaPlayer.SEEK_CLOSEST);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (MainActivity.this.playing) {
                    MainActivity.this.videoView.pause();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.this.mediaPlayer.seekTo(seekBar.getProgress(), MediaPlayer.SEEK_CLOSEST);
                if (MainActivity.this.playing) {
                    MainActivity.this.videoView.start();
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    private void updateProgress() {
        int progress = this.videoView.getCurrentPosition();
        int duration = this.videoView.getDuration();

        this.progressText.setText(String.format(Locale.ROOT, "%02d:%02d", (progress /1000) / 60, (progress /1000) % 60));
        this.durationText.setText(String.format(Locale.ROOT, "%02d:%02d", (duration /1000) / 60, (duration /1000) % 60));
        if (MainActivity.this.playing) {
            this.progressBar.setMax(duration);
            this.progressBar.setProgress(progress);
        }

        this.handler.postDelayed(this::updateProgress, 100);
    }

    private void onVideoPause() {
        this.playing = false;
        this.videoView.pause();

        this.playButton.setImageResource(android.R.drawable.ic_media_play);
    }

    private void onVideoResume() {
        this.playing = true;
        this.videoView.start();

        this.playButton.setImageResource(android.R.drawable.ic_media_pause);
    }
}
