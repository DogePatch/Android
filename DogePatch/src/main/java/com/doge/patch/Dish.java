package com.doge.patch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Gson dish representation of the DogePatch api v1
 */
public class Dish {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String dishName;

    @SerializedName("images")
    public List<String> images;

    @SerializedName("restaurantInfo")
    public Restaurant restaurant;

    @SerializedName("social")
    public String social;

    @SerializedName("lat")
    public double lat;

    @SerializedName("lng")
    public double lng;

    public double distance() {
        return LocationUtils.distanceTo(lat, lng);
    }
}
