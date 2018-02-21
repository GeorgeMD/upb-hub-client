package com.example.georged.orarupb.webApiClient.interfaces;

import com.example.georged.orarupb.webApiClient.models.Schedule;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by George D on 24-Jun-17.
 */

public interface IScheduleService {
    @GET("schedule")
    Call<ArrayList<Schedule>> get(@Query("groupId") int groupId,
                                  @Query("day") String day,
                                  @Query("semigroup") int semigroup);
}
