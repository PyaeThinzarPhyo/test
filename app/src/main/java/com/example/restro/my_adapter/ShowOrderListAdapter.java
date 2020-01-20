package com.example.restro.my_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restro.R;
import com.example.restro.my_db.my_entity.OrderTable;

import java.util.ArrayList;
import java.util.List;

public class ShowOrderListAdapter extends RecyclerView.Adapter<ShowOrderListAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<OrderTable> student;
    private  CustomDialogAdapter adapter;

    public ShowOrderListAdapter(Context mCtx, List<OrderTable> student) {
        this.mCtx = mCtx;
        this.student = student;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.order_list_recy, parent, false);
        return new TasksViewHolder(view);
    }



    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
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

        TextView tablename, floor, total,date,time,status,invoice;
        ImageView order_list_remove;
        ArrayList<String> foodList=new ArrayList<>();
        ArrayList<String> priceList=new ArrayList<>();
        String allList;
        RecyclerView recyclerView;
        public TasksViewHolder(View itemView) {
            super(itemView);
            invoice=itemView.findViewById( R.id.tv_dialog_invoice );
            tablename=itemView.findViewById(R.id.tv_dialog_table);
            floor=itemView.findViewById(R.id.tv_dialog_floor);
            total=itemView.findViewById(R.id.tv_total_order);
            recyclerView=itemView.findViewById(R.id.alert_recycler);
            date=itemView.findViewById(R.id.tv_dialog_date);
            time=itemView.findViewById(R.id.tv_dialog_time);
            status=itemView.findViewById(R.id.status);
            order_list_remove=itemView.findViewById( R.id.order_list_remove );

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

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

        }

        void showData(OrderTable stu){
            if(stu!=null){
                tablename.setText("Table :"+stu.getTable_name());
                date.setText("Date :"+stu.getDate());
                floor.setText(stu.getFloor());
                foodList=stu.getOrder_list();
                priceList=stu.getPrice_list();
                allList=stu.getStatus();
                time.setText("Time :"+stu.getTime());
                total.setText("Total :"+stu.getTotal()+"ks");
                invoice.setText("Invoice no:"+"D"+stu.getInvoice());


//                if (foodList!=null && priceList!=null){
                if (tablename!=null && floor!=null && foodList!=null && priceList !=null && invoice!=null) {
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager( mCtx, LinearLayoutManager.VERTICAL, false );
                    recyclerView.setLayoutManager( linearLayoutManager1 );
                    adapter = new CustomDialogAdapter( mCtx, foodList, priceList );
                    recyclerView.setAdapter( adapter );

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

    }




}
