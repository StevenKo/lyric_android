package com.kosbrother.lyric;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.kosbrother.fragment.SingerAlbumFragment;
import com.kosbrother.fragment.SingerNewsFragment;
import com.kosbrother.fragment.SingerSongFragment;
import com.kosbrother.fragment.SingerVideoFragment;
import com.viewpagerindicator.TabPageIndicator;

public class SingerActivity extends FragmentActivity{
	
	private ViewPager pager;
	private String[] CONTENT;
	private Bundle mBundle;
	private int SingerId;
	private String SingerName;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);
        
        mBundle = this.getIntent().getExtras();
        SingerId = mBundle.getInt("SingerId");
        SingerName = mBundle.getString("SingerName");
        
        Resources res = getResources();
        CONTENT = res.getStringArray(R.array.singer_tabs);

        
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
        	if(position==0){
            	kk = SingerAlbumFragment.newInstance(SingerId);
        	}else if(position == 1){
        		kk = SingerSongFragment.newInstance(SingerId);
        	}else if(position == 2){
        		kk = SingerNewsFragment.newInstance(SingerName);
        	}else if(position == 3){
        		kk = SingerVideoFragment.newInstance(SingerName);
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
}
