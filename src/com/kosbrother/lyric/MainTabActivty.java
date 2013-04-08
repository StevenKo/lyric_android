package com.kosbrother.lyric;



import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

import com.kosbrother.lyric.R;


public class MainTabActivty extends TabActivity {
    
	private TabHost mTabHost;
	
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbarscreen);
        
        mTabHost = getTabHost();
        
        setupTab(TabHotActivity.class,"最新熱門", R.drawable.icon_tab_hot);
		setupTab(TabSingerActivity.class, "歌手列表", R.drawable.icon_tab_singer);
		setupTab(TabSearchActivity.class,"搜索", R.drawable.icon_tab_search);
		setupTab(TabCollectActivity.class,"收藏", R.drawable.icon_tab_box);
        
        
    }
    
    
    private void setupTab(Class<?> ccls, String name, Integer iconId) {
	    Intent intent = new Intent().setClass(this, ccls);

	    View tab = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
	    ImageView image = (ImageView) tab.findViewById(R.id.icon);
	    TextView text = (TextView) tab.findViewById(R.id.text);
	    if(iconId != null){
	        image.setImageResource(iconId);
	    }
	    text.setText(name);
	    TabSpec spec = mTabHost.newTabSpec(name).setIndicator(tab).setContent(intent);
	    mTabHost.addTab(spec);

	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
        case R.id.action_settings: // setting
        	Toast.makeText(MainTabActivty.this, "SEARCH", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//            startActivity(intent);
            break;
        case R.id.action_about:
        	Toast.makeText(MainTabActivty.this, "SEARCH", Toast.LENGTH_SHORT).show();
//            aboutUsDialog.show();
            break;
        case R.id.action_contact:
        	Toast.makeText(MainTabActivty.this, "SEARCH", Toast.LENGTH_SHORT).show();
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.recommend_url)));
//            startActivity(browserIntent);
            break;
        case R.id.action_grade: // response
            // Toast.makeText(MainActivity.this, "SEARCH", Toast.LENGTH_SHORT).show();
            break;
        }
        return true;
    }

}
