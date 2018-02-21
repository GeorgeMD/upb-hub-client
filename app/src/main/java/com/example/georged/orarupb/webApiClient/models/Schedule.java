package com.example.georged.orarupb.webApiClient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.LocalTime;

/**
 * Created by George D on 24-Jun-17.
 */

public class Schedule {
    @Expose
    @SerializedName("scheduleId")
    public Integer scheduleId;

    @Expose
    @SerializedName("details")
    public String details;

    @Expose
    @SerializedName("endHour")
    public String endHour;

    @Expose
    @SerializedName("startHour")
    public String startHour;

    @Expose
    @SerializedName("course")
    public String course;

    @Expose
    @SerializedName("courseShort")
    public String courseShort;

    @Expose
    @SerializedName("profName")
    public String professor;

    @Expose
    @SerializedName("location")
    public String location;

    @Expose
    @SerializedName("address")
    public String address;
}
