package emcorp.studio.mutamtour;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.Library.TypefaceUtil;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText edtHp;
    Button btnSubmit;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/barclays.ttf");
        getSupportActionBar().hide();
        edtHp = (EditText)findViewById(R.id.edtNoHp);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtHp.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"HP belum diisi", Toast.LENGTH_SHORT).show();
                    edtHp.requestFocus();
                }else{
                    if(SharedFunction.getInstance(ForgotPasswordActivity.this).isNetworkConnected()){
                        ResetProcess();
                    }else{
                        Toast.makeText(getApplicationContext(),"Internet tidak tersedia, periksa koneksi anda !", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    public void ResetProcess(){
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
                                //Succes
                                SharedFunction.getInstance(ForgotPasswordActivity.this).openActivityFinish(LoginActivity.class);
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
                params.put("function", Constant.FUNCTION_FORGOT);
                params.put("key", Constant.KEY);
                params.put("hp", edtHp.getText().toString());
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


    @Override
    public void onBackPressed() {
        SharedFunction.getInstance(getApplicationContext()).openActivityFinish(LoginActivity.class);
    }
}
