package emcorp.studio.mutamtour.Library;

/**
 * Created by Bani Fahlevi on 1/20/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Belal on 26/11/16.
 */

public class SharedFunction {

    private static SharedFunction mInstance;
    private static Context mCtx;

    private SharedFunction(Context context) {
        mCtx = context;
    }

    public static synchronized SharedFunction getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedFunction(context);
        }
        return mInstance;
    }

    public void openActivityFinish(Class toActivity){
        Intent i = new Intent(mCtx,toActivity);
        mCtx.startActivity(i);
        ((Activity)mCtx).finish();
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}