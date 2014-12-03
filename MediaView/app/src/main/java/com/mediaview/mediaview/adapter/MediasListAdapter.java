package com.mediaview.mediaview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mediaview.mediaview.R;
import com.mediaview.mediaview.model.Media;

/**
 * Created by iem on 03/12/14.
 */
public class MediasListAdapter extends ArrayAdapter<Media> {
    private final Context context;
    private final Media[] values;

    public MediasListAdapter(Context context, Media[] values) {
        super(context, R.layout.media_listview_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.media_listview_item, parent, false);



        return rowView;
    }
}
