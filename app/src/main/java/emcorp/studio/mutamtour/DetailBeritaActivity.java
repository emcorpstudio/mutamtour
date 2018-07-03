package emcorp.studio.mutamtour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.TypefaceUtil;

public class DetailBeritaActivity extends AppCompatActivity {
    private View parent_view;
    TextView title,date, content;
    ImageView image;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private FloatingActionButton fab;
    String linkYoutube = "";
    SpannableStringBuilder SS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/barclays.ttf");

        parent_view = findViewById(android.R.id.content);
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        content = (TextView) findViewById(R.id.content);
        image = (ImageView) findViewById(R.id.image);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("");


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title.setText(extras.getString("title"));
            date.setText(extras.getString("date"));
            content.setText(extras.getString("content"));
            Picasso.with(DetailBeritaActivity.this)
                    .load(Constant.PICT_URL+extras.getString("image"))
                    .error(R.drawable.ic_logo)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(image);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(linkYoutube)));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DetailBeritaActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(DetailBeritaActivity.this,MainActivity.class);
                i.putExtra("MENU","BERITA");
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DetailBeritaActivity.this,MainActivity.class);
        i.putExtra("MENU","BERITA");
        startActivity(i);
        finish();
    }
}
