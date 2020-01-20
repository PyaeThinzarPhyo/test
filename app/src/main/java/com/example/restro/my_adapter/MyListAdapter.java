package com.example.restro.my_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restro.R;

import com.example.restro.my_db.my_entity.ProductTable;
import com.example.restro.my_interface.MyitemClick;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.TasksViewHolder> {
    private Context mCtx;
    private List<ProductTable> myList;
    private ArrayList<String> myOrderList;
    private MyitemClick mylistener;
    public MyListAdapter(Context mCtx, List<ProductTable> myList,MyitemClick myitemClick) {
        this.mCtx = mCtx;
        this.myList = myList;
        this.mylistener=myitemClick;
    }

    @Override
    public MyListAdapter.TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.food_recycler_list, parent, false);

        return new MyListAdapter.TasksViewHolder(view,mylistener);
    }

    @Override
    public void onBindViewHolder(MyListAdapter.TasksViewHolder holder, int position) {
        ProductTable tech = myList.get(position);

        holder.foodList.setText(tech.getProduct_name());
        holder.prize.setText(tech.getPrize()+"\t"+"ks");
        holder.imageView.setImageURI(Uri.parse(tech.getImage_uri()));
    }

    @Override
    public int getItemCount() {

        if (myList.size() > 0) {
            return myList.size();
        }
        else return 0;
    }


    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyitemClick myitemClick;
        TextView foodList,prize;
        ImageView imageView;

        public TasksViewHolder(View itemView,MyitemClick myitemClick) {
            super(itemView);
            foodList=itemView.findViewById(R.id.food_recycler_list);
            prize=itemView.findViewById(R.id.food_recycler_prize);
            imageView = (ImageView) itemView.findViewById(R.id.menu_order_img);
            itemView.setOnClickListener(this);
            this.myitemClick=myitemClick;
        }

        @Override
        public void onClick(View v) {
            ProductTable myorder=myList.get(getAdapterPosition());
            String myproduct=myorder.getProduct_name();
            String myprize=myorder.getPrize();
            Log.e("ss",myproduct);

            if(mylistener!=null){
                mylistener.itemClick(myproduct,myprize);
            }

        }

    }

}
