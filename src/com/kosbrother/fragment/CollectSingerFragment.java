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
        
        return myFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


}
