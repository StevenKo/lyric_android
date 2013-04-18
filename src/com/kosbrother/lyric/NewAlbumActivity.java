package com.kosbrother.lyric;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;
import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.Album;
import com.taiwan.imageload.ListAlbumAdapter;

public class NewAlbumActivity extends Activity {

	public  int myPage = 1;
	private Boolean checkLoad = true;
	private LoadMoreListView myList;
	private ArrayList<Album> mAlbums;
	private ArrayList<Album> moreAlbums = new ArrayList<Album>();
	private ListAlbumAdapter mdapter;
	private LinearLayout progressLayout;
	private LinearLayout reloadLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadmore);
        setTitle("最新熱門專輯");
        myList = (LoadMoreListView) findViewById(R.id.news_list);
        progressLayout = (LinearLayout) findViewById(R.id.layout_progress);
    	reloadLayout = (LinearLayout) findViewById(R.id.layout_reload);
       
    	myList.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
            	if(checkLoad){
					myPage = myPage +1;
					new LoadMoreTask().execute();
				}else{
					myList.onLoadMoreComplete();
				}
            }
        });
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
        	
        	mAlbums = LyricAPI.getNewAlbums(myPage);
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
          		myList.setAdapter(mdapter);
          	  }catch(Exception e){
          		 
          	  }
            }else{
            	reloadLayout.setVisibility(View.VISIBLE);
            }

        }
    }
    
    private class LoadMoreTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
        	
        	moreAlbums.clear();
        	moreAlbums = LyricAPI.getNewAlbums(myPage);
        	if(moreAlbums!= null){
	        	for(int i=0; i<moreAlbums.size();i++){	        		
	        		mAlbums.add(moreAlbums.get(i));
	            }
        	}
        	
        	
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            
            if(moreAlbums!= null && moreAlbums.size()!=0){
            	mdapter.notifyDataSetChanged();	                
            }else{
                checkLoad= false;
                Toast.makeText(NewAlbumActivity.this, "no more data", Toast.LENGTH_SHORT).show();            	
            }       
          	myList.onLoadMoreComplete();
          	
          	
        }
    }
}
