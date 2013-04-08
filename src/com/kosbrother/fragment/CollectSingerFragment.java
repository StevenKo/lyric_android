package com.kosbrother.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.kosbrother.lyric.R;
import com.taiwan.imageload.GridViewAdapter;

public class CollectSingerFragment extends Fragment {
	
	private GridView mGridView;
	
	private String[] mStrings ={
			"最新熱門",
			"熱門專輯",
			"熱門歌曲",
	};
	
    public static CollectSingerFragment newInstance() {

    	CollectSingerFragment fragment = new CollectSingerFragment();
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.layout_singer, container, false);
        mGridView = (GridView) myFragmentView.findViewById(R.id.grid_singers);
        GridViewAdapter mdapter = new GridViewAdapter(getActivity(), mStrings);
        mGridView.setAdapter(mdapter);
        
        return myFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


}
