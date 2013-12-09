package com.doge.patch;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DishListItem extends RelativeLayout {
    private static final String TAG = DishListItem.class.getSimpleName();

    private final ViewPager mBackgroundViewPager;
    private final TextView mDishNameTextView;
    private final TextView mRestaurantNameTextView;
    private final TextView mSocialTextView;
    private final TextView mLocationTextView;

    private Dish mDish;
    private DishViewHolder mTag;


    public DishListItem(Context context) {
        this(context, null);
    }

    public DishListItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_dish, this, true);

        mBackgroundViewPager = (ViewPager) view.findViewById(R.id.background_view_pager);
        mDishNameTextView = (TextView) view.findViewById(R.id.dish_name);
        mRestaurantNameTextView = (TextView) view.findViewById(R.id.restaurant_name);
        mSocialTextView = (TextView) view.findViewById(R.id.social);
        mLocationTextView = (TextView) view.findViewById(R.id.location);
    }

    /**
     * Update {@link #mDish}
     * The {@link #mDish} can be changed if the view is used as a convertView in a ListView.
     */
    public void setDish(Dish dish) {
        mDish = dish;
        mDishNameTextView.setText(mDish.dishName + " — ");
        mRestaurantNameTextView.setText(mDish.restaurantName);
        mSocialTextView.setText(mDish.social);
        // TODO: format this string properly and handle distance units
        mLocationTextView.setText("" + mDish.location + " mi");
    }

    public DishViewHolder getTag() {
        if (mTag == null) {
            mTag = new DishViewHolder();
        }
        return mTag;
    }


    private class DishViewHolder {
        public final ViewPager backgroundViewPager;
        public final TextView dishNameTextView;
        public final TextView restaurantNameTextView;
        public final TextView socialTextView;
        public final TextView locationTextView;

        public DishViewHolder() {
            backgroundViewPager = mBackgroundViewPager;
            dishNameTextView = mDishNameTextView;
            restaurantNameTextView = mRestaurantNameTextView;
            socialTextView = mSocialTextView;
            locationTextView = mLocationTextView;
        }
    }
}
