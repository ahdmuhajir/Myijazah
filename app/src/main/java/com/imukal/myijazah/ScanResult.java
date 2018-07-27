package com.imukal.myijazah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imukal.myijazah.act.PrefManager;
import com.imukal.myijazah.rest.UtilsApi;
import com.imukal.myijazah.rest.apiClient;
import com.imukal.myijazah.rest.apiInterface;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanResult extends AppCompatActivity {
    PrefManager prefManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        prefManager = new PrefManager(this);
        sharedPreferences = this.getSharedPreferences(prefManager.PREF_NAME, 0);
        editor = sharedPreferences.edit();
        TextView nim= findViewById(R.id.nim);
        TextView nama= findViewById(R.id.tvnama);
        TextView prodi=findViewById(R.id.tvprodi);
        TextView tglLulus=findViewById(R.id.tvlulus);
        TextView noIjazah= findViewById(R.id.tvijazah);
        nim.setText(sharedPreferences.getString("nim",""));
        nama.setText(sharedPreferences.getString("nama",""));
        prodi.setText(sharedPreferences.getString("prodi",""));
        tglLulus.setText(sharedPreferences.getString("tanggal_lulusd3",""));
        noIjazah.setText(sharedPreferences.getString("no_ijazah",""));

    }

}
