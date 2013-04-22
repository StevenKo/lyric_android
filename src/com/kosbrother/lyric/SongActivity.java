package com.kosbrother.lyric;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.kosbrother.fragment.SongLyricFragment;
import com.kosbrother.fragment.SongVideoFragment;
import com.kosbrother.lyric.db.SQLiteLyric;
import com.kosbrother.lyric.entity.Song;
import com.viewpagerindicator.TabPageIndicator;

public class SongActivity extends FragmentActivity {

    private ViewPager           pager;
    private String[]            CONTENT;
    private Bundle              mBundle;
    private int                 SongId;
    private String              SongName;
    public static Song          theSong;

    private final int           ID_COLLECT = 777777;
    private AlertDialog.Builder aboutUsDialog;

	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);
               
        mBundle = this.getIntent().getExtras();
        SongId = mBundle.getInt("SongId");
        SongName = mBundle.getString("SongName");
        theSong = new Song(SongId, SongName, "null", 0, "");
        
        setTitle(SongName);
        int sdkVersion = android.os.Build.VERSION.SDK_INT; 
        if(sdkVersion > 10){
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setIcon(R.drawable.app_icon);
//        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        setActionBarTitle(SongName);
        
        Resources res = getResources();
        CONTENT = res.getStringArray(R.array.song_tabs);

        FragmentStatePagerAdapter adapter = new GoogleMusicAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        setAboutUsDialog();
    }
    
    
//    @SuppressLint("NewApi")
//	private void setActionBarTitle(String songName) {
//        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflator.inflate(R.layout.item_title, null);
//        TextView titleText = ((TextView) v.findViewById(R.id.title));
//        titleText.setText(songName);
//        titleText.setSelected(true);
//        getActionBar().setCustomView(v);
//    }
    
    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0, ID_COLLECT, 1, getResources().getString(R.string.menu_collect_song)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
	    	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"brotherkos@gmail.com"});
	    	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "聯絡我們 from 歌曲王國");
	    	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
	    	startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	        break;
	    case R.id.action_grade:
	    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.recommend_url)));
			startActivity(browserIntent);
	        break;
        case ID_COLLECT:
            if (!theSong.getLyric().equals("null")) {
                SQLiteLyric db = new SQLiteLyric(SongActivity.this);
                if (db.isSongCollected(theSong.getId())) {
                    db.deleteSong(theSong);
                    Toast.makeText(SongActivity.this, "已刪除此歌曲收藏", Toast.LENGTH_SHORT).show();
                } else {
                    db.insertSong(theSong);
                    Toast.makeText(SongActivity.this, "已加入此歌曲收藏", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SongActivity.this, "讀取中,請稍候", Toast.LENGTH_SHORT).show();
            }
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
            Fragment kk = new Fragment();
            if (position == 0) {
                kk = SongLyricFragment.newInstance(SongId);
            } else if (position == 1) {
                kk = SongVideoFragment.newInstance(SongName);
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
}
