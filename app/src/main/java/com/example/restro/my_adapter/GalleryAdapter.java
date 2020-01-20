package com.example.restro.my_adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restro.R;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter {
    private Context ctx;
    private int pos;
    private LayoutInflater inflater;
    private TextView ivGallery;
    private ArrayList<String> mArrayUri;
    public GalleryAdapter(Context ctx, ArrayList<String> mylist) {

        this.ctx = ctx;
        this.mArrayUri = mylist;
    }

    @Override
    public int getCount() {
        return mArrayUri.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayUri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        pos = position;
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.gv_item, parent, false);

        ivGallery = (TextView) itemView.findViewById(R.id.table_name);

        ivGallery.setText(mArrayUri.get(position));

        return itemView;
    }
}
