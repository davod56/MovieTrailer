package com.example.MovieTrailer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class OnlineMoviesActivity extends AppCompatActivity {
    VideoView videoView;
    MediaController ctlr;
    ProgressDialog bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_online_movies);

        videoView=findViewById(R.id.videoView);
        Intent res=getIntent();
        String URL=res.getStringExtra("URL");
        bar=new ProgressDialog(OnlineMoviesActivity.this);
        bar.setTitle("Connecting server");
        bar.setMessage("Please Wait... ");
        bar.setCancelable(false);
        bar.show();
        if(bar.isShowing()) {
            videoView.setVideoPath(URL);
            videoView.start();
            ctlr = new MediaController(this);
            ctlr.setMediaPlayer(videoView);
            videoView.setMediaController(ctlr);
            videoView.requestFocus();

        }
        bar.dismiss();
    }


}
