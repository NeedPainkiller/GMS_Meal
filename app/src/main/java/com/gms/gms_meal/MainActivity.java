package com.gms.gms_meal;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.gms.gms_meal.Dev.DeveloperActivity;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private MaterialViewPager materialViewPager;
    private DrawerLayout drawerLayout;
    private View nav;
    ActionBarDrawerToggle actionBarDrawerToggle;

    private Toolbar toolbar;
    Shimmer shimmer;
    ShimmerTextView shimmerTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("");

        materialViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav = (View) findViewById(R.id.left_drawer);
        shimmerTextView = (ShimmerTextView) findViewById(R.id.logo_white);
        shimmer = new Shimmer();
        shimmer.start(shimmerTextView);


        toolbar = materialViewPager.getToolbar();

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        actionBarDrawerToggle.syncState();

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        materialViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            int oldPosition = -1;


            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case 0:

                        RecyclerViewFragment lunchRecyclerViewFragment = new RecyclerViewFragment(0).getFrag();
                        return lunchRecyclerViewFragment;
                    case 1:
                         RecyclerViewFragment dinnerRecyclerViewFragment = new RecyclerViewFragment(1).getFrag();
                        return dinnerRecyclerViewFragment;
//                    case 2:
//                        return ScrollFragment.newInstance();
                    default:
                        return ScrollFragment.newInstance();
                }

            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);

                if (position == oldPosition) {
                    return;
                }
                oldPosition = position;
                int color = 0;
                String imgUrl = "";

                switch (position) {

                    case 0:

                        imgUrl = "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg";
                        color = getResources().getColor(R.color.blue);
                        break;

                    case 1:
                        imgUrl = "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg";
                        color = getResources().getColor(R.color.green);
                        break;
                    case 2:
                        imgUrl = "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg";
                        color = getResources().getColor(R.color.cyan);
                        break;
                    case 3:
                        imgUrl = "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg";
                        color = getResources().getColor(R.color.red);
                        break;

                }

                int fadeDuration = 400;
                materialViewPager.setImageUrl(imgUrl, fadeDuration);
                materialViewPager.setColor(color, fadeDuration);

            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {

                Locale locale = Locale.KOREAN;

                switch (position) {
                    case 0:
                        return getString(R.string.Lunch).toUpperCase(locale);
                    case 1:
                        return  getString(R.string.Dinner).toUpperCase(locale);
                    case 2:
                        return "rate";
                    case 3:
                        return "Sample";
                }
                return "";
            }
        });

        materialViewPager.getViewPager().setOffscreenPageLimit(materialViewPager.getViewPager().getAdapter().getCount());
        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
        materialViewPager.getViewPager().setCurrentItem(0);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(nav)) {
                    drawerLayout.closeDrawer(nav);

                } else {
                    drawerLayout.openDrawer(nav);

                }

                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, DeveloperActivity.class));

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
