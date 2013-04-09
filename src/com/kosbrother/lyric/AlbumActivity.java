package com.kosbrother.lyric;

import java.util.ArrayList;

import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;
import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.Song;
import com.taiwan.imageload.ListSongAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class AlbumActivity extends Activity {

	private int ID_INTRODUCE = 88888;
	
	private LinearLayout progressLayout;
	private LinearLayout reloadLayout;
	private LoadMoreListView myList;
	private Button buttonReload;
	private ListSongAdapter mdapter;
	private ArrayList<Song> mSongs;
	
	private Bundle mBundle;
	private int albumId;
	private String albumName;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadmore);
        
        
        mBundle = this.getIntent().getExtras();
        albumId = mBundle.getInt("AlbumId");
        albumName = mBundle.getString("AlbumName");
        
        if(albumName != null && !albumName.equals("null") && !albumName.equals("")){
        	setTitle(albumName);
        }
        
        progressLayout = (LinearLayout) findViewById(R.id.layout_progress);
    	reloadLayout = (LinearLayout) findViewById(R.id.layout_reload);
    	buttonReload = (Button) findViewById(R.id.button_reload);
    	myList = (LoadMoreListView) findViewById(R.id.news_list);
        myList.setOnLoadMoreListener(new OnLoadMoreListener() {
			public void onLoadMore() {
				// Do the work to load more items at the end of list
				myList.onLoadMoreComplete();
			}
		});
        
 
      new DownloadChannelsTask().execute();    
        
    }
    
    @SuppressLint("NewApi")
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
    	menu.add(0, ID_INTRODUCE, 0, getResources().getString(R.string.menu_introduce)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
   	
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
        	
        	mSongs = LyricAPI.getAlbumSongs(albumId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressLayout.setVisibility(View.GONE);
                       
            if(mSongs !=null && mSongs.size()!=0){
          	  try{
          		mdapter = new ListSongAdapter(AlbumActivity.this, mSongs);
          		myList.setAdapter(mdapter);
          	  }catch(Exception e){
          		 
          	  }
            }else{
            	reloadLayout.setVisibility(View.VISIBLE);
            }

        }
    }
}
