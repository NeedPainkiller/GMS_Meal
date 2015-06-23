package com.gms.gms_meal;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.GMS.security.RateDialog;
import com.andexert.library.RippleView;
import com.andexert.library.RippleView.OnRippleCompleteListener;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.gms.gms_meal.Alarm.AlarmManagement;
import com.gms.gms_meal.DB.CreateDB;
import com.gms.gms_meal.Dev.DeveloperActivity;
import com.gms.gms_meal.Meal_Package.DinnerViewFragment;
import com.gms.gms_meal.Meal_Package.LunchViewFragment;
import com.gms.gms_meal.Rate_Package.RateViewFragment;
import com.gms.gms_meal.lib.FontAwesomeText;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.Locale;


public class MainActivity extends ActionBarActivity implements OnClickListener, OnRippleCompleteListener {

  public static Context context;
  private MaterialViewPager materialViewPager;
  private DrawerLayout drawerLayout;
  private LinearLayout nav;
  private ActionBarDrawerToggle actionBarDrawerToggle;
  private RippleView developerRoppleView, opensourceRippleView, githubRippleView;
  private FontAwesomeText developerFont, opensourceFont, githubFont;
  private Toolbar toolbar;
  private Shimmer shimmer;
  private ShimmerTextView shimmerTextView, developerSim, opensourceSim, githubSim;
  private FloatingActionMenu menu;
  private FloatingActionButton Main_facebook;
  private FloatingActionButton Main_refresh;
  private FloatingActionButton Main_rate;
  private FloatingActionButton Main_Dinner;
  private FloatingActionButton Main_Lunch;
  private SharedPreferences sharedPreferences;
  private SharedPreferences.Editor editor;
  private View v;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    context = MainActivity.this;
    setTitle("");
    v = getWindow().getDecorView().findViewById(android.R.id.content);

    developerRoppleView = (RippleView) findViewById(R.id.nav_RippleDeveloper);
    developerRoppleView.setOnRippleCompleteListener(this);
    opensourceRippleView = (RippleView) findViewById(R.id.nav_RippleOpensource);
    opensourceRippleView.setOnRippleCompleteListener(this);
    githubRippleView = (RippleView) findViewById(R.id.nav_RippleGithub);
    githubRippleView.setOnRippleCompleteListener(this);

    developerFont = (FontAwesomeText) findViewById(R.id.nav_FontDeveloper);

    opensourceFont = (FontAwesomeText) findViewById(R.id.nav_FontOpensource);

    githubFont = (FontAwesomeText) findViewById(R.id.nav_FontGithub);

    shimmer = new Shimmer();

    developerSim = (ShimmerTextView) findViewById(R.id.nav_ShimmerDeveloper);
    shimmer.start(developerSim);
    opensourceSim = (ShimmerTextView) findViewById(R.id.nav_ShimmerOpensource);
    shimmer.start(opensourceSim);
    githubSim = (ShimmerTextView) findViewById(R.id.nav_ShimmerGithub);
    shimmer.start(githubSim);

    materialViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    nav = (LinearLayout) findViewById(R.id.left_drawer);

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

    menu = (FloatingActionMenu) findViewById(R.id.MainMenu);
    menu.setClosedOnTouchOutside(true);

    Main_facebook = (FloatingActionButton) findViewById(R.id.Main_facebook);
    Main_facebook.setOnClickListener(this);
    Main_refresh = (FloatingActionButton) findViewById(R.id.Main_refresh);
    Main_refresh.setOnClickListener(this);
    Main_rate = (FloatingActionButton) findViewById(R.id.Main_rate);
    Main_rate.setOnClickListener(this);

    Main_Lunch = (FloatingActionButton) findViewById(R.id.Main_Lunch);
    Main_Lunch.setOnClickListener(this);
    Main_Dinner = (FloatingActionButton) findViewById(R.id.Main_Dinner);
    Main_Dinner.setOnClickListener(this);

    actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
    actionBarDrawerToggle.syncState();

    drawerLayout.setDrawerListener(actionBarDrawerToggle);

    materialViewPagerInit();

    materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
    materialViewPager.getViewPager().setCurrentItem(0);
    materialViewPager.getViewPager().setOffscreenPageLimit(-1);

    sharedPreferences = getSharedPreferences("Alarm", MODE_PRIVATE);
    editor = sharedPreferences.edit();

    if (sharedPreferences.getBoolean("lunch", false)) {
      Main_Lunch.setImageResource(R.mipmap.ic_alarm_on);
    } else {
      editor.putBoolean("lunch", false);
    }

