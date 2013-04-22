package com.kosbrother.lyric;

import com.kosbrother.fragment.CollectAlbumFragment;
import com.kosbrother.fragment.CollectSingerFragment;
import com.kosbrother.fragment.CollectSongFragment;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class TabCollectActivity extends FragmentActivity{
	
	private String[] CONTENT;
	private ViewPager pager;
	private FragmentStatePagerAdapter adapter;
	private TabPageIndicator indicator;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);
        
        Resources res = getResources();
        CONTENT = res.getStringArray(R.array.tabs);
        
        adapter = new GoogleMusicAdapter(getSupportFragmentManager());

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        
    }
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
    }
	
	@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
		this.getParent().onMenuItemSelected(featureId, item);
        return true;
    }
	
	class GoogleMusicAdapter extends FragmentStatePagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	Fragment kk = new Fragment();        	
        	if(position==0){
            	kk = CollectSongFragment.newInstance(TabCollectActivity.this);
        	}else if(position == 1){
        		kk = CollectAlbumFragment.newInstance(TabCollectActivity.this);
        	}else if(position == 2){        		
        		kk = CollectSingerFragment.newInstance(TabCollectActivity.this);
        	}
            return kk;
        }
       

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length];
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }
	
	public void onResume(){
		super.onResume();
		if(adapter!=null && pager!=null){
			int int_page = pager.getCurrentItem();
			adapter = new GoogleMusicAdapter(getSupportFragmentManager());
			pager.setAdapter(adapter);
			indicator.setViewPager(pager);
			pager.setCurrentItem(int_page);
			indicator.setCurrentItem(int_page);
		}		
	}
	
	@Override
    public void onBackPressed() {
		this.getParent().onBackPressed(); 
    }
	
	
}
