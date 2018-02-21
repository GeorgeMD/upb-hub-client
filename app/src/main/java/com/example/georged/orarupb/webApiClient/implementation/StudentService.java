package com.example.georged.orarupb.webApiClient.implementation;

import android.util.Log;

import com.example.georged.orarupb.utils.Constants;
import com.example.georged.orarupb.utils.interfaces.IAction;
import com.example.georged.orarupb.utils.interfaces.IServiceEvents;
import com.example.georged.orarupb.webApiClient.interfaces.IStudentService;
import com.example.georged.orarupb.webApiClient.models.Student;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by George D on 23-Jun-17.
 */

public class StudentService extends BaseService<IStudentService>{

    public StudentService(String url, IServiceEvents events) {
        super(url, IStudentService.class, events);
    }

    public void login(String email, String password, final IAction<Student> callback) {
        events.onRequest();

        api.login(email, password).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                callback.Call(response.body());
                events.onResponse();
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.e(Constants.API_ERR + "[" + StudentService.class.getName() + "]", t.getMessage());
                events.onFailure();
            }
        });
    }

    //Create new student
    public void create(final Student student, final IAction<Integer> callback) {
        events.onRequest();

        api.post(student).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                callback.Call(response.body());
                events.onResponse();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e(Constants.API_ERR, t.getMessage());
                events.onFailure();
            }
        });
    }
}
