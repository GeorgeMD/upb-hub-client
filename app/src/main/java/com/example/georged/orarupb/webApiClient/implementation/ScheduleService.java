package com.example.georged.orarupb.webApiClient.implementation;

import android.util.Log;

import com.example.georged.orarupb.utils.Constants;
import com.example.georged.orarupb.utils.interfaces.IAction;
import com.example.georged.orarupb.utils.interfaces.IServiceEvents;
import com.example.georged.orarupb.webApiClient.interfaces.IScheduleService;
import com.example.georged.orarupb.webApiClient.models.Schedule;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by George D on 24-Jun-17.
 */

public class ScheduleService extends BaseService<IScheduleService> {

    ScheduleService(String url, IServiceEvents events) {
        super(url, IScheduleService.class, events);
    }

    public void get(int groupId, String day, int semigroup, final IAction<ArrayList<Schedule>> callback) {
        events.onRequest();

        api.get(groupId, day, semigroup).enqueue(new Callback<ArrayList<Schedule>>() {
            @Override
            public void onResponse(Call<ArrayList<Schedule>> call, Response<ArrayList<Schedule>> response) {
                callback.Call(response.body());
                events.onResponse();
            }

            @Override
            public void onFailure(Call<ArrayList<Schedule>> call, Throwable t) {
                Log.e(Constants.API_ERR, t.getMessage());
                events.onFailure();
            }
        });
    }
}
