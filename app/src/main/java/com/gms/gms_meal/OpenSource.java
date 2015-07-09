package com.gms.gms_meal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.andexert.library.RippleView;
import com.andexert.library.RippleView.OnRippleCompleteListener;

/**
 * Created by kam6376 on 2015-06-13.
 */
public class OpenSource extends ActionBarActivity implements OnRippleCompleteListener {

  /**
   * Called when the activity is first created.
   */

  RippleView MaterialViewPager_license,
      RippleEffect_license,
      FloatingActionButton_license,
      ParallaxPagerTransformer_license,
      shimmer_license,
      androidBootstrap_license,
      GoogleMaterialIcon_license;


  Intent intent = new Intent(Intent.ACTION_VIEW);
  Uri Git;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.l_opensource);

    MaterialViewPager_license = (RippleView) findViewById(R.id.MaterialViewPager_license);
    MaterialViewPager_license.setOnRippleCompleteListener(this);

    RippleEffect_license = (RippleView) findViewById(R.id.RippleEffect_license);
    RippleEffect_license.setOnRippleCompleteListener(this);

    FloatingActionButton_license = (RippleView) findViewById(R.id.FloatingActionButton_license);
    FloatingActionButton_license.setOnRippleCompleteListener(this);

    ParallaxPagerTransformer_license = (RippleView) findViewById(R.id.ParallaxPagerTransformer_license);
    ParallaxPagerTransformer_license.setOnRippleCompleteListener(this);

    shimmer_license = (RippleView) findViewById(R.id.shimmer_license);
    shimmer_license.setOnRippleCompleteListener(this);

    androidBootstrap_license = (RippleView) findViewById(R.id.androidBootstrap_license);
    androidBootstrap_license.setOnRippleCompleteListener(this);

    GoogleMaterialIcon_license = (RippleView) findViewById(R.id.GoogleMaterialIcon_license);
    GoogleMaterialIcon_license.setOnRippleCompleteListener(this);

  }


  @Override
  public void onComplete(RippleView rippleView) {
    switch (rippleView.getId()) {
      case R.id.MaterialViewPager_license:

        Git = Uri.parse("https://github.com/florent37/MaterialViewPager");

        break;
      case R.id.RippleEffect_license:
        Git = Uri.parse("https://github.com/traex/RippleEffect");
        break;
      case R.id.FloatingActionButton_license:
        Git = Uri.parse("https://github.com/Clans/FloatingActionButton");

        break;
      case R.id.ParallaxPagerTransformer_license:
        Git = Uri.parse("https://github.com/xgc1986/ParallaxPagerTransformer");

        break;
      case R.id.shimmer_license:
        Git = Uri.parse("https://github.com/RomainPiel/Shimmer-android");

        break;
      case R.id.androidBootstrap_license:
        Git = Uri.parse("https://github.com/Bearded-Hen/Android-Bootstrap");

        break;
      case R.id.GoogleMaterialIcon_license:
        Git = Uri.parse("http://google.github.io/material-design-icons/#getting-icons");

        break;

      default:
        break;
    }

    intent.setData(Git);
    startActivity(intent);

  }
}
