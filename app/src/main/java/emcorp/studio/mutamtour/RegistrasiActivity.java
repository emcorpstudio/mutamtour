package emcorp.studio.mutamtour;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.SharedFunction;

public class RegistrasiActivity extends AppCompatActivity {
    List<String> listkdprov = new ArrayList<String>();
    List<String> listnmprov = new ArrayList<String>();
    List<String> listkdkabkota = new ArrayList<String>();
    List<String> listnmkabkota = new ArrayList<String>();
    List<String> listkdkecamatan = new ArrayList<String>();
    List<String> listnmkecamatan = new ArrayList<String>();
    List<String> listiddesa = new ArrayList<String>();
    List<String> listnmdesa = new ArrayList<String>();
    private ProgressDialog progressDialog;
    EditText edtName,edtNoHp,edtPassword,edtKonfirmasiPassword;
    Spinner spinProvinsi,spinKabupaten,spinKecamatan,spinDesa;
    Button btnDafter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        getSupportActionBar().hide();
        spinProvinsi = (Spinner)findViewById(R.id.spinProvinsi);
        spinKabupaten = (Spinner)findViewById(R.id.spinKabupaten);
        spinKecamatan = (Spinner)findViewById(R.id.spinKecamatan);
        spinDesa = (Spinner)findViewById(R.id.spinDesa);
        edtName = (EditText)findViewById(R.id.edtName);
        edtNoHp = (EditText)findViewById(R.id.edtNoHp);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtKonfirmasiPassword = (EditText)findViewById(R.id.edtKonfirmasiPassword);
        btnDafter = (Button)findViewById(R.id.btnDafter);

        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtKonfirmasiPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        LoadProv();

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
                if(SharedFunction.getInstance(RegistrasiActivity.this).isNetworkConnected()){
                    if(edtName.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Nama belum diisi", Toast.LENGTH_SHORT).show();
                        edtName.requestFocus();
                    }else{
                        if(edtNoHp.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(),"No HP belum diisi", Toast.LENGTH_SHORT).show();
                            edtNoHp.requestFocus();
                        }else{
                            if(!edtPassword.getText().toString().equals(edtKonfirmasiPassword.getText().toString())){
                                Toast.makeText(getApplicationContext(),"Password tidak sesuai!", Toast.LENGTH_SHORT).show();
                                edtPassword.requestFocus();
                            }else{
                                if(spinProvinsi.getSelectedItemPosition()>=0){
                                    if(spinKabupaten.getSelectedItemPosition()>=0){
                                        if(spinKecamatan.getSelectedItemPosition()>=0){
                                            if(spinDesa.getSelectedItemPosition()>=0){
                                                RegisterProcess();
                                            }else{
                                                Toast.makeText(getApplicationContext(),"Desa belum dipilih!",Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Kecamatan belum dipilih!",Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Kabupaten belum dipilih!",Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"Provinsi belum dipilih!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Internet tidak tersedia, periksa koneksi anda !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void LoadProv(){
        progressDialog = new ProgressDialog(RegistrasiActivity.this);
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
                                Toast.makeText(RegistrasiActivity.this,"Tidak ada data", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String kdprov = isiArray.getString("kdprov");
                                    String nmprov = isiArray.getString("nmprov");
                                    listkdprov.add(kdprov);
                                    listnmprov.add(nmprov);
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegistrasiActivity.this, android.R.layout.simple_spinner_item, listnmprov);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinProvinsi.setAdapter(dataAdapter);
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
                                RegistrasiActivity.this,
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void LoadKab(final String kdprov){
        progressDialog = new ProgressDialog(RegistrasiActivity.this);
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
                                Toast.makeText(RegistrasiActivity.this,"Tidak ada data", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String kdkabkota = isiArray.getString("kdkabkota");
                                    String nmkabkota = isiArray.getString("nmkabkota");
                                    listkdkabkota.add(kdkabkota);
                                    listnmkabkota.add(nmkabkota);
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegistrasiActivity.this, android.R.layout.simple_spinner_item, listnmkabkota);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinKabupaten.setAdapter(dataAdapter);
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
                                RegistrasiActivity.this,
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void LoadKec(final String kdprov, final String kdkabkota){
        progressDialog = new ProgressDialog(RegistrasiActivity.this);
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
                                Toast.makeText(RegistrasiActivity.this,"Tidak ada data", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String kdkecamatan = isiArray.getString("kdkecamatan");
                                    String nmkecamatan = isiArray.getString("nmkecamatan");
                                    listkdkecamatan.add(kdkecamatan);
                                    listnmkecamatan.add(nmkecamatan);
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegistrasiActivity.this, android.R.layout.simple_spinner_item, listnmkecamatan);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinKecamatan.setAdapter(dataAdapter);
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
                                RegistrasiActivity.this,
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void LoadDesa(final String kdprov, final String kdkabkota, final String kdkecamatan){
        progressDialog = new ProgressDialog(RegistrasiActivity.this);
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
                                Toast.makeText(RegistrasiActivity.this,"Tidak ada data", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String kddesa = isiArray.getString("kddesa");
                                    String nmdesa = isiArray.getString("nmdesa");
                                    listiddesa.add(kddesa);
                                    listnmdesa.add(nmdesa);
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegistrasiActivity.this, android.R.layout.simple_spinner_item, listnmdesa);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinDesa.setAdapter(dataAdapter);
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
                                RegistrasiActivity.this,
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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
                                SharedFunction.getInstance(getApplicationContext()).openActivityFinish(LoginActivity.class);
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
                params.put("function", Constant.FUNCTION_REGISTER);
                params.put("key", Constant.KEY);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    @Override
    public void onBackPressed() {
        SharedFunction.getInstance(getApplicationContext()).openActivityFinish(LoginActivity.class);
    }
}
