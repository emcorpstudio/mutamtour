package emcorp.studio.mutamtour;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {
    private VideoView vv;
    private MediaController mediacontroller;
    private Uri uri;
    private boolean isContinuously = false;
    private ProgressBar progressBar;
    String linkYoutube = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getSupportActionBar().hide();
        progressBar = (ProgressBar) findViewById(R.id.progrss);
        vv = (VideoView) findViewById(R.id.vv);

        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            linkYoutube = extras.getString("link");
//            linkYoutube = "http://www.demonuts.com/Demonuts/smallvideo.mp4";
            progressBar.setVisibility(View.VISIBLE);
            String uriPath = linkYoutube;
            uri = Uri.parse(uriPath);
            vv.setMediaController(mediacontroller);
            vv.setVideoURI(uri);
            vv.requestFocus();
            vv.start();
        }else{
            progressBar.setVisibility(View.VISIBLE);
        }



        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                vv.start();
            }
        });

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(VideoPlayerActivity.this,VideoActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(VideoPlayerActivity.this,VideoActivity.class);
        startActivity(i);
        finish();
    }
}
