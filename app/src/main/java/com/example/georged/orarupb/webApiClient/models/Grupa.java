package com.example.georged.orarupb.webApiClient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by George D on 23-Jun-17.
 */

public class Grupa {

    @Expose
    @SerializedName("GrupaId")
    public Integer grupaId;

    @Expose
    @SerializedName("Numar")
    public String numar;

    @Expose
    @SerializedName("An")
    public Integer an;
}
