package com.example.restro.my_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restro.R;

import java.util.ArrayList;

public class CustomDialogAdapter  extends RecyclerView.Adapter<CustomDialogAdapter.TasksViewHolder> {
    private Context mCtx;
    private ArrayList<String> myList;
    private ArrayList<String> myOrderList;
    private String btnOrderStatus;

    public CustomDialogAdapter(Context mCtx, ArrayList<String> myList,ArrayList<String> myOrderList) {
        this.mCtx = mCtx;
        this.myList = myList;
        this.myOrderList=myOrderList;
    }

    @Override
    public CustomDialogAdapter.TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.custom_order_list, parent, false);
        return new CustomDialogAdapter.TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomDialogAdapter.TasksViewHolder holder, int position) {
        holder.foodList.setText(myList.get(position));
        holder.prize.setText(myOrderList.get(position));
    }

    @Override
    public int getItemCount() {

        //return student.size();
        if (myList.size() > 0) {
            return myList.size();
        }
        else return 0;
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView foodList,prize;
        Button btnStatus;

        public TasksViewHolder(final View itemView) {
            super(itemView);
            foodList = itemView.findViewById(R.id.custom_order_list);
            prize = itemView.findViewById(R.id.custom_order_prize);
            btnStatus=itemView.findViewById(R.id.btn_order_status);

            itemView.setOnClickListener(this);
            btnStatus.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.btn_order_status:
                        btnStatus.setText( "finish" );
                        btnStatus.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( "#ff94c2" ) ) );
                        break;
//                    btnOrderStatus= String.valueOf( Integer.parseInt(myOrderList.get(getAdapterPosition())) );
//                    Log.e("Finish",String.valueOf(btnOrderStatus));
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {


        }
    }
}
