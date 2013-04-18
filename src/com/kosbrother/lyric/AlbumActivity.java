package com.kosbrother.lyric;

import java.util.ArrayList;

import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;
import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.db.SQLiteLyric;
import com.kosbrother.lyric.entity.Album;
import com.kosbrother.lyric.entity.Song;
import com.taiwan.imageload.ListSongAdapter;

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
import android.widget.Toast;


public class AlbumActivity extends Activity {

	private LinearLayout progressLayout;
	private LinearLayout reloadLayout;
	private LoadMoreListView myList;
	private Button buttonReload;
	private ListSongAdapter mdapter;
	private ArrayList<Song> mSongs;
	
	private Bundle mBundle;
	private int albumId;
	private String albumName;
	private Album mAlbum;
	
	private final int ID_INTRODUCE = 666666;
	private final int ID_COLLECT   = 777777;
	private AlertDialog.Builder aboutUsDialog;
	private AlertDialog.Builder introduceDialog;
	private ProgressDialog progressDialog   = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadmore);
        
        
        mBundle = this.getIntent().getExtras();
        albumId = mBundle.getInt("AlbumId");
        albumName = mBundle.getString("AlbumName");
        
        mAlbum = new Album(albumId, albumName, null, "null");
        
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
        
      setIntroduceDialog();
      setAboutUsDialog();
    }
    
    @SuppressLint("NewApi")
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
    	menu.add(0, ID_INTRODUCE, 0, getResources().getString(R.string.menu_introduce)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    	menu.add(0, ID_COLLECT, 1, getResources().getString(R.string.menu_collect)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); 
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
	    case ID_INTRODUCE:
	    	if(mAlbum.getDescription().equals("null")){
	    		new DownloadAlbumTask().execute();
	    	}else{
	    		introduceDialog.setMessage(mAlbum.getDescription());
	    		introduceDialog.show();
	    	}
	        break;
	    case ID_COLLECT:
	    	
		    SQLiteLyric db = new SQLiteLyric(AlbumActivity.this);
		    if(db.isAlbumCollected(mAlbum.getId())){
		    	db.deleteAlbum(mAlbum);
		    	Toast.makeText(AlbumActivity.this, "已刪除此歌曲收藏", Toast.LENGTH_SHORT).show();
		    }else{
		    	db.insertAlbum(mAlbum);
		    	Toast.makeText(AlbumActivity.this, "已加入此歌曲收藏", Toast.LENGTH_SHORT).show();
		    }	    	
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
        	
        	mSongs = LyricAPI.getAlbumSongs(mAlbum.getId());
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
	
	private void setIntroduceDialog(){
		introduceDialog = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.album_introduce_title))
                .setMessage(mAlbum.getDescription())
                .setPositiveButton(getResources().getString(R.string.yes_string), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
	}
	
	private class DownloadAlbumTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(AlbumActivity.this, "資料讀取中...", null);
            progressDialog.setCancelable(true);
        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
        	// get DB Song data
        	Album aAlbum = LyricAPI.getAlbum(mAlbum.getId());
        	if(aAlbum!=null){
        		mAlbum = aAlbum;
        	}
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(!mAlbum.getDescription().equals("null")){
            	introduceDialog.setMessage(mAlbum.getDescription());
	    		introduceDialog.show();
        	}else{
        		Toast.makeText(AlbumActivity.this, "無簡介", Toast.LENGTH_SHORT).show();
        	}

        }
    }
}

