package com.kosbrother.lyric;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;
import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.db.SQLiteLyric;
import com.kosbrother.lyric.entity.Album;
import com.kosbrother.lyric.entity.Singer;
import com.kosbrother.lyric.entity.Song;
import com.taiwan.imageload.GridViewSingersAdapter;
import com.taiwan.imageload.ListAlbumAdapter;
import com.taiwan.imageload.ListSongAdapter;

public class SearchActivity extends Activity {

	private LinearLayout progressLayout;
	private LinearLayout reloadLayout;
	private LoadMoreListView myList;
	private Button buttonReload;
	private ListSongAdapter mSongAdapter;
	private ListAlbumAdapter mAlbumAdapter;
	private GridViewSingersAdapter mSingerAdapter;
	private ArrayList<Song> mSongs;
	private ArrayList<Album> mAlbums;
	private ArrayList<Singer> mSingers;
	
	private Bundle mBundle;
	private int searchTypeId;
	private String searchName;
	private Album mAlbum;

	private AlertDialog.Builder aboutUsDialog;
	private ProgressDialog progressDialog   = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mBundle = this.getIntent().getExtras();
        searchTypeId = mBundle.getInt("SearchTypeId");
        searchName = mBundle.getString("SearchName");
        
        if(searchTypeId == 0){       
        	setContentView(R.layout.loadmore);
        	setTitle("歌曲搜索 >"+searchName);
        	progressLayout = (LinearLayout) findViewById(R.id.layout_progress);
        	reloadLayout = (LinearLayout) findViewById(R.id.layout_reload);
        	buttonReload = (Button) findViewById(R.id.button_reload);
        	myList = (LoadMoreListView) findViewById(R.id.news_list);
        }else if(searchTypeId == 1){
        	setContentView(R.layout.loadmore);
        	setTitle("專輯搜索 >"+searchName);
        	progressLayout = (LinearLayout) findViewById(R.id.layout_progress);
        	reloadLayout = (LinearLayout) findViewById(R.id.layout_reload);
        	buttonReload = (Button) findViewById(R.id.button_reload);
        	myList = (LoadMoreListView) findViewById(R.id.news_list);
        }else if(searchTypeId == 2){
        	setContentView(R.layout.layout_collect_grid);
        	setTitle("歌手搜索 >"+searchName);
        	// 還沒寫
        }
        
        
        
        
        
         
      new DownloadChannelsTask().execute();    
        
      setAboutUsDialog();
    }
    
    @SuppressLint("NewApi")
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
    
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

	    int itemId = item.getItemId();
	    switch (itemId) {
	    case android.R.id.home:
	        // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
	        break;
	    case R.id.action_settings:
	    	
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
//	    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.recommend_url)));
//			startActivity(browserIntent);
	        break;
	    }
	    return true;
	}
    
    private class DownloadChannelsTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            
        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
        	if(searchTypeId == 0){       
        		mSongs = LyricAPI.searchSongName(searchName, 1);
            }else if(searchTypeId == 1){
            	mAlbums = LyricAPI.searchAlbum(searchName, 1);
            }else if(searchTypeId == 2){
            	mSingers = LyricAPI.searchSinger(searchName, 1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressLayout.setVisibility(View.GONE);
            
            if(searchTypeId == 0){           
	            if(mSongs !=null && mSongs.size()!=0){
	          	  try{
	          		mSongAdapter = new ListSongAdapter(SearchActivity.this, mSongs);
	          		myList.setAdapter(mSongAdapter);
	          	  }catch(Exception e){
	          		 
	          	  }
	            }else{
	            	reloadLayout.setVisibility(View.VISIBLE);
	            }
            }else if(searchTypeId == 1){
            	if(mAlbums !=null && mAlbums.size()!=0){
  	          	  try{
  	          		mAlbumAdapter = new ListAlbumAdapter(SearchActivity.this, mAlbums);
  	          		myList.setAdapter(mAlbumAdapter);
  	          	  }catch(Exception e){
  	          		 
  	          	  }
  	            }else{
  	            	reloadLayout.setVisibility(View.VISIBLE);
  	            }
            }else if(searchTypeId == 2){
            	if(mSingers !=null && mSingers.size()!=0){
    	          	try{
    	          		mSingerAdapter = new GridViewSingersAdapter(SearchActivity.this, mSingers);
    	          		myList.setAdapter(mSingerAdapter);
    	          	}catch(Exception e){
    	          		 
    	          	}
    	        }else{
    	            reloadLayout.setVisibility(View.VISIBLE);
    	        }
            }

        }
    }
    
    private void setAboutUsDialog() {
        // TODO Auto-generated method stub
        aboutUsDialog = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.about_us_string)).setIcon(R.drawable.play_store_icon)
                .setMessage(getResources().getString(R.string.about_us))
                .setPositiveButton(getResources().getString(R.string.yes_string), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }
	
}
