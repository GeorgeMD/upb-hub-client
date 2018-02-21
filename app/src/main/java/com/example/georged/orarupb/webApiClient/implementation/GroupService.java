package com.example.georged.orarupb.webApiClient.implementation;

import android.util.Log;

import com.example.georged.orarupb.utils.Constants;
import com.example.georged.orarupb.utils.interfaces.IAction;
import com.example.georged.orarupb.utils.interfaces.IServiceEvents;
import com.example.georged.orarupb.webApiClient.interfaces.IGroupService;
import com.example.georged.orarupb.webApiClient.models.Grupa;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by George D on 23-Jun-17.
 */

public class GroupService extends BaseService<IGroupService> {

    public GroupService(String url, IServiceEvents events) {
        super(url, IGroupService.class, events);
    }

    public void get(final IAction<ArrayList<Grupa>> callback) {
        events.onRequest();

        api.get().enqueue(new Callback<ArrayList<Grupa>>() {
            @Override
            public void onResponse(Call<ArrayList<Grupa>> call, Response<ArrayList<Grupa>> response) {
                callback.Call(response.body());
                events.onResponse();
            }

            @Override
            public void onFailure(Call<ArrayList<Grupa>> call, Throwable t) {
                Log.e(Constants.API_ERR, t.getMessage());
                events.onFailure();
            }
        });
    }
}
