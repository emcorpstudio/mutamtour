package emcorp.studio.mutamtour;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.CustomTypefaceSpan;
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.Library.TypefaceUtil;

public class PaketActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    List<String> listfoto = new ArrayList<String>();
    List<String> listketerangan = new ArrayList<String>();
    ViewPager viewPager;
    int posPager = -1;
    Timer timer;
    SpannableStringBuilder SS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/barclays.ttf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.background_toolbar)))));
        actionBar.setTitle(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>Akun Saya</font>"));
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/barclays.ttf");
        SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>Paket</font>"));
        SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(SS);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.icon_toolbar), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        viewPager = (ViewPager) findViewById(R.id.pager);

        if(SharedFunction.getInstance(getApplicationContext()).isNetworkConnected()){
            LoadProcess();
        }else{
            Toast.makeText(getApplicationContext(),R.string.internet_error, Toast.LENGTH_LONG).show();
        }
    }

    public void LoadProcess(){
        progressDialog = new ProgressDialog(PaketActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CETAK",response);
                        progressDialog.dismiss();
                        listfoto.clear();
                        listketerangan.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(getApplicationContext(),"Tidak ada slider", Toast.LENGTH_SHORT).show();
                                viewPager.setVisibility(View.GONE);
                            }else{
                                viewPager.setVisibility(View.VISIBLE);
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String foto = isiArray.getString("foto");
                                    String keterangan = isiArray.getString("keterangan");
                                    listfoto.add(foto);
                                    listketerangan.add(keterangan);
                                }
                                ImageAdapter adapter = new ImageAdapter(getApplicationContext());
                                viewPager.setAdapter(adapter);
                                posPager = 0;
                                TimerTask timerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        viewPager.post(new Runnable(){

                                            @Override
                                            public void run() {
//                                                viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%listfoto.size()+1);
                                                viewPager.setCurrentItem(posPager,true);
                                                if(posPager<listfoto.size()-1){
                                                    posPager = posPager + 1;
                                                }else{
                                                    posPager = 0;
                                                }
                                            }
                                        });
                                    }
                                };
                                timer = new Timer();
                                timer.schedule(timerTask, 5000, 5000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", Constant.FUNCTION_LISTPAKET);
                params.put("key", Constant.KEY);
                return params;
            }
        };
        DefaultRetryPolicy policy = new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public class ImageAdapter extends PagerAdapter {
        Context context;
        ImageAdapter(Context context)
        {
            this.context=context;
        }
        @Override
        public int getCount() {
            return listfoto.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
//            Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
            ImageView imageView = new ImageView(context);
            int padding = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
//            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            /*Picasso.with(getApplicationContext())
                    .load(Constant.PICT_URL+listfoto.get(position))
                    .error(R.drawable.ic_logo)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imageView);*/
            Glide.with(getApplicationContext())
                    .load(Constant.PICT_URL+listfoto.get(position))
                    .error(R.drawable.ic_logo)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }

    @Override
    public void onResume() {
        if(timer!=null){
            timer.cancel();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if(timer!=null){
            timer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PaketActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(PaketActivity.this,MainActivity.class);
                i.putExtra("MENU","MORE");
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PaketActivity.this,MainActivity.class);
        i.putExtra("MENU","MORE");
        startActivity(i);
        finish();
    }
}
