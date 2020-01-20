package com.example.restro.my_adapter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restro.R;
import com.example.restro.activity.MenuListActivity;
import com.example.restro.activity.MyHoldOnOrderActivity;
import com.example.restro.my_db.my_entity.OrderTable;
import com.example.restro.my_db.my_entity.Take_order_Table;

import java.util.ArrayList;
import java.util.List;

public class TakeAwayAdapter extends RecyclerView.Adapter<TakeAwayAdapter.TasksViewHolder> {
    private Context mCtx;
    private List<Take_order_Table> student;
    private  CustomDialogAdapter adapter1;

    public TakeAwayAdapter(Context mCtx, List<Take_order_Table> student) {
        this.mCtx = mCtx;
        this.student = student;
    }

    @Override
    public TakeAwayAdapter.TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate( R.layout.take_order_recycler, parent, false);
        return new TakeAwayAdapter.TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        holder.showData(student.get(position));

    }


    @Override
    public int getItemCount() {

        //return student.size();
        if (student.size() > 0) {
            return student.size();
        }
        else return 0;
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  total,date,time,status,invoice;
        ImageView order_list_remove;
        ArrayList<String> foodList=new ArrayList<>();
        ArrayList<String> priceList=new ArrayList<>();
        String allList;
        RecyclerView RecyclerView;
        public TasksViewHolder(final View itemView) {
            super(itemView);
            total=itemView.findViewById(R.id.tv_total_order);
            RecyclerView=itemView.findViewById(R.id.alert_take_recycler);
            date=itemView.findViewById(R.id.tv_dialog_take_date);
            time=itemView.findViewById(R.id.tv_dialog_time);
            status=itemView.findViewById(R.id.status);
            invoice=itemView.findViewById(R.id.tv_take_invoice);
            order_list_remove=itemView.findViewById( R.id.order_list_remove );

            order_list_remove.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                    builder.setTitle("Are you sure to delete?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            removeAt(getAdapterPosition());

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();
                }
            } );
            itemView.setOnClickListener( this );
        }

        void showData(Take_order_Table stu){
            if(stu!=null){
                date.setText("Date :"+stu.getDate());
                foodList=stu.getOrder_list();
                priceList=stu.getPrice_list();
                allList=stu.getStatus();
                time.setText("Time :"+stu.getTime());
                total.setText("Total :"+stu.getTotal()+"ks");
                invoice.setText("Invoice no:"+"T"+stu.getTinvoice());

//                if (foodList!=null && priceList!=null){
                if(invoice!=null) {
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager( mCtx, LinearLayoutManager.VERTICAL, false );
                    RecyclerView.setLayoutManager( linearLayoutManager1 );
                    adapter1 = new CustomDialogAdapter( mCtx, foodList, priceList );
                    RecyclerView.setAdapter( adapter1 );

                }

//                }
            }
        }

        public void removeAt(int position) {
            student.remove(position);
            foodList.remove(position);
            priceList.remove( position );
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, student.size());
        }

        @Override
        public void onClick(final View view) {

        }
    }

}
