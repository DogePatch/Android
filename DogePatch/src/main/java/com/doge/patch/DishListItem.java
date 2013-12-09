package com.doge.patch;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class DishListItem extends RelativeLayout {
    private static final String TAG = DishListItem.class.getSimpleName();

    private final LayoutInflater mInflater;
    private final ViewPager mBackgroundViewPager;
    private final TextView mDishNameTextView;
    private final TextView mRestaurantNameTextView;
    private final TextView mSocialTextView;
    private final TextView mLocationTextView;

    private Dish mDish;
    private DishViewHolder mTag;
    private DishBackgroundPagerAdapter mAdapter = new DishBackgroundPagerAdapter();
    private ImageLoader mImageLoader;
    private List<String> mImageUrls = new ArrayList<String>();
    private View mTouchTarget;


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
        mBackgroundViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private float mLastPositionOffset = 0f;
            private int mPreviousState = ViewPager.SCROLL_STATE_IDLE;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if(positionOffset < mLastPositionOffset && positionOffset < 0.7) {
//                    mBackgroundViewPager.setCurrentItem(position);
//                } else if(positionOffset > mLastPositionOffset && positionOffset > 0.3) {
//                    mBackgroundViewPager.setCurrentItem(position+1);
//                }
//                mLastPositionOffset = positionOffset;
            }

            @Override
            public void onPageSelected(int i) { }
            @Override
            public void onPageScrollStateChanged(int state) {
                // All of this is to inhibit any scrollable container from consuming our touch events as the user is changing pages
                Log.d(TAG, "mScrollState " + state);
                if (mPreviousState == ViewPager.SCROLL_STATE_IDLE) {
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        mTouchTarget = mBackgroundViewPager;
                    }
                } else {
                    if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_SETTLING) {
                        mTouchTarget = null;
                    }
                }

                mPreviousState = state;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mTouchTarget != null) {
            boolean wasProcessed = mTouchTarget.onTouchEvent(ev);
            Log.d(TAG, "wasProcessed " + wasProcessed);

            if (wasProcessed) {
                requestDisallowInterceptTouchEvent(true);
            } else {
                requestDisallowInterceptTouchEvent(false);
                mTouchTarget = null;
            }

            return wasProcessed;
        }
        Log.d(TAG, "touch taget " + mTouchTarget);
        return super.dispatchTouchEvent(ev);
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

        RequestQueue queue = DogePatchUtils.getRequestQueue(getContext().getApplicationContext());
        mImageLoader = new ImageLoader(queue,new BitmapLruCache());
        for (String url : mDish.imageUrls) {
            mImageUrls.add(url);
        }
        mAdapter.notifyDataSetChanged();
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