    if (sharedPreferences.getBoolean("dinner", false)) {
      Main_Dinner.setImageResource(R.mipmap.ic_alarm_on);
    } else {
      editor.putBoolean("dinner", false);
    }
    if (!sharedPreferences.getBoolean("canAlarm", false)) {
      Main_Lunch.setEnabled(false);
      Main_Dinner.setEnabled(false);
      Main_Lunch.setLabelText("DB?? ????? ???? ???????, ??????? ??????????");
      Main_Dinner.setLabelText("DB?? ????? ???? ???????, ??????? ??????????");
    }
    editor.commit();
  }

  @Override
  protected void onDestroy() {
    System.gc();
    MaterialViewPagerHelper.unregister(context);
    super.onDestroy();
  }


  void materialViewPagerInit() {
    materialViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
      int oldPosition = -1;
      SparseArray<View> views = new SparseArray<View>();

      @Override
      public Fragment getItem(int i) {
        switch (i) {
          case 0:

            LunchViewFragment lunchRecyclerViewFragment = new LunchViewFragment(0, getApplicationContext(), v).getFrag();
            return lunchRecyclerViewFragment;

          case 1:
            DinnerViewFragment dinnerRecyclerViewFragment = new DinnerViewFragment(1, getApplicationContext(), v).getFrag();
            return dinnerRecyclerViewFragment;

          default:
            RateViewFragment rateViewFragment = new RateViewFragment(MainActivity.this).getFrag();
            return rateViewFragment;
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

            imgUrl = "http://arch.goeia.go.kr/archmain/?menugrp=cafe&master=home&act=exec&mode=fullpic&cafe_sid=2160";
            color = Color.parseColor("#4169e1");
            break;

          case 1:
            imgUrl = "http://batv.iansan.net/batvadmin/news/file_upload/data01/%ED%95%99%EA%B5%90.gif";
            color = Color.parseColor("#000000");
            break;

          case 2:
            imgUrl = "";
            color = getResources().getColor(R.color.cyan);
            break;
        }

        int fadeDuration = 400;
        materialViewPager.setImageUrl(imgUrl, fadeDuration);
        materialViewPager.setColor(color, fadeDuration);

      }

      @Override
      public int getCount() {
        return 3;
      }

      @Override
      public CharSequence getPageTitle(int position) {

        Locale locale = Locale.KOREAN;

        switch (position) {
          case 0:
            return getString(R.string.Lunch).toUpperCase(locale);
          case 1:
            return getString(R.string.Dinner).toUpperCase(locale);
          case 2:
            return "????";
          case 3:
            return "GMS????";
        }
        return "";
      }

      @Override
      public int getItemPosition(Object item) {
        return POSITION_NONE;
      }

      @Override
      public Object instantiateItem(View container, int position) {
        View root = container;//refresh?? ??
        ((ViewPager) container).addView(root);
        views.put(position, root);
        return root;
      }

      @Override
      public void destroyItem(View collection, int position, Object o) {
        View view = (View) o;
        ((ViewPager) collection).removeView(view);
        views.remove(position);
        view = null;
      }

      @Override
      public void notifyDataSetChanged() {
        int key = 0;
        for (int i = 0; i < views.size(); i++) {
          key = views.keyAt(i);
          View view = views.get(key);
          //refresh?? ?????
        }
        super.notifyDataSetChanged();
      }


    });
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

  @Override
  public void onBackPressed() {
//        super.onBackPressed();
    if (drawerLayout.isDrawerOpen(nav)) {
      drawerLayout.closeDrawer(nav);

    } else {
      finish();
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.Main_facebook:    //ok

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/youngwon.kang.52")));
        break;
      case R.id.Main_refresh: //ok
        CreateDB.CreateDataBase.reset = true;
        materialViewPager.getViewPager().getAdapter().notifyDataSetChanged();

        break;
      case R.id.Main_rate:  //ok

        RateDialog rateDialog = new RateDialog(context, v.getRootView());
        rateDialog.show();
        materialViewPager.getViewPager().setCurrentItem(2);
        break;


      case R.id.Main_Lunch:
        if (sharedPreferences.getBoolean("canAlarm", false)) {
          AlarmManagement alarmManagement = new AlarmManagement(getApplicationContext());
          if (!sharedPreferences.getBoolean("lunch", false)) {
            Main_Lunch.setImageResource(R.mipmap.ic_alarm_on);
            Main_Lunch.setLabelText("점심알람 설정 (11:00)");
            editor.putBoolean("lunch", true);
            alarmManagement.setrAlarm(true);
          } else {
            Main_Lunch.setImageResource(R.mipmap.ic_alarm);
            editor.putBoolean("lunch", false);
            alarmManagement.cancleAlarm(true);
            Main_Lunch.setLabelText("점심알람 (OFF)");
          }
          editor.commit();
        } else {
          Main_Lunch.setLabelText("기초데이터가 없을 시에는 알람 설정이 불가능합니다.");
        }

        break;
      case R.id.Main_Dinner:
        if (sharedPreferences.getBoolean("canAlarm", false)) {
          AlarmManagement alarmManagement = new AlarmManagement(getApplicationContext());
          if (!sharedPreferences.getBoolean("dinner", false)) {
            Main_Dinner.setImageResource(R.mipmap.ic_alarm_on);
            Main_Dinner.setLabelText("석식알람 설정 (5:00)");
            editor.putBoolean("dinner", true);
            alarmManagement.setrAlarm(false);
          } else {
            Main_Dinner.setImageResource(R.mipmap.ic_alarm);
            Main_Dinner.setLabelText("석식알람 (OFF)");
            editor.putBoolean("dinner", false);
            alarmManagement.cancleAlarm(false);
          }
          editor.commit();
        } else {
          Main_Dinner.setLabelText("기초데이터가 없을 시에는 알람 설정이 불가능합니다.");
        }

        break;
    }
  }


  @Override
  public void onComplete(RippleView rippleView) {
    switch (rippleView.getId()) {
      case R.id.nav_RippleDeveloper:
        startActivity(new Intent(MainActivity.this, DeveloperActivity.class));
        break;
      case R.id.nav_RippleOpensource:
        startActivity(new Intent(MainActivity.this, OpenSource.class));
        break;
      case R.id.nav_RippleGithub:
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/kam6512/GMS_Meal")));
        break;
    }
  }
}
