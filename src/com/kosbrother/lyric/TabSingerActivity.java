package com.kosbrother.lyric;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.taiwan.imageload.GridViewAdapter;

public class TabSingerActivity extends Activity {

	private GridView mGridView;
	
	private String[] mStrings ={
			"最新熱門",
			"熱門專輯",
			"熱門歌曲",
	};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_singer);
        mGridView = (GridView) findViewById(R.id.grid_singers);
        GridViewAdapter mdapter = new GridViewAdapter(TabSingerActivity.this, mStrings);
        mGridView.setAdapter(mdapter);
        
    }
}