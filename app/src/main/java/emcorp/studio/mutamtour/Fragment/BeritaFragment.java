package emcorp.studio.mutamtour.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import emcorp.studio.mutamtour.Adapter.BeritaAdapter;
import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.DetailBeritaActivity;
import emcorp.studio.mutamtour.R;


public class BeritaFragment extends Fragment {
    ListView list;
    private ProgressDialog progressDialog;
    List<String> listid = new ArrayList<String>();
    List<String> listjudul = new ArrayList<String>();
    List<String> listisi = new ArrayList<String>();
    List<String> listthumbnail = new ArrayList<String>();
    List<String> listcreated_date = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_berita, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        list = (ListView)view.findViewById(R.id.listView);
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
                        listid.clear();
                        listjudul.clear();
                        listisi.clear();
                        listthumbnail.clear();
                        listcreated_date.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(getContext(),"Tidak ada data", Toast.LENGTH_SHORT).show();
                                list.setVisibility(View.GONE);
                            }else{
                                list.setVisibility(View.VISIBLE);
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String id = isiArray.getString("recid");
                                    String judul = isiArray.getString("judul");
                                    String isi = isiArray.getString("berita");
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
                params.put("function", Constant.FUNCTION_LISTBERITA);
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
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void getAllData(){
        list.setAdapter(null);
        BeritaAdapter adapter = new BeritaAdapter(getActivity(), listid,listjudul,listisi,listthumbnail,listcreated_date);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DetailBeritaActivity.class);
                i.putExtra("title",listjudul.get(position));
                i.putExtra("date",listcreated_date.get(position));
                i.putExtra("content",listisi.get(position));
                i.putExtra("image",listthumbnail.get(position));
                startActivity(i);
                getActivity().finish();
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
    public void onResume() {
        super.onResume();
    }


}
