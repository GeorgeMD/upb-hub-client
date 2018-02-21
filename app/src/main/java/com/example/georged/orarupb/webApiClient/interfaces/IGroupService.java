package com.example.georged.orarupb.webApiClient.interfaces;

import com.example.georged.orarupb.webApiClient.models.Grupa;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by George D on 23-Jun-17.
 */

public interface IGroupService {
    @GET("Groups")
    Call<ArrayList<Grupa>> get();

    @GET("Groups/{id}")
    Call<Grupa> get(@Path("id") int grupaId);
}
