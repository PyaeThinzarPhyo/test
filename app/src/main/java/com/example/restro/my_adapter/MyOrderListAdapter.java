package com.example.restro.my_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restro.R;
import com.example.restro.myObj.AddonSpinnerObj;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.Add_on_Table;
import com.example.restro.my_db.my_entity.ProductTable;
import com.example.restro.my_interface.MyGetData;
import com.example.restro.my_interface.OrderRemove;

import java.util.ArrayList;
import java.util.List;

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.TasksViewHolder> {
    private Context mCtx;
    private List<String> myList;
    private ArrayList<String> myOrderList;
    private ArrayList<String> addOnListForSpinner=new ArrayList<>(  );
    private List<AddonSpinnerObj> addonSpinnerObjs=new ArrayList<>(  );
    private MyGetData myGetData;

    private int total;
    private OrderRemove mylistener;
    int productId;
    String productName;



    public MyOrderListAdapter(Context mCtx, ArrayList<String> myList,ArrayList<String> myOrderList,int total,OrderRemove orderRemove,MyGetData myGetData) {
        this.mCtx = mCtx;
        this.myList = myList;
        this.myOrderList=myOrderList;
        this.total=total;
        this.mylistener=orderRemove;
        this.myGetData=myGetData;
    }

    @Override
    public MyOrderListAdapter.TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.order_recycler_list, parent, false);
        return new MyOrderListAdapter.TasksViewHolder(view,mylistener,myGetData);
    }

    @Override
    public void onBindViewHolder(MyOrderListAdapter.TasksViewHolder holder, int position) {
        String tech = myList.get(position);

        holder.foodList.setText(tech);
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

    class TasksViewHolder extends RecyclerView.ViewHolder  {

        TextView foodList, prize;
        ImageView btn_decrease;
        OrderRemove orderRemove;
        LinearLayout orderlist;
        Button btn_dialog_save;
        private int myprize;
        String add_productName,add_categoryName;
        MyGetData myGetData;

        public TasksViewHolder(final View itemView, OrderRemove orderRemove, final MyGetData myGetData) {
            super( itemView );
            foodList = itemView.findViewById( R.id.order_recycler_list );
            prize = itemView.findViewById( R.id.order_prize );
            btn_decrease = itemView.findViewById( R.id.btn_decrease );
            orderlist=itemView.findViewById( R.id.order_list_adapter );
            this.myGetData=myGetData;



            orderlist.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productName=myList.get( getAdapterPosition() );
                    myGetData.listClickInAdapter(productName);
                    notifyDataSetChanged();



//                    getData();
//                    getSpinnerList();
//                    if (addOnListForSpinner!=null){
//                        AlertDialog.Builder builder1 = new AlertDialog.Builder( mCtx );
//                        builder1.setTitle( "Add_On" );
//                        final View customLayout = LayoutInflater.from( mCtx ).inflate( R.layout.add_on_custon_dialog_activity, null );
//                        builder1.setView( customLayout );
//                        getSpinnerList();
//                        Spinner tableListSpinner = customLayout.findViewById( R.id.add_on_list_spinner );
//                        Button btnAlertDone = customLayout.findViewById( R.id.btn_dialog_save );
//                        ArrayAdapter<String> adp1 = new ArrayAdapter<String>( mCtx,
//                                android.R.layout.simple_list_item_1 ,addOnListForSpinner);
//                        adp1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
//                        tableListSpinner.setAdapter( adp1 );
//                        btnAlertDone.setOnClickListener( new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        } );
//
//
//                        AlertDialog dialog = builder1.create();
//                        dialog.show();
//                    }

                }
            } );



            btn_decrease.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder( mCtx );
                    builder.setTitle( "Are you sure to delete?" );
                    builder.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            removeAt( getAdapterPosition() );

                        }
                    } );
                    builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    } );

                    AlertDialog ad = builder.create();
                    ad.show();
                }
            } );
            this.orderRemove = orderRemove;


        }



        public void removeAt(int position) {
            myList.remove( position );
            myOrderList.remove( position );
            total = total - myprize;
            notifyItemRemoved( position );
            notifyItemRangeChanged( position, myList.size() );

        }




//        public void getData() {
//            productName=myList.get( getAdapterPosition() );
//                class GetTasks extends AsyncTask<Void, Void, List<ProductTable>> {
//
//                    @Override
//                    protected List<ProductTable> doInBackground(Void... voids) {
//                        List<ProductTable> stores = DatabaseClient
//                                .getInstance(mCtx.getApplicationContext())
//                                .getAppDatabase()
//                                .productDao()
//                                .getByProductName(productName);
//
//                        return stores;
//                    }
//
//                    @Override
//                    protected void onPostExecute(List<ProductTable> mystore) {
//                        super.onPostExecute(mystore);
//                        for (int i=0;i<mystore.size();i++){
//                            productId=mystore.get( i ).getId();
//                        }
//
//                    }
//
//                }
//                GetTasks gt=new GetTasks();
//                gt.execute();
//            }
//
//        }
//    public void getSpinnerList() {
//
//        class GetTasks extends AsyncTask<Void, Void, List<AddonSpinnerObj>> {
//
//            @Override
//            protected List<AddonSpinnerObj> doInBackground(Void... voids) {
//                List<Add_on_Table> stores = DatabaseClient
//                        .getInstance(mCtx.getApplicationContext())
//                        .getAppDatabase()
//                        .add_on_tableDao()
//                        .getAllByProductId(productId);
//                for (int i=0;i<stores.size();i++){
//                    AddonSpinnerObj addonSpinnerObj=new AddonSpinnerObj();
//                    addonSpinnerObj.setAddOnName( stores.get( i ).getAdd_name() );
//                    addonSpinnerObjs.add( addonSpinnerObj );
//                }
//
//
//
//                return addonSpinnerObjs;
//
//            }
//
//            @Override
//            protected void onPostExecute(List<AddonSpinnerObj> mystore) {
//                super.onPostExecute(mystore);
//                addOnListForSpinner.clear();
//                addOnListForSpinner.add( "Please Select" );
//                for (int i=0;i<mystore.size();i++){
//                    addOnListForSpinner.add( mystore.get( i ).getAddOnName() );
//                }
////                if(mystore!=null && mystore.size()>0){
////                   // Log.i("TAG","KKKKKK "+ mystore.get( 0 ).getId());
////
////                        for (int i=0;i<mystore.size();i++) {
////                            addOnListForSpinner.add( mystore.get( i ).getAdd_name() );
////                        }
////                }
//
//
//
//        }
//
//    }
//        GetTasks gt=new GetTasks();
//        gt.execute();
//
//
}
}

