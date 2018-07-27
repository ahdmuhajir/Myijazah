package com.imukal.myijazah.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface apiInterface {
    @FormUrlEncoded
    @POST("api.php")
    Call<ResponseBody> validasiIjazah(
            @Field("nim_mhs") String nim,
            @Field("no_ijazah") String no_ijazah
    );
}
