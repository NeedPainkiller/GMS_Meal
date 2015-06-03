package com.gms.gms_meal.Dev;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.gms.gms_meal.R;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

/**
 * Created by kam6376 on 2015-05-26.
 */
public class DeveloperActivity extends FragmentActivity {

    ViewPager viewPager;
    DeveloperPagerAdapter developerPagerAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_developer);

        viewPager = (ViewPager) findViewById(R.id.devPager);
        viewPager.setBackgroundColor(0xFF000000);

        ParallaxPagerTransformer pt = new ParallaxPagerTransformer((R.id.devImage));
        pt.setBorder(20);
        //pt.setSpeed(0.2f);
        viewPager.setPageTransformer(false, pt);

        developerPagerAdapter = new DeveloperPagerAdapter(getSupportFragmentManager());
        developerPagerAdapter.setPager(viewPager); //only for this transformer

        Bundle bNina = new Bundle();
        bNina.putInt("image", R.mipmap.dev1);

        bNina.putString("name", "Kang YoungWon");

        bNina.putString("detail", "Developer");
        DeveloperPagerFragment pfNina = new DeveloperPagerFragment();
        pfNina.setArguments(bNina);

        Bundle bNiju = new Bundle();
        bNiju.putInt("image", R.mipmap.dev2);
        bNiju.putString("name", "Kim JunWhui");
        bNiju.putString("detail", "Planner");
        DeveloperPagerFragment pfNiju = new DeveloperPagerFragment();
        pfNiju.setArguments(bNiju);

        Bundle bYuki = new Bundle();
        bYuki.putInt("image", R.mipmap.dev3);
        bYuki.putString("name", "An ByoungChan");
        bYuki.putString("detail", "Designer");
        DeveloperPagerFragment pfYuki = new DeveloperPagerFragment();
        pfYuki.setArguments(bYuki);

        Bundle bKero = new Bundle();
        bKero.putInt("image", R.mipmap.dev3);
        bKero.putString("name", "Park sungcherl");
        bKero.putString("detail", "Marketing");
        DeveloperPagerFragment pfKero = new DeveloperPagerFragment();
        pfKero.setArguments(bKero);
        developerPagerAdapter.add(pfNina);
        developerPagerAdapter.add(pfNiju);
        developerPagerAdapter.add(pfYuki);
        developerPagerAdapter.add(pfKero);
        viewPager.setAdapter(developerPagerAdapter);


        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();

        return super.onOptionsItemSelected(item);
    }
}
