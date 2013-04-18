package com.kosbrother.fragment;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import com.kosbrother.lyric.R;
import com.kosbrother.lyric.db.SQLiteLyric;
import com.kosbrother.lyric.entity.Singer;
import com.taiwan.imageload.GridViewSingersAdapter;

public class CollectSingerFragment extends Fragment {
	
	private LinearLayout progressLayout;
	private LinearLayout noDataLayout;
	private GridView myGrid;
	private GridViewSingersAdapter mdapter;
	private ArrayList<Singer> mSingers;
	
	
    public static CollectSingerFragment newInstance() {

    	CollectSingerFragment fragment = new CollectSingerFragment();
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.layout_collect_grid, container, false);
        progressLayout = (LinearLayout) myFragmentView.findViewById(R.id.layout_progress);
        noDataLayout = (LinearLayout) myFragmentView.findViewById(R.id.layout_no_data);
        
        myGrid = (GridView) myFragmentView.findViewById(R.id.gridview_collect);
        
        if (mdapter != null) {
            progressLayout.setVisibility(View.GONE);
            myGrid.setAdapter(mdapter);
        } else {
            new DownloadChannelsTask().execute();
        }
        
        return myFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

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
        	// get DB Song data
        	SQLiteLyric db = new SQLiteLyric(getActivity());         
        	mSingers = db.getAllSingers();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressLayout.setVisibility(View.GONE);
                       
            if(mSingers !=null && mSingers.size()!=0){
          	  try{
          		mdapter = new GridViewSingersAdapter(getActivity(), mSingers);
          		myGrid.setAdapter(mdapter);
          	  }catch(Exception e){
          		 
          	  }
            }else{
            	noDataLayout.setVisibility(View.VISIBLE);
            }

        }
    }


}
