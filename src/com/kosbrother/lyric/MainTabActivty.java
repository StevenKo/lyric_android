package com.kosbrother.lyric;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.kosbrother.lyric.db.SQLiteLyric;

public class MainTabActivty extends TabActivity {

    private TabHost mTabHost;
    private AlertDialog.Builder aboutUsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbarscreen);

//        SQLiteLyric db = new SQLiteLyric(this);
//        db.getAllAlbums();
//        db.getAllSingers();
//        db.getAllSongs();

        mTabHost = getTabHost();

        setupTab(TabHotActivity.class, "最新熱門", R.drawable.icon_tab_hot);
        setupTab(TabSingerActivity.class, "歌手列表", R.drawable.icon_tab_singer);
        setupTab(TabSearchActivity.class, "搜索", R.drawable.icon_tab_search);
        setupTab(TabCollectActivity.class, "收藏", R.drawable.icon_tab_box);
        
        setAboutUsDialog();

    }

    private void setupTab(Class<?> ccls, String name, Integer iconId) {
        Intent intent = new Intent().setClass(this, ccls);

        View tab = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        ImageView image = (ImageView) tab.findViewById(R.id.icon);
        TextView text = (TextView) tab.findViewById(R.id.text);
        if (iconId != null) {
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
    public void onBackPressed() {
        if (mTabHost.getCurrentTab() == 0) {
            finish();
        } else {
            mTabHost.setCurrentTab(0);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
//        case R.id.action_settings: // setting
//            Toast.makeText(MainTabActivty.this, "SEARCH", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//            startActivity(intent);
//            break;
        case R.id.action_about:
            aboutUsDialog.show();
            break;
        case R.id.action_contact:
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "brotherkos@gmail.com" });
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "聯絡我們 from 歌曲王國");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            break;
        case R.id.action_grade:
             Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.recommend_url)));
             startActivity(browserIntent);
            break;
        }
        return true;
    }
    
    private void setAboutUsDialog() {
        // TODO Auto-generated method stub
        aboutUsDialog = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.about_us_string)).setIcon(R.drawable.app_icon_72)
                .setMessage(getResources().getString(R.string.about_us))
                .setPositiveButton(getResources().getString(R.string.yes_string), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

}
