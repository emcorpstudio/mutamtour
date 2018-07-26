package emcorp.studio.mutamtour.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.R;


public class LegalitasFragment extends Fragment {
    private ProgressDialog progressDialog;
    TextView tvTentang;
    ImageView img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_legalitas, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setHasOptionsMenu(true);
        tvTentang = (TextView) view.findViewById(R.id.tvTentang);
        img = (ImageView) view.findViewById(R.id.img);
        if(SharedFunction.getInstance(getContext()).isNetworkConnected()){
            LoadProcess();
        }else{
            Toast.makeText(getContext(),R.string.internet_error, Toast.LENGTH_LONG).show();
        }
        return view;
    }

    public void LoadProcess(){
        progressDialog = new ProgressDialog(getContext());
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
                        try {
//                            JSONObject obj = new JSONObject(response);
//                            JSONObject userDetails = obj.getJSONObject("hasil");
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(getContext(),"Tidak ada data",Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String foto = isiArray.getString("foto");
                                    String tentang = isiArray.getString("legal");
                                    tvTentang.setText(tentang);
                                    /*Picasso.with(getContext())
                                            .load(Constant.PICT_URL+foto)
                                            .error(R.drawable.ic_logo)
                                            .networkPolicy(NetworkPolicy.NO_CACHE)
                                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                            .into(img);*/
                                    Glide.with(getContext())
                                            .load(Constant.PICT_URL+foto)
                                            .error(R.drawable.ic_logo)
                                            .thumbnail(0.5f)
                                            .crossFade()
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(img);
                                }
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
                                getContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", Constant.FUNCTION_LISTLEGAL);
                params.put("key", Constant.KEY);
                return params;
            }
        };
        DefaultRetryPolicy policy = new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
