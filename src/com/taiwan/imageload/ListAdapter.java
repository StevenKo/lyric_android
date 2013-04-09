package com.taiwan.imageload;


import com.kosbrother.lyric.HotAlbumActivity;
import com.kosbrother.lyric.HotSongActivity;
import com.kosbrother.lyric.NewAlbumActivity;
import com.kosbrother.lyric.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
        vi = inflater.inflate(R.layout.item_list, null);
	    TextView text=(TextView)vi.findViewById(R.id.text_list);;
	    ImageView image = (ImageView)vi.findViewById(R.id.image_list);
	    text.setText(data[position]);
	    image.setImageResource(imgIDs[position]);
        
	    
	    if(position==0){
		    vi.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                // Toast.makeText(activity, "tt", Toast.LENGTH_SHORT).show();
	                Intent intent = new Intent(activity, NewAlbumActivity.class);
//	                Bundle bundle = new Bundle();
//	                bundle.putInt("CategoryId", data.get(position).getId());
//	                bundle.putString("CategoryTitle", data.get(position).getCateName());
//	                intent.putExtras(bundle);
	                activity.startActivity(intent);
	            }
	        });
	    }else if(position==1){
		    vi.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                Intent intent = new Intent(activity, HotAlbumActivity.class);
	                activity.startActivity(intent);
	            }
	        });
	    }else if(position==2){
		    vi.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                Intent intent = new Intent(activity, HotSongActivity.class);
	                activity.startActivity(intent);
	            }
	        });
	    }
	    
	   return vi;
    }
}