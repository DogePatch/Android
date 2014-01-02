package com.doge.patch;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that displays a list of search results
 */
public class SearchResultsFragment extends ListFragment implements View.OnClickListener {
    private static final String TAG = SearchResultsFragment.class.getSimpleName();

    private List<Dish> mDishes = new ArrayList<Dish>();
    private ApiUtils mApiUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(mAdapter);

        mApiUtils = new ApiUtils(getActivity().getApplicationContext());
        mApiUtils.getDishes(new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                mDishes = ((DishList) o).dishes;
                Log.d(TAG, "Got " + mDishes.size() + " dishes");
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Dish dish = ((ListItemDishView) view).getDish();
        Intent intent = new Intent(getActivity(), DishActivity.class);
        intent.putExtra(DishActivity.EXTRA_DISH, dish);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private BaseAdapter mAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return mDishes.size() * 20;
        }

        /**
         * Will always return {@Dish}
         */
        @Override
        public Object getItem(int position) {
            return mDishes.get(position % mDishes.size());
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new ListItemDishView(getActivity());
            }

            Dish dish = (Dish) getItem(position);
            ((ListItemDishView) convertView).setDish(dish);
            convertView.setOnClickListener(SearchResultsFragment.this);

            return convertView;
        }
    };
}
