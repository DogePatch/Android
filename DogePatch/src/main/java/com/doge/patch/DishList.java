package com.doge.patch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Gson object for a list of dishes
 */
public class DishList {

    @SerializedName("dishes")
    List<Dish> dishes;

}
