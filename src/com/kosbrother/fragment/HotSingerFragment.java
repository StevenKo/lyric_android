package com.kosbrother.fragment;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.kosbrother.lyric.R;
import com.kosbrother.lyric.api.LyricAPI;
import com.kosbrother.lyric.entity.Singer;
import com.taiwan.imageload.GridViewSingersAdapter;

public class HotSingerFragment extends Fragment {

    // private ListSongAdapter mdapter;
    private ArrayList<Singer>      mSingers;
    private final int              category_id;
    private GridView               mGridView;
    private GridViewSingersAdapter mdapter;
    private LinearLayout           progressLayout;
    private LinearLayout           reloadLayout;
    private Button                 buttonReload;

    public HotSingerFragment(int category_id) {

        // SingerCategoryFragment fragment = new SingerCategoryFragment();
        this.category_id = category_id;
        // return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.layout_category, container, false);
        progressLayout = (LinearLayout) myFragmentView.findViewById(R.id.layout_progress);
        reloadLayout = (LinearLayout) myFragmentView.findViewById(R.id.layout_reload);
        mGridView = (GridView) myFragmentView.findViewById(R.id.grid_search_way);
        buttonReload = (Button) myFragmentView.findViewById(R.id.button_reload);

        if (mdapter != null) {
            // progressLayout.setVisibility(View.GONE);
            mGridView.setAdapter(mdapter);
        } else {
            new DownloadChannelsTask().execute();
        }

        return myFragmentView;
    }

    private class DownloadChannelsTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub

            mSingers = LyricAPI.getCategoryHotSingers(category_id, 1);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressLayout.setVisibility(View.GONE);

            if (mSingers != null && mSingers.size() != 0) {
                try {
                    mdapter = new GridViewSingersAdapter(getActivity(), mSingers);
                    mGridView.setAdapter(mdapter);
                } catch (Exception e) {

                }
            } else {
                reloadLayout.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}