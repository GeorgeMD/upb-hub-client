package com.example.georged.orarupb.webApiClient.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by georgemd on 21.02.2018.
 */

public interface IUtilsService {
    @GET("utils")
    Call<Boolean> checkVersion(@Query("version") String version);
}
