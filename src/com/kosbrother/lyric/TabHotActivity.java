package com.kosbrother.lyric;


import il.yapps.views.ciclegallery.YappsCircleGallery;

import com.taiwan.imageload.ListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class TabHotActivity extends Activity {

	private ListView mListView;
	
	private Integer[] mImageIds = { 
			R.drawable.icon_list_new,
			R.drawable.icon_list_hot, 
			R.drawable.icon_list_song,
			};
	
	private String[] mStrings ={
			"最新熱門",
			"熱門專輯",
			"熱門歌曲",
	};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hot);
        mListView = (ListView) findViewById(R.id.list_tab_hot);
        ListAdapter mdapter = new ListAdapter(TabHotActivity.this, mStrings, mImageIds);
        YappsCircleGallery yappsGallery = (YappsCircleGallery) findViewById(R.id.gallery);
        mListView.setAdapter(mdapter);  
        
    }
}
