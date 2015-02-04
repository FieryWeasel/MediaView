package com.mediaview.mediaview.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mediaview.mediaview.DAO.accessor.MediaDataAccessor;
import com.mediaview.mediaview.R;
import com.mediaview.mediaview.adapter.MediasListAdapter;
import com.mediaview.mediaview.model.Media;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IEM on 14/11/2014.
 */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        return rootView;
    }
}