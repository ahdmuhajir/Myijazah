package com.imukal.myijazah;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.zxing.Result;
import com.imukal.myijazah.act.PrefManager;
import com.imukal.myijazah.rest.UtilsApi;
import com.imukal.myijazah.rest.apiInterface;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    apiInterface mApiInterface;
    PrefManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        manager = new PrefManager(this);
        mApiInterface= UtilsApi.getApiService();
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                //Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }
    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (mScannerView == null) {
                    mScannerView= new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        Log.v("TAG",result.getText());
        Log.v("TAG",result.getBarcodeFormat().toString());
        final String isi[]= result.getText().split("&");
        int total = isi.length;
        if(result.getText().toString().indexOf("&")==10 && total==2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Scan Result");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cekKeaslian(isi);
                }
            });
            builder.setMessage("Scan Berhasil, Klik OK untuk Melihat Hasilnya");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Scan Result");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent a = new Intent(getApplicationContext(),MainActivity.class);
                    finish();
                    startActivity(a);
                }
            });
            builder.setMessage("Data Tidak Ditemukan");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
       //resume camera preview after get text
       // mScannerView.resumeCameraPreview(this);
    }
    private void cekKeaslian(String [] data){
        mApiInterface.validasiIjazah(data[0],data[1]).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("error").equals("false")){
                            String nim=jsonObject.getJSONObject("user").getString("nim_mhs");
                            String nama=jsonObject.getJSONObject("user").getString("nama_mhs");
                            String prodi=jsonObject.getJSONObject("user").getString("prodi");
                            String tanggal_lulusd3=jsonObject.getJSONObject("user").getString("tanggal_lulusd3");
                            String no_ijazah=jsonObject.getJSONObject("user").getString("no_ijazah");
                            manager.setSudahLogin(new String[]{nama,nim,prodi,tanggal_lulusd3,no_ijazah});
                            Intent i = new Intent(getApplicationContext(),ScanResult.class);
                            startActivity(i);
                        }else{
                            String err = jsonObject.getString("error_msg");
                            Toast.makeText(getApplicationContext(),err,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Koneksi terputus, Ulangi Scan",Toast.LENGTH_LONG).show();
                Intent a = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(a);
            }
        });

    }
}
