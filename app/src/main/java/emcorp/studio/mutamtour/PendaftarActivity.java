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

import emcorp.studio.mutamtour.Adapter.PendaftarAdapter;
import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.CustomTypefaceSpan;
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.Library.TypefaceUtil;

public class PendaftarActivity extends AppCompatActivity {
    List<String> listrecid = new ArrayList<String>();
    List<String> listnama = new ArrayList<String>();
    List<String> listtgl_lahir = new ArrayList<String>();
    List<String> listjk = new ArrayList<String>();
    List<String> listhp = new ArrayList<String>();
    List<String> listemail = new ArrayList<String>();
    List<String> listalamat = new ArrayList<String>();
    List<String> listprovinsi = new ArrayList<String>();
    List<String> listkota = new ArrayList<String>();
    List<String> listkecamatan = new ArrayList<String>();
    List<String> listdesa = new ArrayList<String>();
    ListView list;
    private ProgressDialog progressDialog;
    SpannableStringBuilder SS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftar);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/barclays.ttf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        setTitle("Pendaftar");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.background_toolbar)))));
        actionBar.setTitle(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>Pendaftar</font>"));
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/barclays.ttf");
        SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>Pendaftar</font>"));
        SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(SS);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.icon_toolbar), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        list = (ListView)findViewById(R.id.listView);
        if(SharedFunction.getInstance(PendaftarActivity.this).isNetworkConnected()){
            LoadProcess();
        }else{
            Toast.makeText(PendaftarActivity.this,R.string.internet_error, Toast.LENGTH_LONG).show();
        }
    }

    public void LoadProcess(){
        progressDialog = new ProgressDialog(PendaftarActivity.this);
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
                        listrecid.clear();
                        listnama.clear();
                        listtgl_lahir.clear();
                        listjk.clear();
                        listhp.clear();
                        listemail.clear();
                        listalamat.clear();
                        listprovinsi.clear();
                        listkota.clear();
                        listkecamatan.clear();
                        listdesa.clear();
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
                                    String recid = isiArray.getString("recid");
                                    String nama = isiArray.getString("nama");
                                    String tgl_lahir = isiArray.getString("tgl_lahir");
                                    String jk = isiArray.getString("jk");
                                    String hp = isiArray.getString("hp");
                                    String email = isiArray.getString("email");
                                    String alamat = isiArray.getString("alamat");
                                    String provinsi = isiArray.getString("provinsi");
                                    String kota = isiArray.getString("kota");
                                    String kecamatan = isiArray.getString("kecamatan");
                                    String desa = isiArray.getString("desa");
                                    listrecid.add(recid);
                                    listnama.add(nama);
                                    listtgl_lahir.add(tgl_lahir);
                                    listjk.add(jk);
                                    listhp.add(hp);
                                    listemail.add(email);
                                    listalamat.add(alamat);
                                    listprovinsi.add(provinsi);
                                    listkota.add(kota);
                                    listkecamatan.add(kecamatan);
                                    listdesa.add(desa);
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
                params.put("function", Constant.FUNCTION_LISTPENDAFTAR);
                params.put("key", Constant.KEY);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getAllData(){
        list.setAdapter(null);
        PendaftarAdapter adapter = new PendaftarAdapter(PendaftarActivity.this, listrecid,listnama,listtgl_lahir,listjk,listhp,listemail,listalamat,listprovinsi,listkota,listkecamatan,listdesa);
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PendaftarActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(PendaftarActivity.this,MainActivity.class);
                i.putExtra("MENU","MORE");
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PendaftarActivity.this,MainActivity.class);
        i.putExtra("MENU","MORE");
        startActivity(i);
        finish();
    }
}
