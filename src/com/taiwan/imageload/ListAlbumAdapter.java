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

import com.kosbrother.lyric.AlbumActivity;
import com.kosbrother.lyric.R;
import com.kosbrother.lyric.entity.Album;

public class ListAlbumAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<Album> data;
    private static LayoutInflater inflater=null;
   
    
    public ListAlbumAdapter(Activity a, ArrayList<Album> d) {
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
//        View vi= new View();      
        View vi = inflater.inflate(R.layout.item_album_list, null);
	    TextView text_name=(TextView)vi.findViewById(R.id.text_album_name);
	    TextView text_time=(TextView)vi.findViewById(R.id.text_album_time);
	    TextView text_description=(TextView)vi.findViewById(R.id.text_album_description);
	        
	    text_name.setText("專輯名稱: "+data.get(position).getName());
	    text_time.setText("發片日期: "+data.get(position).getDate());
	    text_description.setText(data.get(position).getDescription());
	    
	    vi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AlbumActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("AlbumId", data.get(position).getId());
                bundle.putString("AlbumName", data.get(position).getName());
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
	        
        return vi;
    }
}
