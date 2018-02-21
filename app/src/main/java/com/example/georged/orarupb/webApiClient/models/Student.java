package com.example.georged.orarupb.webApiClient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by George D on 23-Jun-17.
 */

public class Student implements Serializable {

    @Expose
    @SerializedName("studentId")
    public Integer id;

    @Expose
    @SerializedName("lastName")
    public String nume;

    @Expose
    @SerializedName("firstName")
    public String prenume;

    @Expose
    @SerializedName("email")
    public String email;

    @Expose
    @SerializedName("phone")
    public String telefon;

    @Expose
    @SerializedName("groupId")
    public Integer groupId;

    @Expose
    @SerializedName("groupNumber")
    public String groupNumber;

//    @Expose
//    @SerializedName("password")
    public String password;
}
