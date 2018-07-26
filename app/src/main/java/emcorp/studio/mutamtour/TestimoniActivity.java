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
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.List;
import java.util.Map;

import emcorp.studio.mutamtour.Adapter.TestimoniAdapter;
import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.CustomTypefaceSpan;
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.Library.TypefaceUtil;

public class TestimoniActivity extends AppCompatActivity {
    ListView list;
    private ProgressDialog progressDialog;
    List<String> listid = new ArrayList<String>();
    List<String> listjudul = new ArrayList<String>();
    List<String> listisi = new ArrayList<String>();
    List<String> listthumbnail = new ArrayList<String>();
    List<String> listcreated_date = new ArrayList<String>();
    SpannableStringBuilder SS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimoni);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/barclays.ttf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        setTitle("Testimoni");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.background_toolbar)))));
        actionBar.setTitle(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>Testimoni</font>"));
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/barclays.ttf");
        SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>Testimoni</font>"));
        SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(SS);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.icon_toolbar), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        list = (ListView)findViewById(R.id.listView);
        if(SharedFunction.getInstance(getApplicationContext()).isNetworkConnected()){
            LoadProcess();
        }else{
            Toast.makeText(getApplicationContext(),R.string.internet_error, Toast.LENGTH_LONG).show();
        }
    }

    public void LoadProcess(){
        progressDialog = new ProgressDialog(TestimoniActivity.this);
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
                        listid.clear();
                        listjudul.clear();
                        listisi.clear();
                        listthumbnail.clear();
                        listcreated_date.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(getApplicationContext(),"Tidak ada data", Toast.LENGTH_SHORT).show();
                                list.setVisibility(View.GONE);
                            }else{
                                list.setVisibility(View.VISIBLE);
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String id = isiArray.getString("recid");
                                    String judul = isiArray.getString("nama");
                                    String isi = isiArray.getString("testimoni");
                                    String thumbnail = isiArray.getString("foto");
                                    String created_date = isiArray.getString("tanggal");
                                    listid.add(id);
                                    listjudul.add(judul);
                                    listisi.add(isi);
                                    listthumbnail.add(thumbnail);
                                    listcreated_date.add(created_date);
                                }
                                getAllData();
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
                params.put("function", Constant.FUNCTION_LISTTESTIMONI);
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
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void getAllData(){
        list.setAdapter(null);
        TestimoniAdapter adapter = new TestimoniAdapter(TestimoniActivity.this, listid,listjudul,listisi,listthumbnail,listcreated_date);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getActivity(), DetailBeritaActivity.class);
//                i.putExtra("title",listjudul.get(position));
//                i.putExtra("date",listcreated_date.get(position));
//                i.putExtra("content",listisi.get(position));
//                i.putExtra("image",listthumbnail.get(position));
//                startActivity(i);
//                getActivity().finish();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                dialogAdd(position);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TestimoniActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(TestimoniActivity.this,MainActivity.class);
                i.putExtra("MENU","MORE");
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(TestimoniActivity.this,MainActivity.class);
        i.putExtra("MENU","MORE");
        startActivity(i);
        finish();
    }
}
