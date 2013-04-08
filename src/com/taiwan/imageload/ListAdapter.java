package com.taiwan.imageload;

import java.util.ArrayList;

import com.kosbrother.lyric.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    private Integer[] imgIDs;
    
    public ListAdapter(Activity a, String[] d, Integer[] imgId) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
        imgIDs = imgId;
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item_list, null);
	        TextView text=(TextView)vi.findViewById(R.id.text_list);;
	        ImageView image = (ImageView)vi.findViewById(R.id.image_list);
	        text.setText(data[position]);
	        image.setImageResource(imgIDs[position]);
        return vi;
    }
}