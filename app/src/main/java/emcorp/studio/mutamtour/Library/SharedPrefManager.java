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
    private static final String NAMA = "NAMA";
    private static final String HP = "HP";
    private static final String EMAIL = "EMAIL";
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWORD";
    private static final String PROVINSI = "PROVINSI";
    private static final String KABUPATEN = "KABUPATEN";
    private static final String KECAMATAN = "KECAMATAN";
    private static final String DESA = "DESA";

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

    public boolean setPASSWORD(String PASSWORD){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.PASSWORD,PASSWORD);
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

    public boolean setPROVINSI(String PROVINSI){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.PROVINSI,PROVINSI);
        editor.apply();
        return true;
    }
    public boolean setKABUPATEN(String KABUPATEN){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.KABUPATEN,KABUPATEN);
        editor.apply();
        return true;
    }
    public boolean setKECAMATAN(String KECAMATAN){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.KECAMATAN,KECAMATAN);
        editor.apply();
        return true;
    }
    public boolean setDESA(String DESA){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.DESA,DESA);
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
    public String getPASSWORD(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD, null);
    }
    public String getEMAIL(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, null);
    }
    public String getID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ID, null);
    }
    public String getPROVINSI(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PROVINSI, null);
    }
    public String getKABUPATEN(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KABUPATEN, null);
    }
    public String getKECAMATAN(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KECAMATAN, null);
    }
    public String getDESA(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DESA, null);
    }
    public String getNAMA(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAMA, null);
    }
    public String getHP(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(HP, null);
    }



}