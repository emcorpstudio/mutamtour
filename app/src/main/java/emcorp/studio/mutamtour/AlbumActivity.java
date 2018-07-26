package emcorp.studio.mutamtour;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import emcorp.studio.mutamtour.Adapter.AlbumAdapter;
import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.CustomTypefaceSpan;
import emcorp.studio.mutamtour.Library.Image;
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.Library.TypefaceUtil;

public class AlbumActivity extends AppCompatActivity {
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private AlbumAdapter mAdapter;
    private RecyclerView recyclerView;
    SpannableStringBuilder SS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/barclays.ttf");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.background_toolbar)))));
        actionBar.setTitle(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>Gallery</font>"));
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/barclays.ttf");
        SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>Gallery</font>"));
        SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(SS);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.icon_toolbar), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        mAdapter = new AlbumAdapter(getApplicationContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new AlbumAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new AlbumAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(AlbumActivity.this,GalleryActivity.class);
                i.putExtra("album",images.get(position).getRecid());
                i.putExtra("nama",images.get(position).getName());
                startActivity(i);
                finish();
//                Toast.makeText(getApplicationContext(),images.get(position).getName(),Toast.LENGTH_SHORT).show();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("images", images);
//                bundle.putInt("position", position);
//
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
//                newFragment.setArguments(bundle);
//                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        if(SharedFunction.getInstance(AlbumActivity.this).isNetworkConnected()){
            LoadProcess();
        }else{
            Toast.makeText(AlbumActivity.this,R.string.internet_error, Toast.LENGTH_LONG).show();
        }
    }

    public void LoadProcess(){
        pDialog = new ProgressDialog(AlbumActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CETAK",response);
                        pDialog.dismiss();
                        images.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(getApplicationContext(),"Tidak ada data", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String link = Constant.PICT_URL;
                                    String recid = isiArray.getString("recid");
                                    String nama = isiArray.getString("nama");
                                    String foto = link+isiArray.getString("foto");
                                    foto = foto.replace(" ","%20");
                                    Image image = new Image();
                                    image.setName(nama);
                                    image.setRecid(recid);
                                    image.setFoto(foto);
                                    images.add(image);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
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
                params.put("function", Constant.FUNCTION_LISTALBUM);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AlbumActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(AlbumActivity.this,MainActivity.class);
                i.putExtra("MENU","MORE");
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AlbumActivity.this,MainActivity.class);
        i.putExtra("MENU","MORE");
        startActivity(i);
        finish();
    }
}
