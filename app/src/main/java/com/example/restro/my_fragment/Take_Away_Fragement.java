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
import com.example.restro.my_adapter.TakeAwayAdapter;
import com.example.restro.my_db.DatabaseClient;
import com.example.restro.my_db.my_entity.Take_order_Table;

import java.util.List;

public class Take_Away_Fragement extends Fragment {
    private RecyclerView take_away_recyclerview;
    private TakeAwayAdapter takeAwayAdapter;
    public Take_Away_Fragement(){


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.take_away_screen,container,false);
        take_away_recyclerview=view.findViewById( R.id.take_away_recyclerview );
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( this.getActivity() );
        take_away_recyclerview.setLayoutManager( linearLayoutManager );
        getTakeAway();
        return view;
    }

    private void getTakeAway() {
        class GetTasks extends AsyncTask<Void, Void, List<Take_order_Table>> {

            @Override
            protected List<Take_order_Table> doInBackground(Void... voids) {
                List<Take_order_Table> student = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .take_order_tableDao()
                        .getAll();

                return student;
            }

            @Override
            protected void onPostExecute(List<Take_order_Table> student) {
                super.onPostExecute(student);
                takeAwayAdapter=new TakeAwayAdapter(getActivity(),student);
                take_away_recyclerview.setAdapter(takeAwayAdapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
