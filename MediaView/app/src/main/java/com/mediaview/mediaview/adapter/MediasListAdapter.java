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

        TextView name = (TextView) rowView.findViewById(R.id.first_line);
        TextView desc = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);

        name.setText(values[position].getName());
        icon.setImageResource(getResourcesIcon(values[position].getType()));
        desc.setText("");

        return rowView;
    }

    private int getResourcesIcon(Media.EType type) {

        int res = 0;

        if(type == Media.EType.Image) {
            res = R.drawable.ic_images;
        }else if(type == Media.EType.Video) {
            res = R.drawable.ic_videos;
        }else if(type == Media.EType.Audio) {
            res = R.drawable.ic_audios;
        }else if(type == Media.EType.Texte) {
            res = R.drawable.ic_texts;
        }

        return res;
    }


}
