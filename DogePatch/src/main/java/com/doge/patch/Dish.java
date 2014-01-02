package com.doge.patch;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Gson dish representation of the DogePatch api v1
 */
public class Dish implements Parcelable{

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(dishName);
        parcel.writeStringList(images);
        parcel.writeParcelable(restaurant, flags);
        parcel.writeString(social);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }

    public static final Parcelable.Creator<Dish> CREATOR
            = new Parcelable.Creator<Dish>() {
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    private Dish(Parcel in) {
        id = in.readInt();
        dishName = in.readString();
        images = new ArrayList<String>();
        in.readStringList(images);
        restaurant = in.readParcelable(Restaurant.class.getClassLoader());
        social = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    @Override
    public String toString() {
        return dishName + " at " + restaurant.name;
    }
}
