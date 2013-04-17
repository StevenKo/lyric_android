package com.kosbrother.lyric;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.Singer;
import com.taiwan.imageload.GridViewSingersAdapter;

public class CategoryWayResultActivity extends Activity {

	private GridView mGridView;
	private ArrayList<Singer> mSingers;
	private Bundle mBundle;
	private int searchWayId;
	private String searchWayName;
	private GridViewSingersAdapter mdapter;
	private LinearLayout progressLayout;
	private LinearLayout reloadLayout;
	private Button buttonReload;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_singer);
        mGridView = (GridView) findViewById(R.id.grid_singers);
        progressLayout = (LinearLayout) findViewById(R.id.layout_progress);
    	reloadLayout = (LinearLayout) findViewById(R.id.layout_reload);
    	buttonReload = (Button) findViewById(R.id.button_reload);
        
        mBundle = this.getIntent().getExtras();
        searchWayId = mBundle.getInt("SearchWayId");
        searchWayName = mBundle.getString("SearchWayName");
        
        
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
        	
        	mSingers = LyricAPI.getSingers(searchWayId, 1);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            	progressLayout.setVisibility(View.GONE);
                       
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
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}