package com.taiwan.imageload;


import com.kosbrother.lyric.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {

    private final Activity         activity;
    private final String[] data;
    private static LayoutInflater  inflater = null;
    public ImageLoader             imageLoader;

    public GridViewAdapter(Activity a, String[] d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext(), 70);

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

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        // if (convertView == null)
        // vi = inflater.inflate(R.layout.item_gridview_novel, null);

//        Display display = activity.getWindowManager().getDefaultDisplay();
//        int width = display.getWidth(); // deprecated
//        int height = display.getHeight(); // deprecated
//        
//        if (width > 480) {
//           
//        } else {
//            vi = inflater.inflate(R.layout.item_gridview_novel_small, null);
//        }
        
        vi = inflater.inflate(R.layout.item_gridview_novel, null);
        TextView mTextView = (TextView) vi.findViewById(R.id.text_grid_item);
        mTextView.setText(data[position]);
        
        vi.setClickable(true);
        vi.setFocusable(true);
        // vi.setBackgroundResource(android.R.drawable.menuitem_background);
        vi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(activity, "tt", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(activity, NovelIntroduceActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("NovelId", data.get(position).getId());
//                bundle.putString("NovelName", data.get(position).getName());
//                bundle.putString("NovelAuthor", data.get(position).getAuthor());
//                bundle.putString("NovelDescription", data.get(position).getDescription());
//                bundle.putString("NovelUpdate", data.get(position).getLastUpdate());
//                bundle.putString("NovelPicUrl", data.get(position).getPic());
//                bundle.putString("NovelArticleNum", data.get(position).getArticleNum());
//                intent.putExtras(bundle);
//                activity.startActivity(intent);

            }

        });

        return vi;
    }
}
