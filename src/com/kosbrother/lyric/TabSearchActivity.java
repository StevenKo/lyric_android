package com.kosbrother.lyric;

import android.app.Activity;
import android.os.Bundle;

public class TabSearchActivity extends Activity {
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        
    }
    
    @Override
    public void onBackPressed() {
		this.getParent().onBackPressed(); 
    }
}
