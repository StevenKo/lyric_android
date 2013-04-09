package com.kosbrother.lyric;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.costum.android.widget.LoadMoreListView;
import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.Album;
import com.taiwan.imageload.ListAlbumAdapter;

public class NewAlbumActivity extends Activity {

	private LoadMoreListView mListView;
	private ArrayList<Album> mAlbums;
	private ListAlbumAdapter mdapter;
	private LinearLayout progressLayout;
	private LinearLayout reloadLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadmore);
        setTitle("最新專輯");
        mListView = (LoadMoreListView) findViewById(R.id.news_list);
        progressLayout = (LinearLayout) findViewById(R.id.layout_progress);
    	reloadLayout = (LinearLayout) findViewById(R.id.layout_reload);
       
        new DownloadChannelsTask().execute();
        
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
        	
        	mAlbums = LyricAPI.getNewAlbums(1);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressLayout.setVisibility(View.GONE);
                       
            if(mAlbums !=null && mAlbums.size()!=0){
          	  try{
          		mdapter = new ListAlbumAdapter(NewAlbumActivity.this, mAlbums);
                mListView.setAdapter(mdapter);
          	  }catch(Exception e){
          		 
          	  }
            }else{
            	reloadLayout.setVisibility(View.VISIBLE);
            }

        }
    }
}
