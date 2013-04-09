package com.kosbrother.lyric;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

import com.kosbrother.fragment.CollectSongFragment;
import com.kosbrother.fragment.HotAlbumFragment;
import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.SingerCategory;
import com.taiwan.imageload.ListAlbumAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class HotAlbumActivity extends FragmentActivity{
	
	private ViewPager pager;
	private ArrayList<SingerCategory> mCategory;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);
        
        mCategory = LyricAPI.getSingerCategories();
        
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
        	Fragment kk = new Fragment();        	
        	kk = HotAlbumFragment.newInstance(mCategory.get(position).getId());
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
