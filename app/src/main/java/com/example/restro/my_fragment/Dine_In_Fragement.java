package com.example.restro.my_fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restro.R;
import com.example.restro.activity.InvoiceActivity;
import com.example.restro.my_adapter.ShowOrderListAdapter;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.OrderTable;

import java.util.ArrayList;
import java.util.List;


public class Dine_In_Fragement extends Fragment {
    private RecyclerView dine_in_recyclerview;
    private ShowOrderListAdapter showOrderListAdapter;
    public  Dine_In_Fragement(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.dine_in_screen,container,false);
        dine_in_recyclerview=view.findViewById( R.id.dine_in_recyclerview );
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( this.getActivity() );
        dine_in_recyclerview.setLayoutManager( linearLayoutManager );
        getTasks();
        return view;
    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<OrderTable>> {

            @Override
            protected List<OrderTable> doInBackground(Void... voids) {
                List<OrderTable> student = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .orderTableDao()
                        .getAll();

                return student;
            }

            @Override
            protected void onPostExecute(List<OrderTable> student) {
                super.onPostExecute(student);
                showOrderListAdapter=new ShowOrderListAdapter(getActivity(),student);
               dine_in_recyclerview.setAdapter(showOrderListAdapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
