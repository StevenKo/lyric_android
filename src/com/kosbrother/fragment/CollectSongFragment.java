package com.kosbrother.fragment;


import java.util.ArrayList;

import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;
import com.kosbrother.lyric.R;
import com.kosbrother.lyric.db.SQLiteLyric;
import com.kosbrother.lyric.entity.Song;
import com.taiwan.imageload.ListSongAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CollectSongFragment extends Fragment {
	
	private LinearLayout progressLayout;
	private LinearLayout noDataLayout;
	private ListView myList;
	private ListSongAdapter mdapter;
	private ArrayList<Song> mSongs;
	
    public static CollectSongFragment newInstance() {
    	CollectSongFragment fragment = new CollectSongFragment();
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.layout_collect, container, false);
        progressLayout = (LinearLayout) myFragmentView.findViewById(R.id.layout_progress);
        noDataLayout = (LinearLayout) myFragmentView.findViewById(R.id.layout_no_data);
        
    	myList = (ListView) myFragmentView.findViewById(R.id.listview_collect);
        
        if (mdapter != null) {
            progressLayout.setVisibility(View.GONE);
            myList.setAdapter(mdapter);
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
            mSongs = db.getAllSongs();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressLayout.setVisibility(View.GONE);
                       
            if(mSongs !=null && mSongs.size()!=0){
          	  try{
          		mdapter = new ListSongAdapter(getActivity(), mSongs);
          		myList.setAdapter(mdapter);
          	  }catch(Exception e){
          		 
          	  }
            }else{
            	noDataLayout.setVisibility(View.VISIBLE);
            }

        }
    }


}
