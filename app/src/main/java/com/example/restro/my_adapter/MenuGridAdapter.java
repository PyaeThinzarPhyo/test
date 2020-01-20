package com.example.restro.my_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.restro.R;
import com.example.restro.my_db.my_entity.MyMenuTable;
import com.example.restro.my_interface.GridViewItemClick;

import java.util.ArrayList;
import java.util.List;

public class MenuGridAdapter extends BaseAdapter {
    private Context ctx;
    private int pos;
    private LayoutInflater inflater;
    private TextView ivGallery;
    private List<MyMenuTable> mArrayUri;
    private GridViewItemClick mylistener;
    public MenuGridAdapter(Context ctx, List<MyMenuTable> mylist,GridViewItemClick itemClick) {

        this.ctx = ctx;
        this.mArrayUri = mylist;
        this.mylistener = itemClick;
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
    public View getView(final int position, View convertView, ViewGroup parent) {


        Log.e("pos", String.valueOf(pos));
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.menu_categories_list, parent, false);
        CardView cardView=itemView.findViewById(R.id.card_grid);
        ivGallery=itemView.findViewById(R.id.category_item);
        ivGallery.setText(mArrayUri.get(position).getCategory_name());

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String categoryname=mArrayUri.get(position).getCategory_name();
                if(mylistener!=null){
                    mylistener.gridViewItemClick(categoryname);
                }

            }
        });

        return itemView;
    }
}
