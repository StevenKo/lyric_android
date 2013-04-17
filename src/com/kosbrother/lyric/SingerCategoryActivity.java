package com.kosbrother.lyric;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.kosbrother.fragment.HotSongFragment;
import com.kosbrother.fragment.SingerCategoryFragment;
import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.SingerSearchWay;
import com.viewpagerindicator.TabPageIndicator;

public class SingerCategoryActivity extends FragmentActivity{
	
	private ViewPager pager;
	private ArrayList<SingerSearchWay> mCategory;
	private Bundle mBundle;
	private int categoryId;
	private String categoryName;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);
        
        mBundle = this.getIntent().getExtras();
        categoryId = mBundle.getInt("SingerCategoryId");
        categoryName = mBundle.getString("SingerCategoryName");
        
        mCategory = LyricAPI.getSingerCategoryWays(categoryId);;
        
        FragmentStatePagerAdapter adapter = new GoogleMusicAdapter(getSupportFragmentManager());

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        
    }
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
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
}
