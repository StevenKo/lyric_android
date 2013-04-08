package com.kosbrother.fragment;



import il.yapps.views.ciclegallery.YappsCircleGallery;

import com.kosbrother.lyric.R;
import com.taiwan.imageload.ListAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CollectSongFragment extends Fragment {
	
	private ListView mListView;
	
	private Integer[] mImageIds = { 
			R.drawable.icon_list_new,
			R.drawable.icon_list_hot, 
			R.drawable.icon_list_song,
			};
	
	private String[] mStrings ={
			"最新熱門",
			"熱門專輯",
			"熱門歌曲",
	};
	
    public static CollectSongFragment newInstance() {

    	CollectSongFragment fragment = new CollectSongFragment();
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.layout_hot, container, false);
        mListView = (ListView) myFragmentView.findViewById(R.id.list_tab_hot);
        ListAdapter mdapter = new ListAdapter(getActivity(), mStrings, mImageIds);
        YappsCircleGallery yappsGallery = (YappsCircleGallery) myFragmentView.findViewById(R.id.gallery);
        mListView.setAdapter(mdapter);

        return myFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


}
