package com.doge.patch;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.*;

public class DishListItem extends RelativeLayout {
    private static final String TAG = DishListItem.class.getSimpleName();

    private final LayoutInflater mInflater;
    private final ViewPager mBackgroundViewPager;
    private final TextView mDishNameTextView;
    private final TextView mRestaurantNameTextView;
    private final TextView mSocialTextView;
    private final TextView mLocationTextView;
    private float xDownPosition;
    private float yDownPosition;

    private Dish mDish;
    private DishBackgroundPagerAdapter mAdapter = new DishBackgroundPagerAdapter();
    private ImageLoader mImageLoader;
    private List<String> mImageUrls = new ArrayList<String>();


    public DishListItem(Context context) {
        this(context, null);
    }

    public DishListItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.list_item_dish, this, true);

        mBackgroundViewPager = (ViewPager) view.findViewById(R.id.background_view_pager);
        mDishNameTextView = (TextView) view.findViewById(R.id.dish_name);
        mRestaurantNameTextView = (TextView) view.findViewById(R.id.restaurant_name);
        mSocialTextView = (TextView) view.findViewById(R.id.social);
        mLocationTextView = (TextView) view.findViewById(R.id.location);

        mBackgroundViewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);

        if (action == MotionEvent.ACTION_DOWN) {
            xDownPosition = event.getX();
            yDownPosition = event.getY();
        } else if (action == MotionEvent.ACTION_MOVE) {
            float xDiff = Math.abs(event.getX() - xDownPosition);
            float yDiff = Math.abs(event.getY() - yDownPosition);
            if (xDiff > yDiff) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    /**
     * Update {@link #mDish}
     * The {@link #mDish} can be changed if the view is used as a convertView in a ListView.
     */
    public void setDish(Dish dish) {
        mDish = dish;
        mDishNameTextView.setText(mDish.dishName + " — ");
        mRestaurantNameTextView.setText(mDish.restaurant.name);
        mSocialTextView.setText(mDish.social);
        // TODO: format this string properly and handle distance units
        mLocationTextView.setText("" + mDish.distance() + " mi");

        RequestQueue queue = DogePatchUtils.getRequestQueue(getContext().getApplicationContext());
        mImageLoader = new ImageLoader(queue,new BitmapLruCache());
        mImageUrls = mDish.images;
        Collections.shuffle(mImageUrls, new Random(System.nanoTime()));
        mAdapter.notifyDataSetChanged();
    }

    private class DishBackgroundPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NetworkImageView imageView = new NetworkImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setDefaultImageResId(R.drawable.dish_placeholder);
            imageView.setImageUrl(mImageUrls.get(position), mImageLoader);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((NetworkImageView) object);
        }
    }
}
