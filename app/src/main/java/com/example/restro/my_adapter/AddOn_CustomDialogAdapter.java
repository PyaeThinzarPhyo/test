package com.example.restro.my_adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restro.R;

import java.util.ArrayList;


public class AddOn_CustomDialogAdapter extends RecyclerView.Adapter<AddOn_CustomDialogAdapter.TasksViewHolder> {
    private Context mCtx;
    private ArrayList<String> myList;
    private ArrayList<String> myOrderList;
    public AddOn_CustomDialogAdapter(Context mCtx, ArrayList<String> myList,ArrayList<String> myOrderList) {
        this.mCtx = mCtx;
        this.myList = myList;
        this.myOrderList=myOrderList;

    }
    @NonNull
    @Override
    public AddOn_CustomDialogAdapter.TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate( R.layout.add_on_custon_dialog_activity, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AddOn_CustomDialogAdapter.TasksViewHolder holder, int position) {
        holder.add_on_list_spinner.setText(myList.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView add_on_list_spinner;
        public TasksViewHolder(@NonNull View itemView) {
            super( itemView );
            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View view) {

        }
    }
}
