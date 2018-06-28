package emcorp.studio.mutamtour.Library;

/**
 * Created by Bani Fahlevi on 1/20/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Belal on 26/11/16.
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "Mutamtour";

    private static final String ID = "ID";
    private static final String KODE = "KODE";
    private static final String NAMA = "NAMA";
    private static final String ALAMAT = "ALAMAT";
    private static final String EMAIL = "EMAIL";
    private static final String HP = "HP";
    private static final String LP = "LP";
    private static final String LOGIN = "LOGIN";
    private static final String TGL = "TGL";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean loginUser(String NAMA, String ID){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.NAMA,NAMA);
        editor.putString(this.ID,ID);
        editor.putString(this.LOGIN,"1");
        editor.apply();

        return true;
    }

    public boolean setID(String ID){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.ID,ID);
        editor.apply();
        return true;
    }

    public boolean setKODE(String KODE){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.KODE,KODE);
        editor.apply();
        return true;
    }

    public boolean setNAMA(String NAMA){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.NAMA,NAMA);
        editor.apply();
        return true;
    }

    public boolean setALAMAT(String ALAMAT){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.ALAMAT,ALAMAT);
        editor.apply();
        return true;
    }

    public boolean setEMAIL(String EMAIL){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.EMAIL,EMAIL);
        editor.apply();
        return true;
    }

    public boolean setHP(String HP){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.HP,HP);
        editor.apply();
        return true;
    }

    public boolean setLP(String LP){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.LP,LP);
        editor.apply();
        return true;
    }
    public boolean setTGL(String TGL){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.TGL,TGL);
        editor.apply();
        return true;
    }

    public boolean isLogin(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.LOGIN,"1");
        editor.apply();
        return true;
    }

    public boolean isLogout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
        editor.apply();
        return true;
    }

    public String getLogin(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LOGIN, null);
    }
    public String getNAMA(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAMA, null);
    }
    public String getALAMAT(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ALAMAT, null);
    }
    public String getHP(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(HP, null);
    }
    public String getEMAIL(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, null);
    }
    public String getLP(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LP, null);
    }
    public String getKODE(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KODE, null);
    }
    public String getTGL(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TGL, null);
    }



}