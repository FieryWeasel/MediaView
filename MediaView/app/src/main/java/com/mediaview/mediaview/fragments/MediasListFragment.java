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
import com.mediaview.mediaview.model.Media;
import com.mediaview.mediaview.R;
import com.mediaview.mediaview.adapter.MediasListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IEM on 14/11/2014.
 */
public class MediasListFragment extends Fragment {

    private static Media.EType type;
    private List<Media> medias = new ArrayList<Media>();

    public interface OnElementSelectedListener{
        public abstract void onElementSelected(Media m);
    }
    private OnElementSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.media_fragment_list, container, false);

        final ListView list = (ListView) rootView.findViewById(R.id.media_list_listView);

        MediaDataAccessor mediaAccessor = new MediaDataAccessor(getActivity().getApplicationContext());
        medias = mediaAccessor.getMediaByType(type.toString());

        Media[] mediasTab = new Media[medias.size()];
        list.setAdapter(new MediasListAdapter(getActivity(), medias.toArray(mediasTab)));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                if(listener != null)
                    listener.onElementSelected(medias.get(position));
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (OnElementSelectedListener) activity;
        } catch (Exception e){
            Log.d(this.getClass().getName(), "Error - Activity must implement OnElementSelectedListener");
        }
    }

    public void setMediaType(Media.EType type){
        this.type = type;
    }
}