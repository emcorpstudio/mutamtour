package emcorp.studio.mutamtour;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.Library.SharedPrefManager;
import emcorp.studio.mutamtour.Library.TypefaceUtil;

public class LoginActivity extends AppCompatActivity {
    EditText edtHp, edtPassword;
    Button btnLogin;
    String tokenFirebase = "";
    private ProgressDialog progressDialog;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    TextView tvRegister, tvForgot;
    SpannableStringBuilder SS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/barclays.ttf");
        getSupportActionBar().hide();
        edtHp = (EditText)findViewById(R.id.edtHp);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvForgot = (TextView) findViewById(R.id.tvForgot);
        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        checkLocationPermission();
//        checkCallPermission();

        if(SharedPrefManager.getInstance(getApplicationContext()).getLogin()!=null){
            if(SharedPrefManager.getInstance(getApplicationContext()).getLogin().equals("1")){
//                SharedFunction.getInstance(LoginActivity.this).openActivityFinish(MainActivity.class);
//                finish();
                LoginProcess(SharedPrefManager.getInstance(getApplicationContext()).getHP(),SharedPrefManager.getInstance(getApplicationContext()).getPASSWORD());
            }
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtHp.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"HP belum diisi", Toast.LENGTH_SHORT).show();
                    edtHp.requestFocus();
                }else{
                    if(edtPassword.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Password belum diisi", Toast.LENGTH_SHORT).show();
                        edtPassword.requestFocus();
                    }else{
                        if(SharedFunction.getInstance(LoginActivity.this).isNetworkConnected()){
                            LoginProcess(edtHp.getText().toString(),edtPassword.getText().toString());
                        }else{
                            Toast.makeText(getApplicationContext(),"Internet tidak tersedia, periksa koneksi anda !", Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedFunction.getInstance(LoginActivity.this).openActivityFinish(RegistrasiActivity.class);
                finish();
            }
        });
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedFunction.getInstance(LoginActivity.this).openActivityFinish(ForgotPasswordActivity.class);
                finish();
            }
        });
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Permission")
                        .setMessage("Dibutuhkan permission ke lokasi anda")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCallPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Permission")
                        .setMessage("Dibutuhkan call permission")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
            return false;
        } else {
            return true;
        }
    }

    public void LoginProcess(final String hp, final String password){
        tokenFirebase = FirebaseInstanceId.getInstance().getToken();
//        Toast.makeText(getApplicationContext(),tokenFirebase,Toast.LENGTH_SHORT).show();
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
                            String recid = userDetails.getString("recid");
                            String name = userDetails.getString("nama");
                            String hp = userDetails.getString("hp");
                            String provinsi = userDetails.getString("provinsi");
                            String kabupaten = userDetails.getString("kabupaten");
                            String kecamatan = userDetails.getString("kecamatan");
                            String desa = userDetails.getString("desa");
                            if(success.equals("1")){
                                //Succes
                                SharedPrefManager.getInstance(getApplicationContext()).setID(recid);
                                SharedPrefManager.getInstance(getApplicationContext()).setNAMA(name);
                                SharedPrefManager.getInstance(getApplicationContext()).setHP(hp);
                                SharedPrefManager.getInstance(getApplicationContext()).setPROVINSI(provinsi);
                                SharedPrefManager.getInstance(getApplicationContext()).setKABUPATEN(kabupaten);
                                SharedPrefManager.getInstance(getApplicationContext()).setKECAMATAN(kecamatan);
                                SharedPrefManager.getInstance(getApplicationContext()).setDESA(desa);
                                if(SharedPrefManager.getInstance(getApplicationContext()).getLogin()!=null){
                                    if(SharedPrefManager.getInstance(getApplicationContext()).getLogin().equals("1")){
                                        SharedPrefManager.getInstance(getApplicationContext()).setPASSWORD(SharedPrefManager.getInstance(getApplicationContext()).getPASSWORD());
                                    }else{
                                        SharedPrefManager.getInstance(getApplicationContext()).setPASSWORD(edtPassword.getText().toString());
                                    }
                                }else{
                                    SharedPrefManager.getInstance(getApplicationContext()).setPASSWORD(edtPassword.getText().toString());
                                }
                                SharedPrefManager.getInstance(getApplicationContext()).isLogin();
                                SharedFunction.getInstance(LoginActivity.this).openActivityFinish(MainActivity.class);
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
                params.put("function", Constant.FUNCTION_LOGIN);
                params.put("key", Constant.KEY);
                params.put("hp", hp);
                params.put("password", password);
                params.put("key_push", tokenFirebase);
                Log.d("key_push",tokenFirebase);
                return params;
            }
        };
        DefaultRetryPolicy policy = new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void checkForPhonePermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            // Permission already granted.
        }
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
        finish();
    }
}
