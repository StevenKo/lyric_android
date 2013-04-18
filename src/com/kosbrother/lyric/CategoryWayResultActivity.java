package com.kosbrother.lyric;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.Singer;
import com.taiwan.imageload.GridViewSingersAdapter;
import com.taiwan.imageload.LoadMoreGridView;

public class CategoryWayResultActivity extends Activity {

	private LoadMoreGridView   mGridView;
	private ArrayList<Singer> mSingers;
	private ArrayList<Singer>	   moreSingers;
	private Bundle mBundle;
	private int searchWayId;
	private String searchWayName;
	private GridViewSingersAdapter mdapter;
	private LinearLayout progressLayout;
	private LinearLayout reloadLayout;
	private Button buttonReload;
	private Boolean   checkLoad  = true;
    private int       myPage     = 1;
    private LinearLayout     loadmoreLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadmore_grid);
        mGridView = (LoadMoreGridView) findViewById(R.id.grid_loadmore);
        progressLayout = (LinearLayout) findViewById(R.id.layout_progress);
    	reloadLayout = (LinearLayout) findViewById(R.id.layout_reload);
    	loadmoreLayout = (LinearLayout) findViewById(R.id.load_more_grid);
    	buttonReload = (Button) findViewById(R.id.button_reload);
        
        mBundle = this.getIntent().getExtras();
        searchWayId = mBundle.getInt("SearchWayId");
        searchWayName = mBundle.getString("SearchWayName");
        
        mGridView.setOnLoadMoreListener(new LoadMoreGridView.OnLoadMoreListener() {
            public void onLoadMore() {
                // Do the work to load more items at the end of list

                if (checkLoad) {
                    myPage = myPage + 1;
                    loadmoreLayout.setVisibility(View.VISIBLE);
                    new LoadMoreTask().execute();
                } else {
                	mGridView.onLoadMoreComplete();
                }
            }
        });
        
        buttonReload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                progressLayout.setVisibility(View.VISIBLE);
                reloadLayout.setVisibility(View.GONE);
                new DownloadChannelsTask().execute();
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
        	
        	mSingers = LyricAPI.getSingers(searchWayId, myPage);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            	progressLayout.setVisibility(View.GONE);
            	loadmoreLayout.setVisibility(View.GONE);
                       
            if(mSingers !=null && mSingers.size()!=0){
          	  try{        		
                mdapter = new GridViewSingersAdapter(CategoryWayResultActivity.this, mSingers);
                mGridView.setAdapter(mdapter);
          	  }catch(Exception e){
          		 
          	  }
            }else{
            	reloadLayout.setVisibility(View.VISIBLE);
            }

        }
    }
    
    private class LoadMoreTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub

        	moreSingers = LyricAPI.getSingers(searchWayId, myPage);
            if (moreSingers != null && moreSingers.size()!=0) {
                for (int i = 0; i < moreSingers.size(); i++) {
                	mSingers.add(moreSingers.get(i));
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            loadmoreLayout.setVisibility(View.GONE);

            if (moreSingers != null && moreSingers.size()!=0) {
            	mdapter.notifyDataSetChanged();
            } else {
                checkLoad = false;
                Toast.makeText(CategoryWayResultActivity.this, "no more data", Toast.LENGTH_SHORT).show();
            }
            mGridView.onLoadMoreComplete();

        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}