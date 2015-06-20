package com.gms.gms_meal.Dev;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by kam6376 on 2015-05-26.
 */
public class DeveloperPagerAdapter extends FragmentStatePagerAdapter {
  private ArrayList<DeveloperPagerFragment> developerPagerFragments;
  private ViewPager viewPager;

  public DeveloperPagerAdapter(FragmentManager fragmentManager) {
    super(fragmentManager);
    developerPagerFragments = new ArrayList<>();
  }

  @Override
  public Fragment getItem(int position) {
    return developerPagerFragments.get(position);
  }

  @Override
  public int getCount() {
    return developerPagerFragments.size();
  }

  public void add(DeveloperPagerFragment developerPagerFragment) {
    developerPagerFragment.setAdapter(this);
    developerPagerFragments.add(developerPagerFragment);
    notifyDataSetChanged();
    viewPager.setCurrentItem(
        getCount() - 1, true
    );
  }

  public void remove(DeveloperPagerFragment developerPagerFragment) {
    developerPagerFragments.remove(developerPagerFragment);

    int pos = viewPager.getCurrentItem();
    notifyDataSetChanged();

    viewPager.setAdapter(this);
    if (pos >= this.getCount()) {
      pos = this.getCount() - 1;
    }
    viewPager.setCurrentItem(pos, true);
  }

  public int getItemPosition(Object o) {
    return POSITION_NONE;
  }

  public void setPager(ViewPager pager) {
    viewPager = pager;
  }
}
