package com.taiwan.imageload;

import java.util.ArrayList;

import com.kosbrother.lyric.entity.YoutubeVideo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;


public class GalleryAdapter extends BaseAdapter {

    private final Activity        activity;
    private final ArrayList<YoutubeVideo> data;
    private static LayoutInflater inflater = null;
    public ImageLoader            imageLoader;
    private final int             width;
    private final int             height;
    private Integer[] tagIDs;

    public GalleryAdapter(Activity a, ArrayList<YoutubeVideo> d, int myWidth, int mheight, Integer[] tagIds) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext(), 100);
        width = myWidth;
        height = mheight;
        tagIDs = tagIds;
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

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
//        if (convertView == null)
//            vi = inflater.inflate(R.layout.item_gallery, null);
//
//        TextView textTitle = (TextView) vi.findViewById(R.id.text_gallery_title);
//        ImageView image = (ImageView) vi.findViewById(R.id.image_gallery);
//        TextView textCategory = (TextView) vi.findViewById(R.id.text_gallery_category);
//
//        textTitle.setText(data.get(position).getTitle());
//        textCategory.setText(data.get(position).getCategoryName());
//        textCategory.setBackgroundResource(tagIDs[position]);
//        
//        if(data.get(position).getPictures().size()>0){
//        	imageLoader.DisplayImage(data.get(position).getPictures().get(0).getUrl(), image);
//        }else{
//        	image.setImageResource(R.drawable.app_icon250);
//        }
        
        return vi;
    }
}