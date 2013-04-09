package com.kosbrother.lyric;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.SingerCategory;
import com.taiwan.imageload.GridViewAdapter;

public class TabSingerActivity extends Activity {

	private GridView mGridView;
	private ArrayList<SingerCategory> mCategory;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_singer);
        
        mCategory = LyricAPI.getSingerCategories();
        
        mGridView = (GridView) findViewById(R.id.grid_singers);
        GridViewAdapter mdapter = new GridViewAdapter(TabSingerActivity.this, mCategory);
        mGridView.setAdapter(mdapter);
        
    }
}