package com.example.restro.my_fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.restro.R;
import com.example.restro.activity.MenuListActivity;
import com.example.restro.my_adapter.GalleryAdapter;

import java.util.ArrayList;

public class SecondFloorFragment extends Fragment {
    private ArrayList<String> tableList=new ArrayList<>();
    private GridView gridView;
    private int selectedItem=-1;
    private String mytext,floor;
    private LinearLayout linearLayout;
    private GalleryAdapter galleryAdapter;
    public SecondFloorFragment(){

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.second_floor_screen,container,false);
        gridView=v.findViewById(R.id.gv_second_table_List);
        floor="Second Floor";
        tableList.clear();
        tableList.add("B1");
        tableList.add("B2");
        tableList.add("B3");
        tableList.add("B5");
        tableList.add("B6");
        tableList.add("C7");
        tableList.add("C8");
        tableList.add("C9");
        tableList.add("C10");
        tableList.add("C11");
        tableList.add("C12");

        galleryAdapter = new GalleryAdapter(getActivity(),tableList);
        gridView.setAdapter(galleryAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                linearLayout=view.findViewById(R.id.mybg);
                linearLayout.setBackgroundColor(Color.parseColor("#ffbd69"));
                mytext= (String) parent.getItemAtPosition(position);
                Intent intent=new Intent(getActivity(), MenuListActivity.class);
                intent.putExtra("tablename",mytext);
                intent.putExtra("floor",floor);
                startActivity(intent);
                getActivity().finish();
            }

        });
        return v;
    }
}
