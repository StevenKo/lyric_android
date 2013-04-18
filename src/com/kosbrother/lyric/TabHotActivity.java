package com.kosbrother.lyric;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kosbrother.circlegallery.CircleGalleryAdapter;
import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.Video;
import com.taiwan.imageload.ListAdapter;

public class TabHotActivity extends Activity {

    private ListView             mListView;
    private Gallery              mGallery;
    private LinearLayout         linearDownLoading;
    private LinearLayout         linearNetwork;
    private ArrayList<Video>     mHotVideos;
    private CircleGalleryAdapter mCircleAdapter;

    private final Integer[]      mImageIds = { R.drawable.icon_list_new, R.drawable.icon_list_hot, R.drawable.icon_list_song, R.drawable.hot_singer };
    private final String[]       mStrings  = { "最新熱門", "熱門專輯", "熱門歌曲", "熱門歌手" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hot);
        linearDownLoading = (LinearLayout) findViewById(R.id.linear_downloading);
        linearNetwork = (LinearLayout) findViewById(R.id.linear_network);
        mGallery = (Gallery) findViewById(R.id.gallery);

        mListView = (ListView) findViewById(R.id.list_tab_hot);
        ListAdapter mdapter = new ListAdapter(TabHotActivity.this, mStrings, mImageIds);
        mListView.setAdapter(mdapter);

        new DownloadChannelsTask().execute();

    }

    private class DownloadChannelsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub

            mHotVideos = LyricAPI.getHotVideos();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            linearDownLoading.setVisibility(View.GONE);

            if (mHotVideos != null && mHotVideos.size() != 0) {
                try {
                    mCircleAdapter = new CircleGalleryAdapter(TabHotActivity.this, mHotVideos);
                    mGallery.setAdapter(mCircleAdapter);
                    // To select the xSelected one -> 0 is the first.
                    int xSelected = 0;
                    // To make the view go to the middle of our 'endless' array
                    mGallery.setSelection(Integer.MAX_VALUE / 2 + (Integer.MAX_VALUE / 2) % 5 - 1 + xSelected);
                } catch (Exception e) {

                }
            } else {
                linearNetwork.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    
    @Override
    public void onBackPressed() {
    	this.getParent().onBackPressed(); 
    }
}
