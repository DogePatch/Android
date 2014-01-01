package com.doge.patch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Gson dish representation of the DogePatch api v1
 */
public class Restaurant {

    @SerializedName("id")
    public int it;

    @SerializedName("name")
    public String name;

    @SerializedName("lat")
    public double lat;

    @SerializedName("lng")
    public double lng;

    @SerializedName("tags")
    public List<String> tags;
}
