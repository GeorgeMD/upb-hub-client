package com.example.georged.orarupb.webApiClient.implementation;

import android.util.Log;

import com.example.georged.orarupb.utils.Constants;
import com.example.georged.orarupb.utils.interfaces.IAction;
import com.example.georged.orarupb.utils.interfaces.IServiceEvents;
import com.example.georged.orarupb.webApiClient.interfaces.IUtilsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by georgemd on 21.02.2018.
 */

public class UtilsService extends BaseService<IUtilsService> {
    UtilsService(String url, IServiceEvents events) {
        super(url, IUtilsService.class, events);
    }

    public void checkVersion(String version, final IAction<Boolean> callback) {
        api.checkVersion(version).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                callback.Call(response.body());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e(Constants.API_ERR, t.getMessage());
            }
        });
    }
}
