package com.doge.patch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Display a single dish
 */
public class DishActivity extends Activity {
    private static final String TAG = DishActivity.class.getSimpleName();

    public static final String EXTRA_DISH = "extra.dish";

    private Dish mDish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDish = getIntent().getParcelableExtra(EXTRA_DISH);
        if (mDish == null) {
            Log.e(TAG, "There was no dish in the intent. The app will likely crash soon.");
        }

        setContentView(R.layout.dish);
        ((ViewPager) findViewById(R.id.images)).setAdapter(mAdapter);
        ((TextView) findViewById(R.id.dish_name)).setText(mDish.dishName);
        ((TextView) findViewById(R.id.restaurant_name)).setText("@ " + mDish.restaurant.name);

        getActionBar().setTitle(mDish.dishName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private PagerAdapter mAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return mDish.images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NetworkImageView imageView = new NetworkImageView(DishActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setDefaultImageResId(R.drawable.dish_placeholder);
            imageView.setImageUrl(mDish.images.get(position),
                    DogePatchUtils.getImageLoader(getApplicationContext()));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((NetworkImageView) object);
        }
    };
}
