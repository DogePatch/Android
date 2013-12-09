package com.doge.patch;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
    DrawerLayout mDrawerLayout;
    RelativeLayout mContentFragment;
    DrawerAdapter mDrawerAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    TextView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawer();


        ListView listView = (ListView) findViewById(R.id.fragment_container);
        listView.setAdapter(new DishListAdapter());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (TextView) findViewById(R.id.drawer_list);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle("SO DOGE");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("MUCH NAV");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }


    class DrawerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private class DishListAdapter extends BaseAdapter {
        private final String[] DUMMY_DISH_NAMES = {
                "Steak Taco",
                "Fishy Tacoz",
                "Chicken Taco",
                "Taco Nacho",
                "Tofu Taco",
                "Taco Supreme",
                "Hipster Taco",
                "Vegan Taco",
                "Yummy Taco",
                "Asian Taco",
        };
        private final String[] DUMMY_RESTAURANT_NAMES = {
                "Iron Cactus",
                "Mas Amigos",
                "El Matate",
                "El Farlito",
                "Las Palmas",
                "Taco Bell",
                "Garaje",
                "Los Comadres",
                "Papito",
                "El Norteno"
        };
        private final String[] DUMMY_SOCIALS = {
                "Kashif, Katie, and 2 others",
                "Kashif, Gabe, and 2 others",
                "Robert, Katie, and 2 others",
                "Kashif, Katie, and 2 others",
                "Kashif, Katie, and 2 others",
                "Kashif, Katie, and 2 others",
                "Kashif, Katie, and 2 others",
                "Kashif, Katie, and 2 others",
                "Kashif, Katie, and 2 others",
                "Kashif, Katie, and 2 others",
        };
        private final double[] DUMMY_LOCATIONS = {
                0.1,
                0.03,
                0.2,
                0.1,
                0.5,
                0.2,
                0.1,
                0.1,
                0.2,
                0.6
        };

        @Override
        public int getCount() {
            return DUMMY_DISH_NAMES.length;
        }

        /**
         * Will always return {@Dish}
         */
        @Override
        public Object getItem(int position) {
            Dish dish = new Dish();
            dish.dishName = DUMMY_DISH_NAMES[position];
            dish.restaurantName = DUMMY_RESTAURANT_NAMES[position];
            dish.social = DUMMY_SOCIALS[position];
            dish.location = DUMMY_LOCATIONS[position];
            return dish;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new DishListItem(MainActivity.this);
            }

            Dish dish = (Dish) getItem(position);
            ((DishListItem) convertView).setDish(dish);
            return convertView;
        }
    }
}
