package com.example.georged.orarupb.webApiClient.interfaces;

import com.example.georged.orarupb.webApiClient.models.Student;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by George D on 23-Jun-17.
 */

public interface IStudentService {
    @GET("student")
    Call<Student> login(@Query("email") String email, @Query("password") String password);


    @POST("students")
    Call<Integer> post(@Body Student student);

    @PUT("students")
    Call<ResponseBody> put(@Body Student student);

    @GET("GetByGroup/{groupId}")
    Call<ArrayList<Student>> getByGroup(@Path("groupId") int groupId);
}
