package com.taiwan.imageload;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kosbrother.lyric.R;
import com.kosbrother.lyric.SongActivity;
import com.kosbrother.lyric.entity.Song;

public class ListSongAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<Song> data;
    private static LayoutInflater inflater=null;
   
    
    public ListSongAdapter(Activity a, ArrayList<Song> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
     
        View vi = inflater.inflate(R.layout.item_song_list, null);
	    TextView text_name=(TextView)vi.findViewById(R.id.text_song_name);	        
	    text_name.setText("歌曲名稱: "+data.get(position).getName());
	    
	    vi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SongActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("SongId", data.get(position).getId());
                bundle.putString("SongName", data.get(position).getName());
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
	    
        return vi;
    }
}
