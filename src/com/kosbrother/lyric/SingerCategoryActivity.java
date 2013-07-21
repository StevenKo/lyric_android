package com.kosbrother.lyric;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.adwhirl.AdWhirlLayout;
import com.adwhirl.AdWhirlManager;
import com.adwhirl.AdWhirlTargeting;
import com.adwhirl.AdWhirlLayout.AdWhirlInterface;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;
import com.kosbrother.fragment.SingerCategoryFragment;
import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.SingerSearchWay;
import com.viewpagerindicator.TabPageIndicator;

@SuppressLint("NewApi")
public class SingerCategoryActivity extends FragmentActivity implements AdWhirlInterface {

    private ViewPager                  pager;
    private ArrayList<SingerSearchWay> mCategory;
    private Bundle                     mBundle;
    private int                        categoryId;
    private String                     categoryName;
    private AlertDialog.Builder        aboutUsDialog;
    private final String  adWhirlKey = Setting.adwhirlKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);

        mBundle = this.getIntent().getExtras();
        categoryId = mBundle.getInt("SingerCategoryId");
        categoryName = mBundle.getString("SingerCategoryName");

        setTitle(categoryName);
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        if (sdkVersion > 10) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mCategory = LyricAPI.getSingerCategoryWays(categoryId);
        ;

        FragmentStatePagerAdapter adapter = new GoogleMusicAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        indicator.setCurrentItem(1);

        setAboutUsDialog();
        
        try {
            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth(); // deprecated
            int height = display.getHeight(); // deprecated

            if (width > 320) {
                setAdAdwhirl();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
        case android.R.id.home:
            finish();
            break;
        case R.id.action_about:
            aboutUsDialog.show();
            break;
        case R.id.action_contact:
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "brotherkos@gmail.com" });
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "聯絡我們 from 歌曲王國");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            break;
        case R.id.action_grade:
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.recommend_url)));
            startActivity(browserIntent);
            break;
        }
        return true;
    }

    class GoogleMusicAdapter extends FragmentStatePagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            SingerCategoryFragment kk = new SingerCategoryFragment(mCategory.get(position).getId());
            return kk;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategory.get(position).getName();
        }

        @Override
        public int getCount() {
            return mCategory.size();
        }
    }

    private void setAboutUsDialog() {
        // TODO Auto-generated method stub
        aboutUsDialog = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.about_us_string)).setIcon(R.drawable.app_icon_72)
                .setMessage(getResources().getString(R.string.about_us))
                .setPositiveButton(getResources().getString(R.string.yes_string), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }
    
    private void setAdAdwhirl() {
        // TODO Auto-generated method stub
        AdWhirlManager.setConfigExpireTimeout(1000 * 60);
        AdWhirlTargeting.setAge(23);
        AdWhirlTargeting.setGender(AdWhirlTargeting.Gender.MALE);
        AdWhirlTargeting.setKeywords("online games gaming");
        AdWhirlTargeting.setPostalCode("94123");
        AdWhirlTargeting.setTestMode(false);

        AdWhirlLayout adwhirlLayout = new AdWhirlLayout(this, adWhirlKey);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.adonView);

        adwhirlLayout.setAdWhirlInterface(this);

        mainLayout.addView(adwhirlLayout);

        mainLayout.invalidate();
    }

    @Override
    public void adWhirlGeneric() {
        // TODO Auto-generated method stub

    }

    public void rotationHoriztion(int beganDegree, int endDegree, AdView view) {
        final float centerX = 320 / 2.0f;
        final float centerY = 48 / 2.0f;
        final float zDepth = -0.50f * view.getHeight();

        Rotate3dAnimation rotation = new Rotate3dAnimation(beganDegree, endDegree, centerX, centerY, zDepth, true);
        rotation.setDuration(1000);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(rotation);
    }
    
    @Override
    public void onStart() {
      super.onStart();
      EasyTracker.getInstance().activityStart(this);
    }

    @Override
    public void onStop() {
      super.onStop();
      EasyTracker.getInstance().activityStop(this);
    }
}
