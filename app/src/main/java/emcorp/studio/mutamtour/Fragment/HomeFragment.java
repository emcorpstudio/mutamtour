package emcorp.studio.mutamtour.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.R;


public class HomeFragment extends Fragment {
    private ProgressDialog progressDialog;
    List<String> listfoto = new ArrayList<String>();
    List<String> listketerangan = new ArrayList<String>();
    ViewPager viewPager;
    int posPager = -1;
    Timer timer;
    String tokenFirebase = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        viewPager = (ViewPager) view.findViewById(R.id.pager);

        if(SharedFunction.getInstance(getContext()).isNetworkConnected()){
            LoadProcess();
        }else{
            Toast.makeText(getContext(),R.string.internet_error, Toast.LENGTH_LONG).show();
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                posPager = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        return view;
    }

    public void LoadProcess(){
        tokenFirebase = "";//FirebaseInstanceId.getInstance().getToken();
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
                        listfoto.clear();
                        listketerangan.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(getContext(),"Tidak ada slider", Toast.LENGTH_SHORT).show();
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
                                ImageAdapter adapter = new ImageAdapter(getContext());
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
                params.put("function", Constant.FUNCTION_LISTSLIDER);
                params.put("key", Constant.KEY);
//                params.put("key_push", tokenFirebase);
//                params.put("hp", SharedPrefManager.getInstance(getContext()).getHP());
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
            /*Picasso.with(getContext())
                    .load(Constant.PICT_URL+listfoto.get(position))
                    .error(R.drawable.ic_logo)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imageView);*/
            Glide.with(getContext())
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

}
