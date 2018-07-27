package com.imukal.myijazah.act;


import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    public static final String CEK_LOGIN = "IsLogin";
    // Shared preferences file name
    public static final String PREF_NAME = "PREF_AHD";
    private static final String CEK_SUDAH_LOGIN = "sessionLogin";
    private static final String CEK_SUDAH_SCAN = "sudahScan";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    // context
    public PrefManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    // session login
    public void clear() {
        editor.clear();
        editor.commit();
    }

    public void setSudahLogin(String []data) {
       // editor.putBoolean(CEK_SUDAH_LOGIN, sudahLogin);
        editor.putString("nama",data[0]);
        editor.putString("nim",data[1]);
        editor.putString("prodi",data[2]);
        editor.putString("tanggal_lulusd3",data[3]);
        editor.putString("no_ijazah",data[4]);
        editor.commit();
    }

    // ini method khusus pemanggilan button logout
    // karena logout tidak memerlukan data user;
    public void setSudahLogin(boolean sudahLogin) {
        editor.putBoolean(CEK_SUDAH_LOGIN, sudahLogin);
        editor.commit();
    }

    // -------------------------------------------------------- //

    //sesionSudah Scan
    public boolean isSudahScan() {
        return pref.getBoolean(CEK_SUDAH_SCAN, false);
    }

    public void setSudahScan(boolean sudahScan) {
        editor.putBoolean(CEK_SUDAH_SCAN, sudahScan);
    }

}
