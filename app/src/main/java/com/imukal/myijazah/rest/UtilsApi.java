package com.imukal.myijazah.rest;

import retrofit2.Retrofit;

public class UtilsApi {
    public static final String BASE_URL="https://ahd.imukal.com/api/";

    public static  apiInterface getApiService(){
        return apiClient.getClient(BASE_URL).create(apiInterface.class);
    }
}
