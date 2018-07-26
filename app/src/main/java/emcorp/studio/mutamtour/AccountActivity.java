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
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.CustomTypefaceSpan;
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.Library.SharedPrefManager;
import emcorp.studio.mutamtour.Library.TypefaceUtil;

public class AccountActivity extends AppCompatActivity {
    EditText edtName, edtNoHp, edtPassword;
    Spinner spinProvinsi, spinKabupaten, spinKecamatan, spinDesa;
    Button btnDafter;
    private ProgressDialog progressDialog;
    SpannableStringBuilder SS;
    List<String> listkdprov = new ArrayList<String>();
    List<String> listnmprov = new ArrayList<String>();
    List<String> listkdkabkota = new ArrayList<String>();
    List<String> listnmkabkota = new ArrayList<String>();
    List<String> listkdkecamatan = new ArrayList<String>();
    List<String> listnmkecamatan = new ArrayList<String>();
    List<String> listiddesa = new ArrayList<String>();
    List<String> listnmdesa = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/barclays.ttf");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.background_toolbar)))));
        actionBar.setTitle(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>Akun Saya</font>"));
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/barclays.ttf");
        SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>Akun Saya</font>"));
        SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(SS);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.icon_toolbar), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        spinProvinsi = (Spinner)findViewById(R.id.spinProvinsi);
        spinKabupaten = (Spinner)findViewById(R.id.spinKabupaten);
        spinKecamatan = (Spinner)findViewById(R.id.spinKecamatan);
        spinDesa = (Spinner)findViewById(R.id.spinDesa);
        edtName = (EditText)findViewById(R.id.edtName);
        edtNoHp = (EditText)findViewById(R.id.edtNoHp);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnDafter = (Button)findViewById(R.id.btnDafter);

        edtName.setTypeface(type);
        edtNoHp.setTypeface(type);
        edtPassword.setTypeface(type);
        btnDafter.setTypeface(type);

        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        edtName.setText(SharedPrefManager.getInstance(getApplicationContext()).getNAMA());
        edtNoHp.setText(SharedPrefManager.getInstance(getApplicationContext()).getHP());

        if(SharedFunction.getInstance(getApplicationContext()).isNetworkConnected()){
            LoadProv();
        }else{
            Toast.makeText(getApplicationContext(),R.string.internet_error, Toast.LENGTH_LONG).show();
        }

        spinProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LoadKab(listkdprov.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LoadKec(listkdprov.get(spinProvinsi.getSelectedItemPosition()),listkdkabkota.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LoadDesa(listkdprov.get(spinProvinsi.getSelectedItemPosition()),listkdkabkota.get(spinKabupaten.getSelectedItemPosition()),listkdkecamatan.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnDafter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SharedFunction.getInstance(AccountActivity.this).isNetworkConnected()){
                    if(edtName.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Nama belum diisi", Toast.LENGTH_SHORT).show();
                        edtName.requestFocus();
                    }else{
                        if(edtNoHp.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(),"No HP belum diisi", Toast.LENGTH_SHORT).show();
                            edtNoHp.requestFocus();
                        }else{
                            RegisterProcess();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Internet tidak tersedia, periksa koneksi anda !", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void RegisterProcess(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("CETAK",Constant.ROOT_URL+" "+response);
//                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject userDetails = obj.getJSONObject("hasil");
                            String message = userDetails.getString("message");
                            String success = userDetails.getString("success");
                            if(success.equals("1")){
                                String name = userDetails.getString("nama");
                                String hp = userDetails.getString("hp");
                                String provinsi = userDetails.getString("provinsi");
                                String kabupaten = userDetails.getString("kabupaten");
                                String kecamatan = userDetails.getString("kecamatan");
                                String desa = userDetails.getString("desa");
                                SharedPrefManager.getInstance(getApplicationContext()).setNAMA(name);
                                SharedPrefManager.getInstance(getApplicationContext()).setHP(hp);
                                SharedPrefManager.getInstance(getApplicationContext()).setPROVINSI(provinsi);
                                SharedPrefManager.getInstance(getApplicationContext()).setKABUPATEN(kabupaten);
                                SharedPrefManager.getInstance(getApplicationContext()).setKECAMATAN(kecamatan);
                                SharedPrefManager.getInstance(getApplicationContext()).setDESA(desa);
                                Intent i = new Intent(AccountActivity.this,MainActivity.class);
                                i.putExtra("MENU","MORE");
                                startActivity(i);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

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
                params.put("function", Constant.FUNCTION_REGISTERUPDATE);
                params.put("key", Constant.KEY);
                params.put("recid", SharedPrefManager.getInstance(getApplicationContext()).getID());
                params.put("nama", edtName.getText().toString());
                params.put("provinsi", listkdprov.get(spinProvinsi.getSelectedItemPosition()));
                params.put("kabupaten", listkdkabkota.get(spinKabupaten.getSelectedItemPosition()));
                params.put("kecamatan", listkdkecamatan.get(spinKecamatan.getSelectedItemPosition()));
                params.put("desa", listiddesa.get(spinDesa.getSelectedItemPosition()));
                params.put("hp", edtNoHp.getText().toString());
                params.put("password", edtPassword.getText().toString());
                return params;
            }
        };
        DefaultRetryPolicy policy = new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void LoadDesa(final String kdprov, final String kdkabkota, final String kdkecamatan){
        progressDialog = new ProgressDialog(AccountActivity.this);
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
                        listiddesa.clear();
                        listnmdesa.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(AccountActivity.this,"Tidak ada data", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String kddesa = isiArray.getString("kddesa");
                                    String nmdesa = isiArray.getString("nmdesa");
                                    listiddesa.add(kddesa);
                                    listnmdesa.add(nmdesa);
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AccountActivity.this, android.R.layout.simple_spinner_item, listnmdesa);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinDesa.setAdapter(dataAdapter);
                                spinDesa.setSelection(findPosition(SharedPrefManager.getInstance(getApplicationContext()).getDESA(),listiddesa));
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
                                AccountActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", Constant.FUNCTION_LISTDESA);
                params.put("key", Constant.KEY);
                params.put("kdprov", kdprov);
                params.put("kdkabkota", kdkabkota);
                params.put("kdkecamatan", kdkecamatan);
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

    public void LoadKec(final String kdprov, final String kdkabkota){
        progressDialog = new ProgressDialog(AccountActivity.this);
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
                        listkdkecamatan.clear();
                        listnmkecamatan.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(AccountActivity.this,"Tidak ada data", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String kdkecamatan = isiArray.getString("kdkecamatan");
                                    String nmkecamatan = isiArray.getString("nmkecamatan");
                                    listkdkecamatan.add(kdkecamatan);
                                    listnmkecamatan.add(nmkecamatan);
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AccountActivity.this, android.R.layout.simple_spinner_item, listnmkecamatan);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinKecamatan.setAdapter(dataAdapter);
                                spinKecamatan.setSelection(findPosition(SharedPrefManager.getInstance(getApplicationContext()).getKECAMATAN(),listkdkecamatan));
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
                                AccountActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", Constant.FUNCTION_LISTKEC);
                params.put("key", Constant.KEY);
                params.put("kdprov", kdprov);
                params.put("kdkabkota", kdkabkota);
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

    public void LoadKab(final String kdprov){
        progressDialog = new ProgressDialog(AccountActivity.this);
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
                        listkdkabkota.clear();
                        listnmkabkota.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(AccountActivity.this,"Tidak ada data", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String kdkabkota = isiArray.getString("kdkabkota");
                                    String nmkabkota = isiArray.getString("nmkabkota");
                                    listkdkabkota.add(kdkabkota);
                                    listnmkabkota.add(nmkabkota);
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AccountActivity.this, android.R.layout.simple_spinner_item, listnmkabkota);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinKabupaten.setAdapter(dataAdapter);
                                spinKabupaten.setSelection(findPosition(SharedPrefManager.getInstance(getApplicationContext()).getKABUPATEN(),listkdkabkota));
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
                                AccountActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", Constant.FUNCTION_LISTKAB);
                params.put("key", Constant.KEY);
                params.put("kdprov", kdprov);
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

    public void LoadProv(){
        progressDialog = new ProgressDialog(AccountActivity.this);
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
                        listkdprov.clear();
                        listnmprov.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(AccountActivity.this,"Tidak ada data", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String kdprov = isiArray.getString("kdprov");
                                    String nmprov = isiArray.getString("nmprov");
                                    listkdprov.add(kdprov);
                                    listnmprov.add(nmprov);
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AccountActivity.this, android.R.layout.simple_spinner_item, listnmprov);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinProvinsi.setAdapter(dataAdapter);
                                spinProvinsi.setSelection(findPosition(SharedPrefManager.getInstance(getApplicationContext()).getPROVINSI(),listkdprov));
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
                                AccountActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", Constant.FUNCTION_LISTPROV);
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

    public Integer findPosition(String kode, List<String> listkode){
        Integer pos = 0;
        for(int i=0;i<listkode.size();i++){
            if(listkode.get(i).equals(kode)){
                pos = i;
                break;
            }
        }
        return pos;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AccountActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(AccountActivity.this,MainActivity.class);
                i.putExtra("MENU","MORE");
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AccountActivity.this,MainActivity.class);
        i.putExtra("MENU","MORE");
        startActivity(i);
        finish();
    }
}
